package com.example.cosmetic.view.dashboard;

import com.example.cosmetic.view.utils.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private JLabel lblRevenue, lblTotalCustomers, lblOutOfStock, lblExpiring;
    private JTable tableExpiring, tableTopSelling;
    private DefaultTableModel modelExpiring, modelTopSelling;

    public DashboardPanel() {
        setLayout(new BorderLayout(0, 20));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(24, 24, 24, 24));

        // --- Title ---
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false);
        JLabel lblTitle = UITheme.createSectionTitle("Trang Chủ");
        JLabel lblSub   = UITheme.createSubTitle("BẢNG ĐIỀU KHIỂN TỔNG QUAN");
        JPanel titleCol = new JPanel();
        titleCol.setLayout(new BoxLayout(titleCol, BoxLayout.Y_AXIS));
        titleCol.setOpaque(false);
        titleCol.add(lblTitle);
        titleCol.add(Box.createVerticalStrut(2));
        titleCol.add(lblSub);
        titlePanel.add(titleCol, BorderLayout.WEST);
        add(titlePanel, BorderLayout.NORTH);

        // --- CENTER ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, 20));
        centerPanel.setOpaque(false);

        // CARDS ROW
        JPanel cardsRow = new JPanel(new GridLayout(1, 4, 16, 0));
        cardsRow.setOpaque(false);

        lblRevenue        = new JLabel("0 VNĐ", SwingConstants.CENTER);
        lblTotalCustomers = new JLabel("0",     SwingConstants.CENTER);
        lblOutOfStock     = new JLabel("0",     SwingConstants.CENTER);
        lblExpiring       = new JLabel("0",     SwingConstants.CENTER);

        cardsRow.add(buildStatCard("💰 Doanh Thu Hôm Nay",   lblRevenue,        UITheme.INFO,    UITheme.INFO_BG));
        cardsRow.add(buildStatCard("👥 Tổng Khách Hàng",     lblTotalCustomers, UITheme.SUCCESS, UITheme.SUCCESS_BG));
        cardsRow.add(buildStatCard("📦 SP Hết Hàng",         lblOutOfStock,     UITheme.DANGER,  UITheme.DANGER_BG));
        cardsRow.add(buildStatCard("⏰ SP Sắp Hết Hạn (30N)",lblExpiring,       UITheme.WARNING, UITheme.WARNING_BG));

        centerPanel.add(cardsRow, BorderLayout.NORTH);

        // TABLES ROW
        JPanel tablesRow = new JPanel(new GridLayout(1, 2, 20, 0));
        tablesRow.setOpaque(false);

        // Expiring table
        modelExpiring = new DefaultTableModel(new String[]{"Tên Mỹ Phẩm", "Tồn kho", "Ngày hết hạn"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableExpiring = new JTable(modelExpiring);
        UITheme.styleTable(tableExpiring);
        tablesRow.add(buildTableCard("⚠ Cảnh Báo: SP Sắp Hết Hạn (<30 ngày)", tableExpiring, UITheme.DANGER));

        // Top selling table
        modelTopSelling = new DefaultTableModel(new String[]{"Tên Mỹ Phẩm", "Đã Bán", "Doanh Thu", "Lần bán cuối"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableTopSelling = new JTable(modelTopSelling);
        UITheme.styleTable(tableTopSelling);
        tablesRow.add(buildTableCard("🏆 Top 10 Sản Phẩm Bán Chạy Nhất", tableTopSelling, UITheme.SUCCESS));

        centerPanel.add(tablesRow, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel buildStatCard(String title, JLabel valueLabel, Color accent, Color bgColor) {
        JPanel card = UITheme.createCard();
        card.setLayout(new BorderLayout());
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top colored accent bar
        JPanel accentBar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        accentBar.setOpaque(false);
        accentBar.setPreferredSize(new Dimension(0, 4));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTitle.setForeground(UITheme.getTextSec());

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        valueLabel.setForeground(accent);

        // Left colored stripe
        JPanel leftStripe = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
                g2.dispose();
            }
        };
        leftStripe.setOpaque(false);
        leftStripe.setPreferredSize(new Dimension(8, 0));

        JPanel content = new JPanel(new BorderLayout(0, 8));
        content.setOpaque(false);
        content.add(lblTitle,    BorderLayout.NORTH);
        content.add(valueLabel,  BorderLayout.CENTER);

        card.add(leftStripe, BorderLayout.WEST);
        card.add(content,    BorderLayout.CENTER);

        // Wrap thêm margin
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card);
        wrapper.setBorder(new EmptyBorder(0, 0, 4, 4)); // shadow offset
        return wrapper;
    }

    private JPanel buildTableCard(String title, JTable table, Color accent) {
        JPanel card = UITheme.createCard();
        card.setLayout(new BorderLayout(0, 10));
        card.setBorder(new EmptyBorder(16, 16, 16, 16));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(UITheme.getTextColor());
        lblTitle.setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, accent));
        lblTitle.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 3, 0, 0, accent),
                new EmptyBorder(0, 8, 0, 0)
        ));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scroll.getViewport().setBackground(Color.WHITE);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(scroll,   BorderLayout.CENTER);

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setOpaque(false);
        wrapper.add(card);
        wrapper.setBorder(new EmptyBorder(0, 0, 4, 4));
        return wrapper;
    }

    // Getters — giữ nguyên cho DashboardController
    public JLabel getLblRevenue()        { return lblRevenue; }
    public JLabel getLblTotalCustomers() { return lblTotalCustomers; }
    public JLabel getLblOutOfStock()     { return lblOutOfStock; }
    public JLabel getLblExpiring()       { return lblExpiring; }
    public DefaultTableModel getModelExpiring()    { return modelExpiring; }
    public DefaultTableModel getModelTopSelling()  { return modelTopSelling; }
}
