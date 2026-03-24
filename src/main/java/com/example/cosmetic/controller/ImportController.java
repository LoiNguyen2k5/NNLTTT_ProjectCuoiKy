package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.*;
import com.example.cosmetic.service.*;
import com.example.cosmetic.view.invoice.ImportPanel;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ImportController {
    private final ImportPanel view;
    private final ProductService productService;
    private final SupplierService supplierService;
    private final ImportReceiptService importService;
    private final Staff currentStaff;

    public ImportController(ImportPanel view, ProductService ps, SupplierService ss, ImportReceiptService is, Staff staff) {
        this.view = view; this.productService = ps; this.supplierService = ss; this.importService = is; this.currentStaff = staff;
        loadInitialData();
        initEvents();
    }

    private void loadInitialData() {
        view.getCbProduct().removeAllItems(); view.getCbSupplier().removeAllItems();
        productService.getAllProducts().forEach(p -> view.getCbProduct().addItem(p));
        supplierService.getAllSuppliers().forEach(s -> view.getCbSupplier().addItem(s));
    }

    private void initEvents() {
        view.getBtnAddCart().addActionListener(e -> {
            Product prod = (Product) view.getCbProduct().getSelectedItem();
            if (prod == null) return;
            try {
                int qty = Integer.parseInt(view.getTxtQuantity().getText());
                BigDecimal price = new BigDecimal(view.getTxtImportPrice().getText());
                if (qty <= 0 || price.doubleValue() < 0) throw new Exception();

                DefaultTableModel model = view.getCartModel();
                BigDecimal total = price.multiply(new BigDecimal(qty));
                model.addRow(new Object[]{prod.getId(), prod.getName(), qty, price, total});
                calculateTotal();
            } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Dữ liệu nhập không hợp lệ!"); }
        });

        view.getBtnImport().addActionListener(e -> {
            if (view.getCartModel().getRowCount() == 0) { JOptionPane.showMessageDialog(view, "Phiếu trống!"); return; }
            Supplier sup = (Supplier) view.getCbSupplier().getSelectedItem();
            if (sup == null) return;

            try {
                ImportReceipt receipt = new ImportReceipt();
                receipt.setStaff(currentStaff); receipt.setSupplier(sup);
                receipt.setTotalAmount(new BigDecimal(view.getTxtTotal().getText()));

                List<ImportReceiptDetail> details = new ArrayList<>();
                for (int i = 0; i < view.getCartModel().getRowCount(); i++) {
                    ImportReceiptDetail d = new ImportReceiptDetail();
                    Product p = new Product(); p.setId((Long) view.getCartModel().getValueAt(i, 0));
                    d.setProduct(p); d.setQuantity((int) view.getCartModel().getValueAt(i, 2));
                    d.setImportPrice((BigDecimal) view.getCartModel().getValueAt(i, 3));
                    d.setReceipt(receipt);
                    details.add(d);
                }
                receipt.setDetails(details);
                importService.processImport(receipt);
                
                JOptionPane.showMessageDialog(view, "Nhập kho thành công!");
                view.getCartModel().setRowCount(0); view.getTxtTotal().setText("0");
            } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage()); }
        });
    }

    private void calculateTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (int i = 0; i < view.getCartModel().getRowCount(); i++) 
            grandTotal = grandTotal.add((BigDecimal) view.getCartModel().getValueAt(i, 4));
        view.getTxtTotal().setText(grandTotal.toString());
    }
}