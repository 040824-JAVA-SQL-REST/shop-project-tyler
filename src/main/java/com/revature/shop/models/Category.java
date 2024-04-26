package com.revature.shop.models;

import java.util.UUID;

import com.revature.shop.dtos.requests.AddCategoryRequest;

public class Category {
    private String id;
    private String name;

    public Category() {
    }

    public Category(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Category(AddCategoryRequest req) {
        this.id = UUID.randomUUID().toString();
        this.name = req.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
