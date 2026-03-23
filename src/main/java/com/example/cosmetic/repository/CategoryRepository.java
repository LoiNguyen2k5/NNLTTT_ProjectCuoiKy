package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Category;
import java.util.List;

public interface CategoryRepository {
    List<Category> findAll();
    Category findById(Long id);
    void save(Category category);
    void update(Category category);
    void delete(Long id);
}