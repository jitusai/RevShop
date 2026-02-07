package com.revshop.dao;

import com.revshop.model.Product;
import java.util.List;

public interface IProductDAO {

    boolean addProduct(Product product);

    Product getProductById(int productId);

    boolean updateProduct(Product product);

    boolean deleteProduct(int productId);

    List<Product> getAllProducts();

    boolean productExists(int productId);

    List<Product> getProductsByCategory(int categoryId);

    List<Product> getProductsBySeller(int sellerId);

    boolean updateStock(int productId, int qty);
}
