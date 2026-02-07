package com.revshop.dao;

import java.util.List;
import com.revshop.model.Category;

public interface ICategoryDAOImpl {

    List<Category> getAllCategories();

    Category getCategoryById(int categoryId);
}
