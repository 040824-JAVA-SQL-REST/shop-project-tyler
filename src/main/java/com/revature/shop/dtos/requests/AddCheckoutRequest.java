package com.revature.shop.dtos.requests;

public class AddCheckoutRequest {
    String orderId;
    String payment_method,

    public AddCheckoutRequest() {
    }

    public AddCheckoutRequest(String orderId, String payment_method) {
        this.orderId = orderId;
        this.payment_method = payment_method;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

}
