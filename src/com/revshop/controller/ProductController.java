package com.revshop.controller;

import java.util.List;
import java.util.Scanner;

import com.revshop.model.Product;
import com.revshop.service.IProductService;
import com.revshop.service.ProductServiceImpl;

public class ProductController {

    private final IProductService productService;
    private final Scanner sc;

    public ProductController(Scanner sc) {
        this.productService = new ProductServiceImpl();
        this.sc = sc;
    }

    // VIEW ALL
    public void showProducts() {

        List<Product> products = productService.getAllProducts();

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        products.forEach(System.out::println);
    }

    //ADD PRODUCT
    public void addProduct(int sellerId) {

        Product p = new Product();
        p.setSellerId(sellerId);

        System.out.print("Category Id: ");
        p.setCategoryId(Integer.parseInt(sc.nextLine()));

        System.out.print("Name: ");
        p.setName(sc.nextLine());

        System.out.print("Description: ");
        p.setDescription(sc.nextLine());

        System.out.print("Price: ");
        p.setPrice(Double.parseDouble(sc.nextLine()));

        System.out.print("MRP: ");
        p.setMrp(Double.parseDouble(sc.nextLine()));

        System.out.print("Discount Price: ");
        p.setDiscountPrice(Double.parseDouble(sc.nextLine()));

        System.out.print("Stock Quantity: ");
        p.setStockQuantity(Integer.parseInt(sc.nextLine()));

        System.out.print("Stock Threshold: ");
        p.setStockThreshold(Integer.parseInt(sc.nextLine()));

        p.setActive(true);

        System.out.println(
                productService.addProduct(p)
                        ? "Product added successfully"
                        : "Failed to add product"
        );
    }

    // VIEW BY SELLER
    public void viewBySeller(int sellerId) {
        productService.getProductsBySeller(sellerId)
                .forEach(System.out::println);
    }

    // VIEW BY CATEGORY
    public void viewByCategory(int categoryId) {
        productService.getProductsByCategory(categoryId)
                .forEach(System.out::println);
    }

    // UPDATE STOCK
    public void updateStock() {

        System.out.print("Product Id: ");
        int productId = Integer.parseInt(sc.nextLine());

        System.out.print("Quantity to reduce: ");
        int qty = Integer.parseInt(sc.nextLine());

        System.out.println(
                productService.updateStock(productId, qty)
                        ? "Stock updated"
                        : "Stock update failed"
        );
    }
}
