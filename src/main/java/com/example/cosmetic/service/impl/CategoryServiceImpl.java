package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Category;
import com.example.cosmetic.repository.CategoryRepository;
import com.example.cosmetic.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void addCategory(Category category) throws Exception {
        validateCategory(category);
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Category category) throws Exception {
        if (category.getId() == null) {
            throw new Exception("Không tìm thấy ID để cập nhật!");
        }
        validateCategory(category);
        categoryRepository.update(category);
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        if (id == null) {
            throw new Exception("Vui lòng chọn loại mỹ phẩm cần xóa!");
        }
        categoryRepository.delete(id);
    }

    // Hàm dùng chung để check lỗi
    private void validateCategory(Category category) throws Exception {
        if (category.getName() == null || category.getName().trim().isEmpty()) {
            throw new Exception("Tên loại mỹ phẩm không được để trống!");
        }
    }
}