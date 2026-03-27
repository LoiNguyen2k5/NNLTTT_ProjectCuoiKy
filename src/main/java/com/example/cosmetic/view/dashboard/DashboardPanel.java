package com.example.cosmetic.view.dashboard;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel lblRevenue, lblTotalCustomers, lblOutOfStock, lblExpiring;
    private JTable tableExpiring, tableTopSelling;
    private DefaultTableModel modelExpiring, modelTopSelling;

    public DashboardPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // --- TITLE ---
        JLabel lblTitle = new JLabel("TRANG CHỦ - BẢNG ĐIỀU KHIỂN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(50, 50, 50));
        add(lblTitle, BorderLayout.NORTH);

        // --- CARDS AREA ---
        JPanel panelCards = new JPanel(new GridLayout(1, 4, 15, 0));
        
        lblRevenue = createLabelValue("0 VNĐ");
        panelCards.add(createCard("Doanh Thu Hôm Nay", lblRevenue, new Color(13, 110, 253), new Color(207, 226, 255)));
        
        lblTotalCustomers = createLabelValue("0");
        panelCards.add(createCard("Tổng Khách Hàng", lblTotalCustomers, new Color(25, 135, 84), new Color(209, 231, 221)));
        
        lblOutOfStock = createLabelValue("0");
        panelCards.add(createCard("Sản Phẩm Hết Hàng", lblOutOfStock, new Color(220, 53, 69), new Color(248, 215, 218)));
        
        lblExpiring = createLabelValue("0");
        panelCards.add(createCard("SP Sắp Hết Hạn (30N)", lblExpiring, new Color(253, 126, 20), new Color(255, 229, 208)));

        // --- TABLES AREA ---
        JPanel panelTables = new JPanel(new GridLayout(1, 2, 20, 0));

        // Expiring Table
        modelExpiring = new DefaultTableModel(new String[]{"Tên Mỹ Phẩm", "Tồn kho", "Ngày hết hạn"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableExpiring = new JTable(modelExpiring);
        tableExpiring.setRowHeight(30);
        tableExpiring.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableExpiring.getTableHeader().setBackground(new Color(255, 229, 208));
        JScrollPane scrollExpiring = new JScrollPane(tableExpiring);
        scrollExpiring.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Cảnh Báo: Lô mỹ phẩm sắp hết hạn (<30 ngày)", 0, 0, new Font("Arial", Font.BOLD, 13), new Color(220, 53, 69)));
        panelTables.add(scrollExpiring);

        // Top Selling Table
        modelTopSelling = new DefaultTableModel(new String[]{"Tên Mỹ Phẩm", "Đã Bán", "Doanh Thu", "Lần bán cuối"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableTopSelling = new JTable(modelTopSelling);
        tableTopSelling.setRowHeight(30);
        tableTopSelling.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableTopSelling.getTableHeader().setBackground(new Color(209, 231, 221));
        JScrollPane scrollTopSelling = new JScrollPane(tableTopSelling);
        scrollTopSelling.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY), "Top 10 Sản phẩm Bán chạy nhất", 0, 0, new Font("Arial", Font.BOLD, 13), new Color(25, 135, 84)));
        panelTables.add(scrollTopSelling);

        // Assemble center
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.add(panelCards, BorderLayout.NORTH);
        centerPanel.add(panelTables, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private JLabel createLabelValue(String text) {
        JLabel lbl = new JLabel(text, SwingConstants.CENTER);
        lbl.setFont(new Font("Arial", Font.BOLD, 22));
        return lbl;
    }

    private JPanel createCard(String title, JLabel lblValue, Color fgColor, Color bgColor) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(bgColor);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(fgColor, 2, true),
            new EmptyBorder(20, 10, 20, 10)
        ));
        
        JLabel lblTitle = new JLabel(title, SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 15));
        lblTitle.setForeground(fgColor);
        
        lblValue.setForeground(fgColor);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    public JLabel getLblRevenue() { return lblRevenue; }
    public JLabel getLblTotalCustomers() { return lblTotalCustomers; }
    public JLabel getLblOutOfStock() { return lblOutOfStock; }
    public JLabel getLblExpiring() { return lblExpiring; }
    public DefaultTableModel getModelExpiring() { return modelExpiring; }
    public DefaultTableModel getModelTopSelling() { return modelTopSelling; }
}
