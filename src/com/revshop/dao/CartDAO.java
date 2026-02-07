package com.revshop.dao;

import java.sql.*;
import java.util.List;

import com.revshop.model.CartItem;
import com.revshop.util.JDBCUtil;

public class CartDAO implements ICartDAO {
    @Override
    public void addToCart(int userId, int productId, int qty) {

        String insertSql =
                "INSERT INTO cart_items (cart_item_id, cart_id, product_id, quantity) " +
                        "VALUES (cart_items_seq.NEXTVAL, ?, ?, ?)";

        String updateSql =
                "UPDATE cart_items SET quantity = quantity + ? " +
                        "WHERE cart_id = ? AND product_id = ?";

        try (Connection con = JDBCUtil.getConnection()) {

            int cartId = getOrCreateCartId(userId, con);

            if (cartItemExists(cartId, productId, con)) {
                // UPDATE quantity
                try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                    ps.setInt(1, qty);
                    ps.setInt(2, cartId);
                    ps.setInt(3, productId);
                    ps.executeUpdate();
                }
            } else {
                // ➕ INSERT new row
                try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                    ps.setInt(1, cartId);
                    ps.setInt(2, productId);
                    ps.setInt(3, qty);
                    ps.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    public boolean cartItemExists(int userId, int productId) {

        String sql =
                "SELECT 1 FROM cart_items " +
                        "WHERE cart_id=(SELECT cart_id FROM carts WHERE user_id=?) " +
                        "AND product_id=?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeQuery().next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public int getCurrentQuantity(int userId, int productId) {

        String sql =
                "SELECT quantity FROM cart_items " +
                        "WHERE cart_id=(SELECT cart_id FROM carts WHERE user_id=?) " +
                        "AND product_id=?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    @Override
    public int updateCartItem(int userId, int productId, int qty) {

        String sql =
                "UPDATE cart_items SET quantity=? " +
                        "WHERE cart_id=(SELECT cart_id FROM carts WHERE user_id=?) " +
                        "AND product_id=?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, userId);
            ps.setInt(3, productId);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int removeCartItem(int userId, int productId) {

        String sql =
                "DELETE FROM cart_items " +
                        "WHERE cart_id=(SELECT cart_id FROM carts WHERE user_id=?) " +
                        "AND product_id=?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, productId);
            return ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public void viewCart(int userId) {

        String sql =
                "SELECT p.product_id, p.name, p.price, c.quantity " +
                        "FROM cart_items c " +
                        "JOIN products p ON c.product_id = p.product_id " +
                        "WHERE c.cart_id=(SELECT cart_id FROM carts WHERE user_id=?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            System.out.println("\n🛒 Your Cart:");
            boolean empty = true;

            while (rs.next()) {
                empty = false;
                System.out.println(
                        rs.getInt("product_id") + " | " +
                                rs.getString("name") + " | ₹" +
                                rs.getDouble("price") + " | Qty: " +
                                rs.getInt("quantity")
                );
            }

            if (empty) {
                System.out.println("Cart is empty.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<CartItem> getCartItems(int userId) {
        return List.of();
    }

    @Override
    public void clearCart(int userId) {

    }

    private int getOrCreateCartId(int userId, Connection con) throws Exception {

        // 1. Try to get existing cart
        String selectSql = "SELECT cart_id FROM carts WHERE user_id = ?";
        try (PreparedStatement ps = con.prepareStatement(selectSql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        }

        // 2. If not exists, create cart
        String insertSql =
                "INSERT INTO carts (cart_id, user_id, created_at) " +
                        "VALUES (carts_seq.NEXTVAL, ?, SYSDATE)";

        try (PreparedStatement ps = con.prepareStatement(insertSql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }

        // 3. Fetch newly created cart_id
        try (PreparedStatement ps = con.prepareStatement(selectSql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("cart_id");
            }
        }

        throw new RuntimeException("Unable to create cart for user");
    }

    private boolean cartItemExists(int cartId, int productId, Connection con) throws Exception {

        String sql = "SELECT quantity FROM cart_items WHERE cart_id = ? AND product_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            return ps.executeQuery().next();
        }
    }



}
