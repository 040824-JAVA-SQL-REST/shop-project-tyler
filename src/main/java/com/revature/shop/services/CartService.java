package com.revature.shop.services;

import java.util.Optional;

import com.revature.shop.daos.CartDao;
import com.revature.shop.models.Cart;

public class CartService {
    private CartDao cartDao;

    public CartService() {
    }

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public String getCartIdByUserId(String userId) {
        Optional<Cart> optCart = cartDao.findAll().stream()
                .filter(c -> ((Cart) c).getUserId().equals(userId))
                .findFirst();
        if (optCart.isPresent()) {
            Cart cart = optCart.get();
            return cart.getId();
        } else {
            // need to create a cart for this user
            Cart newCart = new Cart(userId);
            cartDao.save(newCart);
            return newCart.getId();
        }
    }

    public Cart getCartByUserId(String userId) {
        Optional<Cart> optCart = cartDao.findAll().stream()
                .filter(c -> ((Cart) c).getUserId().equals(userId))
                .findFirst();
        if (optCart.isPresent()) {
            Cart cart = optCart.get();
            return cart;
        } else {
            // need to create a cart for this user
            Cart newCart = new Cart(userId);
            return cartDao.save(newCart);
        }
    }

}
