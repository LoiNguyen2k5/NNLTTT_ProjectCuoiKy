package com.example.cosmetic.service.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.*;
import com.example.cosmetic.repository.InvoiceRepository;
import com.example.cosmetic.service.InvoiceService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.Date;
import java.util.List;

public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository repo;

    public InvoiceServiceImpl(InvoiceRepository repo) {
        this.repo = repo;
    }

    @Override
    public void processCheckout(Invoice invoice) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        
        try {
            tx.begin();
            
            // Khởi tạo mã hóa đơn và ngày lập
            invoice.setInvoiceCode("HD" + System.currentTimeMillis());
            invoice.setInvoiceDate(new Date()); 
            
            // Lưu thông tin Hóa đơn
            em.persist(invoice);
            
            // Xử lý lưu Chi tiết và TRỪ KHO
            for (InvoiceDetail detail : invoice.getDetails()) {
                Product p = em.find(Product.class, detail.getProduct().getId());
                if (p == null) throw new Exception("Không tìm thấy sản phẩm!");
                
                if (p.getQuantity() < detail.getQuantity()) {
                    throw new Exception("Sản phẩm '" + p.getName() + "' không đủ hàng! (Chỉ còn " + p.getQuantity() + ")");
                }
                
                // Trừ kho
                p.setQuantity(p.getQuantity() - detail.getQuantity());
                em.merge(p);
                
                // Lưu chi tiết
                detail.setProduct(p);
                detail.setInvoice(invoice);
                em.persist(detail);
            }
            
            tx.commit(); // Thành công thì lưu toàn bộ
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback(); // Lỗi thì hủy toàn bộ
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return repo.findAll();
    }
}