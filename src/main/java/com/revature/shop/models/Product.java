package com.revature.shop.models;

import java.util.UUID;

import com.revature.shop.dtos.requests.EditProductRequest;
import com.revature.shop.dtos.requests.NewProductRequest;

public class Product {
    private String id;
    private String name;
    private String description;
    private float price;

    public Product() {
    }

    public Product(String name, String description, float price) {
        this.name = name;
        this.description = description;
        this.price = price;
        id = UUID.randomUUID().toString();
    }

    public Product(String id, String name, String description, float price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Product(NewProductRequest req) {
        this.id = UUID.randomUUID().toString();
        this.name = req.getName();
        this.description = req.getDescription();
        this.price = req.getPrice();
    }

    public Product(EditProductRequest req) {
        this.id = req.getId();
        this.name = req.getName();
        this.description = req.getDescription();
        this.price = req.getPrice();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
