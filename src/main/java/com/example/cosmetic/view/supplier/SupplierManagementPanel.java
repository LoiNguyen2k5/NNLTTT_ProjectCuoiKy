package com.example.cosmetic.view.supplier;
import javax.swing.*; import javax.swing.table.DefaultTableModel; import java.awt.*;

public class SupplierManagementPanel extends JPanel {
    private JTextField txtId, txtName, txtAddress, txtPhone;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table; private DefaultTableModel tableModel;

    public SupplierManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin Nhà cung cấp"));

        panelForm.add(new JLabel("ID (Tự động):")); txtId = new JTextField(); txtId.setEditable(false); panelForm.add(txtId);
        panelForm.add(new JLabel("Tên Nhà CC (*):")); txtName = new JTextField(); panelForm.add(txtName);
        panelForm.add(new JLabel("Địa chỉ:")); txtAddress = new JTextField(); panelForm.add(txtAddress);
        panelForm.add(new JLabel("Điện thoại:")); txtPhone = new JTextField(); panelForm.add(txtPhone);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Thêm"); btnUpdate = new JButton("Sửa"); btnDelete = new JButton("Xóa"); btnClear = new JButton("Làm mới");
        panelButtons.add(btnAdd); panelButtons.add(btnUpdate); panelButtons.add(btnDelete); panelButtons.add(btnClear);

        JPanel panelTop = new JPanel(new BorderLayout()); panelTop.add(panelForm, BorderLayout.CENTER); panelTop.add(panelButtons, BorderLayout.SOUTH);
        add(panelTop, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Tên Nhà CC", "Địa chỉ", "Điện thoại"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel); table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table); scrollPane.setBorder(BorderFactory.createTitledBorder("Danh sách Nhà cung cấp"));
        add(scrollPane, BorderLayout.CENTER);
    }
    public JTextField getTxtId() { return txtId; } public JTextField getTxtName() { return txtName; } public JTextField getTxtAddress() { return txtAddress; } public JTextField getTxtPhone() { return txtPhone; }
    public JButton getBtnAdd() { return btnAdd; } public JButton getBtnUpdate() { return btnUpdate; } public JButton getBtnDelete() { return btnDelete; } public JButton getBtnClear() { return btnClear; }
    public JTable getTable() { return table; } public DefaultTableModel getTableModel() { return tableModel; }
}