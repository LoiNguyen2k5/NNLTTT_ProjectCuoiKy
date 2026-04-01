package com.example.cosmetic.view.statistics;

import com.example.cosmetic.view.utils.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StatisticsPanel extends JPanel {
    private JLabel              lblTotalCustomers, lblOutOfStock, lblTodayRevenue, lblTotalRevenue, lblExpiringSoon;
    private JTable              tableTopSelling, tableExpiring;
    private DefaultTableModel   tableModel, expiringModel;
    private JButton             btnRefresh, btnExportPDF;
    private JPanel              pnlChart;

    public StatisticsPanel() {
        setLayout(new BorderLayout(0, 14));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 16, 24));

        // ===== TITLE =====
        add(buildTitlePanel(), BorderLayout.NORTH);

        // ===== CENTER =====
        JPanel centerWrapper = new JPanel(new BorderLayout(0, 12));
        centerWrapper.setOpaque(false);

        // -- KPI Row: 5 stat cards --
        JPanel kpiRow = new JPanel(new GridLayout(1, 5, 10, 0));
        kpiRow.setOpaque(false);
        kpiRow.setPreferredSize(new Dimension(0, 108));

        lblTotalCustomers = addKpiCard(kpiRow, "Tong Khach",     "KH",  UITheme.INFO,    UITheme.INFO_BG);
        lblOutOfStock     = addKpiCard(kpiRow, "Het Hang",       "SP",  UITheme.DANGER,  UITheme.DANGER_BG);
        lblExpiringSoon   = addKpiCard(kpiRow, "Sap Het Han",    "SP",  UITheme.WARNING, UITheme.WARNING_BG);
        lblTodayRevenue   = addKpiCard(kpiRow, "DT Hom Nay",     "VND", UITheme.SUCCESS, UITheme.SUCCESS_BG);
        lblTotalRevenue   = addKpiCard(kpiRow, "Tong Doanh Thu", "VND", UITheme.PRIMARY, UITheme.PRIMARY_LIGHT);

        // -- Table row: Top Selling + Expiring + Chart --
        JPanel tableRow = new JPanel(new GridLayout(1, 3, 10, 0));
        tableRow.setOpaque(false);

        // Top 10 selling
        JPanel topSellingCard = UITheme.createCard();
        topSellingCard.setLayout(new BorderLayout(0, 8));
        topSellingCard.setBorder(new EmptyBorder(12, 12, 12, 12));
        JLabel lbl1 = makeCardTitle("Top 10 Ban Chay Nhat");
        tableModel = new DefaultTableModel(
                new String[]{"Ten San Pham", "Da Ban", "Doanh Thu", "Ngay Ban Gan Nhat"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableTopSelling = new JTable(tableModel);
        UITheme.styleTable(tableTopSelling);
        JScrollPane scroll1 = new JScrollPane(tableTopSelling);
        scroll1.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        topSellingCard.add(lbl1, BorderLayout.NORTH);
        topSellingCard.add(scroll1, BorderLayout.CENTER);

        // Expiring soon
        JPanel expiringCard = UITheme.createCard();
        expiringCard.setLayout(new BorderLayout(0, 8));
        expiringCard.setBorder(new EmptyBorder(12, 12, 12, 12));
        JLabel lbl2 = makeCardTitle("Canh Bao Sap Het Han");
        expiringModel = new DefaultTableModel(
                new String[]{"Ten San Pham", "Ton", "Han Su Dung"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tableExpiring = new JTable(expiringModel);
        UITheme.styleTable(tableExpiring);
        JScrollPane scroll2 = new JScrollPane(tableExpiring);
        scroll2.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        expiringCard.add(lbl2, BorderLayout.NORTH);
        expiringCard.add(scroll2, BorderLayout.CENTER);

        // Chart panel
        JPanel chartCard = UITheme.createCard();
        chartCard.setLayout(new BorderLayout(0, 8));
        chartCard.setBorder(new EmptyBorder(12, 12, 12, 12));
        JLabel lbl3 = makeCardTitle("Bieu Do Doanh Thu");
        pnlChart = new JPanel(new BorderLayout());
        pnlChart.setOpaque(false);
        chartCard.add(lbl3, BorderLayout.NORTH);
        chartCard.add(pnlChart, BorderLayout.CENTER);

        tableRow.add(topSellingCard);
        tableRow.add(expiringCard);
        tableRow.add(chartCard);

        centerWrapper.add(kpiRow, BorderLayout.NORTH);
        centerWrapper.add(tableRow, BorderLayout.CENTER);
        add(centerWrapper, BorderLayout.CENTER);

        // ===== SOUTH: Buttons =====
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 8));
        bottomBar.setOpaque(false);

        btnExportPDF = UITheme.createButton("Xuat Bao Cao PDF", UITheme.DANGER);
        btnExportPDF.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnExportPDF.setPreferredSize(new Dimension(190, 38));

        btnRefresh = UITheme.createButton("Lam Moi Thong Ke", UITheme.BTN_CLEAR);
        btnRefresh.setPreferredSize(new Dimension(190, 38));

        bottomBar.add(btnExportPDF);
        bottomBar.add(btnRefresh);
        add(bottomBar, BorderLayout.SOUTH);
    }

    /** Tao KPI card voi accent color bar o tren */
    private JLabel addKpiCard(JPanel parent, String title, String unit, Color accentColor, Color bgColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // White background rounded
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                // Accent bar at top (4px high, full width)
                g2.setColor(accentColor);
                g2.fillRect(0, 0, getWidth(), 4);
                // Slight shadow at bottom
                g2.setColor(new Color(0, 0, 0, 10));
                g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                g2.dispose();
            }
        };
        card.setLayout(new BorderLayout(0, 2));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UITheme.BORDER_COLOR, 1, true),
                new EmptyBorder(12, 14, 10, 14)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblTitle.setForeground(UITheme.getTextSec());

        JLabel lblValue = new JLabel("0");
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblValue.setForeground(accentColor);

        JLabel lblUnit = new JLabel(unit);
        lblUnit.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        lblUnit.setForeground(UITheme.getTextSec());

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, 0));
        bottom.setOpaque(false);
        bottom.add(lblValue);
        bottom.add(lblUnit);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(bottom, BorderLayout.CENTER);

        parent.add(card);
        return lblValue;
    }

    private JLabel makeCardTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(UITheme.getTextColor());
        lbl.setBorder(new EmptyBorder(0, 0, 6, 0));
        return lbl;
    }

    private JPanel buildTitlePanel() {
        JLabel t = UITheme.createSectionTitle("Thong Ke");
        JLabel s = UITheme.createSubTitle("TONG QUAN HIEU SUAT KINH DOANH");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(t); p.add(Box.createVerticalStrut(2)); p.add(s);
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(p, BorderLayout.WEST);
        return wrap;
    }

    // ========== GETTERS ==========
    public JLabel           getLblTotalCustomers() { return lblTotalCustomers; }
    public JLabel           getLblOutOfStock()     { return lblOutOfStock; }
    public JLabel           getLblTodayRevenue()   { return lblTodayRevenue; }
    public JLabel           getLblTotalRevenue()   { return lblTotalRevenue; }
    public JLabel           getLblExpiringSoon()   { return lblExpiringSoon; }
    public DefaultTableModel getTableModel()       { return tableModel; }
    public DefaultTableModel getExpiringModel()    { return expiringModel; }
    public JButton          getBtnRefresh()        { return btnRefresh; }
    public JButton          getBtnExportPDF()      { return btnExportPDF; }
    public JPanel           getPnlChart()          { return pnlChart; }
}