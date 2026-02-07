package com.revshop.controller;

import com.revshop.model.Payment;
import com.revshop.service.IPaymentService;
import com.revshop.service.PaymentServiceImpl;

import java.util.Scanner;

public class PaymentController {

    private final IPaymentService paymentService;
    private final Scanner sc;

    public PaymentController(Scanner sc) {
        this.sc = sc;
        this.paymentService = new PaymentServiceImpl(); // constructor already safe
    }

    public boolean makePayment(int orderId, double amount) {

        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(amount);

        System.out.println("\n===== PAYMENT =====");
        System.out.println("Amount to pay: ₹" + amount);
        System.out.println("1. UPI");
        System.out.println("2. CARD");
        System.out.println("3. CASH ON DELIVERY");
        System.out.print("Choose payment method: ");

        int choice;
        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
            return false;
        }

        switch (choice) {
            case 1 -> payment.setPaymentMethod("UPI");
            case 2 -> payment.setPaymentMethod("CARD");
            case 3 -> payment.setPaymentMethod("COD");
            default -> {
                System.out.println("Invalid payment method");
                return false;
            }
        }

        payment.setPaymentStatus("SUCCESS");

        boolean result = paymentService.processPayment(payment);

        if (result) {
            System.out.println("Payment successful!");
        } else {
            System.out.println("Payment failed!");
        }

        return result;
    }

    public void viewAllPayments() {
        var payments = paymentService.viewAllPayments();

        if (payments.isEmpty()) {
            System.out.println("No payments found");
            return;
        }

        System.out.println("\n===== PAYMENTS =====");
        for (Payment p : payments) {
            System.out.println(
                    "PaymentId: " + p.getPaymentId() +
                            " | OrderId: " + p.getOrderId() +
                            " | Amount: ₹" + p.getAmount() +
                            " | Method: " + p.getPaymentMethod() +
                            " | Status: " + p.getPaymentStatus()
            );
        }
    }

}
