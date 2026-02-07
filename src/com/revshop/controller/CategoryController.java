package com.revshop.controller;

import java.util.Scanner;

public class CategoryController {

    private final ProductController productController;
    private final Scanner sc;

    public CategoryController(ProductController productController, Scanner sc) {
        this.productController = productController;
        this.sc = sc;
    }

    //VIEW BY CATEGORY
    public void viewByCategory() {
        System.out.print("Category Id: ");
        int id = Integer.parseInt(sc.nextLine());
        productController.viewByCategory(id);
    }
}
