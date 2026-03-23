package com.example.cosmetic.view.category;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoryManagementPanel extends JPanel {
    private JTextField txtId, txtName, txtDescription;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    public CategoryManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        // --- Phần Form nhập liệu (Phía trên) ---
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Loại mỹ phẩm"));

        panelForm.add(new JLabel("ID (Tự động):"));
        txtId = new JTextField();
        txtId.setEditable(false); // ID không cho sửa
        panelForm.add(txtId);

        panelForm.add(new JLabel("Tên loại (*):"));
        txtName = new JTextField();
        panelForm.add(txtName);

        panelForm.add(new JLabel("Mô tả:"));
        txtDescription = new JTextField();
        panelForm.add(txtDescription);

        // --- Phần Nút bấm (Ở giữa) ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        // Gom Form và Nút vào một khối đặt ở trên cùng (NORTH)
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);
        add(panelTop, BorderLayout.NORTH);

        // --- Phần Bảng dữ liệu (Phía dưới) ---
        String[] columns = {"ID", "Tên loại", "Mô tả"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Khóa không cho gõ trực tiếp vào bảng
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Loại mỹ phẩm"));
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters cho Controller xài
    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtDescription() { return txtDescription; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}