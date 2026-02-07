package com.revshop.dao;

import com.revshop.model.OrderItem;
import com.revshop.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderItemDAOImpl implements IOrderItemDAO {

    @Override
    public boolean insertOrderItem(OrderItem item) {

        String sql =
                "INSERT INTO order_items " +
                        "(order_id, product_id, seller_id, quantity) " +
                        "VALUES (?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderId());
            ps.setInt(2, item.getProductId());
            ps.setInt(3, item.getSellerId());
            ps.setInt(4, item.getQuantity());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
