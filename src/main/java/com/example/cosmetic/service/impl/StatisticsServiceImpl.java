package com.example.cosmetic.service.impl;
import com.example.cosmetic.repository.StatisticsRepository;
import com.example.cosmetic.service.StatisticsService;
import java.math.BigDecimal;
import java.util.List;

public class StatisticsServiceImpl implements StatisticsService {
    private final StatisticsRepository repo;
    public StatisticsServiceImpl(StatisticsRepository repo) { this.repo = repo; }
    
    @Override public long getTotalCustomers() { return repo.getTotalCustomers(); }
    @Override public long getOutOfStockProducts() { return repo.getOutOfStockProducts(); }
    @Override public BigDecimal getTodayRevenue() { return repo.getTodayRevenue(); }
    @Override public BigDecimal getTotalRevenue() { return repo.getTotalRevenue(); } // Bổ sung
    @Override public List<Object[]> getTopSellingProducts() { return repo.getTopSellingProducts(); }
    @Override public List<Object[]> getExpiringProducts(int daysThreshold) { return repo.getExpiringProducts(daysThreshold); }
}