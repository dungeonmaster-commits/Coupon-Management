package com.coupon.coupon_management.Repository;

import com.coupon.coupon_management.Model.Coupon;
import com.coupon.coupon_management.Model.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class InMemoryStore {

    // coupons  by code
    private static final ConcurrentMap<String, Coupon> coupons = new ConcurrentHashMap<>();

    // users  by userId
    private static final ConcurrentMap<String, User> users = new ConcurrentHashMap<>();

    // usage map: userId -> (couponCode -> usedCount)
    private static final ConcurrentMap<String, ConcurrentMap<String, Integer>> usage = new ConcurrentHashMap<>();

    public static void addCoupon(Coupon c) {
        if (c == null || c.getCode() == null) throw new IllegalArgumentException("coupon or code null");
        coupons.put(c.getCode(), c);
    }

    public static Coupon getCoupon(String code) {
        if (code == null) return null;
        return coupons.get(code);
    }

    public static List<Coupon> getAllCoupons() {
        return new ArrayList<>(coupons.values());
    }

    public static void addUser(User u) {
        if (u == null || u.getUserId() == null) throw new IllegalArgumentException("user or userId null");
        users.put(u.getUserId(), u);
    }

    public static User getUser(String userId) {
        if (userId == null) return null;
        return users.get(userId);
    }

    public static int getUsageCount(String userId, String couponCode) {
        if (userId == null || couponCode == null) return 0;
        ConcurrentMap<String, Integer> m = usage.get(userId);
        if (m == null) return 0;
        return m.getOrDefault(couponCode, 0);
    }

    public static void incrementUsage(String userId, String couponCode) {
        if (userId == null || couponCode == null) return;
        usage.computeIfAbsent(userId, k -> new ConcurrentHashMap<>())
                .merge(couponCode, 1, Integer::sum);
    }

    public static void reset() {
        coupons.clear();
        users.clear();
        usage.clear();
    }

    public static Map<String, User> getAllUsers() {
        return new HashMap<>(users);  // return a copy for safety
    }

}
