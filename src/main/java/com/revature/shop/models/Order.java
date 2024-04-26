package com.revature.shop.models;

import java.util.UUID;

public class Order {
    private String id;
    private String pending;
    private String paymentMethod;
    private String userId;

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

}
