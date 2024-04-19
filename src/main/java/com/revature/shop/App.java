package com.revature.shop;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import com.revature.shop.daos.RoleDao;
import com.revature.shop.daos.UserDao;
import com.revature.shop.models.User;
import com.revature.shop.services.RoleService;
import com.revature.shop.services.RouterService;
import com.revature.shop.services.UserService;

public class App {
    public static void main(String[] args) throws SQLException, IOException {
        Scanner scan = new Scanner(System.in);
        User session = new User();

        new RouterService(scan,
                session,
                new App().getUserService())
                .navigate("/start")
                .startInterface();

        scan.close();
    }

    private UserService getUserService() {
        return new UserService(new UserDao(), new RoleService(new RoleDao()));
    }
}
