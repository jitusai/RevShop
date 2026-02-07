package com.revshop.service;

import java.util.List;

import com.revshop.dao.IFavoriteDAO;
import com.revshop.dao.FavoriteDAOImpl;
import com.revshop.model.Favorite;
import com.revshop.util.LoggerUtil;

public class FavoriteServiceImpl implements IFavoriteService {

    private IFavoriteDAO favoriteDao = new FavoriteDAOImpl();

    @Override
    public boolean addFavorite(Favorite favorite) {
        try {
            boolean success = favoriteDao.addFavorite(favorite);

            if (success) {
                LoggerUtil.logInfo(
                        "Favorite added. UserId: " + favorite.getUserId() +
                                ", ProductId: " + favorite.getProductId()
                );
            } else {
                LoggerUtil.logWarning("Failed to add favorite");
            }

            return success;
        } catch (Exception e) {
            LoggerUtil.logError("Error while adding favorite", e);
            return false;
        }
    }

    @Override
    public List<Favorite> viewFavorites(int userId) {
        try {
            LoggerUtil.logInfo("Fetching favorites for userId: " + userId);
            return favoriteDao.getFavoritesByUser(userId);
        } catch (Exception e) {
            LoggerUtil.logError("Error while fetching favorites for userId: " + userId, e);
            return List.of();
        }
    }

    @Override
    public void removeFavorite(int favoriteId) {
        try {
            favoriteDao.removeFavorite(favoriteId);
            LoggerUtil.logInfo("Favorite removed. FavoriteId: " + favoriteId);
        } catch (Exception e) {
            LoggerUtil.logError("Error while removing favorite. FavoriteId: " + favoriteId, e);
        }
    }
}
