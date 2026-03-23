package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Product;
import com.example.cosmetic.repository.ProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class ProductRepositoryImpl implements ProductRepository {
    @Override
    public List<Product> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p JOIN FETCH p.category JOIN FETCH p.brand", Product.class).getResultList();
        } finally { em.close(); }
    }

    @Override
    public void save(Product p) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.persist(p); tx.commit(); } catch (Exception e) { tx.rollback(); throw e; } finally { em.close(); }
    }

    @Override
    public void update(Product p) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.merge(p); tx.commit(); } catch (Exception e) { tx.rollback(); throw e; } finally { em.close(); }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try { tx.begin(); Product p = em.find(Product.class, id); if (p != null) em.remove(p); tx.commit(); } catch (Exception e) { tx.rollback(); throw e; } finally { em.close(); }
    }

    @Override
    public List<Product> searchByName(String keyword) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT p FROM Product p WHERE p.name LIKE :kw", Product.class)
                     .setParameter("kw", "%" + keyword + "%").getResultList();
        } finally { em.close(); }
    }
}