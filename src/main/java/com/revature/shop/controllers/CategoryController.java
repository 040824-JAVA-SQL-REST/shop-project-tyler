package com.revature.shop.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.shop.dtos.requests.AddCategoryRequest;
import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.Category;
import com.revature.shop.services.CategoryService;
import com.revature.shop.services.TokenService;

import io.javalin.http.Context;

public class CategoryController {
    private final CategoryService categoryService;
    private final TokenService tokenService;

    public CategoryController(CategoryService categoryService, TokenService tokenService) {
        this.categoryService = categoryService;
        this.tokenService = tokenService;
    }

    public void addCategory(Context ctx) {
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
                errors.put("Error:", "Only admins are allowed to add categories");
                ctx.json(errors);
                return;
            }
            // end of request verification
            AddCategoryRequest req = ctx.bodyAsClass(AddCategoryRequest.class);
            Category newCategory = new Category(req);

            if (newCategory.getName() == null) {
                ctx.status(400);
                errors.put("Error:", "No category name provided!");
                ctx.json(errors);
                return;
            }
            if (!categoryService.isValidName(newCategory.getName())) {
                ctx.status(400);
                errors.put("Error:", "A category name must be 4 or more characters in length");
                ctx.json(errors);
                return;
            }
            if (!categoryService.isUniqueName(newCategory.getName())) {
                ctx.status(400);
                errors.put("Error:", "A category with that name already exists, please choose a differnt name");
                ctx.json(errors);
                return;
            }

            categoryService.save(newCategory);
            ctx.status(201);
            ctx.json(newCategory);
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void getCategories(Context ctx) {
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
            List<Category> categories = categoryService.findAll();
            ctx.status(200);
            ctx.json(categories);

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }
}
