package com.coupon.coupon_management.Model;

public class CartItem {

    private String productId;
    private String category;
    private int unitPrice;
    private int quantity;

    public CartItem() {}

    public CartItem(String productId, String category, int unitPrice, int quantity) {
        this.productId = productId;
        this.category = category;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public String getProductId() { return productId; }
    public void setProductId(String productId) { this.productId = productId; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public int getUnitPrice() { return unitPrice; }
    public void setUnitPrice(int unitPrice) { this.unitPrice = unitPrice; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
