package com.revature.shop.models;

import java.util.UUID;

public class Order {
    private String id;
    private String pending;
    private String paymentMethod;
    private String userId;
    private String created_time;

    public Order() {
    }

    public Order(String userId) {
        this.id = UUID.randomUUID().toString();
        this.userId = userId;
        this.paymentMethod = "";
        this.pending = "";
    }

    public Order(String pending, String paymentMethod, String userId) {
        id = UUID.randomUUID().toString();
        this.pending = pending;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
    }

    public Order(String id, String pending, String paymentMethod, String userId, String created_time) {
        this.id = id;
        this.pending = pending;
        this.paymentMethod = paymentMethod;
        this.userId = userId;
        this.created_time = created_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPending() {
        return pending;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String payment_method) {
        this.paymentMethod = payment_method;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((pending == null) ? 0 : pending.hashCode());
        result = prime * result + ((paymentMethod == null) ? 0 : paymentMethod.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        result = prime * result + ((created_time == null) ? 0 : created_time.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Order other = (Order) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (pending == null) {
            if (other.pending != null)
                return false;
        } else if (!pending.equals(other.pending))
            return false;
        if (paymentMethod == null) {
            if (other.paymentMethod != null)
                return false;
        } else if (!paymentMethod.equals(other.paymentMethod))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        if (created_time == null) {
            if (other.created_time != null)
                return false;
        } else if (!created_time.equals(other.created_time))
            return false;
        return true;
    }

}
