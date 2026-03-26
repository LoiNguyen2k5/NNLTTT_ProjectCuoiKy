package com.example.cosmetic.view.supplier;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SupplierManagementPanel extends JPanel {
    private JTextField txtId, txtName, txtPhone, txtAddress, txtSearch;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public SupplierManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Nhà cung cấp"));

        panelForm.add(new JLabel("ID (Tự động):"));
        txtId = new JTextField();
        txtId.setEditable(false); 
        panelForm.add(txtId);

        panelForm.add(new JLabel("Tên NCC (*):"));
        txtName = new JTextField();
        panelForm.add(txtName);
        
        panelForm.add(new JLabel("Số điện thoại:"));
        txtPhone = new JTextField();
        panelForm.add(txtPhone);

        panelForm.add(new JLabel("Địa chỉ:"));
        txtAddress = new JTextField();
        panelForm.add(txtAddress);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới");

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
        panelSearch.add(new JLabel("Tìm kiếm (Tên/SĐT/Địa chỉ): "));
        txtSearch = new JTextField(25);
        btnSearch = new JButton("Tìm kiếm");
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);

        String[] columns = {"ID", "Tên NCC", "Số điện thoại", "Địa chỉ"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } 
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Nhà cung cấp"));

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.add(panelSearch, BorderLayout.NORTH);
        panelCenter.add(scrollPane, BorderLayout.CENTER);
        
        add(panelCenter, BorderLayout.CENTER);
    }

    public JTextField getTxtId() { return txtId; }
    public JTextField getTxtName() { return txtName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtAddress() { return txtAddress; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}