package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Brand;
import com.example.cosmetic.repository.BrandRepository;
import com.example.cosmetic.service.BrandService;
import java.util.List;

public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public void addBrand(Brand brand) throws Exception {
        validateBrand(brand);
        brandRepository.save(brand);
    }

    @Override
    public void updateBrand(Brand brand) throws Exception {
        if (brand.getId() == null) throw new Exception("Không tìm thấy ID để cập nhật!");
        validateBrand(brand);
        brandRepository.update(brand);
    }

    @Override
    public void deleteBrand(Long id) throws Exception {
        if (id == null) throw new Exception("Vui lòng chọn thương hiệu cần xóa!");
        brandRepository.delete(id);
    }

    @Override
    public List<Brand> searchBrands(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return brandRepository.findAll();
        }
        return brandRepository.searchByName(keyword.trim());
    }

    private void validateBrand(Brand brand) throws Exception {
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new Exception("Tên thương hiệu không được để trống!");
        }
    }
}