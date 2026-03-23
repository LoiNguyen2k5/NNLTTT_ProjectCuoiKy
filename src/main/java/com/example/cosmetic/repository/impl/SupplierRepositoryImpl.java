package com.example.cosmetic.repository.impl;
import com.example.cosmetic.config.JpaUtil; import com.example.cosmetic.model.entity.Supplier; import com.example.cosmetic.repository.SupplierRepository; import jakarta.persistence.*; import java.util.List;
public class SupplierRepositoryImpl implements SupplierRepository {
    @Override public List<Supplier> findAll() { EntityManager em = JpaUtil.getEntityManager(); try { return em.createQuery("SELECT s FROM Supplier s", Supplier.class).getResultList(); } finally { em.close(); } }
    @Override public Supplier findById(Long id) { EntityManager em = JpaUtil.getEntityManager(); try { return em.find(Supplier.class, id); } finally { em.close(); } }
    @Override public void save(Supplier s) { EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction(); try { tx.begin(); em.persist(s); tx.commit(); } catch (Exception e) { if (tx.isActive()) tx.rollback(); throw e; } finally { em.close(); } }
    @Override public void update(Supplier s) { EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction(); try { tx.begin(); em.merge(s); tx.commit(); } catch (Exception e) { if (tx.isActive()) tx.rollback(); throw e; } finally { em.close(); } }
    @Override public void delete(Long id) { EntityManager em = JpaUtil.getEntityManager(); EntityTransaction tx = em.getTransaction(); try { tx.begin(); Supplier s = em.find(Supplier.class, id); if (s != null) em.remove(s); tx.commit(); } catch (Exception e) { if (tx.isActive()) tx.rollback(); throw e; } finally { em.close(); } }
}