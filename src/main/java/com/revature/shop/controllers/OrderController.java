package com.revature.shop.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.shop.dtos.responses.GetOrderResponse;
import com.revature.shop.dtos.responses.GetOrderProduct;
import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.Order;
import com.revature.shop.models.OrderProduct;
import com.revature.shop.services.OrderProductService;
import com.revature.shop.services.OrderService;
import com.revature.shop.services.ProductService;
import com.revature.shop.services.TokenService;

import io.javalin.http.Context;

public class OrderController {
    private final OrderService orderService;
    private final OrderProductService orderProductService;
    private final ProductService productService;
    private final TokenService tokenService;

    public OrderController(OrderService orderService, OrderProductService orderProductService,
            ProductService productService, TokenService tokenService) {
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.productService = productService;
        this.tokenService = tokenService;
    }

    public void createNewOrder(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();
            String token = ctx.header("auth-token");
            // request validation
            if (token == null || token.isEmpty()) {
                ctx.status(401);
                errors.put("Error:", "Your token is not valid");
                ctx.json(errors);
                return;
            }
            Principal principal = tokenService.parseToken(token);
            if (principal == null) {
                ctx.status(401);
                errors.put("Error:", "Your token is not valid");
                ctx.json(errors);
                return;
            }
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN") &&
                    !principal.getRole().getName().equalsIgnoreCase("DEFAULT")) {
                ctx.status(403);
                errors.put("Error:", "Only users are allowed to make a new order");
                ctx.json(errors);
                return;
            }
            // end of request verification
            Order newOrder = new Order(principal.getId());
            orderService.save(newOrder);
            ctx.status(201);
            ctx.json(newOrder);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void getUserOrders(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();
            String token = ctx.header("auth-token");
            // request validation
            if (token == null || token.isEmpty()) {
                ctx.status(401);
                errors.put("Error:", "Your token is not valid");
                ctx.json(errors);
                return;
            }
            Principal principal = tokenService.parseToken(token);
            if (principal == null) {
                ctx.status(401);
                errors.put("Error:", "Your token is not valid");
                ctx.json(errors);
                return;
            }
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN") &&
                    !principal.getRole().getName().equalsIgnoreCase("DEFAULT")) {
                ctx.status(403);
                errors.put("Error:", "Only users can have an order");
                ctx.json(errors);
                return;
            }
            // end of request verification
            String userId = principal.getId();
            List<Order> userOrders = orderService.getAllOrdersByUserId(userId);
            int userOrdersLength = userOrders.size();
            if (userOrdersLength == 0) {
                ctx.status(200);
                return;
            }
            // For each order: Total Cost, Order Date, contents of cart
            // For each cart inside of order: Product Name, Quantity
            ArrayList<GetOrderResponse> ordersPlusProducts = new ArrayList();
            for (int i = 0; i < userOrdersLength; i++) {
                GetOrderResponse temp = new GetOrderResponse();
                String orderId = userOrders.get(i).getId();
                temp.setOrder(userOrders.get(i));
                List<OrderProduct> products = orderProductService.findAllOrderProductsByOrderId(orderId);
                List<GetOrderProduct> orderProducts = new ArrayList();
                for (int j = 0; j < products.size(); j++) {
                    GetOrderProduct productWithName = new GetOrderProduct();
                    OrderProduct currentProduct = products.get(j);
                    productWithName.setName(productService.getNameById(currentProduct.getProductId()));
                    productWithName
                            .setPrice(productService.getProductById(currentProduct.getProductId()).get().getPrice());
                    productWithName.setQuantity(currentProduct.getQuantity());
                    orderProducts.add(productWithName);
                }
                temp.setGetOrderProducts(orderProducts);
                float totalPrice = orderProductService.getTotalPriceByOrderId(orderId);
                temp.setTotalPrice(totalPrice);
                ordersPlusProducts.add(temp);
            }
            ctx.status(200);
            ctx.json(ordersPlusProducts);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void getAllOrders(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();
            String token = ctx.header("auth-token");
            // request validation
            if (token == null || token.isEmpty()) {
                ctx.status(401);
                errors.put("Error:", "Your token is not valid");
                ctx.json(errors);
                return;
            }
            Principal principal = tokenService.parseToken(token);
            if (principal == null) {
                ctx.status(401);
                errors.put("Error:", "Your token is not valid");
                ctx.json(errors);
                return;
            }
            if (!principal.getRole().getName().equalsIgnoreCase("ADMIN")) {
                ctx.status(403);
                errors.put("Error:", "Only admins can get all user orders");
                ctx.json(errors);
                return;
            }
            // end of request verification
            List<Order> userOrders = orderService.getAllOrders();
            int userOrdersLength = userOrders.size();
            if (userOrdersLength == 0) {
                ctx.status(200);
                return;
            }
            ArrayList<GetOrderResponse> ordersPlusProducts = new ArrayList();
            for (int i = 0; i < userOrdersLength; i++) {
                GetOrderResponse temp = new GetOrderResponse();
                String orderId = userOrders.get(i).getId();
                temp.setOrder(userOrders.get(i));
                List<OrderProduct> products = orderProductService.findAllOrderProductsByOrderId(orderId);
                List<GetOrderProduct> orderProducts = new ArrayList();
                for (int j = 0; j < products.size(); j++) {
                    GetOrderProduct productWithName = new GetOrderProduct();
                    OrderProduct currentProduct = products.get(j);
                    productWithName.setName(productService.getNameById(currentProduct.getProductId()));
                    productWithName
                            .setPrice(productService.getProductById(currentProduct.getProductId()).get().getPrice());
                    productWithName.setQuantity(currentProduct.getQuantity());
                    orderProducts.add(productWithName);
                }
                temp.setGetOrderProducts(orderProducts);
                float totalPrice = orderProductService.getTotalPriceByOrderId(orderId);
                temp.setTotalPrice(totalPrice);
                ordersPlusProducts.add(temp);
            }
            ctx.status(200);
            ctx.json(ordersPlusProducts);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }
}
