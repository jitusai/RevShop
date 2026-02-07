package com.revshop.service;

import com.revshop.dao.IUserDAO;
import com.revshop.dao.UserDAOImpl;
import com.revshop.model.User;
import com.revshop.util.LoggerUtil;

public class UserServiceImpl implements IUserService {

    private IUserDAO userDAO = new UserDAOImpl();

    @Override
    public User register(User user) {
        try {
            int id = userDAO.register(user);
            user.setUserId(id);
            LoggerUtil.logInfo("User registered successfully: " + user.getEmail());
            return user;
        } catch (Exception e) {
            LoggerUtil.logError("Error registering user: " + user.getEmail(), e);
            return null;
        }
    }

    @Override
    public User login(String email, String password) {
        try {
            User user = userDAO.login(email, password);
            if (user != null) {
                LoggerUtil.logInfo("User logged in successfully: " + email);
            } else {
                LoggerUtil.logWarning("Invalid login attempt for email: " + email);
            }
            return user;
        } catch (Exception e) {
            LoggerUtil.logError("Error during login for email: " + email, e);
            return null;
        }
    }

    @Override
    public User viewProfile(int userId) {
        try {
            return userDAO.getUserById(userId);
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching user profile for userId: " + userId, e);
            return null;
        }
    }

    @Override
    public void updateProfile(User user) {
        try {
            userDAO.updateUser(user);
            LoggerUtil.logInfo("User profile updated for userId: " + user.getUserId());
        } catch (Exception e) {
            LoggerUtil.logError("Error updating profile for userId: " + user.getUserId(), e);
        }
    }

    @Override
    public boolean changePassword(int userId, String oldPassword, String newPassword) {
        try {
            boolean valid = userDAO.validatePassword(userId, oldPassword);
            if (!valid) {
                LoggerUtil.logWarning("Password change failed due to invalid old password for userId: " + userId);
                return false;
            }

            userDAO.changePassword(userId, newPassword);
            LoggerUtil.logInfo("Password changed successfully for userId: " + userId);
            return true;

        } catch (Exception e) {
            LoggerUtil.logError("Error changing password for userId: " + userId, e);
            return false;
        }
    }

    @Override
    public boolean forgotPassword(String email, String newPassword) {
        try {
            boolean updated = userDAO.updatePasswordByEmail(email, newPassword);
            if (updated) {
                LoggerUtil.logInfo("Password reset successfully for email: " + email);
            } else {
                LoggerUtil.logWarning("Password reset failed for email: " + email);
            }
            return updated;
        } catch (Exception e) {
            LoggerUtil.logError("Error during forgot password for email: " + email, e);
            return false;
        }
    }
}
