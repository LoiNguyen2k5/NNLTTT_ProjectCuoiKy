package com.example.cosmetic.repository;
import java.math.BigDecimal;
import java.util.List;

public interface StatisticsRepository {
    long getTotalCustomers();
    long getOutOfStockProducts();
    BigDecimal getTodayRevenue();
    BigDecimal getTotalRevenue(); // Bổ sung Tổng doanh thu
    List<Object[]> getTopSellingProducts();
}