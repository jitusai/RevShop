package com.revshop.dao;

public interface ICartDAO {

    int removeCartItem(int userId, int productId);
    boolean cartItemExists(int userId, int productId);
    int getCurrentQuantity(int userId, int productId);
    void addToCart(int userId, int productId, int qty);
    int updateCartItem(int userId, int productId, int qty);

    void viewCart(int userId);
    
    java.util.List<com.revshop.model.CartItem> getCartItems(int userId);

    void clearCart(int userId);
}
