package com.revature.shop.screens;

import java.util.Scanner;

public class StartScreen extends BaseScreen {
    private final Scanner scan;

    public StartScreen(Scanner scan) {
        this.scan = scan;
    }

    public void startInterface() {
        clearScreen();
        System.out.println("You have reached the Start Screen!");

        System.out.print("Enter an option: ");
        String userChoice = scan.nextLine();

        System.out.println("You entered " + userChoice);
        pauseScreen(scan);
    }
}
