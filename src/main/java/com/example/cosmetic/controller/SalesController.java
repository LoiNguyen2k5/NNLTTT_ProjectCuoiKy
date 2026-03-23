package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.*;
import com.example.cosmetic.service.*;
import com.example.cosmetic.view.invoice.SalesPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SalesController {
    private final SalesPanel view;
    private final ProductService productService;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;
    private final Staff currentStaff;

    public SalesController(SalesPanel view, ProductService ps, CustomerService cs, InvoiceService is, Staff staff) {
        this.view = view;
        this.productService = ps;
        this.customerService = cs;
        this.invoiceService = is;
        this.currentStaff = staff;

        loadInitialData();
        initEvents();
    }

    private void loadInitialData() {
        // Load sản phẩm vào ComboBox
        productService.getAllProducts().forEach(p -> view.getCbProduct().addItem(p));
        // Load khách hàng vào ComboBox (Tạm thời dùng JComboBox<Object> trong SalesPanel)
        customerService.getAllCustomers().forEach(c -> view.getCbCustomer().addItem(c));
    }

    private void initEvents() {
        // Sự kiện 1: Thêm vào giỏ hàng
        view.getBtnAddCart().addActionListener(e -> addToCart());

        // Sự kiện 2: Thanh toán
        view.getBtnCheckout().addActionListener(e -> processCheckout());
    }

    private void addToCart() {
        Product selectedProd = (Product) view.getCbProduct().getSelectedItem();
        int qty;
        try {
            qty = Integer.parseInt(view.getTxtQuantity().getText());
            if (qty <= 0) throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Số lượng phải là số nguyên dương!");
            return;
        }

        DefaultTableModel model = view.getCartModel();
        boolean exists = false;
        
        // Check nếu sản phẩm đã có trong giỏ thì cộng dồn số lượng
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(selectedProd.getId())) {
                int oldQty = (int) model.getValueAt(i, 2);
                int newQty = oldQty + qty;
                model.setValueAt(newQty, i, 2);
                model.setValueAt(selectedProd.getPrice().multiply(new BigDecimal(newQty)), i, 4);
                exists = true;
                break;
            }
        }

        if (!exists) {
            BigDecimal total = selectedProd.getPrice().multiply(new BigDecimal(qty));
            model.addRow(new Object[]{selectedProd.getId(), selectedProd.getName(), qty, selectedProd.getPrice(), total});
        }
        calculateTotal();
    }

    private void calculateTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (int i = 0; i < view.getCartModel().getRowCount(); i++) {
            grandTotal = grandTotal.add((BigDecimal) view.getCartModel().getValueAt(i, 4));
        }
        view.getTxtTotal().setText(grandTotal.toString());
    }

    private void processCheckout() {
        if (view.getCartModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Giỏ hàng trống!");
            return;
        }

        try {
            Invoice invoice = new Invoice();
            invoice.setStaff(currentStaff);
            invoice.setCustomer((Customer) view.getCbCustomer().getSelectedItem());
            invoice.setTotalAmount(new BigDecimal(view.getTxtTotal().getText()));

            List<InvoiceDetail> details = new ArrayList<>();
            for (int i = 0; i < view.getCartModel().getRowCount(); i++) {
                InvoiceDetail d = new InvoiceDetail();
                // Tìm lại sản phẩm từ ID
                Long pId = (Long) view.getCartModel().getValueAt(i, 0);
                Product p = new Product(); p.setId(pId); // Dummy product để map ID
                
                d.setProduct(p);
                d.setQuantity((int) view.getCartModel().getValueAt(i, 2));
                d.setPrice((BigDecimal) view.getCartModel().getValueAt(i, 3));
                d.setInvoice(invoice);
                details.add(d);
            }
            invoice.setDetails(details);

            // Gọi Service để thực hiện Transaction (Lưu + Trừ kho)
            invoiceService.processCheckout(invoice);
            
            JOptionPane.showMessageDialog(view, "Thanh toán thành công! Kho đã được cập nhật.");
            view.getCartModel().setRowCount(0);
            view.getTxtTotal().setText("0");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi thanh toán: " + ex.getMessage());
        }
    }
}