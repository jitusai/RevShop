package com.revshop.service;

import java.util.List;
import com.revshop.util.LoggerUtil;

import com.revshop.dao.IProductDAO;
import com.revshop.dao.ProductDAOImpl;
import com.revshop.model.Product;

public class ProductServiceImpl implements IProductService {

    private final IProductDAO productDAO = new ProductDAOImpl();

    //  ADD PRODUCT
    @Override
    public boolean addProduct(Product product) {
        try {
            boolean success = productDAO.addProduct(product);
            if (success) {
                LoggerUtil.logInfo("Product added: " + product.getName() + ", Seller ID: " + product.getSellerId());
            } else {
                LoggerUtil.logWarning("Failed to add product: " + product.getName() + ", Seller ID: " + product.getSellerId());
            }
            return success;
        } catch (Exception e) {
            LoggerUtil.logError("Error adding product: " + product.getName(), e);
            return false;
        }
    }

    //  GET PRODUCT BY ID
    @Override
    public Product getProductById(int productId) {
        try {
            Product product = productDAO.getProductById(productId);
            if (product != null) {
                LoggerUtil.logInfo("Fetched product by ID: " + productId);
            } else {
                LoggerUtil.logWarning("Product not found by ID: " + productId);
            }
            return product;
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching product by ID: " + productId, e);
            return null;
        }
    }

    //  UPDATE PRODUCT
    @Override
    public boolean updateProduct(int productId, String name, double price) {
        try {
            Product p = new Product();
            p.setProductId(productId);
            p.setName(name);
            p.setPrice(price);

            boolean updated = productDAO.updateProduct(p);
            if (updated) {
                LoggerUtil.logInfo("Product updated: ID=" + productId + ", Name=" + name + ", Price=" + price);
            } else {
                LoggerUtil.logWarning("Failed to update product: ID=" + productId);
            }
            return updated;
        } catch (Exception e) {
            LoggerUtil.logError("Error updating product ID: " + productId, e);
            return false;
        }
    }

    @Override
    public boolean updateProduct(Product product) {
        return false;
    }

    // ================= DELETE PRODUCT =================
    @Override
    public boolean deleteProduct(int productId) {
        try {
            boolean deleted = productDAO.deleteProduct(productId);
            if (deleted) {
                LoggerUtil.logInfo("Product deleted: ID=" + productId);
            } else {
                LoggerUtil.logWarning("Failed to delete product: ID=" + productId);
            }
            return deleted;
        } catch (Exception e) {
            LoggerUtil.logError("Error deleting product ID: " + productId, e);
            return false;
        }
    }

    //CHECK PRODUCT EXISTS
    @Override
    public boolean productExists(int productId) {
        try {
            boolean exists = productDAO.productExists(productId);
            LoggerUtil.logInfo("Checked product exists: ID=" + productId + " -> " + exists);
            return exists;
        } catch (Exception e) {
            LoggerUtil.logError("Error checking product exists ID: " + productId, e);
            return false;
        }
    }

    // VIEW ALL PRODUCTS
    @Override
    public List<Product> getAllProducts() {
        try {
            List<Product> products = productDAO.getAllProducts();
            LoggerUtil.logInfo("Fetched all products. Total: " + products.size());
            return products;
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching all products", e);
            return null;
        }
    }

    //  VIEW BY SELLER
    @Override
    public List<Product> getProductsBySeller(int sellerId) {
        try {
            List<Product> products = productDAO.getProductsBySeller(sellerId);
            LoggerUtil.logInfo("Fetched products by seller ID: " + sellerId + ", Total: " + products.size());
            return products;
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching products by seller ID: " + sellerId, e);
            return null;
        }
    }

    //  VIEW BY CATEGORY
    @Override
    public List<Product> getProductsByCategory(int categoryId) {
        try {
            List<Product> products = productDAO.getProductsByCategory(categoryId);
            LoggerUtil.logInfo("Fetched products by category ID: " + categoryId + ", Total: " + products.size());
            return products;
        } catch (Exception e) {
            LoggerUtil.logError("Error fetching products by category ID: " + categoryId, e);
            return null;
        }
    }

    //  UPDATE STOCK
    @Override
    public boolean updateStock(int productId, int qty) {
        try {
            boolean updated = productDAO.updateStock(productId, qty);
            if (updated) {
                LoggerUtil.logInfo("Stock updated for product ID: " + productId + ", Quantity reduced: " + qty);
            } else {
                LoggerUtil.logWarning("Failed to update stock for product ID: " + productId);
            }
            return updated;
        } catch (Exception e) {
            LoggerUtil.logError("Error updating stock for product ID: " + productId, e);
            return false;
        }
    }
}
