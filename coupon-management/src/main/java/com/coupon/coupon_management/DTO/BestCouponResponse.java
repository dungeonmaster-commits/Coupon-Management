package com.coupon.coupon_management.DTO;

public class BestCouponResponse {

    private String couponCode;
    private String description;
    private int discountAmount;

    public BestCouponResponse() {}

    public BestCouponResponse(String couponCode, String description, int discountAmount) {
        this.couponCode = couponCode;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }
}
