package com.example.cosmetic.view.invoice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InvoiceManagementPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnViewDetail, btnRefresh;

    public InvoiceManagementPanel() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createTitledBorder("Lịch sử Hóa đơn đã bán"));

        // Bảng hiển thị danh sách hóa đơn
        tableModel = new DefaultTableModel(new String[]{"ID", "Ngày lập", "Tổng tiền", "Nhân viên", "Khách hàng"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Nút chức năng
        JPanel pnlButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnViewDetail = new JButton("Xem chi tiết hóa đơn");
        btnRefresh = new JButton("Làm mới danh sách");
        pnlButtons.add(btnViewDetail);
        pnlButtons.add(btnRefresh);

        add(pnlButtons, BorderLayout.SOUTH);
    }

    // Getters
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnViewDetail() { return btnViewDetail; }
    public JButton getBtnRefresh() { return btnRefresh; }
}