package com.revature.shop.models;

public class CartProduct {
    private String cartId;
    private String productId;
    private int quantity;
    private float cost;

    public CartProduct() {
    }

    public CartProduct(String cartId, String productId, int quantity, float cost) {
        this.cartId = cartId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

}
