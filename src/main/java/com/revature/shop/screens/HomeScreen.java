package com.revature.shop.screens;

import java.util.Scanner;

import com.revature.shop.services.RouterService;

public class HomeScreen extends BaseScreen {

    private final Scanner scan;
    private final RouterService routerService;

    public HomeScreen(Scanner scan, RouterService routerService) {
        this.scan = scan;
        this.routerService = routerService;
    }

    @Override
    public void startInterface() {
        clearScreen();
        System.out.println("You have reached the Home Screen");
        System.out.println("Welcome " + routerService.getSession().getFullName());
        pause(scan);
    }

}
