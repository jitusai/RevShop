package com.revshop.model;

public class Review {

    private int reviewId;
    private int productId;
    private int userId;
    private int rating;
    private String reviewComment;

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewComment() {
        return reviewComment;
    }

    public void setReviewComment(String reviewComment) {
        this.reviewComment = reviewComment;
    }

    private java.sql.Timestamp reviewDate;
    private boolean accept;

    public java.sql.Timestamp getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(java.sql.Timestamp reviewDate) {
        this.reviewDate = reviewDate;
    }

    public boolean isAccept() {
        return accept;
    }

    public void setAccept(boolean accept) {
        this.accept = accept;
    }
}