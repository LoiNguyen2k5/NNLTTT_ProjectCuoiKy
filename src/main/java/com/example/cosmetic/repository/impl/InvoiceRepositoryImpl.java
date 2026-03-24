package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Invoice;
import com.example.cosmetic.repository.InvoiceRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepository {
    @Override
    public List<Invoice> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            // Lấy danh sách hóa đơn, sắp xếp mới nhất lên đầu
            return em.createQuery("SELECT i FROM Invoice i ORDER BY i.id DESC", Invoice.class).getResultList();
        } finally {
            em.close();
        }
    }
}