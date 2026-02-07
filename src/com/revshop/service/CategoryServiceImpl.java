package com.revshop.service;

import com.revshop.dao.ICategoryDAO;
import com.revshop.dao.CategoryDAOImpl;
import com.revshop.model.Category;
import com.revshop.util.LoggerUtil;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {

    private ICategoryDAO categoryDao = new CategoryDAOImpl();

    @Override
    public boolean addCategory(Category category) {
        try {
            boolean success = categoryDao.addCategory(category);

            if (success) {
                LoggerUtil.logInfo("Category added successfully: " + category.getCategoryName());
            } else {
                LoggerUtil.logWarning("Failed to add category: " + category.getCategoryName());
            }

            return success;
        } catch (Exception e) {
            LoggerUtil.logError("Error while adding category", e);
            return false;
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try {
            LoggerUtil.logInfo("Fetching all categories");
            return categoryDao.getAllCategories();
        } catch (Exception e) {
            LoggerUtil.logError("Error while fetching categories", e);
            return List.of();
        }
    }
}
