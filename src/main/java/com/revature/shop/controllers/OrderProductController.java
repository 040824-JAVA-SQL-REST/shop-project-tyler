package com.revature.shop.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.shop.dtos.requests.AddCheckoutRequest;
import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.CartProduct;
import com.revature.shop.models.Order;
import com.revature.shop.services.CartProductService;
import com.revature.shop.services.CartService;
import com.revature.shop.services.OrderProductService;
import com.revature.shop.services.OrderService;
import com.revature.shop.services.TokenService;

import io.javalin.http.Context;

public class OrderProductController {
    private final OrderProductService orderProductService;
    private final CartProductService cartProductService;
    private final CartService cartService;
    private final OrderService orderService;
    private final TokenService tokenService;

    public OrderProductController(
            OrderProductService orderProductService,
            CartProductService cartProductService,
            CartService cartService,
            OrderService orderService,
            TokenService tokenService) {
        this.orderProductService = orderProductService;
        this.cartProductService = cartProductService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.tokenService = tokenService;
    }

    public void checkout(Context ctx) {
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
                errors.put("Error:", "Only users are allowed to checkout");
                ctx.json(errors);
                return;
            }
            // end of request verification

            // step 1) grab orderId from request body
            AddCheckoutRequest req = ctx.bodyAsClass(AddCheckoutRequest.class);
            String orderId = req.getOrderId();
            if (!orderService.isExistingOrderId(orderId)) {
                ctx.status(400);
                errors.put("Error:", "Order id does not exist!");
                ctx.json(errors);
                return;
            }
            // step 2) grab cartId from principal.getId()
            String userId = principal.getId();
            String cartId = cartService.getCartIdByUserId(userId);
            // step 3) 'transfer' items from cart->order with orderProductService
            List<CartProduct> cartProducts = cartProductService.findCartProductWithCertainCartId(cartId);
            orderProductService.moveProductsFromCartProductsToOrderProducts(cartProducts, orderId);
            // step 4) delete items in cart via cartProductService
            cartProductService.deleteAllItemsAssociatedWithCartId(cartId);
            // step 5) set pending and payment_method of the order
            Order order = orderService.getOrderById(orderId).get();
            order.setPaymentMethod(req.getPayment_method());
            order.setPending("PENDING");
            ctx.status(200);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }
}
