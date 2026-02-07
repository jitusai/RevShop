package com.revshop.controller;

import com.revshop.model.CartItem;
import com.revshop.service.CartServiceImpl;
import com.revshop.service.ICartService;

import java.util.List;
import java.util.Scanner;

public class CartController {

    private final ICartService cartService;
    private final Scanner sc;

    public CartController(Scanner sc) {
        this.cartService = new CartServiceImpl();
        this.sc = sc; // Shared scanner
    }

    public void addItem(int userId) {
        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine();

        cartService.addProductToCart(userId, productId, quantity);
        System.out.println("Item added to cart");
    }

    public void viewCart(int userId) {
        List<CartItem> items = cartService.viewCartItems(userId);

        if (items.isEmpty()) {
            System.out.println("Cart is empty");
        } else {
            System.out.println("\nYour Cart:");
            for (CartItem item : items) {
                System.out.println(
                        "Product ID: " + item.getProductId() +
                        " | Quantity: " + item.getQuantity()
                );
            }
        }
    }

    public void updateItem(int userId) {
        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();

        System.out.print("Enter New Quantity: ");
        int quantity = sc.nextInt();
        sc.nextLine(); // consume newline

        cartService.updateCartItem(userId, productId, quantity);
        System.out.println("Quantity updated");
    }

    public void removeItem(int userId) {
        System.out.print("Enter Product ID: ");
        int productId = sc.nextInt();
        sc.nextLine(); // consume newline

        cartService.removeProductFromCart(userId, productId);
        System.out.println("Product removed from cart");
    }

    public void clearCart(int userId) {
        cartService.clearCart(userId);
        System.out.println("Cart cleared");
    }
}
