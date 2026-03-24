package com.example.cosmetic.service.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.*;
import com.example.cosmetic.repository.ImportReceiptRepository;
import com.example.cosmetic.service.ImportReceiptService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;
import java.util.List;

public class ImportReceiptServiceImpl implements ImportReceiptService {
    private final ImportReceiptRepository repo;

    public ImportReceiptServiceImpl(ImportReceiptRepository repo) { this.repo = repo; }

    @Override
    public void processImport(ImportReceipt receipt) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            receipt.setReceiptCode("PN" + System.currentTimeMillis());
            receipt.setImportDate(new Date()); 
            em.persist(receipt);
            
            for (ImportReceiptDetail detail : receipt.getDetails()) {
                Product p = em.find(Product.class, detail.getProduct().getId());
                if (p == null) throw new Exception("Không tìm thấy sản phẩm!");
                
                // LOGIC CỘNG KHO
                p.setQuantity(p.getQuantity() + detail.getQuantity());
                em.merge(p);
                
                detail.setProduct(p);
                detail.setReceipt(receipt);
                em.persist(detail);
            }
            tx.commit(); 
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); 
            throw e;
        } finally { em.close(); }
    }

    @Override public List<ImportReceipt> getAllReceipts() { return repo.findAll(); }
}