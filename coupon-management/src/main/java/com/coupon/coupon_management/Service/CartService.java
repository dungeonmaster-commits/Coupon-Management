package com.coupon.coupon_management.Service;

import com.coupon.coupon_management.Exception.ApiException;
import com.coupon.coupon_management.Model.CartItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CartService {

    public int calculateCartTotal(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new ApiException("Cart cannot be empty");
        }

        return cartItems.stream()
                .filter(Objects::nonNull)
                .mapToInt(i -> Math.max(0, i.getUnitPrice()) * Math.max(1, i.getQuantity()))
                .sum();
    }

    public int countItems(List<CartItem> cartItems) {
        if (cartItems == null) return 0;
        return cartItems.stream()
                .filter(Objects::nonNull)
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}
