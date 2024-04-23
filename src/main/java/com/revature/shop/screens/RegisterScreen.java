package com.revature.shop.screens;

import java.util.Scanner;

import org.mindrot.jbcrypt.BCrypt;

import com.revature.shop.models.User;
import com.revature.shop.services.UserService;

public class RegisterScreen extends BaseScreen {

    private final Scanner scan;
    private final UserService userService;

    public RegisterScreen(Scanner scan, UserService userService) {
        this.scan = scan;
        this.userService = userService;
    }

    @Override
    public void startInterface() {
        String email = "";
        String password = "";
        String firstName = "";
        String lastName = "";
        String username;
        boolean isValidEmail = false;
        boolean isUniqueEmail = false;
        boolean isValidPassword = false;
        boolean isValidFirstName = false;
        boolean isValidLastName = false;

        clearScreen();
        System.out.println("You have made it to the register screen!");

        while (true) {
            if (!isValidEmail) {
                System.out.print("Please enter a email: ");
                email = scan.nextLine();
                if (userService.isValidEmail(email)) {
                    isValidEmail = true;
                } else {
                    System.out.println("Email is not valid");
                    email = "";
                    pause(scan);
                    clearScreen();
                    continue;
                }
            }
            if (!isUniqueEmail) {
                clearScreen();
                if (userService.isUniqueEmail(email)) {
                    isUniqueEmail = true;
                } else {
                    System.out.println("Email is already taken");
                    isValidEmail = false;
                    email = "";
                    pause(scan);
                    continue;
                }
            }
            if (!isValidPassword) {
                clearScreen();
                System.out.print("Please enter a password: ");
                password = scan.nextLine();
                if (userService.isValidPassword(password)) {
                    isValidPassword = true;
                    password = BCrypt.hashpw(password, BCrypt.gensalt(12));
                } else {
                    System.out.println("Password is not valid");
                    pause(scan);
                    continue;
                }
            }
            if (!isValidFirstName) {
                clearScreen();
                System.out.print("Please enter your first name: ");
                firstName = scan.nextLine();
                if (userService.isValidFirstName(firstName)) {
                    isValidFirstName = true;
                } else {
                    System.out.println("First name is not valid");
                    pause(scan);
                    continue;
                }
            }
            if (!isValidLastName) {
                clearScreen();
                System.out.print("Please enter your last name: ");
                lastName = scan.nextLine();
                if (userService.isValidLastName(lastName)) {
                    isValidLastName = true;
                    username = firstName + " " + lastName;
                    break;
                } else {
                    System.out.println("Last name is not valid");
                    pause(scan);
                    continue;
                }
            }
        }

        System.out.println("Adding user...");
        userService.save(new User(email, password, username));
        pause(scan);
    }

}
