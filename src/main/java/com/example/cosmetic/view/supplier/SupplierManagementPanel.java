package com.example.cosmetic.view.supplier;

import com.example.cosmetic.view.components.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierManagementPanel extends JPanel {
    private JTextField txtId, txtName, txtPhone, txtAddress, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public SupplierManagementPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 20, 24));

        add(buildTitlePanel("Nhà Cung Cấp", "DANH SÁCH NHÀ CUNG CẤP"), BorderLayout.NORTH);

        // Form card
        JPanel formCard = UITheme.createCard();
        formCard.setLayout(new BorderLayout(0, 12));
        formCard.setBorder(new EmptyBorder(16, 18, 14, 18));

        JLabel formLbl = new JLabel("Thông tin Nhà Cung Cấp");
        formLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formLbl.setForeground(UITheme.getTextColor());

        JPanel formGrid = new JPanel(new GridLayout(1, 4, 14, 0));
        formGrid.setOpaque(false);

        txtId = UITheme.createTextField(); txtId.setEditable(false);
        txtId.setBackground(UITheme.isDark ? UITheme.DARK_FIELD_BG : new Color(243, 244, 246));
        txtName    = UITheme.createTextField();
        txtPhone   = UITheme.createTextField();
        txtAddress = UITheme.createTextField();

        formGrid.add(makeField("ID (Tự động):",  txtId));
        formGrid.add(makeField("Tên NCC (*):",   txtName));
        formGrid.add(makeField("Số điện thoại:", txtPhone));
        formGrid.add(makeField("Địa chỉ:",       txtAddress));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        btnAdd    = UITheme.createButton("+ Thêm mới",  UITheme.BTN_ADD);
        btnUpdate = UITheme.createButton("✎ Cập nhật",  UITheme.BTN_EDIT);
        btnDelete = UITheme.createButton("✕ Xóa",       UITheme.BTN_DELETE);
        btnClear  = UITheme.createButton("↺ Làm mới",   UITheme.BTN_CLEAR);
        btnRow.add(btnAdd); btnRow.add(btnUpdate); btnRow.add(btnDelete); btnRow.add(btnClear);

        formCard.add(formLbl,  BorderLayout.NORTH);
        formCard.add(formGrid, BorderLayout.CENTER);
        formCard.add(btnRow,   BorderLayout.SOUTH);

        // Table card
        JPanel tableCard = UITheme.createCard();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolbar.setOpaque(false);
        txtSearch = UITheme.createSearchField("Tìm theo tên / SĐT / địa chỉ...");
        txtSearch.setPreferredSize(new Dimension(300, 36));
        btnSearch = UITheme.createButton("🔍 Tìm kiếm", UITheme.BTN_SEARCH);
        toolbar.add(txtSearch); toolbar.add(btnSearch);
        tableCard.add(toolbar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên NCC", "Số điện thoại", "Địa chỉ"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        UITheme.styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scroll.getViewport().setBackground(UITheme.getCardColor());
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
    public JTextField getTxtId()      { return txtId; }
    public JTextField getTxtName()    { return txtName; }
    public JTextField getTxtPhone()   { return txtPhone; }
    public JTextField getTxtAddress() { return txtAddress; }
    public JTextField getTxtSearch()  { return txtSearch; }
    public JButton getBtnAdd()        { return btnAdd; }
    public JButton getBtnUpdate()     { return btnUpdate; }
    public JButton getBtnDelete()     { return btnDelete; }
    public JButton getBtnClear()      { return btnClear; }
    public JButton getBtnSearch()     { return btnSearch; }
    public JTable getTable()          { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}