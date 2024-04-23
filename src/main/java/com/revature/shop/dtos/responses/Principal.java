package com.revature.shop.dtos.responses;

import com.revature.shop.models.Role;
import com.revature.shop.models.User;

public class Principal {
    private String id;
    private String email;
    private Role role;

    public Principal() {

    }

    public Principal(User user, Role role) {
        id = user.getId();
        email = user.getEmail();
        this.role = role;
    }

    public Principal(String id, String email, Role role) {
        this.id = id;
        this.email = email;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
