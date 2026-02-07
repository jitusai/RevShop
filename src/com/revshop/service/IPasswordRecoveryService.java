package com.revshop.service;

public interface IPasswordRecoveryService {

    String getSecurityQuestion(String email);

    boolean validateAnswer(String email, String answer);
    boolean resetPassword(String email, String newPassword);
    boolean recoverPassword(String email);

}
