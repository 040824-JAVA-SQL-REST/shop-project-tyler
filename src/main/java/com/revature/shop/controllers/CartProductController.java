package com.revature.shop.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.revature.shop.dtos.requests.AddProductRequest;
import com.revature.shop.dtos.requests.DeleteCartProductRequest;
import com.revature.shop.dtos.requests.EditQuantityRequest;
import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.CartProduct;
import com.revature.shop.models.Product;
import com.revature.shop.services.CartProductService;
import com.revature.shop.services.CartService;
import com.revature.shop.services.ProductService;
import com.revature.shop.services.TokenService;

import io.javalin.http.Context;

public class CartProductController {
    private final CartProductService cartProductService;
    private final ProductService productService;
    private final CartService cartService;
    private final TokenService tokenService;

    public CartProductController(CartProductService cartProductService,
            ProductService productService,
            CartService cartService,
            TokenService tokenService) {
        this.cartProductService = cartProductService;
        this.productService = productService;
        this.cartService = cartService;
        this.tokenService = tokenService;
    }

    public void addProductToCart(Context ctx) {
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

            AddProductRequest req = ctx.bodyAsClass(AddProductRequest.class);
            Optional<Product> optProduct = productService.getProductById(req.getProductId());
            if (optProduct.isEmpty()) {
                errors.put("Error:", "Product not recgonized. Please check productId");
                ctx.status(400);
                ctx.json(errors);
                return;
            }
            // info needed for a cartProduct
            Product product = optProduct.get();
            String cart_id = cartService.getCartIdByUserId(principal.getId());
            int quantity = req.getQuantity();

            CartProduct cartProduct = new CartProduct(cart_id, product.getId(),
                    quantity, 0.0f);
            cartProductService.save(cartProduct);

            ctx.status(201);

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void updateQuantity(Context ctx) {
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

            EditQuantityRequest req = ctx.bodyAsClass(EditQuantityRequest.class);
            Optional<Product> optProduct = productService.getProductById(req.getProductId());
            if (optProduct.isEmpty()) {
                errors.put("Error:", "Product not recgonized. Please check productId");
                ctx.status(400);
                ctx.json(errors);
                return;
            }
            // info needed for a cartProduct
            Product product = optProduct.get();
            String cart_id = cartService.getCartIdByUserId(principal.getId());
            int quantity = req.getQuantity();
            if (quantity <= 0) {
                errors.put("Error:",
                        "Cannot set quantity to less than 1. If you wish to remove the product from the cart, use the delete request instead");
                ctx.status(400);
                ctx.json(errors);
                return;
            }

            // does the 'item in cart' exist?
            Optional<CartProduct> optCartProduct = cartProductService
                    .findCartProductByCartAndProductId(cart_id, product.getId());
            if (optCartProduct.isEmpty()) {
                errors.put("Error:", "There is no record with this cart and product Id. Check values and resubmit");
                ctx.status(400);
                ctx.json(errors);
                return;
            }
            // item exists, so let's update quantity
            CartProduct foundCartProduct = optCartProduct.get();
            foundCartProduct.setQuantity(quantity);
            cartProductService.update(foundCartProduct);
            ctx.status(200);

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void delete(Context ctx) {
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
            DeleteCartProductRequest req = ctx.bodyAsClass(DeleteCartProductRequest.class);
            Optional<Product> optProduct = productService.getProductById(req.getProductId());
            if (optProduct.isEmpty()) {
                errors.put("Error:", "Product not recgonized. Please check productId");
                ctx.status(400);
                ctx.json(errors);
                return;
            }
            // info needed for a cartProduct
            Product product = optProduct.get();
            String cart_id = cartService.getCartIdByUserId(principal.getId());

            boolean successfulDelete = cartProductService.deleteByCartIdAndProductId(cart_id, product.getId());

            if (successfulDelete) {
                ctx.status(200);
                return;
            } else {
                // deleted 0 rows
                ctx.status(400);
                errors.put("Error:", "There was nothing to delete with that cart and product id combination");
                ctx.json(errors);
                return;
            }

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void getUsersCartProduct(Context ctx) {
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
            String cart_id = cartService.getCartIdByUserId(principal.getId());
            List<CartProduct> currentCartProduct = cartProductService.findCartProductWithCertainCartId(cart_id);
            ctx.status(200);
            ctx.json(currentCartProduct);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void getUsersCartPrice(Context ctx) {
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
            String cart_id = cartService.getCartIdByUserId(principal.getId());
            float totalPrice = cartProductService.getTotalCostByCartId(cart_id);
            ctx.status(200);
            ctx.json(totalPrice);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }
}
