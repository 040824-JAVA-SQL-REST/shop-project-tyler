package com.revature.shop.dtos.requests;

public class AddProductRequest {
    private String productId;
    private int quantity;

    public AddProductRequest() {
    }

    public AddProductRequest(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productName) {
        this.productId = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
