package com.example.cosmetic.view.invoice;

import com.example.cosmetic.model.entity.Product;
import com.example.cosmetic.model.entity.Supplier;
import com.example.cosmetic.view.components.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ImportPanel extends JPanel {
    private JComboBox<Supplier>  cbSupplier;
    private JComboBox<Product>   cbProduct;
    private JTextField           txtQuantity, txtImportPrice, txtTotal;
    private JButton              btnAddCart, btnImport;
    private JTable               tableCart;
    private DefaultTableModel    cartModel;

    public ImportPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 20, 24));

        // ===== TITLE =====
        add(buildTitlePanel(), BorderLayout.NORTH);

        // ===== CENTER: Form card + Table card =====
        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);

        // -- Form card --
        JPanel formCard = UITheme.createCard();
        formCard.setLayout(new BorderLayout(0, 12));
        formCard.setBorder(new EmptyBorder(16, 18, 14, 18));

        JLabel formLbl = new JLabel("Thông Tin Nhập Hàng");
        formLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formLbl.setForeground(UITheme.getTextColor());

        JPanel fieldGrid = new JPanel(new GridLayout(1, 4, 14, 0));
        fieldGrid.setOpaque(false);

        cbSupplier    = UITheme.<Supplier>createComboBox();
        cbProduct     = UITheme.<Product>createComboBox();
        txtQuantity   = UITheme.createTextField(); txtQuantity.setText("10");
        txtImportPrice= UITheme.createTextField(); txtImportPrice.setText("0");

        fieldGrid.add(makeField("Nhà cung cấp:",     cbSupplier));
        fieldGrid.add(makeField("Sản phẩm:",          cbProduct));
        fieldGrid.add(makeField("Số lượng nhập:",     txtQuantity));
        fieldGrid.add(makeField("Giá nhập (VND):",    txtImportPrice));

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setOpaque(false);
        btnAddCart = UITheme.createButton("+ Thêm vào phiếu", UITheme.BTN_ADD);
        btnRow.add(btnAddCart);

        formCard.add(formLbl,    BorderLayout.NORTH);
        formCard.add(fieldGrid,  BorderLayout.CENTER);
        formCard.add(btnRow,     BorderLayout.SOUTH);

        // -- Table card --
        JPanel tableCard = UITheme.createCard();
        tableCard.setLayout(new BorderLayout(0, 0));
        tableCard.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel cartLbl = new JLabel("Chi Tiết Phiếu Nhập");
        cartLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cartLbl.setForeground(UITheme.getTextColor());
        cartLbl.setBorder(new EmptyBorder(0, 0, 10, 0));

        cartModel = new DefaultTableModel(
                new String[]{"ID SP", "Tên Mỹ Phẩm", "Số lượng", "Giá Nhập", "Thành tiền"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCart = new JTable(cartModel);
        UITheme.styleTable(tableCart);

        JScrollPane scroll = new JScrollPane(tableCart);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scroll.getViewport().setBackground(Color.WHITE);

        tableCard.add(cartLbl, BorderLayout.NORTH);
        tableCard.add(scroll,  BorderLayout.CENTER);

        center.add(formCard,  BorderLayout.NORTH);
        center.add(tableCard, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // ===== SOUTH: Total + Import button =====
        JPanel bottomCard = UITheme.createCard();
        bottomCard.setLayout(new FlowLayout(FlowLayout.RIGHT, 14, 10));
        bottomCard.setBorder(new EmptyBorder(4, 18, 4, 18));

        JLabel totalLbl = new JLabel("TỔNG TIỀN NHẬP (VND):");
        totalLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        totalLbl.setForeground(UITheme.getTextSec());
        bottomCard.add(totalLbl);

        txtTotal = new JTextField("0", 14);
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTotal.setForeground(UITheme.DANGER);
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtTotal.setBorder(new EmptyBorder(4, 8, 4, 8));
        bottomCard.add(txtTotal);

        btnImport = UITheme.createButton("📥 CHỐT PHIẾU NHẬP KHO", UITheme.INFO);
        btnImport.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnImport.setPreferredSize(new Dimension(280, 42));
        bottomCard.add(btnImport);

        add(bottomCard, BorderLayout.SOUTH);
    }

    private JPanel buildTitlePanel() {
        JLabel t = UITheme.createSectionTitle("Quản Lý Kho");
        JLabel s = UITheme.createSubTitle("NHẬP HÀNG VÀO KHO");
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

    // ========== GETTERS ==========
    public JComboBox<Supplier> getCbSupplier()    { return cbSupplier; }
    public JComboBox<Product>  getCbProduct()     { return cbProduct; }
    public JTextField          getTxtQuantity()   { return txtQuantity; }
    public JTextField          getTxtImportPrice(){ return txtImportPrice; }
    public JButton             getBtnAddCart()    { return btnAddCart; }
    public JTable              getTableCart()     { return tableCart; }
    public DefaultTableModel   getCartModel()     { return cartModel; }
    public JTextField          getTxtTotal()      { return txtTotal; }
    public JButton             getBtnImport()     { return btnImport; }
}