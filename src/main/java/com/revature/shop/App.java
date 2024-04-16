package com.revature.shop;

import java.util.Scanner;

import com.revature.shop.screens.StartScreen;
import com.revature.shop.services.RouterService;

public class App {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        // new StartScreen(scan).startInterface();
        new RouterService(scan).navigate("/start").startInterface();

        scan.close();
    }
}
