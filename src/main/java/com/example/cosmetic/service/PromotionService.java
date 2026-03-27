package com.example.cosmetic.service;

import com.example.cosmetic.model.entity.Promotion;
import java.math.BigDecimal;
import java.util.List;

public interface PromotionService {
    List<Promotion> getAllPromotions();
    Promotion getPromotionByCode(String code);
    void addPromotion(Promotion promotion) throws Exception;
    void updatePromotion(Promotion promotion) throws Exception;
    void deletePromotion(Long id) throws Exception;
    List<Promotion> searchPromotions(String keyword);
    
    // Áp dụng voucher, trả về số tiền được giảm
    BigDecimal calculateDiscount(String code, BigDecimal orderTotal) throws Exception;
}
