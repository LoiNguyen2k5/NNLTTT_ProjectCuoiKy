package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.ImportReceipt;
import com.example.cosmetic.repository.ImportReceiptRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class ImportReceiptRepositoryImpl implements ImportReceiptRepository {
    @Override
    public List<ImportReceipt> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM ImportReceipt i ORDER BY i.id DESC", ImportReceipt.class).getResultList();
        } finally { em.close(); }
    }
}