package com.coupon.coupon_management.DTO;

import com.coupon.coupon_management.Model.CartItem;

import java.util.List;

public class BestCouponRequest {

    private String userId;
    private List<CartItem> cartItems;

    public BestCouponRequest() {}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
