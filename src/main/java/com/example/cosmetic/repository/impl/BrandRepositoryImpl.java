package com.example.cosmetic.repository.impl;
import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Brand;
import com.example.cosmetic.repository.BrandRepository;
import jakarta.persistence.*;
import java.util.List;

public class BrandRepositoryImpl implements BrandRepository {
    @Override public List<Brand> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.createQuery("SELECT b FROM Brand b", Brand.class).getResultList(); } finally { em.close(); }
    }
    @Override public Brand findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.find(Brand.class, id); } finally { em.close(); }
    }
    @Override public void save(Brand brand) {
        EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.persist(brand); tx.commit(); } catch (Exception e) { if (tx.isActive()) tx.rollback(); throw e; } finally { em.close(); }
    }
    @Override public void update(Brand brand) {
        EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.merge(brand); tx.commit(); } catch (Exception e) { if (tx.isActive()) tx.rollback(); throw e; } finally { em.close(); }
    }
    @Override public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction();
        try { tx.begin(); Brand brand = em.find(Brand.class, id); if (brand != null) em.remove(brand); tx.commit(); } catch (Exception e) { if (tx.isActive()) tx.rollback(); throw e; } finally { em.close(); }
    }
}