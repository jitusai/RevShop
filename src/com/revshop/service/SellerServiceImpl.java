package com.revshop.service;

import com.revshop.dao.ISellerDAO;
import com.revshop.dao.SellerDAOImpl;
import com.revshop.model.Seller;
import com.revshop.util.LoggerUtil;

import java.util.List;

public class SellerServiceImpl implements ISellerService {

    private ISellerDAO sellerDAO = new SellerDAOImpl();

    @Override
    public void registerSeller(Seller seller) {
        try {
            // Basic validation
            if (seller == null) {
                LoggerUtil.logWarning("Attempted to register null seller");
                System.out.println("Seller details cannot be null");
                return;
            }

            if (seller.getUserId() <= 0) {
                LoggerUtil.logWarning("Attempted to register seller with invalid User ID: " + seller.getUserId());
                System.out.println("Invalid User ID");
                return;
            }

            if (seller.getBusinessName() == null || seller.getBusinessName().isBlank()) {
                LoggerUtil.logWarning("Attempted to register seller without business name, User ID: " + seller.getUserId());
                System.out.println("Business name is required");
                return;
            }

            // Call DAO to add seller
            sellerDAO.addSeller(seller);
            LoggerUtil.logInfo("Seller registered successfully: " + seller.getBusinessName() + ", User ID: " + seller.getUserId());

        } catch (Exception e) {
            LoggerUtil.logError("Error while registering seller: " + (seller != null ? seller.getBusinessName() : "null"), e);
            System.out.println("An error occurred during seller registration.");
        }
    }

    @Override
    public Seller getSellerById(int sellerId) {
        try {
            return sellerDAO.getSellerById(sellerId);
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching seller by ID: " + sellerId, e);
            return null;
        }
    }

    @Override
    public Seller getSellerByUserId(int userId) {
        try {
            return sellerDAO.getSellerByUserId(userId);
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching seller by User ID: " + userId, e);
            return null;
        }
    }

    @Override
    public List<Seller> getAllSellers() {
        try {
            return sellerDAO.getAllSellers();
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching all sellers", e);
            return List.of();
        }
    }
}
