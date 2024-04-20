package com.revature.shop.models;

public class Checkout {
    private String id;
    private String user_id;
    private boolean success;
    private String payment_method;
    private float totalCost;

    public Checkout() {
    }

    public Checkout(String user_id) {
        this.user_id = user_id;
    }

    public Checkout(String id, String user_id, boolean success, String payment_method, float totalCost) {
        this.id = id;
        this.user_id = user_id;
        this.success = success;
        this.payment_method = payment_method;
        this.totalCost = totalCost;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(float totalCost) {
        this.totalCost = totalCost;
    }

}
