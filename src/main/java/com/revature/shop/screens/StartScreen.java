package com.revature.shop.screens;

import java.util.Scanner;

public class StartScreen extends BaseScreen {
    private final Scanner scan;

    public StartScreen(Scanner scan) {
        this.scan = scan;
    }

    public void startInterface() {
        while (true) {
            clearScreen();
            System.out.println("You have reached the Start Screen!");

            System.out.println("\n[1] Login");
            System.out.println("[2] Register");
            System.out.println("[x] Quit");

            System.out.print("\nEnter an option: ");
            String userChoice = scan.nextLine();

            switch (userChoice) {
                case "1":
                    System.out.println("You chose to [Login]");
                    break;
                case "2":
                    System.out.println("You chose to [Register]");
                    break;
                case "x":
                case "X":
                    System.out.println("You chose to [Quit]");
                    return;
                default:
                    System.out.println("Choice not recgonized, please select a valid option");
                    pause(scan);
                    continue;
            }
        }
    }
}
