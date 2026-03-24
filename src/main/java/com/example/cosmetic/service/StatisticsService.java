package com.example.cosmetic.service;
import java.math.BigDecimal;
import java.util.List;

public interface StatisticsService {
    long getTotalCustomers();
    long getOutOfStockProducts();
    BigDecimal getTodayRevenue();
    BigDecimal getTotalRevenue(); // Bổ sung
    List<Object[]> getTopSellingProducts();
}