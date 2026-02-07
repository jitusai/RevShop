package com.revshop.dao;

import com.revshop.model.PasswordRecovery;
import com.revshop.util.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PasswordRecoveryDAOImpl implements IPasswordRecoveryDAO {

    @Override
    public boolean recoverPassword(String email) {
        // Just verify email exists, logic handled in service
        return findByEmail(email) != null;
    }

    @Override
    public PasswordRecovery findByEmail(String email) {
        String sql = "SELECT pr.recovery_id, pr.user_id, pr.security_question, pr.security_answer_hash, pr.updated_at " +
                     "FROM users u JOIN password_recovery pr ON u.user_id = pr.user_id WHERE u.email = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                PasswordRecovery pr = new PasswordRecovery();
                pr.setRecoveryId(rs.getInt("recovery_id"));
                pr.setUserId(rs.getInt("user_id"));
                pr.setSecurityQuestion(rs.getString("security_question"));
                pr.setSecurityAnswerHash(rs.getString("security_answer_hash"));
                pr.setUpdatedAt(rs.getDate("updated_at"));
                return pr;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean verifySecurityAnswer(int userId, String answer) {
        String sql = "SELECT 1 FROM password_recovery WHERE user_id = ? AND security_answer_hash = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setString(2, com.revshop.util.PasswordUtil.hash(answer)); // Assuming PasswordUtil exists, otherwise direct compare
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean resetPassword(int userId, String newPassword) {
        String updateUserSql = "UPDATE users SET password_hash = ?, updated_at = SYSDATE WHERE user_id = ?";
        String updateRecoverySql = "UPDATE password_recovery SET updated_at = SYSDATE WHERE user_id = ?";
        try (Connection con = JDBCUtil.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement ps1 = con.prepareStatement(updateUserSql);
                 PreparedStatement ps2 = con.prepareStatement(updateRecoverySql)) {
                ps1.setString(1, newPassword); // Hash if needed
                ps1.setInt(2, userId);
                ps1.executeUpdate();
                ps2.setInt(1, userId);
                ps2.executeUpdate();
                con.commit();
                return true;
            } catch (Exception e) {
                con.rollback();
                throw e;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
