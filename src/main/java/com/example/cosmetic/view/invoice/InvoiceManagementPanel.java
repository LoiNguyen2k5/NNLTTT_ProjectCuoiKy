package com.example.cosmetic.view.invoice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InvoiceManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;

    public InvoiceManagementPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRefresh = new JButton("Làm mới danh sách");
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Mã HĐ", "Khách Hàng", "Nhân Viên Lập", "Ngày Lập", "Tổng Tiền (VND)"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
}