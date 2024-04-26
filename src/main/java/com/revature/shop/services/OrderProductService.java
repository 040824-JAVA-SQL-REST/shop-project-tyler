package com.revature.shop.services;

import java.util.List;
import java.util.Optional;

import com.revature.shop.daos.OrderProductDao;
import com.revature.shop.models.CartProduct;
import com.revature.shop.models.OrderProduct;

public class OrderProductService {
    private final OrderProductDao orderProductDao;

    public OrderProductService(OrderProductDao orderProductDao) {
        this.orderProductDao = orderProductDao;
    }

    public void moveProductsFromCartProductsToOrderProducts(List<CartProduct> cartProducts, String orderId) {
        // As long as the list is not empty, save each item into orderProducts
        if (cartProducts.size() == 0) {
            return;
        }
        for (int i = 0; i < cartProducts.size(); i++) {
            OrderProduct op = new OrderProduct(orderId,
                    cartProducts.get(i).getProductId(),
                    cartProducts.get(i).getQuantity());
            orderProductDao.save(op);
        }
    }
}
