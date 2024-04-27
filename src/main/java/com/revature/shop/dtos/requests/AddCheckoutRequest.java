package com.revature.shop.dtos.requests;

public class AddCheckoutRequest {
    String orderId;
    String paymentMethod;

    public AddCheckoutRequest() {
    }

    public AddCheckoutRequest(String orderId, String paymentMethod) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

}
