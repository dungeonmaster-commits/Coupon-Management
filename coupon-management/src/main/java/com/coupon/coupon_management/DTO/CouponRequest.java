package com.coupon.coupon_management.DTO;

import com.coupon.coupon_management.Model.DiscountType;
import com.coupon.coupon_management.Model.Eligibility;

import java.time.LocalDateTime;

public class CouponRequest {

    private String code;
    private String description;

    private DiscountType discountType;   // enum instead of String
    private int discountValue;

    private Integer maxDiscountAmount;   // nullable only for FLAT coupons

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private int usageLimitPerUser = 1;   // default to 1

    private Eligibility eligibility;

    public CouponRequest() {}


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

    public int getUsageLimitPerUser() { return usageLimitPerUser; }
    public void setUsageLimitPerUser(int usageLimitPerUser) { this.usageLimitPerUser = usageLimitPerUser; }

    public Eligibility getEligibility() { return eligibility; }
    public void setEligibility(Eligibility eligibility) { this.eligibility = eligibility; }
}
