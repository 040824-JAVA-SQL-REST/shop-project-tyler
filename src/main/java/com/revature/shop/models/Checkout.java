package com.revature.shop.models;

import java.util.List;

public class Checkout {
    private String id;
    private List<Product> cart;
    private boolean success;
    private String payment_method;
    private float totalCost;
}
