package com.revshop.dao;

import com.revshop.model.Seller;
import com.revshop.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SellerDAOImpl implements ISellerDAO {

    @Override
    public void addSeller(Seller seller) {

        String sql = "INSERT INTO sellers " +
            "(seller_id, user_id, business_name, gst_number, address, created_at) " +
            "VALUES (seller_seq.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // ✅ Set parameters INSIDE try block
            ps.setInt(1, seller.getUserId());
            ps.setString(2, seller.getBusinessName());
            ps.setString(3, seller.getGstNumber());
            ps.setString(4, seller.getAddress());

            ps.executeUpdate();
            System.out.println("Seller registered successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Seller getSellerById(int sellerId) {

        Seller seller = null;
        String sql = "SELECT * FROM sellers WHERE seller_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                seller = new Seller();
                seller.setSellerId(rs.getInt("seller_id"));
                seller.setUserId(rs.getInt("user_id"));
                seller.setBusinessName(rs.getString("business_name"));
                seller.setGstNumber(rs.getString("gst_number"));
                seller.setAddress(rs.getString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seller;
    }

    public Seller getSellerByUserId(int userId) {
        Seller seller = null;
        String sql = "SELECT * FROM sellers WHERE user_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                seller = new Seller();
                seller.setSellerId(rs.getInt("seller_id"));
                seller.setUserId(rs.getInt("user_id"));
                seller.setBusinessName(rs.getString("business_name"));
                seller.setGstNumber(rs.getString("gst_number"));
                seller.setAddress(rs.getString("address"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return seller;
    }

    public boolean userExists(int userId) {

        String sql = "SELECT 1 FROM users WHERE user_id = ?";
        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public List<Seller> getAllSellers() {

        List<Seller> sellers = new ArrayList<>();
        String sql = "SELECT * FROM sellers";

        try (Connection con = JDBCUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Seller seller = new Seller();
                seller.setSellerId(rs.getInt("seller_id"));
                seller.setUserId(rs.getInt("user_id"));
                seller.setBusinessName(rs.getString("business_name"));
                seller.setGstNumber(rs.getString("gst_number"));
                seller.setAddress(rs.getString("address"));
                sellers.add(seller);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sellers;
    }
}
