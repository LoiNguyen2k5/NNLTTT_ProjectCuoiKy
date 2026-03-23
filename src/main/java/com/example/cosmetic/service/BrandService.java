package com.example.cosmetic.service;
import com.example.cosmetic.model.entity.Brand;
import java.util.List;
public interface BrandService {
    List<Brand> getAllBrands();
    void addBrand(Brand brand) throws Exception;
    void updateBrand(Brand brand) throws Exception;
    void deleteBrand(Long id) throws Exception;
}