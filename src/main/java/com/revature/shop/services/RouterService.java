package com.revature.shop.services;

import java.util.Scanner;

import com.revature.shop.daos.RoleDao;
import com.revature.shop.daos.UserDao;
import com.revature.shop.screens.BaseScreen;
import com.revature.shop.screens.LoginScreen;
import com.revature.shop.screens.RegisterScreen;
import com.revature.shop.screens.StartScreen;

public class RouterService {
    private final Scanner scan;

    public RouterService(Scanner scan) {
        this.scan = scan;
    }

    public BaseScreen navigate(String path) {
        switch (path) {
            case "/start":
                return new StartScreen(scan, this);
            case "/register":
                return new RegisterScreen(scan, new UserService(new UserDao(), new RoleService(new RoleDao())));
            case "/login":
                return new LoginScreen();
            default:
                // System.out.println("path not found!");
                return new StartScreen(scan, this);
        }
    }
}
