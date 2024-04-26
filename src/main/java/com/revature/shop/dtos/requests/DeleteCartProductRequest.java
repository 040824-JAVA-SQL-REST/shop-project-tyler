package com.revature.shop.dtos.requests;

public class DeleteCartProductRequest {
    private String productId;

    public DeleteCartProductRequest() {
    }

    public DeleteCartProductRequest(String productId) {
        this.productId = productId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

}
