package com.revshop.dao;

import com.revshop.model.Review;
import com.revshop.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ReviewDAOImpl implements IReviewDAO {

    private Connection connection = JDBCUtil.getConnection();

    // ✅ Constructor name fixed
    public ReviewDAOImpl() throws Exception {
    }

    @Override
    public boolean addReview(Review review) {
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select reviews_seq.nextval from dual");

            if (rs.next()) {
                review.setReviewId(rs.getInt(1));
            }

            String sql =
                    "insert into reviews(review_id, product_id, user_id, rating, review_comment) " +
                            "values(?,?,?,?,?)";

            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1, review.getReviewId());
            ps.setInt(2, review.getProductId());
            ps.setInt(3, review.getUserId());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getReviewComment());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
