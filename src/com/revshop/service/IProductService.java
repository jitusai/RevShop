package com.revshop.service;

import com.revshop.model.Product;
import java.util.List;

public interface IProductService {

    boolean addProduct(Product product);

    Product getProductById(int productId);

    boolean updateProduct(int productId, String name, double price);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    boolean updateStock(int productId, int qty);

    List<Product> getAllProducts();

    boolean productExists(int productId);

    List<Product> getProductsByCategory(int categoryId);

    List<Product> getProductsBySeller(int sellerId);
}
