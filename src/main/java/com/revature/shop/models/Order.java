package com.revature.shop.models;

import java.util.UUID;

public class Order {
    private String id;
    private String pending;
    private String payment_method;
    private String user_id;

    public Order(String pending, String payment_method, String user_id) {
        id = UUID.randomUUID().toString();
        this.pending = pending;
        this.payment_method = payment_method;
        this.user_id = user_id;
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

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
