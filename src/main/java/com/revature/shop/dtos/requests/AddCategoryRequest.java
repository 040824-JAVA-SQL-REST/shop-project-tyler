package com.revature.shop.dtos.requests;

public class AddCategoryRequest {
    private String name;

    public AddCategoryRequest() {
    }

    public AddCategoryRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
