package com.revshop.main;

import java.util.Scanner;

import com.revshop.controller.CartController;
import com.revshop.controller.CategoryController;
import com.revshop.controller.FavoriteController;
import com.revshop.controller.OrderController;
import com.revshop.controller.ProductController;
import com.revshop.controller.ReviewController;
import com.revshop.controller.SellerController;
import com.revshop.controller.UserController;
import com.revshop.controller.PaymentController;

import com.revshop.model.Seller;
import com.revshop.model.User;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // Controllers
        UserController userController = new UserController(sc);
        ProductController productController = new ProductController(sc);
        CategoryController categoryController = new CategoryController(productController, sc);
        CartController cartController = new CartController(sc);
        SellerController sellerController = new SellerController();
        OrderController orderController = new OrderController(sc);
        FavoriteController favoriteController = new FavoriteController(sc);
        ReviewController reviewController = new ReviewController(sc);
        PaymentController paymentController = new PaymentController(sc);

        User currentUser = null;

        while (true) {
            System.out.println("\n===== REVSHOP E-COMMERCE =====");

            // Start Up  Menu
            if (currentUser == null) {

                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                    continue;
                }

                switch (choice) {
                    case 1:
                        User newUser = userController.register();
                        if (newUser != null && "Seller".equalsIgnoreCase(newUser.getRole())) {

                            System.out.println("\nSeller Details Required:");
                            Seller seller = new Seller();
                            seller.setUserId(newUser.getUserId());

                            sc.nextLine(); // clear buffer

                            System.out.print("Business Name: ");
                            seller.setBusinessName(sc.nextLine());

                            System.out.print("GST Number: ");
                            seller.setGstNumber(sc.nextLine());

                            System.out.print("Address: ");
                            seller.setAddress(sc.nextLine());

                            sellerController.registerSeller(seller);
                        }
                        break;

                    case 2:
                        currentUser = userController.login();
                        break;

                    case 3:
                        System.out.println("Exiting the Page");
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("Invalid choice");
                }
            }

            //  BUYER MENU
            else if ("Buyer".equalsIgnoreCase(currentUser.getRole())) {

                System.out.println("\nBUYER MENU (" + currentUser.getName() + ")");
                System.out.println("1. Browse Products");
                System.out.println("2. Search by Category");
                System.out.println("3. Manage Cart");
                System.out.println("4. Favorites / Wishlist");
                System.out.println("5. Checkout");
                System.out.println("6. View Payments");
                System.out.println("7. Write Review");
                System.out.println("8. Logout");
                System.out.print("Enter choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                    continue;
                }

                switch (choice) {
                    case 1:
                        productController.showProducts();
                        break;

                    case 2:
                        categoryController.viewByCategory();
                        break;

                    case 3:
                        boolean back = false;
                        while (!back) {
                            System.out.println("\nCART MENU");
                            System.out.println("1. View Cart");
                            System.out.println("2. Add Item");
                            System.out.println("3. Update Item");
                            System.out.println("4. Remove Item");
                            System.out.println("5. Checkout");
                            System.out.println("6. Back");
                            System.out.print("Choice: ");

                            int c = Integer.parseInt(sc.nextLine());
                            switch (c) {
                                case 1 -> cartController.viewCart(currentUser.getUserId());
                                case 2 -> cartController.addItem(currentUser.getUserId());
                                case 3 -> cartController.updateItem(currentUser.getUserId());
                                case 4 -> cartController.removeItem(currentUser.getUserId());
                                case 5 -> orderController.checkout(currentUser.getUserId());
                                case 6 -> back = true;
                            }
                        }
                        break;

                    case 4:
                        System.out.println("1. Add Favorite");
                        System.out.println("2. View Favorites");
                        System.out.println("3. Remove Favorite");
                        System.out.print("Choice: ");

                        int favC = Integer.parseInt(sc.nextLine());

                        if (favC == 1) {
                            System.out.print("Enter Product Id: ");
                            int productId = Integer.parseInt(sc.nextLine());
                            favoriteController.addFavorite(currentUser.getUserId(), productId);
                        } else if (favC == 2) {
                            favoriteController.viewFavorites(currentUser.getUserId());
                        } else if (favC == 3) {
                            favoriteController.removeFavorite();
                        } else {
                            System.out.println("Invalid choice");
                        }
                        break;

                    case 5:
                        orderController.checkout(currentUser.getUserId());
                        break;

                    case 6:
                        paymentController.viewAllPayments();
                        break;

                    case 7:
                        reviewController.addReview(currentUser.getUserId());
                        break;

                    case 8:
                        currentUser = null;
                        System.out.println("Logged out.");
                        break;

                    default:
                        System.out.println("Invalid choice");
                }
            }

            // ================= SELLER MENU =================
            else if ("Seller".equalsIgnoreCase(currentUser.getRole())) {

                System.out.println("\nSELLER MENU (" + currentUser.getName() + ")");
                System.out.println("1. Add Product");
                System.out.println("2. View My Products");
                System.out.println("3. Update Stock");
                System.out.println("4. Manage Profile");
                System.out.println("5. Logout");
                System.out.print("Enter choice: ");

                int choice;
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!");
                    continue;
                }

                Seller s = sellerController.getSellerByUserId(currentUser.getUserId());
                if (s == null) {
                    System.out.println("Seller account missing. Contact admin.");
                    currentUser = null;
                    continue;

                }


                switch (choice) {
                    case 1 -> productController.addProduct(s.getSellerId());
                    case 2 -> productController.viewBySeller(s.getSellerId());
                    case 3 -> productController.updateStock();
                    case 4 -> System.out.println("Profile management not implemented yet.");
                    case 5 -> {
                        currentUser = null;
                        System.out.println("Logged out.");
                    }
                    default -> System.out.println("Invalid choice");
                }
            }
        }
    }
}
