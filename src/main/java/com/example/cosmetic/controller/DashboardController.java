package com.example.cosmetic.controller;

import com.example.cosmetic.service.StatisticsService;
import com.example.cosmetic.view.dashboard.DashboardPanel;

import javax.swing.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DashboardController {
    private final DashboardPanel view;
    private final StatisticsService statisticsService;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public DashboardController(DashboardPanel view, StatisticsService statisticsService) {
        this.view = view;
        this.statisticsService = statisticsService;
        
        loadDashboardData();
    }

    public void loadDashboardData() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            private BigDecimal todayRevenue;
            private long totalCustomers;
            private long outOfStock;
            private List<Object[]> expiringList;
            private List<Object[]> topSellingList;

            @Override
            protected Void doInBackground() throws Exception {
                todayRevenue = statisticsService.getTodayRevenue();
                totalCustomers = statisticsService.getTotalCustomers();
                outOfStock = statisticsService.getOutOfStockProducts();
                expiringList = statisticsService.getExpiringProducts(30);
                topSellingList = statisticsService.getTopSellingProducts();
                return null;
            }

            @Override
            protected void done() {
                try {
                    String ds = currencyFormat.format(todayRevenue).replace("₫", "VNĐ");
                    view.getLblRevenue().setText(ds);
                    view.getLblTotalCustomers().setText(String.valueOf(totalCustomers));
                    view.getLblOutOfStock().setText(String.valueOf(outOfStock));
                    view.getLblExpiring().setText(String.valueOf(expiringList != null ? expiringList.size() : 0));

                    view.getModelExpiring().setRowCount(0);
                    if (expiringList != null) {
                        for (Object[] row : expiringList) {
                            String name = (String) row[0];
                            Integer qty = (Integer) row[1];
                            Date expiryDate = (Date) row[2];
                            view.getModelExpiring().addRow(new Object[]{
                                    name, qty, expiryDate != null ? sdf.format(expiryDate) : "N/A"
                            });
                        }
                    }

                    view.getModelTopSelling().setRowCount(0);
                    if (topSellingList != null) {
                        for (Object[] row : topSellingList) {
                            String name = (String) row[0];
                            Long qty = ((Number) row[1]).longValue();
                            BigDecimal rev = (BigDecimal) row[2];
                            Date lastInvoice = (Date) row[3];
                            view.getModelTopSelling().addRow(new Object[]{
                                    name, qty, 
                                    rev != null ? currencyFormat.format(rev).replace("₫", "VNĐ") : "0 VNĐ",
                                    lastInvoice != null ? sdf.format(lastInvoice) : "N/A"
                            });
                        }
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Lỗi khi tải dữ liệu trang chủ: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
}
