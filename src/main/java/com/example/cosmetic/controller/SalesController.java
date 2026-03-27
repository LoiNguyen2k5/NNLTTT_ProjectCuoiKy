package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.*;
import com.example.cosmetic.service.*;
import com.example.cosmetic.view.invoice.SalesPanel;
import com.example.cosmetic.view.utils.PDFExporter; 
import com.example.cosmetic.view.utils.EmailUtil; // Gọi công cụ gửi Email

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SalesController {
    private final SalesPanel view;
    private final ProductService productService;
    private final CustomerService customerService;
    private final InvoiceService invoiceService;
    private final PromotionService promotionService;
    private final Staff currentStaff;
    
    private Promotion appliedPromotion = null;

    public SalesController(SalesPanel view, ProductService ps, CustomerService cs, InvoiceService is, PromotionService promoService, Staff staff) {
        this.view = view;
        this.productService = ps;
        this.customerService = cs;
        this.invoiceService = is;
        this.promotionService = promoService;
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
        view.getCbCustomer().addActionListener(e -> updateCustomerPointsInfo());
        view.getChkUsePoints().addActionListener(e -> calculateTotal());
        view.getBtnApplyVoucher().addActionListener(e -> applyVoucher());
    }

    private void updateCustomerPointsInfo() {
        Customer selectedCustomer = (Customer) view.getCbCustomer().getSelectedItem();
        if (selectedCustomer != null) {
            view.getLblCustomerPoints().setText("Điểm: " + selectedCustomer.getPoints());
        } else {
            view.getLblCustomerPoints().setText("Điểm: 0");
        }
        view.getChkUsePoints().setSelected(false);
        calculateTotal();
    }

    private void addToCart() {
        Product selectedProd = (Product) view.getCbProduct().getSelectedItem();
        if (selectedProd == null) return; 

        // --- Kiểm tra Hạn sử dụng (Date) ---
        java.util.Date expDate = selectedProd.getExpirationDate();
        if (expDate != null) {
            long diff = expDate.getTime() - System.currentTimeMillis();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days <= 30) {
                String msg = (days < 0) ? "Sản phẩm này đã QUÁ HẠN SỬ DỤNG!" : "Sản phẩm này sắp hết hạn (còn " + days + " ngày).";
                int result = JOptionPane.showConfirmDialog(view, 
                    msg + "\nBạn có chắc chắn muốn thêm sản phẩm này vào giỏ hàng?", 
                    "Cảnh Báo Hạn Sử Dụng", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);
                if (result != JOptionPane.YES_OPTION) {
                    return; // Hủy thêm vào giỏ
                }
            }
        }

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
        
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).equals(selectedProd.getId())) {
                int oldQty = (int) model.getValueAt(i, 3);
                int newQty = oldQty + qty;
                model.setValueAt(newQty, i, 3); 
                model.setValueAt(selectedProd.getPrice().multiply(new BigDecimal(newQty)), i, 5); 
                exists = true;
                break;
            }
        }

        if (!exists) {
            BigDecimal total = selectedProd.getPrice().multiply(new BigDecimal(qty));
            model.addRow(new Object[]{
                selectedProd.getId(), 
                selectedProd.getBarcode(),
                selectedProd.getName(), 
                qty, 
                selectedProd.getPrice(), 
                total
            });
        }
        calculateTotal();
    }

    private void applyVoucher() {
        String code = view.getTxtVoucherCode().getText().trim().toUpperCase();
        if (code.isEmpty()) {
            appliedPromotion = null;
            view.getLblVoucherStatus().setText("");
            calculateTotal();
            return;
        }
        
        try {
            // Validate code via service
            BigDecimal grandTotalBase = BigDecimal.ZERO;
            for (int i = 0; i < view.getCartModel().getRowCount(); i++) {
                grandTotalBase = grandTotalBase.add((BigDecimal) view.getCartModel().getValueAt(i, 5));
            }
            
            BigDecimal discount = promotionService.calculateDiscount(code, grandTotalBase);
            appliedPromotion = promotionService.getPromotionByCode(code);
            view.getLblVoucherStatus().setForeground(new java.awt.Color(0, 150, 0));
            view.getLblVoucherStatus().setText("Áp dụng thành công! Giảm: " + new DecimalFormat("#,###").format(discount) + "VND");
            calculateTotal();
        } catch (Exception ex) {
            appliedPromotion = null;
            view.getLblVoucherStatus().setForeground(java.awt.Color.RED);
            view.getLblVoucherStatus().setText(ex.getMessage());
            calculateTotal();
        }
    }

    private void calculateTotal() {
        BigDecimal grandTotal = BigDecimal.ZERO;
        for (int i = 0; i < view.getCartModel().getRowCount(); i++) {
            grandTotal = grandTotal.add((BigDecimal) view.getCartModel().getValueAt(i, 5));
        }
        
        // Trừ Voucher trước
        if (appliedPromotion != null) {
            try {
                BigDecimal discount = promotionService.calculateDiscount(appliedPromotion.getCode(), grandTotal);
                grandTotal = grandTotal.subtract(discount);
                if (grandTotal.compareTo(BigDecimal.ZERO) < 0) grandTotal = BigDecimal.ZERO;
            } catch (Exception e) {
                // Ignore, handled by applyVoucher
            }
        }
        
        // Trừ điểm thưởng
        if (view.getChkUsePoints().isSelected()) {
            Customer selectedCustomer = (Customer) view.getCbCustomer().getSelectedItem();
            if (selectedCustomer != null && selectedCustomer.getPoints() > 0) {
                BigDecimal discount = new BigDecimal(selectedCustomer.getPoints() * 1000);
                if (grandTotal.compareTo(discount) < 0) {
                    discount = grandTotal;
                }
                grandTotal = grandTotal.subtract(discount);
            } else {
                view.getChkUsePoints().setSelected(false);
                JOptionPane.showMessageDialog(view, "Khách hàng không có điểm tích lũy hoặc chưa chọn khách hàng!");
            }
        }
        
        DecimalFormat df = new DecimalFormat("#,###");
        view.getTxtTotal().setText(df.format(grandTotal));
    }

    private void processCheckout() {
        if (view.getCartModel().getRowCount() == 0) {
            JOptionPane.showMessageDialog(view, "Giỏ hàng trống!");
            return;
        }

        Customer selectedCustomer = (Customer) view.getCbCustomer().getSelectedItem();
        if (selectedCustomer == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khách hàng trước khi thanh toán!");
            return;
        }

        try {
            Invoice invoice = new Invoice();
            invoice.setStaff(currentStaff);
            invoice.setCustomer(selectedCustomer);

            BigDecimal finalTotal = BigDecimal.ZERO;
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
                
                finalTotal = finalTotal.add((BigDecimal) view.getCartModel().getValueAt(i, 5));
            }
            
            // Trừ Voucher trước tiên
            if (appliedPromotion != null) {
                try {
                    BigDecimal promoDiscount = promotionService.calculateDiscount(appliedPromotion.getCode(), finalTotal);
                    finalTotal = finalTotal.subtract(promoDiscount);
                    if (finalTotal.compareTo(BigDecimal.ZERO) < 0) finalTotal = BigDecimal.ZERO;
                } catch (Exception ignored) {}
            }

            // Xử lý điểm tích lũy
            int pointsUsed = 0;
            if (view.getChkUsePoints().isSelected() && selectedCustomer.getPoints() > 0) {
                BigDecimal maxDiscount = new BigDecimal(selectedCustomer.getPoints() * 1000);
                if (finalTotal.compareTo(maxDiscount) < 0) {
                    pointsUsed = finalTotal.divide(new BigDecimal(1000), 0, java.math.RoundingMode.UP).intValue();
                    finalTotal = BigDecimal.ZERO;
                } else {
                    pointsUsed = selectedCustomer.getPoints();
                    finalTotal = finalTotal.subtract(maxDiscount);
                }
            }
            
            selectedCustomer.setPoints(selectedCustomer.getPoints() - pointsUsed);
            
            int earnedPoints = finalTotal.divide(new BigDecimal(100000), 0, java.math.RoundingMode.DOWN).intValue();
            selectedCustomer.setPoints(selectedCustomer.getPoints() + earnedPoints);
            
            invoice.setTotalAmount(finalTotal);
            invoice.setDetails(details);

            // 1. GỌI SERVICE LƯU HÓA ĐƠN & TRỪ KHO
            invoiceService.processCheckout(invoice);
            
            // 2. XUẤT FILE PDF
            String pdfFileName = "HoaDon_" + invoice.getInvoiceCode() + ".pdf";
            PDFExporter.exportInvoice(invoice, pdfFileName);

            // 3. TÍNH NĂNG GỬI EMAIL
            int confirmEmail = JOptionPane.showConfirmDialog(view, 
                "Thanh toán thành công!\nBạn có muốn gửi hóa đơn này qua Email cho khách hàng không?", 
                "Gửi Email Hóa Đơn", JOptionPane.YES_NO_OPTION);
                
            if (confirmEmail == JOptionPane.YES_OPTION) {
                String email = JOptionPane.showInputDialog(view, "Nhập địa chỉ Email của khách hàng:");
                if (email != null && !email.trim().isEmpty() && email.contains("@")) {
                    String cusName = selectedCustomer.getFullName();
                    // Gọi hàm gửi mail chạy ngầm
                    EmailUtil.sendInvoiceAsync(email, cusName, pdfFileName);
                    JOptionPane.showMessageDialog(view, "Đang gửi email trong nền. Giao dịch hoàn tất!");
                } else {
                    JOptionPane.showMessageDialog(view, "Email không hợp lệ, đã bỏ qua bước gửi mail.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(view, "Đã lưu hóa đơn: " + pdfFileName);
            }

            // 4. TỰ ĐỘNG MỞ FILE PDF VỪA TẠO
            try {
                Desktop.getDesktop().open(new File(pdfFileName));
            } catch (Exception ex) {
                System.out.println("Không thể tự động mở file PDF.");
            }

            // 5. LÀM SẠCH GIỎ HÀNG SAU KHI XONG
            view.getCartModel().setRowCount(0);
            view.getTxtTotal().setText("0");
            view.getTxtVoucherCode().setText("");
            view.getLblVoucherStatus().setText("");
            appliedPromotion = null;

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(view, "Lỗi thanh toán: " + ex.getMessage());
        }
    }
}