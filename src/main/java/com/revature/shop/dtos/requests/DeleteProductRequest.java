package com.revature.shop.dtos.requests;

public class DeleteProductRequest {
    private String id;

    public DeleteProductRequest() {
    }

    public DeleteProductRequest(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
