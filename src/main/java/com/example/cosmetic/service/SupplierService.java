package com.example.cosmetic.service;
import com.example.cosmetic.model.entity.Supplier;
import java.util.List;
public interface SupplierService { List<Supplier>
     getAllSuppliers(); void addSupplier(Supplier supplier) throws Exception; void updateSupplier(Supplier supplier) throws Exception; void deleteSupplier(Long id) throws Exception; }