package com.example.cosmetic.view.invoice;

import com.example.cosmetic.model.entity.Product;
import com.example.cosmetic.model.entity.Supplier;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ImportPanel extends JPanel {
    private JComboBox<Supplier> cbSupplier;
    private JComboBox<Product> cbProduct;
    private JTextField txtQuantity, txtImportPrice, txtTotal;
    private JButton btnAddCart, btnImport;
    private JTable tableCart;
    private DefaultTableModel cartModel;

    public ImportPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTop = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Nhập Hàng Mới"));

        pnlTop.add(new JLabel("Nhà cung cấp:")); cbSupplier = new JComboBox<>(); pnlTop.add(cbSupplier);
        pnlTop.add(new JLabel("Sản phẩm:")); cbProduct = new JComboBox<>(); pnlTop.add(cbProduct);
        pnlTop.add(new JLabel("Số lượng nhập:")); txtQuantity = new JTextField("10"); pnlTop.add(txtQuantity);
        
        JPanel pnlPriceAdd = new JPanel(new BorderLayout(5, 0));
        txtImportPrice = new JTextField("0"); 
        btnAddCart = new JButton("Thêm vào phiếu"); btnAddCart.setBackground(Color.ORANGE);
        pnlPriceAdd.add(txtImportPrice, BorderLayout.CENTER);
        pnlPriceAdd.add(btnAddCart, BorderLayout.EAST);
        
        pnlTop.add(new JLabel("Giá nhập (VND):")); pnlTop.add(pnlPriceAdd);
        add(pnlTop, BorderLayout.NORTH);

        cartModel = new DefaultTableModel(new String[]{"ID SP", "Tên Mỹ Phẩm", "Số lượng", "Giá Nhập", "Thành tiền"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tableCart = new JTable(cartModel);
        add(new JScrollPane(tableCart), BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new BorderLayout());
        JPanel pnlTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlTotal.add(new JLabel("TỔNG TIỀN NHẬP (VND):"));
        txtTotal = new JTextField("0", 15); txtTotal.setEditable(false); txtTotal.setForeground(Color.RED);
        pnlTotal.add(txtTotal);

        btnImport = new JButton("CHỐT PHIẾU NHẬP KHO");
        btnImport.setBackground(new Color(50, 150, 250)); btnImport.setForeground(Color.WHITE);
        pnlBottom.add(pnlTotal, BorderLayout.NORTH);
        pnlBottom.add(btnImport, BorderLayout.CENTER);
        add(pnlBottom, BorderLayout.SOUTH);
    }

    public JComboBox<Supplier> getCbSupplier() { return cbSupplier; }
    public JComboBox<Product> getCbProduct() { return cbProduct; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JTextField getTxtImportPrice() { return txtImportPrice; }
    public JButton getBtnAddCart() { return btnAddCart; }
    public JTable getTableCart() { return tableCart; }
    public DefaultTableModel getCartModel() { return cartModel; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JButton getBtnImport() { return btnImport; }
}