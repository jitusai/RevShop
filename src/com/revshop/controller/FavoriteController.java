package com.revshop.controller;

import java.util.List;
import java.util.Scanner;

import com.revshop.model.Favorite;
import com.revshop.service.IFavoriteService;
import com.revshop.service.FavoriteServiceImpl;

public class FavoriteController {

    private IFavoriteService favoriteService = new FavoriteServiceImpl();
    private Scanner sc;

    public FavoriteController(Scanner sc) {
        this.sc = sc;
    }

    public void addFavorite(int userId, int productId) {

        Favorite favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(productId);

        boolean isAdded = favoriteService.addFavorite(favorite);

        if (isAdded) {
            System.out.println("Product added to favorites");
        } else {
            System.out.println("Favorite already exists");
        }
    }

    public void viewFavorites(int userId) {

        List<Favorite> favorites = favoriteService.viewFavorites(userId);

        if (favorites.isEmpty()) {
            System.out.println("No favorites found");
            return;
        }

        for (Favorite f : favorites) {
            System.out.println(
                    "FavId: " + f.getFavoriteId() +
                            " ProductId: " + f.getProductId() +
                            " Date: " + f.getCreatedAt()
            );
        }
    }

    public void removeFavorite() {

        System.out.print("Enter Favorite Id: ");
        int favId = sc.nextInt();

        favoriteService.removeFavorite(favId);
        System.out.println("Favorite removed");
    }
}
