package com.revature.shop.models;

import java.util.UUID;

public class Order {
    private String id;
    private String pending;
    private String paymentMethod;
    private String userId;
    private String created_time;

    public Order() {
    }

    public Order(String userId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.paymentMethod = "";
        this.pending = "";
    }

    public Order(String pending, String paymentMethod, String userId) {
        id = UUID.randomUUID().toString();
        this.pending = pending;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
    }

    public Order(String id, String pending, String paymentMethod, String userId, String created_time) {
        this.id = id;
        this.pending = pending;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.created_time = created_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String payment_method) {
        this.paymentMethod = payment_method;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

}
