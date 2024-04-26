package com.revature.shop.controllers;

import java.util.HashMap;
import java.util.Map;

import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.Order;
import com.revature.shop.services.OrderService;
import com.revature.shop.services.TokenService;

import io.javalin.http.Context;

public class OrderController {
    private final OrderService orderService;
    private final TokenService tokenService;

    public OrderController(OrderService orderService, TokenService tokenService) {
        this.orderService = orderService;
        this.tokenService = tokenService;
    }

    public void getNewOrder(Context ctx) {
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
}
