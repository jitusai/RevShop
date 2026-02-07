package com.revshop.model;

public class Product {

    private int productId;
    private int sellerId;
    private int categoryId;
    private String name;
    private String description;
    private double price;
    private double mrp;
    private double discountPrice;
    private int stockQuantity;
    private int stockThreshold;
    private boolean isActive;

    //  getters & setters

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getStockThreshold() {
        return stockThreshold;
    }

    public void setStockThreshold(int stockThreshold) {
        this.stockThreshold = stockThreshold;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }


    @Override
    public String toString() {
        return "Product ID: " + productId +
                " | Name: " + name +
                " | Price: ₹" + price +
                " | MRP: ₹" + mrp +
                " | Discount: ₹" + discountPrice +
                " | Stock: " + stockQuantity +
                " | Category ID: " + categoryId +
                " | Seller ID: " + sellerId +
                " | Active: " + (isActive ? "Yes" : "No");
    }

}
