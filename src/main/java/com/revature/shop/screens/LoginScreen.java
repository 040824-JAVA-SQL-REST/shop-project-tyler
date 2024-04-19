package com.revature.shop.screens;

import java.util.Optional;
import java.util.Scanner;

import com.revature.shop.models.User;
import com.revature.shop.services.RouterService;
import com.revature.shop.services.UserService;

public class LoginScreen extends BaseScreen {

    private final Scanner scan;
    private final UserService userService;
    private final RouterService routerService;

    public LoginScreen(Scanner scan, UserService userService, RouterService routerService) {
        this.scan = scan;
        this.userService = userService;
        this.routerService = routerService;
    }

    @Override
    public void startInterface() {
        String email = "";
        String password = "";
        Optional<User> user;

        clearScreen();
        System.out.println("Welcome to the Login Screen!");

        while (true) {
            System.err.print("Please enter your email: ");
            email = scan.nextLine();
            clearScreen();
            System.err.print("Please enter your password: ");
            password = scan.nextLine();
            user = userService.login(email, password);
            if (user.isEmpty()) {
                // nothing returned means no match
                System.out.println("username or password is incorrect. Please try again");
                pause(scan);
                clearScreen();
                continue;
            } else {
                routerService.setSession(user.get());
                break;
            }
        }

        routerService.navigate("/home").startInterface();
    }

}
