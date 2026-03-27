package com.example.cosmetic.view.customer;

import com.example.cosmetic.model.enums.CustomerGender;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerManagementPanel extends JPanel {
    private JTextField txtFullName, txtPhone, txtSearch, txtPoints;
    private JComboBox<CustomerGender> cbGender;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public CustomerManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        // Form Nhập liệu
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Khách hàng"));

        panelForm.add(new JLabel("Họ và Tên:")); txtFullName = new JTextField(); panelForm.add(txtFullName);
        panelForm.add(new JLabel("Số điện thoại:")); txtPhone = new JTextField(); panelForm.add(txtPhone);
        panelForm.add(new JLabel("Điểm tích lũy (Cập nhật tự động):")); 
        txtPoints = new JTextField(); 
        txtPoints.setEditable(false); // Khác hàng tự đi mua để tăng điểm, hoặc thanh toán thì trừ, không cho nhập tay
        panelForm.add(txtPoints);
        
        panelForm.add(new JLabel("Giới tính:")); 
        cbGender = new JComboBox<>(CustomerGender.values()); 
        panelForm.add(cbGender);

        // Nút chức năng
        JPanel panelButtons = new JPanel(new FlowLayout());
        btnAdd = createStyledButton("Thêm", new Color(40, 167, 69));
        btnUpdate = createStyledButton("Sửa", new Color(0, 123, 255));
        btnDelete = createStyledButton("Xóa", new Color(220, 53, 69));
        btnClear = createStyledButton("Làm mới", new Color(108, 117, 125));
        panelButtons.add(btnAdd); panelButtons.add(btnUpdate); 
        panelButtons.add(btnDelete); panelButtons.add(btnClear);

        // Vùng Tìm kiếm
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSearch.add(new JLabel("Tìm theo SĐT:"));
        txtSearch = new JTextField(15);
        btnSearch = createStyledButton("Tìm kiếm", new Color(23, 162, 184));
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);

        // Gom Form và Nút bấm lên trên cùng
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(panelForm, BorderLayout.NORTH);
        topPanel.add(panelButtons, BorderLayout.CENTER);
        topPanel.add(panelSearch, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu
        tableModel = new DefaultTableModel(new String[]{"ID", "Họ và Tên", "Số điện thoại", "Điểm tích lũy", "Giới tính"}, 0);
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
    public JTextField getTxtFullName() { return txtFullName; }
    public JTextField getTxtPhone() { return txtPhone; }
    public JTextField getTxtPoints() { return txtPoints; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JComboBox<CustomerGender> getCbGender() { return cbGender; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}