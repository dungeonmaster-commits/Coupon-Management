package com.coupon.coupon_management.Service;

import com.coupon.coupon_management.DTO.CouponRequest;
import com.coupon.coupon_management.Model.Coupon;
import com.coupon.coupon_management.Model.DiscountType;
import com.coupon.coupon_management.Repository.InMemoryStore;
import com.coupon.coupon_management.Exception.ApiException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CouponService {

    public Coupon createCoupon(CouponRequest request) {
        // basic validation
        if (request == null) throw new ApiException("Request cannot be null");
        if (request.getCode() == null || request.getCode().isBlank()) throw new ApiException("Missing coupon code");
        if (request.getDiscountType() == null) throw new ApiException("Missing discountType");
        if (request.getDiscountValue() <= 0) throw new ApiException("discountValue must be > 0");

        // check start/end if present
        LocalDateTime s = request.getStartDate();
        LocalDateTime e = request.getEndDate();
        if (s != null && e != null && s.isAfter(e)) throw new ApiException("startDate must be before endDate");

        Integer usageLimit = request.getUsageLimitPerUser();
        if (usageLimit != null && usageLimit < 1) throw new ApiException("usageLimitPerUser must be at least 1 or null");

        // duplicate handling: reject duplicate codes (documented behaviour)
        if (InMemoryStore.getCoupon(request.getCode()) != null) {
            throw new ApiException("Coupon with code already exists: " + request.getCode());
        }

        // build coupon
        Coupon c = new Coupon();
        c.setCode(request.getCode());
        c.setDescription(request.getDescription());
        c.setDiscountType(DiscountType.valueOf(request.getDiscountType().name()));
        c.setDiscountValue(request.getDiscountValue());
        c.setMaxDiscountAmount(request.getMaxDiscountAmount());
        c.setStartDate(request.getStartDate());
        c.setEndDate(request.getEndDate());
        c.setUsageLimitPerUser(request.getUsageLimitPerUser());
        c.setEligibility(request.getEligibility());

        InMemoryStore.addCoupon(c);
        return c;
    }

    public List<Coupon> getAllCoupons() {
        return InMemoryStore.getAllCoupons();
    }

    public Coupon getCoupon(String code) {
        return InMemoryStore.getCoupon(code);
    }
}
