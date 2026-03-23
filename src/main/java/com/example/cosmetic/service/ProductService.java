package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Product;
import java.util.List;

public interface ProductService {
    // Lấy toàn bộ danh sách mỹ phẩm từ DB
    List<Product> getAllProducts();

    // Tìm kiếm sản phẩm theo tên
    List<Product> searchProducts(String keyword);

    // Thêm sản phẩm mới (có validate nghiệp vụ)
    void addProduct(Product product) throws Exception;

    // Cập nhật thông tin sản phẩm
    void updateProduct(Product product) throws Exception;

    // Xóa sản phẩm theo ID
    void deleteProduct(Long id) throws Exception;
}