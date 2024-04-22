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
        while (true) {
            clearScreen();
            System.out.print("Welcome " + routerService.getSession().getFullName().split(" ")[0] + ", ");
            System.out.println("You have reached the Home Screen!");

            System.out.println("\n[1] Shop");
            System.out.println("[2] View history");
            System.out.println("[3] (ADMIN) add/modify products");
            System.out.println("[x] Logout");

            System.out.print("\nEnter an option: ");
            String userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("You chose to [Shop]");
                    break;
                case "2":
                    System.out.println("You chose to [View history]");
                    break;
                case "3":
                    clearScreen();
                    System.out.println("You chose to [add/modify Products]");
                    boolean testValue = routerService.getUserService().authenticateUser(routerService.getSession(),
                            "ADMIN");
                    if (testValue) {
                        routerService.navigate("/product").startInterface();
                        break;
                    } else {
                        System.out.println("You are not authorized to access this screen");
                        pause(scan);
                        break;
                    }
                case "x":
                case "X":
                    System.out.println("You chose to [Logout]");
                    return;
                default:
                    System.out.println("Choice not recgonized, please select a valid option");
                    pause(scan);
                    continue;
            }
        }
    }

}
