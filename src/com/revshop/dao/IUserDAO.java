package com.revshop.dao;

import com.revshop.model.User;

public interface IUserDAO {

    int register(User user);

    User login(String email, String password);

    User getUserById(int userId);

    void updateUser(User user);

    // PASSWORD METHODS
    boolean validatePassword(int userId, String password);

    void changePassword(int userId, String newPassword);

    boolean updatePasswordByEmail(String email, String newPassword);
}
