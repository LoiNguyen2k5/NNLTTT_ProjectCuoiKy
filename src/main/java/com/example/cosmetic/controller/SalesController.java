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
        view.getCbProduct().removeAllItems();
        view.getCbCustomer().removeAllItems();
        productService.getAllProducts().forEach(p -> view.getCbProduct().addItem(p));
        customerService.getAllCustomers().forEach(c -> view.getCbCustomer().addItem(c));
    }

    private void initEvents() {
        view.getBtnAddCart().addActionListener(e -> addToCart());
        view.getBtnCheckout().addActionListener(e -> processCheckout());
    }

    private void addToCart() {
        Product selectedProd = (Product) view.getCbProduct().getSelectedItem();
        if (selectedProd == null) return; 

        int qty;
        try {
            qty = Integer.parseInt(view.getTxtQuantity().getText());
            if (qty <= 0) throw new Exception();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Số lượng phải là số nguyên dương!"); return;
        }

        DefaultTableModel model = view.getCartModel();
        boolean exists = false;
        
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(selectedProd.getId())) {
                int oldQty = (int) model.getValueAt(i, 3);
                int newQty = oldQty + qty;
                model.setValueAt(newQty, i, 3);
                model.setValueAt(selectedProd.getPrice().multiply(new BigDecimal(newQty)), i, 5); 
                exists = true; break;
            }
        }

        if (!exists) {
            BigDecimal total = selectedProd.getPrice().multiply(new BigDecimal(qty));
            model.addRow(new Object[]{ selectedProd.getId(), selectedProd.getBarcode(), selectedProd.getName(), qty, selectedProd.getPrice(), total });
        }
        calculateTotal();
    }

    private void calculateTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (int i = 0; i < view.getCartModel().getRowCount(); i++) {
            grandTotal = grandTotal.add((BigDecimal) view.getCartModel().getValueAt(i, 5)); 
        }
        view.getTxtTotal().setText(grandTotal.toString());
    }

    private void processCheckout() {
        if (view.getCartModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Giỏ hàng trống!"); return;
        }

        Customer selectedCustomer = (Customer) view.getCbCustomer().getSelectedItem();
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khách hàng trước khi thanh toán!"); return;
        }

        try {
            Invoice invoice = new Invoice();
            invoice.setStaff(currentStaff);
            invoice.setCustomer(selectedCustomer);
            invoice.setTotalAmount(new BigDecimal(view.getTxtTotal().getText()));

            List<InvoiceDetail> details = new ArrayList<>();
            for (int i = 0; i < view.getCartModel().getRowCount(); i++) {
                InvoiceDetail d = new InvoiceDetail();
                Long pId = (Long) view.getCartModel().getValueAt(i, 0);
                Product p = new Product(); p.setId(pId);
                
                d.setProduct(p);
                d.setQuantity((int) view.getCartModel().getValueAt(i, 3)); 
                d.setPrice((BigDecimal) view.getCartModel().getValueAt(i, 4)); 
                d.setInvoice(invoice);
                details.add(d);
            }
            invoice.setDetails(details);

            invoiceService.processCheckout(invoice);
            
            JOptionPane.showMessageDialog(view, "Thanh toán thành công! Kho đã được cập nhật.");
            view.getCartModel().setRowCount(0);
            view.getTxtTotal().setText("0");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi thanh toán: " + ex.getMessage());
        }
    }
}