package com.example.cosmetic.view.invoice;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ImportHistoryPanel extends JPanel {
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnRefresh;

    public ImportHistoryPanel() {
        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnRefresh = new JButton("Làm mới danh sách");
        topPanel.add(btnRefresh);
        add(topPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"ID", "Mã Phiếu", "Nhà Cung Cấp", "Người Nhập", "Ngày Nhập", "Tổng Tiền (VND)"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh() { return btnRefresh; }
}