package com.example.cosmetic.view.product;

import com.example.cosmetic.model.entity.Brand;
import com.example.cosmetic.model.entity.Category;
import com.example.cosmetic.view.utils.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductManagementPanel extends JPanel {

    private JTextField txtBarcode, txtName, txtPrice, txtQuantity, txtExpirationDate, txtSearch;
    private JComboBox<Category> cbCategory;
    private JComboBox<Brand>    cbBrand;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductManagementPanel() {
        setLayout(new BorderLayout());
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 20, 24));

        // ===== TOP AREA =====
        JPanel topArea = new JPanel(new BorderLayout(0, 14));
        topArea.setOpaque(false);

        // --- Page title ---
        JPanel titleRow = new JPanel(new BorderLayout());
        titleRow.setOpaque(false);
        JLabel lblTitle = UITheme.createSectionTitle("Sản Phẩm");
        JLabel lblSub   = UITheme.createSubTitle("DANH SÁCH SẢN PHẨM");
        JPanel titleCol = new JPanel();
        titleCol.setLayout(new BoxLayout(titleCol, BoxLayout.Y_AXIS));
        titleCol.setOpaque(false);
        titleCol.add(lblTitle);
        titleCol.add(Box.createVerticalStrut(2));
        titleCol.add(lblSub);
        titleRow.add(titleCol, BorderLayout.WEST);
        topArea.add(titleRow, BorderLayout.NORTH);

        // --- FORM CARD ---
        JPanel formCard = UITheme.createCard();
        formCard.setLayout(new BorderLayout(0, 12));
        formCard.setBorder(new EmptyBorder(18, 18, 14, 18));

        JLabel formTitle = new JLabel("Thông tin Mỹ phẩm");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formTitle.setForeground(UITheme.getTextColor());
        formTitle.setBorder(new EmptyBorder(0, 0, 8, 0));

        // Grid form: 3 cột x 3 hàng
        JPanel formGrid = new JPanel(new GridLayout(3, 4, 12, 10));
        formGrid.setOpaque(false);

        txtBarcode        = UITheme.createTextField();
        txtName           = UITheme.createTextField();
        txtPrice          = UITheme.createTextField();
        txtQuantity       = UITheme.createTextField();
        txtExpirationDate = UITheme.createTextField();
        cbCategory        = UITheme.<Category>createComboBox();
        cbBrand           = UITheme.<Brand>createComboBox();

        formGrid.add(makeField("Mã Barcode:", txtBarcode));
        formGrid.add(makeField("Tên sản phẩm:", txtName));
        formGrid.add(makeField("Giá bán (VNĐ):", txtPrice));
        formGrid.add(makeField("Số lượng:", txtQuantity));
        formGrid.add(makeField("Hạn SD (dd/MM/yyyy):", txtExpirationDate));
        formGrid.add(makeField("Loại mỹ phẩm:", cbCategory));
        formGrid.add(makeField("Thương hiệu:", cbBrand));
        formGrid.add(new JPanel()); // placeholder

        formCard.add(formTitle, BorderLayout.NORTH);
        formCard.add(formGrid,  BorderLayout.CENTER);

        // --- ACTION BUTTONS row ---
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        btnAdd    = UITheme.createButton("+ Thêm mới",   UITheme.BTN_ADD);
        btnUpdate = UITheme.createButton("✎ Cập nhật",   UITheme.BTN_EDIT);
        btnDelete = UITheme.createButton("✕ Xóa",        UITheme.BTN_DELETE);
        btnClear  = UITheme.createButton("↺ Làm mới",    UITheme.BTN_CLEAR);
        btnRow.add(btnAdd);
        btnRow.add(btnUpdate);
        btnRow.add(btnDelete);
        btnRow.add(btnClear);

        JPanel formWrapper = new JPanel(new BorderLayout(0, 10));
        formWrapper.setOpaque(false);
        formWrapper.add(formCard, BorderLayout.CENTER);
        formWrapper.add(btnRow,   BorderLayout.SOUTH);

        topArea.add(formWrapper, BorderLayout.CENTER);
        add(topArea, BorderLayout.NORTH);

        // ===== TABLE CARD =====
        JPanel tableCard = UITheme.createCard();
        tableCard.setLayout(new BorderLayout(0, 12));
        tableCard.setBorder(new EmptyBorder(14, 16, 14, 16));

        // Toolbar: search left + export right
        JPanel toolbar = new JPanel(new BorderLayout(10, 0));
        toolbar.setOpaque(false);

        txtSearch = UITheme.createSearchField("Tìm kiếm sản phẩm...");
        txtSearch.setPreferredSize(new Dimension(260, 36));
        btnSearch = UITheme.createButton("🔍 Tìm kiếm", UITheme.BTN_SEARCH);

        JPanel searchLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        searchLeft.setOpaque(false);
        searchLeft.add(txtSearch);
        searchLeft.add(btnSearch);
        toolbar.add(searchLeft, BorderLayout.WEST);

        tableCard.add(toolbar, BorderLayout.NORTH);

        // Table (không có cột Trạng thái)
        tableModel = new DefaultTableModel(
                new String[]{"ID", "Barcode", "Tên sản phẩm", "Giá bán", "Tồn kho", "Loại", "Thương hiệu", "Hạn SD"}, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTable(table);

        // Căn giữa cột số
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        // Cột Hạn SD căn giữa
        table.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);

        tableCard.add(scrollPane, BorderLayout.CENTER);
        add(tableCard, BorderLayout.CENTER);
    }

    /** Helper tạo label + field theo cột dọc */
    private JPanel makeField(String labelText, JComponent input) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        JLabel lbl = new JLabel(labelText);
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.getTextSec());
        p.add(lbl,   BorderLayout.NORTH);
        p.add(input, BorderLayout.CENTER);
        return p;
    }

    // ===== GETTERS (giữ nguyên để Controller không bị break) =====
    public JTextField getTxtBarcode()        { return txtBarcode; }
    public JTextField getTxtName()           { return txtName; }
    public JTextField getTxtPrice()          { return txtPrice; }
    public JTextField getTxtQuantity()       { return txtQuantity; }
    public JTextField getTxtExpirationDate() { return txtExpirationDate; }
    public JComboBox<Category> getCbCategory() { return cbCategory; }
    public JComboBox<Brand>    getCbBrand()    { return cbBrand; }
    public JButton getBtnAdd()     { return btnAdd; }
    public JButton getBtnUpdate()  { return btnUpdate; }
    public JButton getBtnDelete()  { return btnDelete; }
    public JButton getBtnClear()   { return btnClear; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch()  { return btnSearch; }
    public JTable getTable()       { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}