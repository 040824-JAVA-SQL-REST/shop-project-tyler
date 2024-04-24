package com.revature.shop.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.revature.shop.dtos.requests.NewLoginRequest;
import com.revature.shop.dtos.requests.NewRegisterRequest;
import com.revature.shop.dtos.responses.Principal;
import com.revature.shop.models.Role;
import com.revature.shop.models.User;
import com.revature.shop.services.TokenService;
import com.revature.shop.services.UserService;

import io.javalin.http.Context;

public class UserController {
    private UserService userService;
    private TokenService tokenService;

    public UserController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    public void register(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();
            NewRegisterRequest req = ctx.bodyAsClass(NewRegisterRequest.class);

            if (!userService.isValidEmail(req.getEmail())) {
                errors.put("Error:", "Invalid Email");
                ctx.status(400); // Error 400: Bad request
                ctx.json(errors);
                return;
            }

            if (!userService.isUniqueEmail(req.getEmail())) {
                errors.put("Error:", "Username is already taken");
                ctx.status(409); // Error 409: Conflict
                ctx.json(errors);
                return;
            }

            if (!userService.isValidPassword(req.getPassword())) {
                errors.put("Error:", "Invalid Password");
                ctx.status(400);
                ctx.json(errors);
                return;
            }

            if (!userService.isValidFirstName(req.getFirstName())) {
                errors.put("Error:", "Invalid First Name");
                ctx.status(400); // Error 400: Bad request
                ctx.json(errors);
                return;
            }

            if (!userService.isValidLastName(req.getLastName())) {
                errors.put("Error:", "Invalid Last Name");
                ctx.status(400); // Error 400: Bad request
                ctx.json(errors);
                return;
            }

            // User newUser = new User(req.getEmail(),
            // BCrypt.hashpw(req.getPassword(), BCrypt.gensalt(12)),
            // req.getFirstName() + " " + req.getLastName());
            userService.save(new User(req));

            ctx.status(201); // Status 201: Created
        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
        }
    }

    public void login(Context ctx) {
        try {
            Map<String, String> errors = new HashMap<>();
            NewLoginRequest req = ctx.bodyAsClass(NewLoginRequest.class);

            Optional<User> optUser = userService.login(req.getEmail(), req.getPassword());
            if (optUser.isEmpty()) {
                errors.put("Error:", "Email or Password is incorrect/invalid");
                ctx.json(errors);
                ctx.status(400);
                return;
            }
            User foundUser = optUser.get();
            Role userRole = userService.getUserRole(foundUser);

            Principal principal = new Principal(foundUser, userRole);
            // generate token
            String token = tokenService.generateToken(principal);

            // set token to header
            ctx.header("auth-token", token);
            ctx.json(principal);
            ctx.status(200);

        } catch (Exception e) {
            ctx.status(500);
            e.printStackTrace();
            ;
        }
    }

}
