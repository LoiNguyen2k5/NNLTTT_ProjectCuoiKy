package com.example.cosmetic.view.product;

import com.example.cosmetic.model.entity.Brand;
import com.example.cosmetic.model.entity.Category;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductManagementPanel extends JPanel {
    private JTextField  txtBarcode, txtName, txtPrice, txtQuantity, txtExpirationDate, txtSearch;
    private JComboBox<Category> cbCategory;
    private JComboBox<Brand> cbBrand;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Mỹ phẩm"));

        panelForm.add(new JLabel("Mã Barcode:")); txtBarcode = new JTextField(); panelForm.add(txtBarcode);
        panelForm.add(new JLabel("Tên sản phẩm:")); txtName = new JTextField(); panelForm.add(txtName);
        panelForm.add(new JLabel("Giá bán:")); txtPrice = new JTextField(); panelForm.add(txtPrice);
        panelForm.add(new JLabel("Số lượng:")); txtQuantity = new JTextField(); panelForm.add(txtQuantity);
        panelForm.add(new JLabel("Hạn SD (dd/MM/yyyy):")); txtExpirationDate = new JTextField(); panelForm.add(txtExpirationDate);
        
        panelForm.add(new JLabel("Loại mỹ phẩm:")); cbCategory = new JComboBox<>(); panelForm.add(cbCategory);
        panelForm.add(new JLabel("Thương hiệu:")); cbBrand = new JComboBox<>(); panelForm.add(cbBrand);

        JPanel panelButtons = new JPanel(new FlowLayout());
        btnAdd = createStyledButton("Thêm", new Color(40, 167, 69)); 
        btnUpdate = createStyledButton("Sửa", new Color(0, 123, 255)); 
        btnDelete = createStyledButton("Xóa", new Color(220, 53, 69)); 
        btnClear = createStyledButton("Làm mới", new Color(108, 117, 125));
        panelButtons.add(btnAdd); panelButtons.add(btnUpdate); panelButtons.add(btnDelete); panelButtons.add(btnClear);

        // Thanh tìm kiếm
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(new JLabel("Tìm kiếm (Tên sản phẩm):"));
        txtSearch = new JTextField(20);
        btnSearch = createStyledButton("Tìm kiếm", new Color(23, 162, 184));
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);

        JPanel top = new JPanel(new BorderLayout());
        top.add(panelForm, BorderLayout.NORTH); 
        top.add(panelButtons, BorderLayout.CENTER);
        top.add(panelSearch, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Barcode", "Tên", "Giá", "Kho", "Loại", "Thương hiệu", "Hạn SD"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Getters
    public JTextField getTxtBarcode() { return txtBarcode; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JTextField getTxtExpirationDate() { return txtExpirationDate; }
    public JComboBox<Category> getCbCategory() { return cbCategory; }
    public JComboBox<Brand> getCbBrand() { return cbBrand; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}