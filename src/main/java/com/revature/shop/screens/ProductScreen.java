package com.revature.shop.screens;

import java.util.Scanner;

import com.revature.shop.models.Product;
import com.revature.shop.services.ProductService;
import com.revature.shop.services.RouterService;

public class ProductScreen extends BaseScreen {

    private final Scanner scan;
    private final ProductService productService;
    private final RouterService routerService;

    public ProductScreen(Scanner scan, ProductService productService, RouterService routerService) {
        this.scan = scan;
        this.productService = productService;
        this.routerService = routerService;
    }

    @Override
    public void startInterface() {
        // Users have been authenticated before reaching this screen

        while (true) {
            String name = "";
            String description = "";
            float price = 0.0f;
            clearScreen();
            System.out.print("Would you like to add a product? y/n: ");
            String choice = scan.nextLine().trim();
            if (choice.equalsIgnoreCase("y")) {
                clearScreen();
                System.out.print("Please enter the product name: ");
                name = scan.nextLine();
                System.out.print("Please enter the product description: ");
                description = scan.nextLine();
                System.out.print("Please enter the product price: ");
                price = Float.parseFloat(scan.nextLine());
                Product newProduct = new Product(name, description, price);
                System.out.println("\nadding product...");
                productService.save(newProduct);
            } else {
                return;
            }
        }

        // Product testProduct = new Product("test product", "testing the product
        // save()", 1.23f);
        // clearScreen();
        // System.out.println("Adding a test product...");
        // productService.save(testProduct);
        // System.out.println("Test object added");
        // testProduct.setName("blah");
        // System.out.println("\nretreiving the test product...");
        // testProduct = productService.getProductByName("test product");
        // System.out.println(testProduct.getName());
        // pause(scan);

    }

}
