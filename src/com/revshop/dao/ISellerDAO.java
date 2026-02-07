package com.revshop.dao;

import com.revshop.model.Seller;
import java.util.List;

public interface ISellerDAO {

    void addSeller(Seller seller);

    Seller getSellerById(int sellerId);
    
    Seller getSellerByUserId(int userId);

    List<Seller> getAllSellers();
}
