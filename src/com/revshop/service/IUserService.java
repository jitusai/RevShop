package com.revshop.service;

import com.revshop.model.User;

public interface IUserService {

    User register(User user);

    User login(String email, String password);

    User viewProfile(int userId);

    void updateProfile(User user);

    // PASSWORD FEATURES
    boolean changePassword(int userId, String oldPassword, String newPassword);

    boolean forgotPassword(String email, String newPassword);
}
