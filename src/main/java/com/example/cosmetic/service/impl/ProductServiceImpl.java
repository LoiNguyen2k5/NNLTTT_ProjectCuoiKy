package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Product;
import com.example.cosmetic.repository.ProductRepository;
import com.example.cosmetic.service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;
    public ProductServiceImpl(ProductRepository repo) { this.repo = repo; }

    @Override public List<Product> getAllProducts() { return repo.findAll(); }
    @Override public List<Product> searchProducts(String kw) { return repo.searchByName(kw); }

    @Override
    public void addProduct(Product p) throws Exception {
        if (p.getBarcode().isEmpty() || p.getName().isEmpty()) throw new Exception("Barcode và Tên không được để trống!");
        if (p.getPrice().doubleValue() < 0) throw new Exception("Giá không được âm!"); 
        repo.save(p);
    }

    @Override
    public void updateProduct(Product p) throws Exception { repo.update(p); }
    @Override
    public void deleteProduct(Long id) throws Exception { repo.delete(id); }
}