package com.revature.shop.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.shop.dtos.requests.DeleteProductRequest;
import com.revature.shop.dtos.requests.EditProductRequest;
import com.revature.shop.dtos.requests.NewProductRequest;
import com.revature.shop.dtos.responses.GetProductsResponse;
import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.Product;
import com.revature.shop.services.ProductService;
import com.revature.shop.services.TokenService;

import io.javalin.http.Context;

public class ProductController {
    private final ProductService productService;
    private final TokenService tokenService;

    public ProductController(ProductService productService, TokenService tokenService) {
        this.productService = productService;
        this.tokenService = tokenService;
    }

    public void addProduct(Context ctx) {
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
                errors.put("Error:", "Only admins are allowed to add products");
                ctx.json(errors);
                return;
            }
            // end of request verification
            NewProductRequest req = ctx.bodyAsClass(NewProductRequest.class);
            Product newProduct = new Product(req);

            // Product verification goes here
            if (!productService.isValidName(newProduct.getName())) {
                ctx.status(400);
                errors.put("Error:", "A product name must be 4 or more characters in length");
                ctx.json(errors);
                return;
            }
            if (!productService.isUniqueName(newProduct.getName())) {
                ctx.status(400);
                errors.put("Error:", "A product with that name already exists, please choose a differnt name");
                ctx.json(errors);
                return;
            }
            if (!productService.isValidPrice(newProduct.getPrice())) {
                ctx.status(400);
                errors.put("Error:", "A product must cost more than $0.00");
                ctx.json(errors);
                return;
            }
            productService.save(newProduct);
            ctx.status(201);
            ctx.json(newProduct);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void editProduct(Context ctx) {
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
                errors.put("Error:", "Only admins are allowed to edit products");
                ctx.json(errors);
                return;
            }
            // end of request verification
            EditProductRequest req = ctx.bodyAsClass(EditProductRequest.class);
            Product editProduct = new Product(req);
            // does the product they wish to edit even exist?
            if (productService.getProductById(req.getId()).isEmpty()) {
                ctx.status(400);
                errors.put("Error:", "A product with that ID does not exist");
                ctx.json(errors);
                return;
            }
            // find out which parts of the product should be edited
            boolean editName = false;
            boolean editDescription = false;
            boolean editPrice = false;
            // by checking for default values
            if (editProduct.getName() != null) {
                if (!productService.isValidName(editProduct.getName())) {
                    ctx.status(400);
                    errors.put("Error:", "A product name must be 4 or more characters in length");
                    ctx.json(errors);
                    return;
                }
                if (!productService.isUniqueName(editProduct.getName())) {
                    ctx.status(400);
                    errors.put("Error:", "A product with that name already exists, please choose a differnt name");
                    ctx.json(errors);
                    return;
                }
                editName = true;

            }
            if (editProduct.getDescription() != null) {
                editDescription = true;
            }
            if (editProduct.getPrice() != 0.0f) {
                if (!productService.isValidPrice(editProduct.getPrice())) {
                    ctx.status(400);
                    errors.put("Error:", "A product must cost more than $0.00");
                    ctx.json(errors);
                    return;
                }
                editPrice = true;
            }
            Product oldProduct = productService.getProductById(req.getId()).get();
            if (!editName) {
                editProduct.setName(oldProduct.getName());
            }
            if (!editDescription) {
                editProduct.setDescription(oldProduct.getDescription());
            }
            if (!editPrice) {
                editProduct.setPrice(oldProduct.getPrice());
            }
            productService.update(editProduct);
            ctx.status(200);
            ctx.json(editProduct);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void deleteProduct(Context ctx) {
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
                errors.put("Error:", "Only admins are allowed to delete products");
                ctx.json(errors);
                return;
            }
            // end of request verification
            DeleteProductRequest req = ctx.bodyAsClass(DeleteProductRequest.class);
            if (productService.getProductById(req.getId()).isEmpty()) {
                ctx.status(400);
                errors.put("Error:", "A product with that ID does not exist");
                ctx.json(errors);
                return;
            }
            if (productService.delete(req.getId())) {
                ctx.status(200);
            } else {
                // deleted 0 rows, something went wrong
                ctx.status(502);
                errors.put("Error:", "Database returned 0 rows deleted");
                ctx.json(errors);
                return;
            }
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void getProducts(Context ctx) {
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
            List<Product> allProducts = productService.getAllProducts();
            List<GetProductsResponse> returnedProducts = new ArrayList<>();
            for (int i = 0; i < allProducts.size(); i++) {
                returnedProducts.add(new GetProductsResponse(allProducts.get(i)));
            }
            ctx.status(200);
            ctx.json(returnedProducts);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }
}
