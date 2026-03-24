package com.example.cosmetic.view.customer;

import com.example.cosmetic.model.enums.CustomerGender;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CustomerManagementPanel extends JPanel {
    private JTextField txtFullName, txtPhone, txtSearch;
    private JComboBox<CustomerGender> cbGender;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public CustomerManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        // Form Nhập liệu
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Khách hàng"));

        panelForm.add(new JLabel("Họ và Tên:")); txtFullName = new JTextField(); panelForm.add(txtFullName);
        panelForm.add(new JLabel("Số điện thoại:")); txtPhone = new JTextField(); panelForm.add(txtPhone);
        panelForm.add(new JLabel("Giới tính:")); 
        cbGender = new JComboBox<>(CustomerGender.values()); 
        panelForm.add(cbGender);

        // Nút chức năng
        JPanel panelButtons = new JPanel(new FlowLayout());
        btnAdd = new JButton("Thêm"); btnUpdate = new JButton("Sửa"); 
        btnDelete = new JButton("Xóa"); btnClear = new JButton("Làm mới");
        panelButtons.add(btnAdd); panelButtons.add(btnUpdate); 
        panelButtons.add(btnDelete); panelButtons.add(btnClear);

        // Vùng Tìm kiếm
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelSearch.add(new JLabel("Tìm theo SĐT:"));
        txtSearch = new JTextField(15);
        btnSearch = new JButton("Tìm kiếm");
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);

        // Gom Form và Nút bấm lên trên cùng
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(panelForm, BorderLayout.NORTH);
        topPanel.add(panelButtons, BorderLayout.CENTER);
        topPanel.add(panelSearch, BorderLayout.SOUTH);
        add(topPanel, BorderLayout.NORTH);

        // Bảng dữ liệu
        tableModel = new DefaultTableModel(new String[]{"ID", "Họ và Tên", "Số điện thoại", "Giới tính"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    // Getters
    public JTextField getTxtFullName() { return txtFullName; }
    public JTextField getTxtPhone() { return txtPhone; }
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