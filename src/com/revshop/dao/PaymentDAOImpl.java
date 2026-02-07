package com.revshop.dao;

import com.revshop.model.Payment;
import com.revshop.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAOImpl implements IPaymentDAO {

    private Connection connection = JDBCUtil.getConnection();

    public PaymentDAOImpl() throws Exception {
    }

    // CREATE
    @Override
    public boolean savePayment(Payment payment) {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select payments_seq.nextval from dual");
            rs.next();
            payment.setPaymentId(rs.getInt(1));

            String sql = "insert into payments values(?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, payment.getPaymentId());
            ps.setInt(2, payment.getOrderId());
            ps.setDouble(3, payment.getAmount());
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getPaymentStatus());

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // READ BY ID
    @Override
    public Payment getPaymentById(int paymentId) {
        try {
            String sql = "select * from payments where payment_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, paymentId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt(1));
                p.setOrderId(rs.getInt(2));
                p.setAmount(rs.getDouble(3));
                p.setPaymentMethod(rs.getString(4));
                p.setPaymentStatus(rs.getString(5));
                return p;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // READ ALL
    @Override
    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select * from payments");

            while (rs.next()) {
                Payment p = new Payment();
                p.setPaymentId(rs.getInt(1));
                p.setOrderId(rs.getInt(2));
                p.setAmount(rs.getDouble(3));
                p.setPaymentMethod(rs.getString(4));
                p.setPaymentStatus(rs.getString(5));
                list.add(p);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    // UPDATE
    @Override
    public boolean updatePaymentStatus(int paymentId, String status) {
        try {
            String sql = "update payments set payment_status=? where payment_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, status);
            ps.setInt(2, paymentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // DELETE
    @Override
    public boolean deletePayment(int paymentId) {
        try {
            String sql = "delete from payments where payment_id=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, paymentId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
