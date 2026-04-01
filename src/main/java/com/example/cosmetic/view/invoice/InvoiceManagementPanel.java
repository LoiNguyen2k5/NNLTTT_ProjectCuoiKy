package com.example.cosmetic.view.invoice;

import com.example.cosmetic.view.utils.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InvoiceManagementPanel extends JPanel {
    private JTable             table;
    private DefaultTableModel  tableModel;
    private JButton            btnRefresh;

    public InvoiceManagementPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 20, 24));

        // Title
        add(buildTitlePanel(), BorderLayout.NORTH);

        // Table card
        JPanel tableCard = UITheme.createCard();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(14, 16, 14, 16));

        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        toolbar.setOpaque(false);
        btnRefresh = UITheme.createButton("↺ Làm mới danh sách", UITheme.BTN_CLEAR);
        toolbar.add(btnRefresh);
        tableCard.add(toolbar, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(
                new String[]{"ID", "Mã HĐ", "Khách Hàng", "Nhân Viên Lập", "Ngày Lập", "Tổng Tiền (VND)"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTable(table);

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scroll.getViewport().setBackground(Color.WHITE);
        tableCard.add(scroll, BorderLayout.CENTER);

        add(tableCard, BorderLayout.CENTER);
    }

    private JPanel buildTitlePanel() {
        JLabel t = UITheme.createSectionTitle("Bán Hàng");
        JLabel s = UITheme.createSubTitle("LỊCH SỬ HÓA ĐƠN");
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(t); p.add(Box.createVerticalStrut(2)); p.add(s);
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(p, BorderLayout.WEST);
        return wrap;
    }

    public DefaultTableModel getTableModel() { return tableModel; }
    public JButton getBtnRefresh()           { return btnRefresh; }
}