package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Supplier;
import com.example.cosmetic.repository.SupplierRepository;
import com.example.cosmetic.service.SupplierService;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public void addSupplier(Supplier supplier) throws Exception {
        validateSupplier(supplier);
        supplierRepository.save(supplier);
    }

    @Override
    public void updateSupplier(Supplier supplier) throws Exception {
        if (supplier.getId() == null) throw new Exception("Không tìm thấy ID để cập nhật!");
        validateSupplier(supplier);
        supplierRepository.update(supplier);
    }

    @Override
    public void deleteSupplier(Long id) throws Exception {
        if (id == null) throw new Exception("Vui lòng chọn NCC cần xóa!");
        supplierRepository.delete(id);
    }

    @Override
    public List<Supplier> searchSuppliers(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return supplierRepository.findAll();
        }
        return supplierRepository.searchByName(keyword.trim());
    }

    private void validateSupplier(Supplier supplier) throws Exception {
        if (supplier.getName() == null || supplier.getName().trim().isEmpty()) {
            throw new Exception("Tên nhà cung cấp không được để trống!");
        }
    }
}