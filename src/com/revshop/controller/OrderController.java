package com.revshop.controller;

import com.revshop.model.Order;
import com.revshop.service.IOrderService;
import com.revshop.service.OrderServiceImpl;

import java.util.Scanner;

public class OrderController {

    private IOrderService service = new OrderServiceImpl();
    private final Scanner sc;

    public OrderController(Scanner sc) {
        this.sc = sc;
    }

    public void checkout(int userId) {

        System.out.println("\nCheckout Process");

        Order order = new Order();
        order.setUserId(userId);

        if (service.placeOrder(order)) {
            System.out.println("Order placed successfully!");
        } else {
            System.out.println("Failed to place order. Please check stock or cart.");
        }
    }
}
