package com.example.cosmetic.view.invoice;

import com.example.cosmetic.model.entity.Customer;
import com.example.cosmetic.model.entity.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SalesPanel extends JPanel {
    private JComboBox<Product> cbProduct;
    private JTextField txtQuantity;
    private JButton btnAddCart;
    private JComboBox<Customer> cbCustomer; 
    private JTable tableCart;
    private DefaultTableModel cartModel;
    private JTextField txtTotal;
    private JButton btnCheckout;

    public SalesPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTop = new JPanel(new GridLayout(3, 3, 5, 5));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin bán hàng"));

        pnlTop.add(new JLabel("Sản phẩm:"));
        cbProduct = new JComboBox<>();
        pnlTop.add(cbProduct);
        pnlTop.add(new JLabel("")); 

        pnlTop.add(new JLabel("Số lượng mua:"));
        txtQuantity = new JTextField("1");
        pnlTop.add(txtQuantity);
        btnAddCart = new JButton("Thêm vào giỏ");
        btnAddCart.setBackground(new Color(100, 200, 255));
        pnlTop.add(btnAddCart);

        pnlTop.add(new JLabel("Khách hàng:"));
        cbCustomer = new JComboBox<>();
        pnlTop.add(cbCustomer);
        add(pnlTop, BorderLayout.NORTH);

        cartModel = new DefaultTableModel(new String[]{"ID SP", "Barcode", "Tên Mỹ Phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCart = new JTable(cartModel);
        tableCart.getColumnModel().getColumn(2).setPreferredWidth(200); 
        
        JScrollPane scrollPane = new JScrollPane(tableCart);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Giỏ hàng tạm thời"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        pnlBottom.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlTotal.add(new JLabel("TỔNG TIỀN PHẢI THANH TOÁN (VND):"));
        txtTotal = new JTextField("0", 15);
        txtTotal.setEditable(false);
        txtTotal.setFont(new Font("Arial", Font.BOLD, 16));
        txtTotal.setForeground(Color.RED);
        txtTotal.setHorizontalAlignment(JTextField.RIGHT);
        pnlTotal.add(txtTotal);

        btnCheckout = new JButton("THANH TOÁN & LẬP HÓA ĐƠN");
        btnCheckout.setFont(new Font("Arial", Font.BOLD, 14));
        btnCheckout.setBackground(new Color(50, 200, 50));
        btnCheckout.setForeground(Color.WHITE);
        btnCheckout.setPreferredSize(new Dimension(300, 40));

        pnlBottom.add(pnlTotal, BorderLayout.NORTH);
        pnlBottom.add(btnCheckout, BorderLayout.CENTER);

        add(pnlBottom, BorderLayout.SOUTH);
    }

    public JComboBox<Product> getCbProduct() { return cbProduct; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JButton getBtnAddCart() { return btnAddCart; }
    public JComboBox<Customer> getCbCustomer() { return cbCustomer; } 
    public JTable getTableCart() { return tableCart; }
    public DefaultTableModel getCartModel() { return cartModel; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JButton getBtnCheckout() { return btnCheckout; }
}