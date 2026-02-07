package com.revshop.controller;

import com.revshop.model.Review;
import com.revshop.service.IReviewService;
import com.revshop.service.ReviewServiceImpl;

import java.util.List;
import java.util.Scanner;

public class ReviewController {

    private final IReviewService reviewService;
    private final Scanner sc;

    public ReviewController(Scanner sc) {
        try {
            this.reviewService = new ReviewServiceImpl();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.sc = sc;
    }

    public void addReview(int userId) {
        System.out.print("Enter Product ID to review: ");
        int productId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Rating (1-5): ");
        int rating = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Comment: ");
        String comment = sc.nextLine();

        Review review = new Review();
        review.setAccept(true);
        review.setProductId(productId);
        review.setUserId(userId);
        review.setRating(rating);
        review.setReviewComment(comment);
        review.setReviewDate(new java.sql.Timestamp(System.currentTimeMillis()));

        boolean success = reviewService.addReview(review);
        if (success) {
            System.out.println("Review added successfully!");
        } else {
            System.out.println("Failed to add review.");
        }
    }
}
