package com.revature.shop.models;

import java.util.UUID;

public class Role {
    private String id;
    private String name;

    public Role() {

    }

    public Role(String name) {
        id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
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
