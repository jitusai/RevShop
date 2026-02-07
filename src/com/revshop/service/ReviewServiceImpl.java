package com.revshop.service;

import com.revshop.dao.IReviewDAO;
import com.revshop.dao.ReviewDAOImpl;
import com.revshop.model.Review;
import com.revshop.util.LoggerUtil;

public class ReviewServiceImpl implements IReviewService {

    private IReviewDAO reviewDao = new ReviewDAOImpl();

    // Constructor
    public ReviewServiceImpl() throws Exception {
        LoggerUtil.logInfo("ReviewServiceImpl initialized successfully.");
    }

    @Override
    public boolean addReview(Review review) {
        try {
            boolean success = reviewDao.addReview(review);
            if (success) {
                LoggerUtil.logInfo("Review added for Product ID: " + review.getProductId() +
                        ", User ID: " + review.getUserId() +
                        ", Rating: " + review.getRating());
            } else {
                LoggerUtil.logWarning("Failed to add review for Product ID: " + review.getProductId() +
                        ", User ID: " + review.getUserId());
            }
            return success;
        } catch (Exception e) {
            LoggerUtil.logError("Error adding review for Product ID: " + review.getProductId() +
                    ", User ID: " + review.getUserId(), e);
            return false;
        }
    }
}
