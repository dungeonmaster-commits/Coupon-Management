package com.coupon.coupon_management.Controller;

import com.coupon.coupon_management.DTO.BestCouponRequest;
import com.coupon.coupon_management.DTO.BestCouponResponse;
import com.coupon.coupon_management.Service.BestCouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/coupons")
public class CouponEvaluationController {

    private final BestCouponService bestCouponService;

    public CouponEvaluationController(BestCouponService bestCouponService) {
        this.bestCouponService = bestCouponService;
    }

    // Find best coupon for cart
    @PostMapping("/best")
    public ResponseEntity<?> getBestCoupon(@RequestBody BestCouponRequest request) {
        Optional<BestCouponService.Result> opt = bestCouponService.findBestCoupon(request);

        if (opt.isEmpty()) {
            return ResponseEntity.ok("No eligible coupons");
        }

        BestCouponService.Result r = opt.get();

        BestCouponResponse resp = new BestCouponResponse(
                r.coupon.getCode(),
                r.coupon.getDescription(),
                r.discountAmount
        );

        return ResponseEntity.ok(resp);
    }

    // Apply coupon
    @PostMapping("/apply/{couponCode}")
    public ResponseEntity<?> applyCoupon(
            @PathVariable String couponCode,
            @RequestParam String userId,
            @RequestBody BestCouponRequest request // we reuse request for cartItems
    ) {
        int discount = bestCouponService.applyCoupon(
                userId,
                couponCode,
                request.getCartItems()
        );
        return ResponseEntity.ok("Coupon applied, discount = " + discount);
    }
}
