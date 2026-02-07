package com.revshop.service;

import com.revshop.model.Category;
import java.util.List;

public interface ICategoryService {

    boolean addCategory(Category category);

    List<Category> getAllCategories();
}
