package com.example.cosmetic.view.statistics;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private JLabel lblTotalCustomers, lblOutOfStock, lblTodayRevenue, lblTotalRevenue, lblExpiringSoon;
    private JTable tableTopSelling, tableExpiring;
    private DefaultTableModel tableModel, expiringModel;
    private JButton btnRefresh, btnExportPDF; // Khai báo thêm nút xuất PDF
    private JPanel pnlChart; 

    public StatisticsPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlDashboard = new JPanel(new GridLayout(1, 5, 10, 10));
        pnlDashboard.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblTotalCustomers = createDashboardBox("Tổng Khách", pnlDashboard, new Color(100, 200, 255));
        lblOutOfStock = createDashboardBox("Hết Hàng", pnlDashboard, new Color(255, 100, 100));
        lblExpiringSoon = createDashboardBox("Sắp Hết Hạn (<30đ)", pnlDashboard, new Color(255, 150, 50));
        lblTodayRevenue = createDashboardBox("DT Hôm Nay", pnlDashboard, new Color(100, 255, 100));
        lblTotalRevenue = createDashboardBox("TỔNG DT", pnlDashboard, new Color(255, 200, 100)); 
        add(pnlDashboard, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new GridLayout(1, 3, 10, 0));
        
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(BorderFactory.createTitledBorder("Top 10 Bán Chạy Nhất"));
        tableModel = new DefaultTableModel(new String[]{"Tên Sản Phẩm", "Đã Bán", "Doanh Thu", "Ngày Bán Gần Nhất"}, 0);
        tableTopSelling = new JTable(tableModel);
        pnlTable.add(new JScrollPane(tableTopSelling), BorderLayout.CENTER);
        
        JPanel pnlExpiring = new JPanel(new BorderLayout());
        pnlExpiring.setBorder(BorderFactory.createTitledBorder("Cảnh Báo Sắp Hết Hạn"));
        expiringModel = new DefaultTableModel(new String[]{"Tên Sản Phẩm", "Tồn", "Hạn Sử Dụng"}, 0);
        tableExpiring = new JTable(expiringModel);
        pnlExpiring.add(new JScrollPane(tableExpiring), BorderLayout.CENTER);
        
        pnlChart = new JPanel(new BorderLayout());
        pnlChart.setBorder(BorderFactory.createTitledBorder("Biểu đồ Doanh Thu"));
        
        pnlCenter.add(pnlTable);
        pnlCenter.add(pnlExpiring);
        pnlCenter.add(pnlChart);
        add(pnlCenter, BorderLayout.CENTER);

        // --- KHU VỰC NÚT BẤM (Cập nhật) ---
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        
        btnExportPDF = new JButton("Xuất Báo Cáo PDF");
        btnExportPDF.setBackground(new Color(220, 53, 69)); // Màu đỏ nổi bật
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnRefresh = new JButton("Làm mới thống kê");
        
        pnlBottom.add(btnExportPDF);
        pnlBottom.add(btnRefresh);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    private JLabel createDashboardBox(String title, JPanel parent, Color color) {
        JPanel box = new JPanel(new BorderLayout());
        box.setBackground(color);
        box.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
        box.setPreferredSize(new Dimension(200, 100));

        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 14));
        box.add(lblTitle, BorderLayout.NORTH);

        JLabel lblValue = new JLabel("0", SwingConstants.CENTER);
        lblValue.setFont(new Font("Arial", Font.BOLD, 20));
        box.add(lblValue, BorderLayout.CENTER);

        parent.add(box);
        return lblValue;
    }

    // Getters
    public JLabel getLblTotalCustomers() { return lblTotalCustomers; }
    public JLabel getLblOutOfStock() { return lblOutOfStock; }
    public JLabel getLblTodayRevenue() { return lblTodayRevenue; }
    public JLabel getLblTotalRevenue() { return lblTotalRevenue; }
    public JLabel getLblExpiringSoon() { return lblExpiringSoon; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public DefaultTableModel getExpiringModel() { return expiringModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
    public JButton getBtnExportPDF() { return btnExportPDF; } // Getter mới
    public JPanel getPnlChart() { return pnlChart; } 
}