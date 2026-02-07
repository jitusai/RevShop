package com.revshop.controller;

import java.util.Scanner;
import com.revshop.service.IPasswordRecoveryService;
import com.revshop.service.PasswordRecoveryServiceImpl;

public class PasswordRecoveryController {

    private IPasswordRecoveryService service =
            new PasswordRecoveryServiceImpl();

    private final Scanner sc = new Scanner(System.in);

    public void startPasswordRecovery() {

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        String question = service.getSecurityQuestion(email);
        if (question == null) {
            System.out.println("Email not found");
            return;
        }

        System.out.println(question);
        System.out.print("Answer: ");
        String answer = sc.nextLine();

        if (!service.validateAnswer(email, answer)) {
            System.out.println("Wrong answer");
            return;
        }

        System.out.print("New password: ");
        String pwd = sc.nextLine();

        if (service.resetPassword(email, pwd)) {
            System.out.println("Password reset success");
        } else {
            System.out.println("Reset failed");
        }
    }
}
