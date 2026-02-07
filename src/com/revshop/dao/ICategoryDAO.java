package com.revshop.dao;

import com.revshop.model.Category;
import java.util.List;

public interface ICategoryDAO {

    boolean addCategory(Category category);

    List<Category> getAllCategories();
}
