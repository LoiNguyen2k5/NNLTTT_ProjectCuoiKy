package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.repository.StaffRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class StaffRepositoryImpl implements StaffRepository {

    @Override
    public List<Staff> findAll() {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT s FROM Staff s";
            TypedQuery<Staff> query = em.createQuery(jpql, Staff.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Staff findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            String jpql = "SELECT s FROM Staff s WHERE s.username = :username";
            TypedQuery<Staff> query = em.createQuery(jpql, Staff.class);
            query.setParameter("username", username);
            List<Staff> result = query.getResultList();
            return result.isEmpty() ? null : result.get(0);
        } finally {
            em.close();
        }
    }

    @Override
    public void save(Staff staff) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.persist(staff);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw new Exception("Lỗi khi lưu nhân viên vào Database!");
        } finally {
            em.close();
        }
    }

    @Override
    public void update(Staff staff) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            em.merge(staff);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw new Exception("Lỗi khi cập nhật nhân viên!");
        } finally {
            em.close();
        }
    }

    @Override
    public void delete(Long id) throws Exception {
        EntityManager em = JpaUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try {
            trans.begin();
            Staff staff = em.find(Staff.class, id);
            if (staff != null) {
                em.remove(staff);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            throw new Exception("Lỗi khi xóa nhân viên!");
        } finally {
            em.close();
        }
    }
}