package com.example.cosmetic.view.promotion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PromotionManagementPanel extends JPanel {
    private JTextField txtCode, txtDescription, txtDiscountPercent, txtMaxDiscount, txtMinPurchase, txtStartDate, txtEndDate, txtSearch;
    private JCheckBox chkIsActive;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear, btnSearch;
    private JTable table;
    private DefaultTableModel tableModel;

    public PromotionManagementPanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(15, 15, 15, 15));

        // --- FORM PANEL ---
        JPanel panelForm = new JPanel(new GridLayout(4, 2, 25, 15));
        panelForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)), 
                        "Thông tin Khuyến mãi", 0, 0, new Font("Arial", Font.BOLD, 14), new Color(0, 102, 204)),
                new EmptyBorder(15, 15, 15, 15)
        ));

        txtCode = new JTextField();
        txtDescription = new JTextField();
        txtDiscountPercent = new JTextField();
        txtMaxDiscount = new JTextField();
        txtMinPurchase = new JTextField();
        txtStartDate = new JTextField();
        txtEndDate = new JTextField();
        chkIsActive = new JCheckBox("Trạng thái hoạt động (Cho phép sử dụng)");
        chkIsActive.setSelected(true);
        chkIsActive.setFont(new Font("Arial", Font.PLAIN, 13));

        panelForm.add(createFieldPanel("Mã Code (*):", txtCode));
        panelForm.add(createFieldPanel("Mô tả:", txtDescription));
        panelForm.add(createFieldPanel("Phần trăm giảm (1-100) (*):", txtDiscountPercent));
        panelForm.add(createFieldPanel("Giảm tối đa (Optional - VNĐ):", txtMaxDiscount));
        panelForm.add(createFieldPanel("Ngày bắt đầu (dd/MM/yyyy) (*):", txtStartDate));
        panelForm.add(createFieldPanel("Ngày kết thúc (dd/MM/yyyy) (*):", txtEndDate));
        panelForm.add(createFieldPanel("Đơn hàng tối thiểu (Optional - VNĐ):", txtMinPurchase));
        
        JPanel chkPanel = new JPanel(new BorderLayout());
        chkPanel.add(chkIsActive, BorderLayout.CENTER);
        panelForm.add(createFieldPanel("Trạng thái (Áp dụng):", chkPanel));

        // --- BUTTONS PANEL ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        btnAdd = createStyledButton("Thêm mới", new Color(40, 167, 69)); 
        btnUpdate = createStyledButton("Cập nhật", new Color(0, 123, 255)); 
        btnDelete = createStyledButton("Xóa", new Color(220, 53, 69)); 
        btnClear = createStyledButton("Làm mới form", new Color(108, 117, 125));
        
        panelButtons.add(btnAdd); 
        panelButtons.add(btnUpdate); 
        panelButtons.add(btnDelete); 
        panelButtons.add(btnClear);

        // --- SEARCH PANEL ---
        JPanel panelSearch = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelSearch.setBorder(new EmptyBorder(10, 0, 5, 0));
        JLabel lblSearch = new JLabel("Tìm kiếm (Mã Code):");
        lblSearch.setFont(new Font("Arial", Font.BOLD, 13));
        panelSearch.add(lblSearch);
        txtSearch = new JTextField(25);
        txtSearch.setPreferredSize(new Dimension(200, 30));
        btnSearch = createStyledButton("Tìm kiếm", new Color(23, 162, 184));
        panelSearch.add(txtSearch);
        panelSearch.add(btnSearch);

        // --- TOP CONTENT ASSEMBLY ---
        JPanel topContent = new JPanel();
        topContent.setLayout(new BoxLayout(topContent, BoxLayout.Y_AXIS));
        
        panelForm.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelButtons.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelSearch.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        topContent.add(panelForm);
        topContent.add(Box.createRigidArea(new Dimension(0, 5)));
        topContent.add(panelButtons);
        topContent.add(panelSearch);

        add(topContent, BorderLayout.NORTH);

        // --- TABLE ---
        tableModel = new DefaultTableModel(new String[]{
            "ID", "Mã Code", "Mô Tả", "% Giảm", "Giảm Tối Đa", "Đơn Tối Thiểu", "Từ ngày", "Đến ngày", "Trạng thái"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createFieldPanel(String labelText, JComponent inputComp) {
        JPanel panel = new JPanel(new BorderLayout(0, 5));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        
        if (inputComp instanceof JTextField) {
            inputComp.setPreferredSize(new Dimension(0, 32));
        }
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(inputComp, BorderLayout.CENTER);
        return panel;
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
    public JTextField getTxtCode() { return txtCode; }
    public JTextField getTxtDescription() { return txtDescription; }
    public JTextField getTxtDiscountPercent() { return txtDiscountPercent; }
    public JTextField getTxtMaxDiscount() { return txtMaxDiscount; }
    public JTextField getTxtMinPurchase() { return txtMinPurchase; }
    public JTextField getTxtStartDate() { return txtStartDate; }
    public JTextField getTxtEndDate() { return txtEndDate; }
    public JCheckBox getChkIsActive() { return chkIsActive; }
    public JButton getBtnAdd() { return btnAdd; }
    public JButton getBtnUpdate() { return btnUpdate; }
    public JButton getBtnDelete() { return btnDelete; }
    public JButton getBtnClear() { return btnClear; }
    public JTextField getTxtSearch() { return txtSearch; }
    public JButton getBtnSearch() { return btnSearch; }
    public JTable getTable() { return table; }
    public DefaultTableModel getTableModel() { return tableModel; }
}
