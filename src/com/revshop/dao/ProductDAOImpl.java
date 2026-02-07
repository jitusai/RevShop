package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revshop.model.Product;
import com.revshop.util.JDBCUtil;

public class ProductDAOImpl implements IProductDAO {

    // ADD PRODUCT
    @Override
    public boolean addProduct(Product p) {

        String sql =
                "INSERT INTO products " +
                        "(product_id, seller_id, category_id, name, description, " +
                        "price, mrp, discount_price, stock_quantity, is_active) " +
                        "VALUES " +
                        "((SELECT NVL(MAX(product_id),0)+1 FROM products), " +
                        "?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, p.getSellerId());
            ps.setInt(2, p.getCategoryId());
            ps.setString(3, p.getName());
            ps.setString(4, p.getDescription());
            ps.setDouble(5, p.getPrice());
            ps.setDouble(6, p.getMrp());
            ps.setDouble(7, p.getDiscountPrice());
            ps.setInt(8, p.getStockQuantity());
            ps.setInt(9, p.isActive() ? 1 : 0);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // GET BY ID
    @Override
    public Product getProductById(int productId) {

        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return map(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // UPDATE PRODUCT
    @Override
    public boolean updateProduct(Product product) {

        String sql =
                "UPDATE products " +
                        "SET name = ?, description = ?, price = ?, mrp = ?, discount_price = ?, updated_at = SYSDATE " +
                        "WHERE product_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setString(2, product.getDescription());
            ps.setDouble(3, product.getPrice());
            ps.setDouble(4, product.getMrp());        // FIX
            ps.setDouble(5, product.getDiscountPrice());
            ps.setInt(6, product.getProductId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // DELETE PRODUCT
    @Override
    public boolean deleteProduct(int productId) {

        String sql = "DELETE FROM products WHERE product_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // CHECK EXISTS
    @Override
    public boolean productExists(int productId) {

        String sql = "SELECT 1 FROM products WHERE product_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, productId);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // VIEW ALL ACTIVE PRODUCTS
    @Override
    public List<Product> getAllProducts() {

        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE is_active = 1";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // VIEW BY SELLER
    @Override
    public List<Product> getProductsBySeller(int sellerId) {

        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE seller_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, sellerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // VIEW BY CATEGORY
    @Override
    public List<Product> getProductsByCategory(int categoryId) {

        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, categoryId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(map(rs));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // UPDATE STOCK
    @Override
    public boolean updateStock(int productId, int qty) {

        String sql =
                "UPDATE products " +
                        "SET stock_quantity = stock_quantity - ? " +
                        "WHERE product_id = ? AND stock_quantity >= ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, qty);
            ps.setInt(2, productId);
            ps.setInt(3, qty);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // RESULTSET MAPPER
    private Product map(ResultSet rs) throws Exception {

        Product p = new Product();
        p.setProductId(rs.getInt("product_id"));
        p.setSellerId(rs.getInt("seller_id"));
        p.setCategoryId(rs.getInt("category_id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setPrice(rs.getDouble("price"));
        p.setMrp(rs.getDouble("mrp"));
        p.setDiscountPrice(rs.getDouble("discount_price"));
        p.setStockQuantity(rs.getInt("stock_quantity"));
        p.setActive(rs.getInt("is_active") == 1);

        return p;
    }
}
