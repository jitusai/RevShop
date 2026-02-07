package com.revshop.controller;

import com.revshop.model.Seller;
import com.revshop.service.ISellerService;
import com.revshop.service.SellerServiceImpl;

public class SellerController {

    private ISellerService sellerService = new SellerServiceImpl();

    public void registerSeller(Seller seller) {
        sellerService.registerSeller(seller);
    }

    public Seller getSellerByUserId(int userId) {
        return sellerService.getSellerByUserId(userId);
    }

    public void viewSeller(int sellerId) {

        Seller seller = sellerService.getSellerById(sellerId);

        if (seller != null) {
            System.out.println("Seller ID     : " + seller.getSellerId());
            System.out.println("Business Name : " + seller.getBusinessName());
            System.out.println("GST Number    : " + seller.getGstNumber());
            System.out.println("Address       : " + seller.getAddress());
        } else {
            System.out.println("Seller not found");
        }
    }
}
