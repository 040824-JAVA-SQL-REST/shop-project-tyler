package com.revature.shop.models;

import java.util.UUID;

public class Cart {
    private String id;
    private String userId;
    private float totalCost;

    public Cart(String userId, float totalCost) {
        id = UUID.randomUUID().toString();
        this.userId = userId;
        this.totalCost = totalCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

}
