package com.coupon.coupon_management.Service;

import com.coupon.coupon_management.DTO.BestCouponRequest;
import com.coupon.coupon_management.Model.CartItem;
import com.coupon.coupon_management.Model.Coupon;
import com.coupon.coupon_management.Model.Eligibility;
import com.coupon.coupon_management.Model.User;
import com.coupon.coupon_management.Repository.InMemoryStore;
import com.coupon.coupon_management.Exception.ApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BestCouponService {

    private final CouponService couponService;
    private final UserService userService;
    private final CartService cartService;

    public BestCouponService(CouponService couponService,
                             UserService userService,
                             CartService cartService) {
        this.couponService = couponService;
        this.userService = userService;
        this.cartService = cartService;
    }

    public static class Result {
        public final Coupon coupon;
        public final int discountAmount;
        public Result(Coupon coupon, int discountAmount) {
            this.coupon = coupon;
            this.discountAmount = discountAmount;
        }
    }

    /**
     * Find best eligible coupon per assignment rules:
     * 1) highest discount amount
     * 2) if tie -> earliest endDate
     * 3) if tie -> lexicographically smaller code
     */
    public Optional<Result> findBestCoupon(BestCouponRequest request) {
        Objects.requireNonNull(request, "request cannot be null");

        User user = userService.getUser(request.getUserId());
        if (user == null) throw new ApiException("User not found: " + request.getUserId());

        List<CartItem> cartItems = Optional.ofNullable(request.getCartItems()).orElse(Collections.emptyList());
        int cartValue = cartService.calculateCartTotal(cartItems);
        int itemsCount = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

        List<Coupon> all = couponService.getAllCoupons();

        List<Result> eligibleResults = all.stream()
                .filter(c -> isActive(c))
                .filter(c -> !exceededUsage(request.getUserId(), c))
                .map(c -> {
                    Eligibility e = c.getEligibility();
                    boolean eligible = isEligible(user, cartItems, cartValue, itemsCount, e);
                    if (!eligible) return null;
                    int discount = computeDiscount(c, cartValue);
                    if (discount <= 0) return null;
                    return new Result(c, discount);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Comparator<Result> comparator = Comparator
                .comparingInt((Result r) -> r.discountAmount).reversed() // highest discount first
                .thenComparing((r1, r2) -> {
                    LocalDateTime e1 = r1.coupon.getEndDate();
                    LocalDateTime e2 = r2.coupon.getEndDate();
                    // null-safety: null -> treat as "far future" (so non-null earlier wins)
                    if (e1 == null && e2 == null) return 0;
                    if (e1 == null) return 1;
                    if (e2 == null) return -1;
                    return e1.compareTo(e2);
                })
                .thenComparing(r -> r.coupon.getCode());

        return eligibleResults.stream().sorted(comparator).findFirst();
    }

    /**
     * Apply coupon (increment usage) — returns discount amount. Will throw ApiException if not applicable.
     */
    public int applyCoupon(String userId, String couponCode, List<CartItem> cartItems) {
        Coupon coupon = couponService.getCoupon(couponCode);
        if (coupon == null) throw new ApiException("Coupon not found: " + couponCode);
        User user = userService.getUser(userId);
        if (user == null) throw new ApiException("User not found: " + userId);

        if (!isActive(coupon)) throw new ApiException("Coupon not active: " + couponCode);
        if (exceededUsage(userId, coupon)) throw new ApiException("Usage limit exceeded for coupon: " + couponCode);

        int cartValue = cartService.calculateCartTotal(cartItems);
        int itemsCount = cartItems.stream().mapToInt(CartItem::getQuantity).sum();

        if (!isEligible(user, cartItems, cartValue, itemsCount, coupon.getEligibility())) {
            throw new ApiException("Coupon not eligible for this user/cart");
        }

        int discount = computeDiscount(coupon, cartValue);
        // increment usage in store
        InMemoryStore.incrementUsage(userId, coupon.getCode());
        return discount;
    }

    private boolean isActive(Coupon c) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime s = c.getStartDate();
        LocalDateTime e = c.getEndDate();
        // Treat null start as available immediately, null end as no expiry.
        boolean afterStart = (s == null) || !now.isBefore(s);
        boolean beforeEnd = (e == null) || !now.isAfter(e);
        return afterStart && beforeEnd;
    }

    private boolean exceededUsage(String userId, Coupon coupon) {
        Integer limit = coupon.getUsageLimitPerUser();
        if (limit == null || limit <= 0) return false; // unlimited
        int used = InMemoryStore.getUsageCount(userId, coupon.getCode());
        return used >= limit;
    }

    private int computeDiscount(Coupon c, int cartValue) {
        if (c.getDiscountType() == null) return 0;
        if (c.getDiscountType().name().equalsIgnoreCase("FLAT")) {
            return Math.min(Math.max(0, c.getDiscountValue()), cartValue);
        } else { // PERCENT
            long raw = Math.round((long) cartValue * c.getDiscountValue() / 100.0);
            if (c.getMaxDiscountAmount() != null) {
                return (int) Math.min(raw, c.getMaxDiscountAmount());
            } else {
                return (int) Math.min(raw, cartValue);
            }
        }
    }

    private boolean isEligible(User user, List<CartItem> cartItems, int cartValue, int itemsCount, Eligibility e) {
        if (e == null) return true;

        // user-tier
        if (e.getAllowedUserTiers() != null && !e.getAllowedUserTiers().isEmpty()) {
            if (user.getUserTier() == null || !e.getAllowedUserTiers().contains(user.getUserTier())) {
                return false;
            }
        }

        // min lifetime spend
        if (user.getLifetimeSpend() < e.getMinLifetimeSpend()) return false;

        // min orders
        if (user.getOrdersPlaced() < e.getMinOrdersPlaced()) return false;

        // first order only
        if (e.isFirstOrderOnly() && user.getOrdersPlaced() > 0) return false;

        // country
        if (e.getAllowedCountries() != null && !e.getAllowedCountries().isEmpty()) {
            if (user.getCountry() == null || !e.getAllowedCountries().contains(user.getCountry())) return false;
        }

        // min cart value
        if (cartValue < e.getMinCartValue()) return false;

        // min items count
        if (itemsCount < e.getMinItemsCount()) return false;

        // applicable categories: at least one item in cart must belong
        if (e.getApplicableCategories() != null && !e.getApplicableCategories().isEmpty()) {
            boolean any = cartItems.stream().anyMatch(ci ->
                    ci.getCategory() != null &&
                            e.getApplicableCategories().stream()
                                    .anyMatch(cat -> cat.name().equalsIgnoreCase(ci.getCategory())));
            if (!any) return false;
        }

        // excluded categories: if any item belongs to excluded -> not eligible
        if (e.getExcludedCategories() != null && !e.getExcludedCategories().isEmpty()) {
            boolean any = cartItems.stream().anyMatch(ci ->
                    ci.getCategory() != null &&
                            e.getExcludedCategories().stream()
                                    .anyMatch(cat -> cat.name().equalsIgnoreCase(ci.getCategory())));
            if (any) return false;
        }

        return true;
    }
}
