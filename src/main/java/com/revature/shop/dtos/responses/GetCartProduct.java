package com.revature.shop.dtos.responses;

public class GetCartProduct {
    private String name;
    private int quantity;
    private float price;
    private float costInCart;

    public GetCartProduct() {
    }

    public GetCartProduct(String name, int quantity, float price, float costInCart) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.costInCart = costInCart;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getCostInCart() {
        return costInCart;
    }

    public void setCostInCart(float costInCart) {
        this.costInCart = costInCart;
    }

}
