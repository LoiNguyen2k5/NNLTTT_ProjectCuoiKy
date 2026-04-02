package com.example.cosmetic.controller;

import com.example.cosmetic.model.enums.StaffRole;
import com.example.cosmetic.service.StatisticsService;
import com.example.cosmetic.view.statistics.StatisticsPanel;
import com.example.cosmetic.view.utils.PDFExporter;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.JOptionPane;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatisticsController {
    private final StatisticsService service;
    private final StatisticsPanel view;
    private final StaffRole role;

    public StatisticsController(StatisticsService service, StatisticsPanel view, StaffRole role) {
        this.service = service;
        this.view = view;
        this.role = role;
        
        view.getBtnRefresh().addActionListener(e -> loadData());
        // Chi an nut neu la STAFF, nhung bao ve them o tang controller
        view.getBtnExportPDF().addActionListener(e -> {
            if (role == StaffRole.STAFF) {
                JOptionPane.showMessageDialog(view,
                        "Bạn không có quyền xuất báo cáo PDF.",
                        "Không có quyền", JOptionPane.WARNING_MESSAGE);
                return;
            }
            exportReportPDF();
        });
        loadData();
    }

    private void loadData() {
        try {
            java.text.DecimalFormat df = new java.text.DecimalFormat("#,###");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

            view.getLblTotalCustomers().setText(String.valueOf(service.getTotalCustomers()));
            view.getLblOutOfStock().setText(String.valueOf(service.getOutOfStockProducts()));
            view.getLblTodayRevenue().setText(df.format(service.getTodayRevenue()) + " đ");
            view.getLblTotalRevenue().setText(df.format(service.getTotalRevenue()) + " đ");

            // Xử lý Cảnh báo Hết hạn
            List<Object[]> expiringList = service.getExpiringProducts(30);
            view.getLblExpiringSoon().setText(String.valueOf(expiringList.size()));
            view.getExpiringModel().setRowCount(0);
            for (Object[] row : expiringList) {
                String name = (String) row[0];
                int quantity = (Integer) row[1];
                Date expDate = (Date) row[2];
                view.getExpiringModel().addRow(new Object[]{name, quantity, sdf.format(expDate)});
            }

            view.getTableModel().setRowCount(0);
            List<Object[]> topProducts = service.getTopSellingProducts();
            DefaultCategoryDataset dataset = new DefaultCategoryDataset(); 

            for (Object[] row : topProducts) {
                String name = (String) row[0];
                long quantity = ((Number) row[1]).longValue();
                BigDecimal revenue = (BigDecimal) row[2];
                Date lastSaleDate = (Date) row[3]; 
                
                String formattedRevenue = df.format(revenue) + " đ";
                String formattedDate = lastSaleDate != null ? sdf.format(lastSaleDate) : "Chưa rõ";

                view.getTableModel().addRow(new Object[]{name, quantity, formattedRevenue, formattedDate});
                
                String shortName = name;
                if (shortName.length() > 15) shortName = shortName.substring(0, 15) + "...";
                dataset.addValue(revenue.doubleValue(), "Doanh Thu", shortName);
            }

            JFreeChart barChart = ChartFactory.createBarChart(
                    "DOANH THU THEO SẢN PHẨM", "Sản Phẩm", "Doanh Thu (VND)", 
                    dataset, PlotOrientation.VERTICAL, false, true, false
            );
            
            // Cấu hình màu biểu đồ dựa trên Theme
            barChart.setBackgroundPaint(com.example.cosmetic.view.components.UITheme.getCardColor());
            org.jfree.chart.plot.CategoryPlot plot = barChart.getCategoryPlot();
            plot.setBackgroundPaint(com.example.cosmetic.view.components.UITheme.getBgColor());
            barChart.getTitle().setPaint(com.example.cosmetic.view.components.UITheme.getTextColor());
            plot.getDomainAxis().setTickLabelPaint(com.example.cosmetic.view.components.UITheme.getTextColor());
            plot.getDomainAxis().setLabelPaint(com.example.cosmetic.view.components.UITheme.getTextColor());
            plot.getRangeAxis().setTickLabelPaint(com.example.cosmetic.view.components.UITheme.getTextColor());
            plot.getRangeAxis().setLabelPaint(com.example.cosmetic.view.components.UITheme.getTextColor());
            plot.setDomainGridlinePaint(com.example.cosmetic.view.components.UITheme.getBorderColor());
            plot.setRangeGridlinePaint(com.example.cosmetic.view.components.UITheme.getBorderColor());

            ChartPanel chartPanel = new ChartPanel(barChart);
            view.getPnlChart().removeAll(); 
            view.getPnlChart().add(chartPanel, BorderLayout.CENTER);
            view.getPnlChart().validate();
            view.getPnlChart().repaint();

        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }

    // --- HÀM XỬ LÝ XUẤT PDF ---
    private void exportReportPDF() {
        try {
            // 1. Lấy dữ liệu mới nhất từ Database
            long totalCust = service.getTotalCustomers();
            long outOfStock = service.getOutOfStockProducts();
            BigDecimal todayRev = service.getTodayRevenue();
            BigDecimal totalRev = service.getTotalRevenue();
            List<Object[]> topProducts = service.getTopSellingProducts();

            // 2. Tạo tên file theo thời gian thực
            String fileName = "BaoCaoThongKe_" + System.currentTimeMillis() + ".pdf";

            // 3. Gọi công cụ vẽ PDF
            PDFExporter.exportStatisticsReport(totalCust, outOfStock, todayRev, totalRev, topProducts, fileName);

            JOptionPane.showMessageDialog(view, "Trích xuất báo cáo thành công!\nĐã lưu file: " + fileName);

            // 4. Tự động mở file lên xem
            try {
                Desktop.getDesktop().open(new File(fileName));
            } catch (Exception ex) {
                System.out.println("Không thể tự động mở file PDF.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi khi xuất PDF: " + ex.getMessage());
        }
    }
}