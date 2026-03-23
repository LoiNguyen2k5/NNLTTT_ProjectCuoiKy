package com.example.cosmetic.view.product;

import com.example.cosmetic.model.entity.Brand;
import com.example.cosmetic.model.entity.Category;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductManagementPanel extends JPanel {
    private JTextField  txtBarcode, txtName, txtPrice, txtQuantity;
    private JComboBox<Category> cbCategory;
    private JComboBox<Brand> cbBrand;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    public ProductManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Mỹ phẩm"));

        panelForm.add(new JLabel("Mã Barcode:")); txtBarcode = new JTextField(); panelForm.add(txtBarcode);
        panelForm.add(new JLabel("Tên sản phẩm:")); txtName = new JTextField(); panelForm.add(txtName);
        panelForm.add(new JLabel("Giá bán:")); txtPrice = new JTextField(); panelForm.add(txtPrice);
        panelForm.add(new JLabel("Số lượng:")); txtQuantity = new JTextField(); panelForm.add(txtQuantity);
        
        panelForm.add(new JLabel("Loại mỹ phẩm:")); cbCategory = new JComboBox<>(); panelForm.add(cbCategory);
        panelForm.add(new JLabel("Thương hiệu:")); cbBrand = new JComboBox<>(); panelForm.add(cbBrand);

        JPanel panelButtons = new JPanel(new FlowLayout());
        btnAdd = new JButton("Thêm"); btnUpdate = new JButton("Sửa"); btnDelete = new JButton("Xóa"); btnClear = new JButton("Làm mới");
        panelButtons.add(btnAdd); panelButtons.add(btnUpdate); panelButtons.add(btnDelete); panelButtons.add(btnClear);

        JPanel top = new JPanel(new BorderLayout());
        top.add(panelForm, BorderLayout.CENTER); top.add(panelButtons, BorderLayout.SOUTH);
        add(top, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Barcode", "Tên", "Giá", "Kho", "Loại", "Thương hiệu"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Getters
    public JTextField getTxtBarcode() { return txtBarcode; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPrice() { return txtPrice; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JComboBox<Category> getCbCategory() { return cbCategory; }
    public JComboBox<Brand> getCbBrand() { return cbBrand; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}