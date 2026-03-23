package com.example.cosmetic.service.impl;
import com.example.cosmetic.model.entity.Brand;
import com.example.cosmetic.repository.BrandRepository;
import com.example.cosmetic.service.BrandService;
import java.util.List;

public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    public BrandServiceImpl(BrandRepository brandRepository) { this.brandRepository = brandRepository; }
    @Override public List<Brand> getAllBrands() { return brandRepository.findAll(); }
    @Override public void addBrand(Brand brand) throws Exception { validate(brand); brandRepository.save(brand); }
    @Override public void updateBrand(Brand brand) throws Exception { if (brand.getId() == null) throw new Exception("Chọn dữ liệu để sửa!"); validate(brand); brandRepository.update(brand); }
    @Override public void deleteBrand(Long id) throws Exception { if (id == null) throw new Exception("Chọn dữ liệu để xóa!"); brandRepository.delete(id); }
    private void validate(Brand brand) throws Exception { if (brand.getName() == null || brand.getName().trim().isEmpty()) throw new Exception("Tên thương hiệu không được để trống!"); }
}