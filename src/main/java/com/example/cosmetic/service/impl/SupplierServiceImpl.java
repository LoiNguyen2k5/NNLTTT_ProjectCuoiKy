package com.example.cosmetic.service.impl;
import com.example.cosmetic.model.entity.Supplier; import com.example.cosmetic.repository.SupplierRepository; import com.example.cosmetic.service.SupplierService; import java.util.List;
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repo;
    public SupplierServiceImpl(SupplierRepository repo) { this.repo = repo; }
    @Override public List<Supplier> getAllSuppliers() { return repo.findAll(); }
    @Override public void addSupplier(Supplier s) throws Exception { validate(s); repo.save(s); }
    @Override public void updateSupplier(Supplier s) throws Exception { if (s.getId() == null) throw new Exception("Chọn dữ liệu để sửa!"); validate(s); repo.update(s); }
    @Override public void deleteSupplier(Long id) throws Exception { if (id == null) throw new Exception("Chọn dữ liệu để xóa!"); repo.delete(id); }
    private void validate(Supplier s) throws Exception { if (s.getName() == null || s.getName().trim().isEmpty()) throw new Exception("Tên nhà cung cấp không được trống!"); }
}