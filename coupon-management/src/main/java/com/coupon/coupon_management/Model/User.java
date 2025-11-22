package com.coupon.coupon_management.Model;

public class User {

    private String userId;
    private String email;
    private String password;   // plain text ONLY for demo project

    private UserTier userTier; // enum instead of string
    private String country;

    private int lifetimeSpend;
    private int ordersPlaced;

    public User() {}

    public User(String userId,
                String email,
                String password,
                UserTier userTier,
                String country,
                int lifetimeSpend,
                int ordersPlaced) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userTier = userTier;
        this.country = country;
        this.lifetimeSpend = lifetimeSpend;
        this.ordersPlaced = ordersPlaced;
    }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public UserTier getUserTier() { return userTier; }
    public void setUserTier(UserTier userTier) { this.userTier = userTier; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public int getLifetimeSpend() { return lifetimeSpend; }
    public void setLifetimeSpend(int lifetimeSpend) {
        this.lifetimeSpend = Math.max(0, lifetimeSpend);
    }

    public int getOrdersPlaced() { return ordersPlaced; }
    public void setOrdersPlaced(int ordersPlaced) {
        this.ordersPlaced = Math.max(0, ordersPlaced);
    }
}
