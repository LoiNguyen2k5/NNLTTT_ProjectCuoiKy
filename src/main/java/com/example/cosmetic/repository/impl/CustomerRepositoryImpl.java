package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Customer;
import com.example.cosmetic.repository.CustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository {
    @Override
    public List<Customer> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList(); } finally { em.close(); }
    }

    @Override
    public Customer findById(Long id) {
        EntityManager em = JpaUtil.getEntityManager();
        try { return em.find(Customer.class, id); } finally { em.close(); }
    }

    @Override
    public void save(Customer c) {
        EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.persist(c); tx.commit(); } catch (Exception e) { tx.rollback(); throw e; } finally { em.close(); }
    }

    @Override
    public void update(Customer c) {
        EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction();
        try { tx.begin(); em.merge(c); tx.commit(); } catch (Exception e) { tx.rollback(); throw e; } finally { em.close(); }
    }

    @Override
    public void delete(Long id) {
        EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction();
        try { tx.begin(); Customer c = em.find(Customer.class, id); if (c != null) em.remove(c); tx.commit(); } catch (Exception e) { tx.rollback(); throw e; } finally { em.close(); }
    }
}