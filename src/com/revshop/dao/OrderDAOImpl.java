package com.revshop.dao;

import com.revshop.model.Order;
import com.revshop.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OrderDAOImpl implements IOrderDAO {


    @Override
    public boolean placeOrder(Order order) {
        throw new UnsupportedOperationException(
                "placeOrder should be handled in Service layer"
        );
    }

    @Override
    public int insertOrder(Order order) {

        String sql =
                "INSERT INTO orders (order_id, user_id, order_date, total_amount, status) " +
                        "VALUES (orders_seq.NEXTVAL, ?, SYSTIMESTAMP, ?, 'PLACED')";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps =
                     con.prepareStatement(sql, new String[]{"ORDER_ID"})) {

            ps.setInt(1, order.getUserId());
            ps.setDouble(2, order.getTotalAmount());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }
}
