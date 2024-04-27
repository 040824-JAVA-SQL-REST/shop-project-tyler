package com.revature.shop.dtos.responses;

import java.util.List;

import com.revature.shop.models.Order;

public class GetOrderResponse {
    Order order;
    List<GetOrderProduct> getOrderProducts;
    float totalPrice;

    public GetOrderResponse() {
    }

    public GetOrderResponse(Order order, List<GetOrderProduct> getOrderProducts, float totalPrice) {
        this.order = order;
        this.getOrderProducts = getOrderProducts;
        this.totalPrice = totalPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<GetOrderProduct> getGetOrderProducts() {
        return getOrderProducts;
    }

    public void setGetOrderProducts(List<GetOrderProduct> getOrderProducts) {
        this.getOrderProducts = getOrderProducts;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

}
