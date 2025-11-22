package com.coupon.coupon_management.Config;

import com.coupon.coupon_management.Model.*;
import com.coupon.coupon_management.Repository.InMemoryStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class config {

    @Bean
    CommandLineRunner seed() {
        return args -> {
            // seed demo user
            User demo = new User("demo-user-1", "hire-me@anshumat.org", "HireMe@2025!",
                    UserTier.GOLD, "IN", 0, 0);
            InMemoryStore.addUser(demo);

            // sample coupon 1: flat
            Coupon c1 = new Coupon(
                    "WELCOME100",
                    "Flat ₹100 off for first order",
                    DiscountType.FLAT,
                    100,
                    null,
                    LocalDateTime.now().minusDays(30),
                    LocalDateTime.now().plusDays(365),
                    1,
                    new Eligibility(
                            List.of(UserTier.NEW, UserTier.BRONZE, UserTier.SILVER),
                            0, 0, true, List.of("IN"), 0, null, null, 0
                    )
            );
            InMemoryStore.addCoupon(c1);

            // sample coupon 2: percent
            Coupon c2 = new Coupon(
                    "FESTIVE10",
                    "10% off up to ₹200",
                    DiscountType.PERCENT,
                    10,
                    200,
                    LocalDateTime.now().minusDays(1),
                    LocalDateTime.now().plusDays(60),
                    null,
                    null
            );
            InMemoryStore.addCoupon(c2);
        };
    }
}
