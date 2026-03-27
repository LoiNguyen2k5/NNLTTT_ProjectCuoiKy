package com.example.cosmetic.service.impl;

import com.example.cosmetic.model.entity.Promotion;
import com.example.cosmetic.repository.PromotionRepository;
import com.example.cosmetic.service.PromotionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class PromotionServiceImpl implements PromotionService {
    
    private final PromotionRepository repo;

    public PromotionServiceImpl(PromotionRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Promotion> getAllPromotions() {
        return repo.findAll();
    }

    @Override
    public Promotion getPromotionByCode(String code) {
        return repo.findByCode(code);
    }

    @Override
    public void addPromotion(Promotion promotion) throws Exception {
        if (promotion.getCode() == null || promotion.getCode().trim().isEmpty()) {
            throw new Exception("Mã khuyến mãi không được để trống!");
        }
        if (repo.findByCode(promotion.getCode()) != null) {
            throw new Exception("Mã khuyến mãi đã tồn tại!");
        }
        if (promotion.getDiscountPercent() <= 0 || promotion.getDiscountPercent() > 100) {
            throw new Exception("Phần trăm giảm giá phải từ 1 đến 100!");
        }
        if (promotion.getStartDate().after(promotion.getEndDate())) {
            throw new Exception("Ngày bắt đầu không được lớn hơn ngày kết thúc!");
        }
        repo.save(promotion);
    }

    @Override
    public void updatePromotion(Promotion promotion) throws Exception {
        Promotion existing = repo.findById(promotion.getId());
        if (existing == null) {
            throw new Exception("Không tìm thấy khuyến mãi để cập nhật!");
        }
        if (promotion.getDiscountPercent() <= 0 || promotion.getDiscountPercent() > 100) {
            throw new Exception("Phần trăm giảm giá phải từ 1 đến 100!");
        }
        if (promotion.getStartDate().after(promotion.getEndDate())) {
            throw new Exception("Ngày bắt đầu không được lớn hơn ngày kết thúc!");
        }
        repo.update(promotion);
    }

    @Override
    public void deletePromotion(Long id) throws Exception {
        if (repo.findById(id) == null) {
            throw new Exception("Không tìm thấy khuyến mãi để xóa!");
        }
        repo.delete(id);
    }

    @Override
    public List<Promotion> searchPromotions(String keyword) {
        return repo.searchByCode(keyword);
    }

    @Override
    public BigDecimal calculateDiscount(String code, BigDecimal orderTotal) throws Exception {
        Promotion p = repo.findByCode(code);
        if (p == null) {
            throw new Exception("Mã khuyến mãi không tồn tại!");
        }
        if (!p.isActive()) {
            throw new Exception("Mã khuyến mãi đã bị vô hiệu hóa!");
        }
        Date today = new Date();
        if (today.before(p.getStartDate()) || today.after(p.getEndDate())) {
            throw new Exception("Mã khuyến mãi không trong thời gian có hiệu lực!");
        }
        if (p.getMinPurchaseAmount() != null && orderTotal.compareTo(p.getMinPurchaseAmount()) < 0) {
            throw new Exception("Đơn hàng chưa đạt giá trị tối thiểu (" + p.getMinPurchaseAmount() + ") để áp dụng mã này!");
        }
        
        // Tính toán
        BigDecimal discount = orderTotal.multiply(new BigDecimal(p.getDiscountPercent())).divide(new BigDecimal(100));
        
        // Kiểm tra tiền Max Discount
        if (p.getMaxDiscountAmount() != null && p.getMaxDiscountAmount().compareTo(BigDecimal.ZERO) > 0) {
            if (discount.compareTo(p.getMaxDiscountAmount()) > 0) {
                discount = p.getMaxDiscountAmount();
            }
        }
        return discount;
    }
}
