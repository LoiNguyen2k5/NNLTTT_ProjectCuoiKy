package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.repository.StatisticsRepository;
import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class StatisticsRepositoryImpl implements StatisticsRepository {
    @Override
    public long getTotalCustomers() {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.createQuery("SELECT COUNT(c) FROM Customer c", Long.class).getSingleResult(); }
        finally { em.close(); }
    }

    @Override
    public long getOutOfStockProducts() {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.createQuery("SELECT COUNT(p) FROM Product p WHERE p.quantity = 0", Long.class).getSingleResult(); }
        finally { em.close(); }
    }

    @Override
    public BigDecimal getTodayRevenue() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            Date startOfDay = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            BigDecimal total = em.createQuery("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.invoiceDate >= :start", BigDecimal.class)
                                 .setParameter("start", startOfDay).getSingleResult();
            return total != null ? total : BigDecimal.ZERO;
        } finally { em.close(); }
    }

    // TÍNH TỔNG DOANH THU TOÀN BỘ THỜI GIAN
    @Override
    public BigDecimal getTotalRevenue() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            BigDecimal total = em.createQuery("SELECT SUM(i.totalAmount) FROM Invoice i", BigDecimal.class).getSingleResult();
            return total != null ? total : BigDecimal.ZERO;
        } finally { em.close(); }
    }

    // LẤY THÊM MAX(invoiceDate) LÀ NGÀY BÁN GẦN NHẤT
    @Override
    public List<Object[]> getTopSellingProducts() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT p.name, SUM(d.quantity), SUM(d.quantity * d.price), MAX(i.invoiceDate) " +
                          "FROM InvoiceDetail d JOIN d.product p JOIN d.invoice i " +
                          "GROUP BY p.id, p.name ORDER BY SUM(d.quantity) DESC";
            return em.createQuery(jpql, Object[].class).setMaxResults(10).getResultList();
        } finally { em.close(); }
    }

    @Override
    public List<Object[]> getExpiringProducts(int daysThreshold) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            LocalDate thresholdDateLocal = LocalDate.now().plusDays(daysThreshold);
            Date thresholdDate = Date.from(thresholdDateLocal.atStartOfDay(ZoneId.systemDefault()).toInstant());
            
            String jpql = "SELECT p.name, p.quantity, p.expirationDate FROM Product p " +
                          "WHERE p.expirationDate IS NOT NULL AND p.expirationDate <= :threshold " +
                          "AND p.quantity > 0 ORDER BY p.expirationDate ASC";
            
            return em.createQuery(jpql, Object[].class).setParameter("threshold", thresholdDate).getResultList();
        } finally { em.close(); }
    }
}