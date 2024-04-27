package com.revature.shop.utils;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;

import java.io.IOException;

import com.revature.shop.controllers.CartController;
import com.revature.shop.controllers.CartProductController;
import com.revature.shop.controllers.CategoryController;
import com.revature.shop.controllers.OrderController;
import com.revature.shop.controllers.OrderProductController;
import com.revature.shop.controllers.ProductController;
import com.revature.shop.controllers.UserController;
import com.revature.shop.daos.CartDao;
import com.revature.shop.daos.CartProductDao;
import com.revature.shop.daos.CategoryDao;
import com.revature.shop.daos.OrderDao;
import com.revature.shop.daos.OrderProductDao;
import com.revature.shop.daos.ProductDao;
import com.revature.shop.daos.RoleDao;
import com.revature.shop.daos.UserDao;
import com.revature.shop.services.CartProductService;
import com.revature.shop.services.CartService;
import com.revature.shop.services.CategoryService;
import com.revature.shop.services.OrderProductService;
import com.revature.shop.services.OrderService;
import com.revature.shop.services.ProductService;
import com.revature.shop.services.RoleService;
import com.revature.shop.services.TokenService;
import com.revature.shop.services.UserService;

public class JavalinUtil {
    public Javalin getJavalin() throws IOException {
        UserController userController = new UserController(
                getUserService(),
                new TokenService());
        CategoryController categoryController = new CategoryController(
                new CategoryService(new CategoryDao()),
                new TokenService());
        ProductController productController = new ProductController(
                new ProductService(new ProductDao()),
                new CategoryService(new CategoryDao()),
                new TokenService());
        CartController cartController = new CartController(
                new CartService(new CartDao()),
                new TokenService());
        CartProductController cartProductController = new CartProductController(
                new CartProductService(new CartProductDao()),
                new ProductService(new ProductDao()),
                new CartService(new CartDao()),
                new TokenService());
        OrderController orderController = new OrderController(
                new OrderService(new OrderDao()),
                new OrderProductService(new OrderProductDao()),
                new ProductService(new ProductDao()),
                new TokenService());
        OrderProductController orderProductController = new OrderProductController(
                new OrderProductService(new OrderProductDao()),
                new CartProductService(new CartProductDao()),
                new CartService(new CartDao()),
                new OrderService(new OrderDao()),
                new TokenService());

        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/users", () -> {
                    post("/register", userController::register);
                    post("/login", userController::login);
                });

                path("/categories", () -> {
                    get(categoryController::getCategories);
                    post(categoryController::addCategory);
                });

                path("/products", () -> {
                    get(productController::getProducts);
                    post(productController::addProduct);
                    put(productController::editProduct);
                    delete(productController::deleteProduct);
                });

                path("/carts", () -> {
                    get(cartController::getCart);
                });

                path("/cartProducts", () -> {
                    post(cartProductController::addProductToCart);
                    put(cartProductController::updateQuantity);
                    delete(cartProductController::delete);
                    get("/user", cartProductController::getUsersCartProduct);
                    get("/price", cartProductController::getUsersCartPrice);
                });

                path("/orders", () -> {
                    put("/new", orderController::createNewOrder);
                    get("/user", orderController::getUserOrders);
                    get("/all", orderController::getAllOrders);
                });

                path("/orderProducts", () -> {
                    post(orderProductController::checkout);
                });
            });
        });
    }

    private UserService getUserService() {
        return new UserService(new UserDao(), new RoleService(new RoleDao()));
    }
}
