package com.revature.shop.dtos.responses;

import java.util.List;

import com.revature.shop.models.Order;
import com.revature.shop.models.OrderProduct;

public class GetOrderResponse {
    Order order;
    List<OrderProduct> orderProducts;

    public GetOrderResponse() {
    }

    public GetOrderResponse(Order order, List<OrderProduct> orderProducts) {
        this.order = order;
        this.orderProducts = orderProducts;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

}
