package com.coupon.coupon_management.Model;

import java.util.List;

public class Eligibility {

    // User-based rules
    private List<UserTier> allowedUserTiers;   // SILVER, GOLD etc.
    private int minLifetimeSpend = 0;          // default = no requirement
    private int minOrdersPlaced = 0;           // default = no requirement
    private boolean firstOrderOnly = false;    // default = false
    private List<String> allowedCountries;     // keep as String unless needed as enum

    // Cart-based rules
    private int minCartValue = 0;              // default = no minimum
    private List<Category> applicableCategories; // enum based
    private List<Category> excludedCategories;   // enum based

    private int minItemsCount = 0;             // default = no requirement

    public Eligibility() {}

    public Eligibility(List<UserTier> allowedUserTiers,
                       int minLifetimeSpend,
                       int minOrdersPlaced,
                       boolean firstOrderOnly,
                       List<String> allowedCountries,
                       int minCartValue,
                       List<Category> applicableCategories,
                       List<Category> excludedCategories,
                       int minItemsCount) {

        this.allowedUserTiers = allowedUserTiers;
        this.minLifetimeSpend = minLifetimeSpend;
        this.minOrdersPlaced = minOrdersPlaced;
        this.firstOrderOnly = firstOrderOnly;
        this.allowedCountries = allowedCountries;
        this.minCartValue = minCartValue;
        this.applicableCategories = applicableCategories;
        this.excludedCategories = excludedCategories;
        this.minItemsCount = minItemsCount;
    }

    public List<UserTier> getAllowedUserTiers() {
        return allowedUserTiers;
    }

    public void setAllowedUserTiers(List<UserTier> allowedUserTiers) {
        this.allowedUserTiers = allowedUserTiers;
    }

    public int getMinLifetimeSpend() {
        return minLifetimeSpend;
    }

    public void setMinLifetimeSpend(int minLifetimeSpend) {
        this.minLifetimeSpend = minLifetimeSpend;
    }

    public int getMinOrdersPlaced() {
        return minOrdersPlaced;
    }

    public void setMinOrdersPlaced(int minOrdersPlaced) {
        this.minOrdersPlaced = minOrdersPlaced;
    }

    public boolean isFirstOrderOnly() {
        return firstOrderOnly;
    }

    public void setFirstOrderOnly(boolean firstOrderOnly) {
        this.firstOrderOnly = firstOrderOnly;
    }

    public List<String> getAllowedCountries() {
        return allowedCountries;
    }

    public void setAllowedCountries(List<String> allowedCountries) {
        this.allowedCountries = allowedCountries;
    }

    public int getMinCartValue() {
        return minCartValue;
    }

    public void setMinCartValue(int minCartValue) {
        this.minCartValue = minCartValue;
    }

    public List<Category> getApplicableCategories() {
        return applicableCategories;
    }

    public void setApplicableCategories(List<Category> applicableCategories) {
        this.applicableCategories = applicableCategories;
    }

    public List<Category> getExcludedCategories() {
        return excludedCategories;
    }

    public void setExcludedCategories(List<Category> excludedCategories) {
        this.excludedCategories = excludedCategories;
    }

    public int getMinItemsCount() {
        return minItemsCount;
    }

    public void setMinItemsCount(int minItemsCount) {
        this.minItemsCount = minItemsCount;
    }
}
