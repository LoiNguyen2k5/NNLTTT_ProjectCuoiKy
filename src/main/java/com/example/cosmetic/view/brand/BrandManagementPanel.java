package com.example.cosmetic.view.brand;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BrandManagementPanel extends JPanel {
    private JTextField txtId, txtName, txtDescription, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public BrandManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Thương hiệu"));

        panelForm.add(new JLabel("ID (Tự động):"));
        txtId = new JTextField();
        txtId.setEditable(false); 
        panelForm.add(txtId);

        panelForm.add(new JLabel("Tên thương hiệu (*):"));
        txtName = new JTextField();
        panelForm.add(txtName);

        panelForm.add(new JLabel("Mô tả:"));
        txtDescription = new JTextField();
        panelForm.add(txtDescription);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = createStyledButton("Thêm", new Color(40, 167, 69));
        btnUpdate = createStyledButton("Sửa", new Color(0, 123, 255));
        btnDelete = createStyledButton("Xóa", new Color(220, 53, 69));
        btnClear = createStyledButton("Làm mới", new Color(108, 117, 125));

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.add(panelForm, BorderLayout.CENTER);
        panelTop.add(panelButtons, BorderLayout.SOUTH);
        add(panelTop, BorderLayout.NORTH);

        // Thanh tìm kiếm
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSearch.add(new JLabel("Tìm kiếm (Tên/Mô tả): "));
        txtSearch = new JTextField(25);
        btnSearch = createStyledButton("Tìm kiếm", new Color(23, 162, 184));
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);

        String[] columns = {"ID", "Tên thương hiệu", "Mô tả"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } 
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Thương hiệu"));

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(panelSearch, BorderLayout.NORTH);
        panelCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(panelCenter, BorderLayout.CENTER);
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

    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtDescription() { return txtDescription; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}