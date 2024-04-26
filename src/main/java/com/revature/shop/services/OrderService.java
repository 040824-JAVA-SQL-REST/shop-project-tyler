package com.revature.shop.services;

import java.util.Optional;

import com.revature.shop.daos.OrderDao;
import com.revature.shop.models.Order;

public class OrderService {
    private final OrderDao orderDao;

    public OrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public Order save(Order obj) {
        return orderDao.save(obj);
    }

    public Optional<Order> getOrderById(String id) {
        return orderDao.findAll().stream()
                .filter(o -> o.getId().equals(id))
                .findFirst();
    }

    public boolean isExistingOrderId(String orderId) {
        Optional<Order> optOrder = orderDao.findAll()
                .stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst();
        if (optOrder.isEmpty()) {
            return false;
        }
        return true;
    }
}
