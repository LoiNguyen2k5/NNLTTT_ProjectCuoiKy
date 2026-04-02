package com.example.cosmetic.view.invoice;

import com.example.cosmetic.model.entity.Customer;
import com.example.cosmetic.model.entity.Product;
import com.example.cosmetic.view.components.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SalesPanel extends JPanel {
    private JComboBox<Product>   cbProduct;
    private JTextField           txtQuantity;
    private JButton              btnAddCart;
    private JComboBox<Customer>  cbCustomer;
    private JLabel               lblCustomerPoints;
    private JTable               tableCart;
    private DefaultTableModel    cartModel;
    private JTextField           txtTotal;
    private JCheckBox            chkUsePoints;
    private JTextField           txtVoucherCode;
    private JButton              btnApplyVoucher;
    private JLabel               lblVoucherStatus;
    private JButton              btnCheckout;

    public SalesPanel() {
        setLayout(new BorderLayout(0, 16));
        setBackground(UITheme.getBgColor());
        setBorder(new EmptyBorder(20, 24, 20, 24));

        // ===== TITLE =====
        add(buildTitlePanel(), BorderLayout.NORTH);

        // ===== CENTER: Form card + Cart card =====
        JPanel center = new JPanel(new BorderLayout(0, 14));
        center.setOpaque(false);

        // -- Form card --
        JPanel formCard = UITheme.createCard();
        formCard.setLayout(new BorderLayout(0, 12));
        formCard.setBorder(new EmptyBorder(16, 18, 14, 18));

        JLabel formLbl = new JLabel("Thông Tin Bán Hàng");
        formLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formLbl.setForeground(UITheme.getTextColor());

        // 3 fields in a row: Product | Qty+Add Button | Customer
        JPanel fieldRow = new JPanel(new GridLayout(1, 3, 14, 0));
        fieldRow.setOpaque(false);

        // Product field
        cbProduct = UITheme.<Product>createComboBox();
        fieldRow.add(makeField("Sản phẩm:", cbProduct));

        // Qty + button field
        JPanel qtyPanel = new JPanel(new BorderLayout(8, 0));
        qtyPanel.setOpaque(false);
        txtQuantity = UITheme.createTextField();
        txtQuantity.setText("1");
        btnAddCart  = UITheme.createButton("+ Thêm vào giỏ", UITheme.BTN_ADD);
        qtyPanel.add(txtQuantity, BorderLayout.CENTER);
        qtyPanel.add(btnAddCart, BorderLayout.EAST);
        fieldRow.add(makeField("Số lượng mua:", qtyPanel));

        // Customer + points
        JPanel custPanel = new JPanel(new BorderLayout(8, 0));
        custPanel.setOpaque(false);
        cbCustomer = UITheme.<Customer>createComboBox();
        lblCustomerPoints = new JLabel("Điểm: 0");
        lblCustomerPoints.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCustomerPoints.setForeground(UITheme.PRIMARY);
        lblCustomerPoints.setBorder(new EmptyBorder(0, 8, 0, 0));
        custPanel.add(cbCustomer, BorderLayout.CENTER);
        custPanel.add(lblCustomerPoints, BorderLayout.EAST);
        fieldRow.add(makeField("Khách hàng:", custPanel));

        formCard.add(formLbl,  BorderLayout.NORTH);
        formCard.add(fieldRow, BorderLayout.CENTER);

        // -- Cart card --
        JPanel cartCard = UITheme.createCard();
        cartCard.setLayout(new BorderLayout(0, 0));
        cartCard.setBorder(new EmptyBorder(14, 16, 14, 16));

        JLabel cartLbl = new JLabel("Giỏ Hàng Tạm Thời");
        cartLbl.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cartLbl.setForeground(UITheme.getTextColor());
        cartLbl.setBorder(new EmptyBorder(0, 0, 10, 0));

        cartModel = new DefaultTableModel(
                new String[]{"ID SP", "Barcode", "Tên Mỹ Phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCart = new JTable(cartModel);
        UITheme.styleTable(tableCart);
        tableCart.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(tableCart);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR));
        scrollPane.getViewport().setBackground(Color.WHITE);

        cartCard.add(cartLbl, BorderLayout.NORTH);
        cartCard.add(scrollPane, BorderLayout.CENTER);

        center.add(formCard, BorderLayout.NORTH);
        center.add(cartCard, BorderLayout.CENTER);
        add(center, BorderLayout.CENTER);

        // ===== SOUTH: Voucher + Total + Checkout =====
        JPanel bottomCard = UITheme.createCard();
        bottomCard.setLayout(new BorderLayout(0, 8));
        bottomCard.setBorder(new EmptyBorder(14, 18, 14, 18));

        // Voucher row
        JPanel voucherRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        voucherRow.setOpaque(false);
        voucherRow.add(new JLabel("Mã Voucher:") {{
            setFont(UITheme.FONT_LABEL);
            setForeground(UITheme.getTextSec());
        }});
        txtVoucherCode = UITheme.createTextField();
        txtVoucherCode.setPreferredSize(new Dimension(160, 34));
        voucherRow.add(txtVoucherCode);
        btnApplyVoucher = UITheme.createButton("Áp dụng", UITheme.BTN_SEARCH);
        voucherRow.add(btnApplyVoucher);
        lblVoucherStatus = new JLabel("");
        lblVoucherStatus.setFont(UITheme.FONT_LABEL);
        lblVoucherStatus.setForeground(UITheme.SUCCESS);
        voucherRow.add(lblVoucherStatus);

        // Total + checkout row
        JPanel totalRow = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        totalRow.setOpaque(false);
        chkUsePoints = new JCheckBox("Dùng điểm giảm giá");
        chkUsePoints.setFont(UITheme.FONT_BODY);
        chkUsePoints.setOpaque(false);
        chkUsePoints.setForeground(UITheme.getTextSec());
        totalRow.add(chkUsePoints);

        JLabel totalLbl = new JLabel("TỔNG TIỀN THANH TOÁN:");
        totalLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        totalLbl.setForeground(UITheme.getTextSec());
        totalRow.add(totalLbl);

        txtTotal = new JTextField("0", 14);
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTotal.setForeground(UITheme.DANGER);
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        txtTotal.setBorder(new EmptyBorder(4, 8, 4, 8));
        totalRow.add(txtTotal);

        btnCheckout = UITheme.createButton("✔ THANH TOÁN & LẬP HÓA ĐƠN", UITheme.SUCCESS);
        btnCheckout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCheckout.setPreferredSize(new Dimension(310, 42));
        totalRow.add(btnCheckout);

        bottomCard.add(voucherRow, BorderLayout.NORTH);
        bottomCard.add(totalRow, BorderLayout.SOUTH);
        add(bottomCard, BorderLayout.SOUTH);
    }

    private JPanel buildTitlePanel() {
        JLabel t = UITheme.createSectionTitle("Bán Hàng");
        JLabel s = UITheme.createSubTitle("LẬP HÓA ĐƠN BÁN HÀNG");
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
    public JComboBox<Product>  getCbProduct()       { return cbProduct; }
    public JTextField          getTxtQuantity()     { return txtQuantity; }
    public JButton             getBtnAddCart()      { return btnAddCart; }
    public JComboBox<Customer> getCbCustomer()      { return cbCustomer; }
    public JLabel              getLblCustomerPoints(){ return lblCustomerPoints; }
    public JTable              getTableCart()       { return tableCart; }
    public DefaultTableModel   getCartModel()       { return cartModel; }
    public JTextField          getTxtTotal()        { return txtTotal; }
    public JCheckBox           getChkUsePoints()    { return chkUsePoints; }
    public JTextField          getTxtVoucherCode()  { return txtVoucherCode; }
    public JButton             getBtnApplyVoucher() { return btnApplyVoucher; }
    public JLabel              getLblVoucherStatus(){ return lblVoucherStatus; }
    public JButton             getBtnCheckout()     { return btnCheckout; }
}