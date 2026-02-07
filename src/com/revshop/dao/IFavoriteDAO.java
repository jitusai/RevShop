package com.revshop.dao;

import java.util.List;
import com.revshop.model.Favorite;

public interface IFavoriteDAO {

    boolean addFavorite(Favorite favorite);

    List<Favorite> getFavoritesByUser(int userId);

    void removeFavorite(int favoriteId);
}
