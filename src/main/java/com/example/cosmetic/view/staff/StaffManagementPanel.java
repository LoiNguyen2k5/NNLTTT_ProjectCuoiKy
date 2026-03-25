package com.example.cosmetic.view.staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StaffManagementPanel extends JPanel {
    private JTextField txtStaffCode, txtFullName, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRole;
    private JTable tblStaff;
    private DefaultTableModel tableModel;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    public StaffManagementPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // 1. FORM NHẬP LIỆU
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin Nhân viên"));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(new JLabel("Mã Nhân viên:"));
        txtStaffCode = new JTextField();
        formPanel.add(txtStaffCode);

        formPanel.add(new JLabel("Họ và Tên:"));
        txtFullName = new JTextField();
        formPanel.add(txtFullName);

        formPanel.add(new JLabel("Tài khoản (Username):"));
        txtUsername = new JTextField();
        formPanel.add(txtUsername);

        formPanel.add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        formPanel.add(txtPassword);

        formPanel.add(new JLabel("Vai trò (Phân quyền):"));
        String[] roles = {"STAFF", "ADMIN"};
        cbRole = new JComboBox<>(roles);
        formPanel.add(cbRole);

        // Nút chức năng
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(Color.WHITE);
        
        btnAdd = new JButton("Thêm mới");
        btnUpdate = new JButton("Cập nhật");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Làm mới Form");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // 2. BẢNG DỮ LIỆU
        String[] columnNames = {"ID", "Mã NV", "Họ Tên", "Tài khoản", "Vai trò"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Khóa không cho sửa trực tiếp trên bảng
        };
        tblStaff = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblStaff);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Nhân viên"));

        // Click dòng nào hiện lên form dòng đó
        tblStaff.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblStaff.getSelectedRow() != -1) {
                int row = tblStaff.getSelectedRow();
                txtStaffCode.setText(tblStaff.getValueAt(row, 1).toString());
                txtFullName.setText(tblStaff.getValueAt(row, 2).toString());
                txtUsername.setText(tblStaff.getValueAt(row, 3).toString());
                cbRole.setSelectedItem(tblStaff.getValueAt(row, 4).toString());
                txtPassword.setText("123456"); 
            }
        });

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    // =========================================
    // GETTERS CHO CONTROLLER SỬ DỤNG
    // =========================================
    public DefaultTableModel getTableModel() { return tableModel; }
    public JTable getTblStaff() { return tblStaff; }
    public JTextField getTxtStaffCode() { return txtStaffCode; }
    public JTextField getTxtFullName() { return txtFullName; }
    public JTextField getTxtUsername() { return txtUsername; }
    public JPasswordField getTxtPassword() { return txtPassword; }
    public JComboBox<String> getCbRole() { return cbRole; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
}