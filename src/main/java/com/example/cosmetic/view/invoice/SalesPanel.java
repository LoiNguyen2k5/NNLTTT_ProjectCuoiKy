package com.example.cosmetic.view.invoice;

import com.example.cosmetic.model.entity.Customer;
import com.example.cosmetic.model.entity.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SalesPanel extends JPanel {
    // Component chọn sản phẩm
    private JComboBox<Product> cbProduct;
    private JTextField txtQuantity;
    private JButton btnAddCart;

    // Component chọn khách hàng (SỬA LẠI ĐỂ FIX LỖI)
    private JComboBox<Customer> cbCustomer; 
    private JTextField txtCustomerName; // Field này dùng để hiện tên nếu chọn Khách vãng lai

    // Bảng giỏ hàng
    private JTable tableCart;
    private DefaultTableModel cartModel;

    // Phần thanh toán
    private JTextField txtTotal;
    private JButton btnCheckout;

    public SalesPanel() {
        setLayout(new BorderLayout(10, 10));

        // --- 1. Panel phía trên (Chọn SP & KH) ---
        JPanel pnlTop = new JPanel(new GridLayout(3, 3, 5, 5));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin bán hàng"));

        // Dòng 1: Chọn sản phẩm
        pnlTop.add(new JLabel("Sản phẩm:"));
        cbProduct = new JComboBox<>();
        pnlTop.add(cbProduct);
        pnlTop.add(new JLabel("")); // Dummy label cho đẹp layout

        // Dòng 2: Số lượng và Nút add
        pnlTop.add(new JLabel("Số lượng mua:"));
        txtQuantity = new JTextField("1");
        pnlTop.add(txtQuantity);
        btnAddCart = new JButton("Thêm vào giỏ");
        btnAddCart.setBackground(new Color(100, 200, 255));
        pnlTop.add(btnAddCart);

        // Dòng 3: Chọn Khách hàng (SỬA LẠI ĐỂ FIX LỖI)
        pnlTop.add(new JLabel("Khách hàng:"));
        cbCustomer = new JComboBox<>();
        pnlTop.add(cbCustomer);
        
        txtCustomerName = new JTextField();
        txtCustomerName.setEditable(false); // Chỉ hiện tên, không cho gõ
        pnlTop.add(txtCustomerName);

        add(pnlTop, BorderLayout.NORTH);

        // --- 2. Panel chính giữa (Giỏ hàng JTable) ---
        cartModel = new DefaultTableModel(new String[]{"ID SP", "Barcode", "Tên Mỹ Phẩm", "Số lượng", "Đơn giá", "Thành tiền"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Khóa bảng không cho sửa
        };
        tableCart = new JTable(cartModel);
        // Chỉnh chiều rộng cột cho đẹp
        tableCart.getColumnModel().getColumn(2).setPreferredWidth(200); // Tên SP rộng ra
        
        JScrollPane scrollPane = new JScrollPane(tableCart);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Giỏ hàng tạm thời"));
        add(scrollPane, BorderLayout.CENTER);

        // --- 3. Panel phía dưới (Tổng tiền & Thanh toán) ---
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

    // =========================================================
    // GETTERS ĐẦY ĐỦ ĐỂ FIX LỖI CHO CONTROLLER
    // =========================================================
    public JComboBox<Product> getCbProduct() { return cbProduct; }
    public JTextField getTxtQuantity() { return txtQuantity; }
    public JButton getBtnAddCart() { return btnAddCart; }
    
    // ĐÂY LÀ HÀM QUAN TRỌNG NHẤT ĐỂ FIX LỖI CHO ÔNG
    public JComboBox<Customer> getCbCustomer() { return cbCustomer; } 
    
    public JTextField getTxtCustomerName() { return txtCustomerName; }
    public JTable getTableCart() { return tableCart; }
    public DefaultTableModel getCartModel() { return cartModel; }
    public JTextField getTxtTotal() { return txtTotal; }
    public JButton getBtnCheckout() { return btnCheckout; }
}