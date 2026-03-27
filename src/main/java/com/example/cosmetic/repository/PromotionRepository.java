package com.example.cosmetic.repository;

import com.example.cosmetic.model.entity.Promotion;
import java.util.List;

public interface PromotionRepository {
    List<Promotion> findAll();
    Promotion findById(Long id);
    Promotion findByCode(String code);
    void save(Promotion promotion);
    void update(Promotion promotion);
    void delete(Long id);
    List<Promotion> searchByCode(String code);
}
