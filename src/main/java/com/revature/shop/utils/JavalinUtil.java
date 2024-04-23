package com.revature.shop.utils;

import io.javalin.Javalin;
import static io.javalin.apibuilder.ApiBuilder.*;
import com.revature.shop.controllers.UserController;
import com.revature.shop.daos.RoleDao;
import com.revature.shop.daos.UserDao;
import com.revature.shop.services.RoleService;
import com.revature.shop.services.UserService;

public class JavalinUtil {
    public Javalin getJavalin() {
        UserController userController = new UserController(getUserService());
        return Javalin.create(config -> {
            config.router.apiBuilder(() -> {
                path("/users", () -> {
                    post("/register", userController::register);
                    post("/login", userController::login);
                });
            });
        });
    }

    private UserService getUserService() {
        return new UserService(new UserDao(), new RoleService(new RoleDao()));
    }
}
