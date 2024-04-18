package com.revature.shop;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.shop.screens.StartScreen;
import com.revature.shop.services.RouterService;
import com.revature.shop.utils.ConnectionFactory;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner scan = new Scanner(System.in);

        // System.out.println(ConnectionFactory.getInstance().getConnection());
        // scan.nextLine();

        // new StartScreen(scan).startInterface();
        new RouterService(scan).navigate("/start").startInterface();

        scan.close();
    }
}
