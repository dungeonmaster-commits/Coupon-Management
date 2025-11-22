package com.coupon.coupon_management.Model;

import java.time.LocalDateTime;

public class Coupon {

    private String code;
    private String description;

    private DiscountType discountType;;
    private int discountValue;
    private Integer maxDiscountAmount;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Integer usageLimitPerUser;

    private Eligibility eligibility;

    public Coupon() {}

    public Coupon(String code, String description, DiscountType discountType, int discountValue,
                  Integer maxDiscountAmount, LocalDateTime startDate, LocalDateTime endDate,
                  Integer usageLimitPerUser, Eligibility eligibility) {
        this.code = code;
        this.description = description;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.maxDiscountAmount = maxDiscountAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageLimitPerUser = usageLimitPerUser;
        this.eligibility = eligibility;
    }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public DiscountType getDiscountType() { return discountType; }
    public void setDiscountType(DiscountType discountType) { this.discountType = discountType; }

    public int getDiscountValue() { return discountValue; }
    public void setDiscountValue(int discountValue) { this.discountValue = discountValue; }

    public Integer getMaxDiscountAmount() { return maxDiscountAmount; }
    public void setMaxDiscountAmount(Integer maxDiscountAmount) { this.maxDiscountAmount = maxDiscountAmount; }

    public LocalDateTime getStartDate() { return startDate; }
    public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }

    public LocalDateTime getEndDate() { return endDate; }
    public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }

    public Integer getUsageLimitPerUser() { return usageLimitPerUser; }
    public void setUsageLimitPerUser(Integer usageLimitPerUser) { this.usageLimitPerUser = usageLimitPerUser; }

    public Eligibility getEligibility() { return eligibility; }
    public void setEligibility(Eligibility eligibility) { this.eligibility = eligibility; }
}
