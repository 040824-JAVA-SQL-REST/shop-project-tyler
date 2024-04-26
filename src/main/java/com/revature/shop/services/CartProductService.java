package com.revature.shop.services;

import java.util.List;
import java.util.Optional;

import com.revature.shop.daos.CartProductDao;
import com.revature.shop.models.CartProduct;

public class CartProductService {
    private final CartProductDao cartProductDao;

    public CartProductService(CartProductDao cartProductDao) {
        this.cartProductDao = cartProductDao;
    }

    public CartProduct save(CartProduct cartProduct) {
        // TODO update cart's total_cost field after each save
        return cartProductDao.save(cartProduct);
    }

    public Optional<CartProduct> findCartProductByCartAndProductId(String cartId, String productId) {
        return cartProductDao.findAll().stream()
                .filter(cp -> (cp.getCartId().equals(cartId)) &&
                        (cp.getProductId().equals(productId)))
                .findFirst();
    }

    public List<CartProduct> findCartProductWithCertainCartId(String cartId) {
        return cartProductDao.findAll().stream()
                .filter(cp -> cp.getCartId().equals(cartId))
                .toList();
    }

    public CartProduct update(CartProduct cartProduct) {
        return cartProductDao.update(cartProduct);
    }

    public boolean deleteByCartIdAndProductId(String cartId, String productId) {
        return cartProductDao.deleteByCartIdAndProductId(cartId, productId);
    }

    public boolean deleteAllItemsAssociatedWithCartId(String cartId) {
        // grab all of the cartProducts with the cart id
        List<CartProduct> cartProducts = findCartProductWithCertainCartId(cartId);
        // 0 items means nothing to delete, so we're already done
        if (cartProducts.size() == 0) {
            return true;
        }
        for (int i = 0; i < cartProducts.size(); i++) {
            boolean didDelete = deleteByCartIdAndProductId(cartId,
                    cartProducts.get(i).getProductId());
            // failure to delete here means something happened, shouldn't normally reach
            // this state
            if (!didDelete) {
                return false;
            }
        }
        return true;
    }

    public float getTotalCostByCartId(String cartId) {
        return cartProductDao.getCartPrice(cartId);
    }
}
