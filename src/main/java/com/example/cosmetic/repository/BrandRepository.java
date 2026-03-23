package com.example.cosmetic.repository;
import com.example.cosmetic.model.entity.Brand;
import java.util.List;
public interface BrandRepository {
    List<Brand> findAll();
    Brand findById(Long id);
    void save(Brand brand);
    void update(Brand brand);
    void delete(Long id);
}