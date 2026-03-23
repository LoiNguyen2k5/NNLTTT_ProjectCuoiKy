package com.example.cosmetic.repository.impl;

import com.example.cosmetic.config.JpaUtil;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.repository.StaffRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

public class StaffRepositoryImpl implements StaffRepository {
    @Override
    public Staff findByUsername(String username) {
        EntityManager em = JpaUtil.getEntityManager();
        try {
            return em.createQuery("SELECT s FROM Staff s WHERE s.username = :username", Staff.class)
                     .setParameter("username", username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Không tìm thấy user
        } finally {
            em.close();
        }
    }
}