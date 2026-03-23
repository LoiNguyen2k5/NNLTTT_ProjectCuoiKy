package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Invoice;
import com.example.cosmetic.model.entity.InvoiceDetail;
import com.example.cosmetic.model.entity.Product;
import com.example.cosmetic.repository.InvoiceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class InvoiceRepositoryImpl implements InvoiceRepository {

    @Override
    public void createInvoice(Invoice invoice) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // 1. Lưu thông tin hóa đơn tổng trước
            em.persist(invoice);

            // 2. Duyệt qua danh sách chi tiết sản phẩm trong giỏ hàng
            for (InvoiceDetail detail : invoice.getDetails()) {
                // Tìm sản phẩm trong DB để kiểm tra tồn kho
                Product p = em.find(Product.class, detail.getProduct().getId());
                
                if (p == null) {
                    throw new Exception("Sản phẩm ID " + detail.getProduct().getId() + " không tồn tại!");
                }

                // Kiểm tra số lượng tồn kho có đủ bán không
                if (p.getQuantity() < detail.getQuantity()) {
                    throw new Exception("Sản phẩm [" + p.getName() + "] chỉ còn " + p.getQuantity() + " món, không đủ bán!");
                }

                // THỰC HIỆN TRỪ KHO: Cập nhật số lượng mới cho sản phẩm
                p.setQuantity(p.getQuantity() - detail.getQuantity());
                em.merge(p); 
                
                // Thiết lập mối quan hệ ngược lại cho Detail và lưu vào DB
                detail.setInvoice(invoice);
                em.persist(detail);
            }

            // Nếu mọi thứ ok thì chốt giao dịch
            tx.commit();
        } catch (Exception e) {
            // Nếu có bất kỳ lỗi nào (ví dụ: thiếu hàng), trả lại trạng thái cũ hoàn toàn
            if (tx.isActive()) tx.rollback();
            throw e; 
        } finally {
            em.close();
        }
    }

    @Override
    public Invoice findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Invoice.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Invoice> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT i FROM Invoice i ORDER BY i.invoiceDate DESC", Invoice.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }
}