package com.example.cosmetic.controller;

import com.example.cosmetic.service.StatisticsService;
import com.example.cosmetic.view.statistics.StatisticsPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.List;

public class StatisticsController {
    private final StatisticsService service;
    private final StatisticsPanel view;

    public StatisticsController(StatisticsService service, StatisticsPanel view) {
        this.service = service;
        this.view = view;
        
        view.getBtnRefresh().addActionListener(e -> loadData());
        loadData();
    }

    private void loadData() {
        try {
            java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm"); // Format ngày tháng đẹp

            // 1. Nạp số liệu vào 4 ô vuông lớn
            view.getLblTotalCustomers().setText(String.valueOf(service.getTotalCustomers()));
            view.getLblOutOfStock().setText(String.valueOf(service.getOutOfStockProducts()));
            view.getLblTodayRevenue().setText(df.format(service.getTodayRevenue()) + " đ");
            view.getLblTotalRevenue().setText(df.format(service.getTotalRevenue()) + " đ"); // Ô mới

            // 2. Nạp dữ liệu vào Bảng và Biểu đồ
            view.getTableModel().setRowCount(0);
            List<Object[]> topProducts = service.getTopSellingProducts();
            
            DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 

            for (Object[] row : topProducts) {
                String name = (String) row[0];
                long quantity = ((Number) row[1]).longValue();
                BigDecimal revenue = (BigDecimal) row[2];
                java.util.Date lastSaleDate = (java.util.Date) row[3]; // Cột Ngày bán gần nhất
                
                String formattedRevenue = df.format(revenue) + " đ";
                String formattedDate = lastSaleDate != null ? sdf.format(lastSaleDate) : "Chưa rõ";

                // Nhét đủ 4 cột vào bảng
                view.getTableModel().addRow(new Object[]{name, quantity, formattedRevenue, formattedDate});
                
                String shortName = name;
                if (shortName.length() > 15) shortName = shortName.substring(0, 15) + "...";
                
                dataset.addValue(revenue.doubleValue(), "Doanh Thu", shortName);
            }

            // 3. Khởi tạo Biểu đồ Cột
            JFreeChart barChart = ChartFactory.createBarChart(
                    "DOANH THU THEO SẢN PHẨM", 
                    "Sản Phẩm", 
                    "Doanh Thu (VND)", 
                    dataset, 
                    PlotOrientation.VERTICAL, 
                    false, true, false
            );
            
            ChartPanel chartPanel = new ChartPanel(barChart);
            
            // 4. Cập nhật biểu đồ lên UI
            view.getPnlChart().removeAll(); 
            view.getPnlChart().add(chartPanel, BorderLayout.CENTER);
            view.getPnlChart().validate();
            view.getPnlChart().repaint();

        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }
}