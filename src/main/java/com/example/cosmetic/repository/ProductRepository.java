package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Product;
import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    void save(Product product);
    void update(Product product);
    void delete(Long id);
    List<Product> searchByName(String keyword);
}