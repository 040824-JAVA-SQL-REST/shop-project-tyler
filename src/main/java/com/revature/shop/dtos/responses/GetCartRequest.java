package com.revature.shop.dtos.responses;

import com.revature.shop.models.Cart;

public class GetCartRequest {
    private Cart cart;

    public GetCartRequest() {
    }

    public GetCartRequest(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
