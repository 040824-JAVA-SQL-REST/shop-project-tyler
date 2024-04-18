package com.revature.shop.models;

import java.util.UUID;

public class User {
    private String id;
    private String email;
    private String password;
    private String fullName;
    private String roleId;

    public User() {

    }

    public User(String email, String password, String fullName) {
        id = UUID.randomUUID().toString();
        this.email = email;
        this.password = password;
        this.fullName = fullName;
    }

    public User(String id, String email, String password, String fullName, String roleId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.roleId = roleId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
