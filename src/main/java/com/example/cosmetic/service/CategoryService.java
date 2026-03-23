package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Category;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void addCategory(Category category) throws Exception;
    void updateCategory(Category category) throws Exception;
    void deleteCategory(Long id) throws Exception;
}