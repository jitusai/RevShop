package com.revshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.revshop.model.Favorite;
import com.revshop.util.JDBCUtil;

public class FavoriteDAOImpl implements IFavoriteDAO {
    @Override
    public boolean addFavorite(Favorite favorite) {

        String sql =
                "INSERT INTO favorites (favorite_id, user_id, product_id) " +
                        "VALUES (favorites_seq.NEXTVAL, ?, ?)";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, favorite.getUserId());
            ps.setInt(2, favorite.getProductId());
            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            return false;
        }
    }


    @Override
    public List<Favorite> getFavoritesByUser(int userId) {

        List<Favorite> favorites = new ArrayList<>();
        String sql = "SELECT * FROM favorites WHERE user_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Favorite fav = new Favorite();
                fav.setFavoriteId(rs.getInt("favorite_id"));
                fav.setUserId(rs.getInt("user_id"));
                fav.setProductId(rs.getInt("product_id"));
                fav.setCreatedAt(rs.getTimestamp("created_at"));
                favorites.add(fav);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return favorites;
    }

    @Override
    public void removeFavorite(int favoriteId) {

        String sql = "DELETE FROM favorites WHERE favorite_id = ?";

        try (Connection con = JDBCUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, favoriteId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
