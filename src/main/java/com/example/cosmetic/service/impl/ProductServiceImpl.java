package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Product;
import com.example.cosmetic.repository.ProductRepository;
import com.example.cosmetic.service.ProductService;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository repo;
    
    public ProductServiceImpl(ProductRepository repo) { 
        this.repo = repo; 
    }

    @Override 
    public List<Product> getAllProducts() { 
        return repo.findAll(); 
    }
    
    @Override 
    public List<Product> searchProducts(String kw) { 
        return repo.searchByName(kw); 
    }

    @Override
    public void addProduct(Product p) throws Exception {
        if (p.getBarcode() == null || p.getBarcode().trim().isEmpty() || p.getName() == null || p.getName().trim().isEmpty()) {
            throw new Exception("Barcode và Tên không được để trống!");
        }
        if (p.getPrice() == null || p.getPrice().doubleValue() < 0) {
            throw new Exception("Giá không được âm!");
        }
        
        // Kiểm tra mã barcode đã tồn tại chưa
        List<Product> allProducts = repo.findAll();
        for (Product existingProduct : allProducts) {
            if (existingProduct.getBarcode().equals(p.getBarcode())) {
                throw new Exception("Mã Barcode này đã tồn tại trong hệ thống!");
            }
        }
        
        repo.save(p);
    }

    @Override
    public void updateProduct(Product p) throws Exception { 
        if (p.getBarcode() == null || p.getBarcode().trim().isEmpty() || p.getName() == null || p.getName().trim().isEmpty()) {
            throw new Exception("Barcode và Tên không được để trống!");
        }
        if (p.getPrice() == null || p.getPrice().doubleValue() < 0) {
            throw new Exception("Giá không được âm!");
        }
        repo.update(p); 
    }
    
    @Override
    public void deleteProduct(Long id) throws Exception { 
        repo.delete(id); 
    }
}