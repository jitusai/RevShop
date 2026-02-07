package com.revshop.service;

import java.util.List;
import com.revshop.model.Favorite;

public interface IFavoriteService {

    boolean addFavorite(Favorite favorite);

    List<Favorite> viewFavorites(int userId);

    void removeFavorite(int favoriteId);
}
