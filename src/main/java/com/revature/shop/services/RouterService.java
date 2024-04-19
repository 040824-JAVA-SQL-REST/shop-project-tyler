package com.revature.shop.services;

import java.util.Scanner;

import com.revature.shop.models.User;
import com.revature.shop.screens.BaseScreen;
import com.revature.shop.screens.HomeScreen;
import com.revature.shop.screens.LoginScreen;
import com.revature.shop.screens.RegisterScreen;
import com.revature.shop.screens.StartScreen;

public class RouterService {
    private final Scanner scan;
    private User session;
    private final UserService userService;

    public RouterService(Scanner scan, User session, UserService userService) {
        this.scan = scan;
        this.session = session;
        this.userService = userService;
    }

    public BaseScreen navigate(String path) {
        switch (path) {
            case "/start":
                return new StartScreen(scan, this);
            case "/register":
                return new RegisterScreen(scan, userService);
            case "/login":
                return new LoginScreen(scan, userService, this);
            case "/home":
                return new HomeScreen(scan, this);
            default:
                throw new IllegalArgumentException("Invalid path" + path);
        }
    }

    public void setSession(User session) {
        this.session = session;
    }

    public User getSession() {
        return this.session;
    }
}
