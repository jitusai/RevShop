package com.revshop.dao;

import com.revshop.model.PasswordRecovery;

public interface IPasswordRecoveryDAOImpl {

    PasswordRecovery findByEmail(String email);

    boolean verifySecurityAnswer(int userId, String answer);
    boolean resetPassword(int userId, String newPassword);
}
