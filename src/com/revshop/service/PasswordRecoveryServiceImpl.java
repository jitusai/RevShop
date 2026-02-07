package com.revshop.service;

import com.revshop.dao.IPasswordRecoveryDAO;
import com.revshop.dao.PasswordRecoveryDAOImpl;
import com.revshop.model.PasswordRecovery;
import com.revshop.util.LoggerUtil;

public class PasswordRecoveryServiceImpl implements IPasswordRecoveryService {

    private final IPasswordRecoveryDAO dao = new PasswordRecoveryDAOImpl();

    @Override
    public boolean recoverPassword(String email) {
        LoggerUtil.logInfo("Attempting to recover password for email: " + email);
        try {
            boolean success = dao.recoverPassword(email);
            if (success) {
                LoggerUtil.logInfo("Password recovery email sent successfully to: " + email);
            } else {
                LoggerUtil.logWarning("Password recovery failed for email: " + email);
            }
            return success;
        } catch (Exception e) {
            LoggerUtil.logError("Exception during password recovery for email: " + email, e);
            return false;
        }
    }

    @Override
    public String getSecurityQuestion(String email) {
        LoggerUtil.logInfo("Fetching security question for email: " + email);
        try {
            PasswordRecovery pr = dao.findByEmail(email);
            if (pr != null) {
                LoggerUtil.logInfo("Security question retrieved for email: " + email);
                return pr.getSecurityQuestion();
            } else {
                LoggerUtil.logWarning("No security question found for email: " + email);
                return null;
            }
        } catch (Exception e) {
            LoggerUtil.logError("Exception while fetching security question for email: " + email, e);
            return null;
        }
    }

    @Override
    public boolean validateAnswer(String email, String answer) {
        LoggerUtil.logInfo("Validating security answer for email: " + email);
        try {
            PasswordRecovery pr = dao.findByEmail(email);
            if (pr != null) {
                boolean valid = dao.verifySecurityAnswer(pr.getUserId(), answer);
                if (valid) {
                    LoggerUtil.logInfo("Security answer validated successfully for email: " + email);
                } else {
                    LoggerUtil.logWarning("Invalid security answer for email: " + email);
                }
                return valid;
            } else {
                LoggerUtil.logWarning("No record found to validate security answer for email: " + email);
                return false;
            }
        } catch (Exception e) {
            LoggerUtil.logError("Exception while validating security answer for email: " + email, e);
            return false;
        }
    }

    @Override
    public boolean resetPassword(String email, String newPassword) {
        LoggerUtil.logInfo("Attempting to reset password for email: " + email);
        try {
            if (newPassword.length() < 6) {
                LoggerUtil.logWarning("New password too short for email: " + email);
                return false;
            }

            PasswordRecovery pr = dao.findByEmail(email);
            if (pr != null) {
                boolean success = dao.resetPassword(pr.getUserId(), newPassword);
                if (success) {
                    LoggerUtil.logInfo("Password reset successfully for email: " + email);
                } else {
                    LoggerUtil.logWarning("Password reset failed for email: " + email);
                }
                return success;
            } else {
                LoggerUtil.logWarning("No user found for password reset with email: " + email);
                return false;
            }
        } catch (Exception e) {
            LoggerUtil.logError("Exception during password reset for email: " + email, e);
            return false;
        }
    }
}
