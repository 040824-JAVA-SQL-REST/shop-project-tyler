package com.revature.shop.screens;

import java.util.Scanner;

import com.revature.shop.models.User;
import com.revature.shop.services.RouterService;
import com.revature.shop.services.UserService;

public class RegisterScreen extends BaseScreen {

    private final Scanner scan;
    private final RouterService routerService;
    private final UserService userService;

    public RegisterScreen(Scanner scan, RouterService routerService, UserService userService) {
        this.scan = scan;
        this.routerService = routerService;
        this.userService = userService;
    }

    @Override
    public void startInterface() {
        clearScreen();
        System.out.println("You have made it to the register screen!");
        System.out.println("Adding a test user...");
        userService.saveUser(new User("test user", "password"));

        System.out.println("Retreiving test user...");
        User testUser = userService.getUserByUsername("test user");
        System.out.println("name = " + testUser.getUsername() +
                "\nid = " + testUser.getId());
        pause(scan);
    }

}
