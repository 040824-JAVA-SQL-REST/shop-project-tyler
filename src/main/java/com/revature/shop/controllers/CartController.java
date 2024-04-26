package com.revature.shop.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.revature.shop.dtos.requests.GetCartRequest;
import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.Cart;
import com.revature.shop.services.CartService;
import com.revature.shop.services.TokenService;

import io.javalin.http.Context;

public class CartController {
    private final CartService cartService;
    private final TokenService tokenService;

    public CartController(CartService cartService, TokenService tokenService) {
        this.cartService = cartService;
        this.tokenService = tokenService;
    }

    public void getCart(Context ctx) {
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
                errors.put("Error:", "Only users are allowed to view products");
                ctx.json(errors);
                return;
            }
            // end of request verification

            Cart userCart = cartService.getCartByUserId(principal.getId());
            GetCartRequest getCarRequest = new GetCartRequest(userCart);
            ctx.status(200);
            ctx.json(getCarRequest);

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }
}
