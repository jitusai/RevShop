package com.revshop.service;

import com.revshop.dao.CartDAOImpl;
import com.revshop.dao.ICartDAO;
import com.revshop.model.CartItem;
import com.revshop.util.LoggerUtil;

import java.util.List;

public class CartServiceImpl implements ICartService {

    private ICartDAO dao = new CartDAOImpl();

    @Override
    public void addProductToCart(int userId, int productId, int quantity) {
        try {
            dao.addToCart(userId, productId, quantity);
            LoggerUtil.logInfo(
                    "Product added to cart. UserId: " + userId +
                            ", ProductId: " + productId +
                            ", Quantity: " + quantity
            );
        } catch (Exception e) {
            LoggerUtil.logError(
                    "Error adding product to cart. UserId: " + userId +
                            ", ProductId: " + productId,
                    e
            );
        }
    }

    @Override
    public List<CartItem> viewCartItems(int userId) {
        try {
            LoggerUtil.logInfo("Fetching cart items for userId: " + userId);
            return dao.getCartItems(userId);   // no casting
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching cart items for userId: " + userId, e);
            return List.of();
        }
    }

    @Override
    public void updateCartItem(int userId, int productId, int quantity) {
        try {
            dao.updateCartItem(userId, productId, quantity);
            LoggerUtil.logInfo(
                    "Cart item updated. UserId: " + userId +
                            ", ProductId: " + productId +
                            ", Quantity: " + quantity
            );
        } catch (Exception e) {
            LoggerUtil.logError(
                    "Error updating cart item. UserId: " + userId +
                            ", ProductId: " + productId,
                    e
            );
        }
    }

    @Override
    public void removeProductFromCart(int userId, int productId) {
        try {
            dao.removeCartItem(userId, productId);
            LoggerUtil.logInfo(
                    "Product removed from cart. UserId: " + userId +
                            ", ProductId: " + productId
            );
        } catch (Exception e) {
            LoggerUtil.logError(
                    "Error removing product from cart. UserId: " + userId +
                            ", ProductId: " + productId,
                    e
            );
        }
    }

    @Override
    public void clearCart(int userId) {
        try {
            dao.clearCart(userId);   // only works if method exists in ICartDAO
            LoggerUtil.logInfo("Cart cleared for userId: " + userId);
        } catch (Exception e) {
            LoggerUtil.logError("Error clearing cart for userId: " + userId, e);
        }
    }
}
