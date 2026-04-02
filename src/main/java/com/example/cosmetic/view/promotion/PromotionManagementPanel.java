package com.example.cosmetic.view.promotion;

import com.example.cosmetic.view.components.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PromotionManagementPanel extends JPanel {
    private JTextField txtCode, txtDescription, txtDiscountPercent, txtMaxDiscount,
                       txtMinPurchase, txtStartDate, txtEndDate, txtSearch;
    private JCheckBox chkIsActive;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public PromotionManagementPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 20, 24));

        add(buildTitlePanel("Mã Khuyến Mãi", "DANH SÁCH MÃ KHUYẾN MÃI"), BorderLayout.NORTH);

        // ---- FORM CARD ----
        JPanel formCard = UITheme.createCard();
        formCard.setLayout(new BorderLayout(0, 12));
        formCard.setBorder(new EmptyBorder(16, 18, 14, 18));

        JLabel formLbl = new JLabel("Thông tin Khuyến Mãi");
        formLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formLbl.setForeground(UITheme.getTextColor());

        // Row 1: 4 fields
        JPanel row1 = new JPanel(new GridLayout(1, 4, 14, 0));
        row1.setOpaque(false);
        txtCode            = UITheme.createTextField();
        txtDescription     = UITheme.createTextField();
        txtDiscountPercent = UITheme.createTextField();
        txtMaxDiscount     = UITheme.createTextField();
        row1.add(makeField("Mã Code (*):",              txtCode));
        row1.add(makeField("Mô tả:",                    txtDescription));
        row1.add(makeField("% Giảm (1-100) (*):",       txtDiscountPercent));
        row1.add(makeField("Giảm tối đa (VNĐ):",        txtMaxDiscount));

        // Row 2: 4 fields
        JPanel row2 = new JPanel(new GridLayout(1, 4, 14, 0));
        row2.setOpaque(false);
        txtStartDate   = UITheme.createTextField();
        txtEndDate     = UITheme.createTextField();
        txtMinPurchase = UITheme.createTextField();
        chkIsActive    = new JCheckBox("Cho phép sử dụng");
        chkIsActive.setSelected(true);
        chkIsActive.setFont(UITheme.FONT_BODY);
        chkIsActive.setOpaque(false);
        chkIsActive.setForeground(UITheme.getTextColor());

        row2.add(makeField("Ngày bắt đầu (dd/MM/yyyy) (*):", txtStartDate));
        row2.add(makeField("Ngày kết thúc (dd/MM/yyyy) (*):", txtEndDate));
        row2.add(makeField("Đơn hàng tối thiểu (VNĐ):",      txtMinPurchase));
        row2.add(makeField("Trạng thái:",                     chkIsActive));

        JPanel formRows = new JPanel(new GridLayout(2, 1, 0, 12));
        formRows.setOpaque(false);
        formRows.add(row1);
        formRows.add(row2);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        btnAdd    = UITheme.createButton("+ Thêm mới",  UITheme.BTN_ADD);
        btnUpdate = UITheme.createButton("✎ Cập nhật",  UITheme.BTN_EDIT);
        btnDelete = UITheme.createButton("✕ Xóa",       UITheme.BTN_DELETE);
        btnClear  = UITheme.createButton("↺ Làm mới",   UITheme.BTN_CLEAR);
        btnRow.add(btnAdd); btnRow.add(btnUpdate); btnRow.add(btnDelete); btnRow.add(btnClear);

        formCard.add(formLbl,  BorderLayout.NORTH);
        formCard.add(formRows, BorderLayout.CENTER);
        formCard.add(btnRow,   BorderLayout.SOUTH);

        // ---- TABLE CARD ----
        JPanel tableCard = UITheme.createCard();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolbar.setOpaque(false);
        txtSearch = UITheme.createSearchField("Tìm theo mã code...");
        txtSearch.setPreferredSize(new Dimension(260, 36));
        btnSearch = UITheme.createButton("🔍 Tìm kiếm", UITheme.BTN_SEARCH);
        toolbar.add(txtSearch); toolbar.add(btnSearch);
        tableCard.add(toolbar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{
            "ID", "Mã Code", "Mô Tả", "% Giảm", "Giảm Tối Đa", "Đơn Tối Thiểu", "Từ ngày", "Đến ngày", "Trạng thái"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UITheme.styleTable(table);
        // Badge renderer cho cột Trạng thái (index 8)
        table.getColumnModel().getColumn(8).setCellRenderer(new UITheme.GenericBadgeRenderer("Hoạt động"));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);
        center.add(formCard,  BorderLayout.NORTH);
        center.add(tableCard, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
    }

    private JPanel buildTitlePanel(String title, String subtitle) {
        JLabel t = UITheme.createSectionTitle(title);
        JLabel s = UITheme.createSubTitle(subtitle);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(t); p.add(Box.createVerticalStrut(2)); p.add(s);
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(p, BorderLayout.WEST);
        return wrap;
    }

    private JPanel makeField(String label, JComponent input) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.getTextSec());
        p.add(lbl, BorderLayout.NORTH);
        p.add(input, BorderLayout.CENTER);
        return p;
    }

    // Getters
    public JTextField getTxtCode()            { return txtCode; }
    public JTextField getTxtDescription()     { return txtDescription; }
    public JTextField getTxtDiscountPercent() { return txtDiscountPercent; }
    public JTextField getTxtMaxDiscount()     { return txtMaxDiscount; }
    public JTextField getTxtMinPurchase()     { return txtMinPurchase; }
    public JTextField getTxtStartDate()       { return txtStartDate; }
    public JTextField getTxtEndDate()         { return txtEndDate; }
    public JCheckBox  getChkIsActive()        { return chkIsActive; }
    public JButton getBtnAdd()                { return btnAdd; }
    public JButton getBtnUpdate()             { return btnUpdate; }
    public JButton getBtnDelete()             { return btnDelete; }
    public JButton getBtnClear()              { return btnClear; }
    public JTextField getTxtSearch()          { return txtSearch; }
    public JButton getBtnSearch()             { return btnSearch; }
    public JTable getTable()                  { return table; }
    public DefaultTableModel getTableModel()  { return tableModel; }
}
