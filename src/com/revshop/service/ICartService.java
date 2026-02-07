package com.revshop.service;

import com.revshop.model.CartItem;
import java.util.List;

public interface ICartService {

    void addProductToCart(int userId, int productId, int quantity);

    void updateCartItem(int userId, int productId, int quantity);

    void removeProductFromCart(int userId, int productId);

    List<CartItem> viewCartItems(int userId);

    void clearCart(int userId);
}
