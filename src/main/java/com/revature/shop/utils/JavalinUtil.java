package com.revature.shop.utils;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.IOException;

import com.revature.shop.controllers.ProductController;
import com.revature.shop.controllers.UserController;
import com.revature.shop.daos.ProductDao;
import com.revature.shop.daos.RoleDao;
import com.revature.shop.daos.UserDao;
import com.revature.shop.services.ProductService;
import com.revature.shop.services.RoleService;
import com.revature.shop.services.TokenService;
import com.revature.shop.services.UserService;

public class JavalinUtil {
    public Javalin getJavalin() throws IOException {
        UserController userController = new UserController(
                getUserService(),
                new TokenService());
        ProductController productController = new ProductController(
                new ProductService(new ProductDao()),
                new TokenService());

        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/users", () -> {
                    post("/register", userController::register);
                    post("/login", userController::login);
                });

                path("/products", () -> {
                    get(productController::getProducts);
                    post(productController::addProduct);
                    put(productController::editProduct);
                    delete(productController::deleteProduct);
                });
            });
        });
    }

    private UserService getUserService() {
        return new UserService(new UserDao(), new RoleService(new RoleDao()));
    }
}
