package com.example.cosmetic.view.staff;

import com.example.cosmetic.view.components.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StaffManagementPanel extends JPanel {
    private JTextField     txtStaffCode, txtFullName, txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cbRole;
    private JTable         tblStaff;
    private DefaultTableModel tableModel;
    private JButton        btnAdd, btnUpdate, btnDelete, btnClear;

    public StaffManagementPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 20, 24));

        // Title
        add(buildTitlePanel("Quản Lý Nhân Viên", "DANH SÁCH NHÂN VIÊN"), BorderLayout.NORTH);

        // Form card
        JPanel formCard = UITheme.createCard();
        formCard.setLayout(new BorderLayout(0, 12));
        formCard.setBorder(new EmptyBorder(16, 18, 14, 18));

        JLabel formLbl = new JLabel("Thông tin Nhân viên");
        formLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formLbl.setForeground(UITheme.getTextColor());

        JPanel formGrid = new JPanel(new GridLayout(1, 5, 14, 0));
        formGrid.setOpaque(false);

        txtStaffCode = UITheme.createTextField();
        txtFullName  = UITheme.createTextField();
        txtUsername  = UITheme.createTextField();
        txtPassword  = UITheme.createPasswordField();
        cbRole       = UITheme.<String>createComboBox();
        cbRole.addItem("STAFF");
        cbRole.addItem("ADMIN");

        formGrid.add(makeField("Mã Nhân viên:",       txtStaffCode));
        formGrid.add(makeField("Họ và Tên:",           txtFullName));
        formGrid.add(makeField("Tài khoản (Username):",txtUsername));
        formGrid.add(makeField("Mật khẩu:",            txtPassword));
        formGrid.add(makeField("Vai trò (Phân quyền):",cbRole));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        btnAdd    = UITheme.createButton("+ Thêm mới",  UITheme.BTN_ADD);
        btnUpdate = UITheme.createButton("✎ Cập nhật",  UITheme.BTN_EDIT);
        btnDelete = UITheme.createButton("✕ Xóa",       UITheme.BTN_DELETE);
        btnClear  = UITheme.createButton("↺ Làm mới",   UITheme.BTN_CLEAR);
        btnRow.add(btnAdd); btnRow.add(btnUpdate); btnRow.add(btnDelete); btnRow.add(btnClear);

        formCard.add(formLbl,  BorderLayout.NORTH);
        formCard.add(formGrid, BorderLayout.CENTER);
        formCard.add(btnRow,   BorderLayout.SOUTH);

        // Table card
        JPanel tableCard = UITheme.createCard();
        tableCard.setLayout(new BorderLayout(0, 10));
        tableCard.setBorder(new EmptyBorder(14, 16, 14, 16));

        String[] cols = {"ID", "Mã NV", "Họ Tên", "Tài khoản", "Vai trò"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblStaff = new JTable(tableModel);
        UITheme.styleTable(tblStaff);
        // Badge role renderer
        tblStaff.getColumnModel().getColumn(4).setCellRenderer(new UITheme.GenericBadgeRenderer("ADMIN"));

        // Click dòng → điền form
        tblStaff.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tblStaff.getSelectedRow() != -1) {
                int row = tblStaff.getSelectedRow();
                txtStaffCode.setText(tblStaff.getValueAt(row, 1).toString());
                txtFullName.setText (tblStaff.getValueAt(row, 2).toString());
                txtUsername.setText  (tblStaff.getValueAt(row, 3).toString());
                cbRole.setSelectedItem(tblStaff.getValueAt(row, 4).toString());
                txtPassword.setText("123456");
            }
        });

        JScrollPane scroll = new JScrollPane(tblStaff);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scroll.getViewport().setBackground(UITheme.getCardColor());
        tableCard.add(scroll, BorderLayout.CENTER);

        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);
        center.add(formCard,  BorderLayout.NORTH);
        center.add(tableCard, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);
    }

    private JPanel buildTitlePanel(String title, String subtitle) {
        JLabel t = UITheme.createSectionTitle(title);
        JLabel s = UITheme.createSubTitle(subtitle);
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(t); p.add(Box.createVerticalStrut(2)); p.add(s);
        JPanel wrap = new JPanel(new BorderLayout());
        wrap.setOpaque(false);
        wrap.add(p, BorderLayout.WEST);
        return wrap;
    }

    private JPanel makeField(String label, JComponent input) {
        JPanel p = new JPanel(new BorderLayout(0, 4));
        p.setOpaque(false);
        JLabel lbl = new JLabel(label);
        lbl.setFont(UITheme.FONT_LABEL);
        lbl.setForeground(UITheme.getTextSec());
        p.add(lbl, BorderLayout.NORTH);
        p.add(input, BorderLayout.CENTER);
        return p;
    }

    // Getters
    public DefaultTableModel  getTableModel()   { return tableModel; }
    public JTable             getTblStaff()     { return tblStaff; }
    public JTextField         getTxtStaffCode() { return txtStaffCode; }
    public JTextField         getTxtFullName()  { return txtFullName; }
    public JTextField         getTxtUsername()  { return txtUsername; }
    public JPasswordField     getTxtPassword()  { return txtPassword; }
    public JComboBox<String>  getCbRole()       { return cbRole; }
    public JButton            getBtnAdd()       { return btnAdd; }
    public JButton            getBtnUpdate()    { return btnUpdate; }
    public JButton            getBtnDelete()    { return btnDelete; }
    public JButton            getBtnClear()     { return btnClear; }
}