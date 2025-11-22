package com.coupon.coupon_management.Service;

import com.coupon.coupon_management.Exception.ApiException;
import com.coupon.coupon_management.Model.User;
import com.coupon.coupon_management.Model.UserTier;
import com.coupon.coupon_management.Repository.InMemoryStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public User createUser(User user) {
        if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
            throw new ApiException("User ID is required");
        }

        if (InMemoryStore.getUser(user.getUserId()) != null) {
            throw new ApiException("User already exists with ID: " + user.getUserId());
        }

        InMemoryStore.addUser(user);
        return user;
    }

    public User getUser(String userId) {
        User user = InMemoryStore.getUser(userId);
        if (user == null) {
            throw new ApiException("User not found: " + userId);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(InMemoryStore.getAllUsers().values());
    }

    public User updateUserTier(String userId, String newTier) {
        User user = getUser(userId);
        try {
            user.setUserTier(Enum.valueOf(UserTier.class, newTier.trim().toUpperCase()));
            return user;
        } catch (IllegalArgumentException e) {
            throw new ApiException("Invalid user tier: " + newTier);
        }
    }

    public User updateUserDetails(User updated) {
        User user = getUser(updated.getUserId());

        user.setEmail(updated.getEmail());
        user.setCountry(updated.getCountry());
        user.setLifetimeSpend(Math.max(0, updated.getLifetimeSpend()));
        user.setOrdersPlaced(Math.max(0, updated.getOrdersPlaced()));
        user.setUserTier(updated.getUserTier());

        return user;
    }
}

