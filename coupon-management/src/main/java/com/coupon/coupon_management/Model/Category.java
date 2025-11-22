package com.coupon.coupon_management.Model;

public enum Category {
    ELECTRONICS,
    FASHION,
    FOOD,
    GROCERY,
    ACCESSORIES,
    BEAUTY,
    OTHERS;

    public static Category fromString(String s) {
        if (s == null) return null;
        try {
            return Category.valueOf(s.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; // invalid input → return null instead of crashing
        }
    }

}
