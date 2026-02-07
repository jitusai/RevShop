package com.revshop.controller;

import java.util.Scanner;

import com.revshop.model.User;
import com.revshop.service.IUserService;
import com.revshop.service.UserServiceImpl;

public class UserController {

    private final IUserService userService;
    private final Scanner sc;

    public UserController(Scanner sc) {
        this.userService = new UserServiceImpl();
        this.sc = sc;
    }

    // REGISTER
    public User register() {

        User user = new User();

        System.out.print("Name: ");
        user.setName(sc.nextLine());

        System.out.print("Email: ");
        user.setEmail(sc.nextLine());

        System.out.print("Password: ");
        user.setPassword(sc.nextLine());

        System.out.print("Phone: ");
        user.setPhone(sc.nextLine());

        System.out.println("1. Buyer");
        System.out.println("2. Seller");
        System.out.print("Choose role: ");
        int choice = Integer.parseInt(sc.nextLine());

        user.setRole(choice == 2 ? "Seller" : "Buyer");


        User registeredUser = userService.register(user);

        if (registeredUser == null || registeredUser.getUserId() <= 0) {
            System.out.println("Registration failed");
            return null;
        }

        System.out.println("Registration successful!");
        return registeredUser;
    }

    // LOGIN
    public User login() {

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = userService.login(email, password);

        if (user == null) {
            System.out.println("Invalid email or password");
            return null;
        }

        System.out.println("Login successful! Welcome " + user.getName());
        return user;
    }
}
