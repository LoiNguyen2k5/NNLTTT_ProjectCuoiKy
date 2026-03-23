package com.example.cosmetic.view.brand;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class BrandManagementPanel extends JPanel {
    private JTextField txtId, txtName, txtDescription;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel tableModel;

    public BrandManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Thương hiệu"));

        panelForm.add(new JLabel("ID (Tự động):")); txtId = new JTextField(); txtId.setEditable(false); panelForm.add(txtId);
        panelForm.add(new JLabel("Tên thương hiệu (*):")); txtName = new JTextField(); panelForm.add(txtName);
        panelForm.add(new JLabel("Mô tả:")); txtDescription = new JTextField(); panelForm.add(txtDescription);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Thêm"); btnUpdate = new JButton("Sửa"); btnDelete = new JButton("Xóa"); btnClear = new JButton("Làm mới");
        panelButtons.add(btnAdd); panelButtons.add(btnUpdate); panelButtons.add(btnDelete); panelButtons.add(btnClear);

        JPanel panelTop = new JPanel(new BorderLayout()); panelTop.add(panelForm, BorderLayout.CENTER); panelTop.add(panelButtons, BorderLayout.SOUTH);
        add(panelTop, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên thương hiệu", "Mô tả"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel); table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table); scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Thương hiệu"));
        add(scrollPane, BorderLayout.CENTER);
    }
    public JTextField getTxtId() { return txtId; } public JTextField getTxtName() { return txtName; } public JTextField getTxtDescription() { return txtDescription; }
    public JButton getBtnAdd() { return btnAdd; } public JButton getBtnUpdate() { return btnUpdate; } public JButton getBtnDelete() { return btnDelete; } public JButton getBtnClear() { return btnClear; }
    public JTable getTable() { return table; } public DefaultTableModel getTableModel() { return tableModel; }
}