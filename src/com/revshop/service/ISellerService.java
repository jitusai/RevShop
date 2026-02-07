package com.revshop.service;

import com.revshop.model.Seller;
import java.util.List;

public interface ISellerService {

    void registerSeller(Seller seller);

    Seller getSellerById(int sellerId);

    Seller getSellerByUserId(int userId);

    List<Seller> getAllSellers();
}
