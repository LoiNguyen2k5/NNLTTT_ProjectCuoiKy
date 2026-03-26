package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Supplier;
import com.example.cosmetic.repository.SupplierRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class SupplierRepositoryImpl implements SupplierRepository {

    @Override
    public List<Supplier> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Supplier s", Supplier.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Supplier findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.find(Supplier.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Supplier supplier) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(supplier);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Supplier supplier) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(supplier);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Supplier supplier = em.find(Supplier.class, id);
            if (supplier != null) {
                em.remove(supplier);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Supplier> searchByName(String keyword) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT s FROM Supplier s WHERE s.name LIKE :keyword OR s.phone LIKE :keyword OR s.address LIKE :keyword";
            return em.createQuery(jpql, Supplier.class)
                     .setParameter("keyword", "%" + keyword + "%")
                     .getResultList();
        } finally {
            em.close();
        }
    }
}