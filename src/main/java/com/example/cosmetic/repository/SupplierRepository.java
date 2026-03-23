package com.example.cosmetic.repository;
import com.example.cosmetic.model.entity.Supplier;
import java.util.List;
public interface SupplierRepository {
    List<Supplier> findAll(); Supplier findById(Long id); void save(Supplier supplier); void update(Supplier supplier); void delete(Long id);
}