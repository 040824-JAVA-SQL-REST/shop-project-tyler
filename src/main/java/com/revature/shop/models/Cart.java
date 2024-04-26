package com.revature.shop.models;

import java.util.UUID;

public class Cart {
    private String id;
    private String userId;

    public Cart() {
    }

    public Cart(String userId) {
        id = UUID.randomUUID().toString();
        this.userId = userId;
    }

    public Cart(String id, String userId) {
        this.id = id;
        this.userId = userId;
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
}
