package com.revature.shop;

import java.io.IOException;
import java.sql.SQLException;

import com.revature.shop.utils.JavalinUtil;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        new JavalinUtil().getJavalin().start(8080);
    }
}
