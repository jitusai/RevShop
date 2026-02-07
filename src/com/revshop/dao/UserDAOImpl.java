package com.revshop.dao;

import com.revshop.model.User;
import com.revshop.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAOImpl implements IUserDAO {

    // ================= CHECK EMAIL EXISTS =================
    private boolean emailExists(String email) {
        String sql = "SELECT 1 FROM users WHERE email = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ================= REGISTER =================
    @Override
    public int register(User user) {
        if (emailExists(user.getEmail())) {
            return -1;
        }

        String getMaxIdSql = "SELECT NVL(MAX(user_id), 0) FROM users";
        String insertSql = "INSERT INTO users (user_id, name, email, password_hash, phone, role) VALUES (?, ?, ?, ?, ?, ?)";
        int userId = -1;

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement psMax = con.prepareStatement(getMaxIdSql);
             ResultSet rs = psMax.executeQuery()) {

            if (rs.next()) {
                userId = rs.getInt(1) + 1;
            }

            try (PreparedStatement psInsert = con.prepareStatement(insertSql)) {
                psInsert.setInt(1, userId);
                psInsert.setString(2, user.getName());
                psInsert.setString(3, user.getEmail());
                psInsert.setString(4, user.getPassword());
                psInsert.setString(5, user.getPhone());
                psInsert.setString(6, user.getRole());
                psInsert.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        return userId;
    }

    // ================= LOGIN =================
    @Override
    public User login(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password_hash = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= GET USER BY ID =================
    @Override
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // ================= UPDATE PROFILE =================
    @Override
    public void updateUser(User user) {
        String sql = "UPDATE users SET name = ?, phone = ?, updated_at = SYSDATE WHERE user_id = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getPhone());
            ps.setInt(3, user.getUserId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VERIFY OLD PASSWORD
    @Override
    public boolean validatePassword(int userId, String password) {

        String sql = "SELECT 1 FROM users WHERE user_id = ? AND password_hash = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // CHANGE PASSWORD
    @Override
    public void changePassword(int userId, String newPassword) {

        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // FORGOT PASSWORD
    @Override
    public boolean updatePasswordByEmail(String email, String newPassword) {

        String sql = "UPDATE users SET password_hash = ? WHERE email = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setString(2, email);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
