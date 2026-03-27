package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Promotion;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.service.PromotionService;
import com.example.cosmetic.view.promotion.PromotionManagementPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PromotionController {
    private final PromotionService service;
    private final PromotionManagementPanel view;
    private final Staff currentStaff;
    private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public PromotionController(PromotionService service, PromotionManagementPanel view, Staff currentStaff) {
        this.service = service;
        this.view = view;
        this.currentStaff = currentStaff;

        loadDataToTable();
        initEvents();
        checkPermissions();
    }
    
    private void checkPermissions() {
        if (currentStaff != null && currentStaff.getRole() == com.example.cosmetic.model.enums.StaffRole.STAFF) {
            view.getBtnAdd().setEnabled(false);
            view.getBtnUpdate().setEnabled(false);
            view.getBtnDelete().setEnabled(false);
            view.getBtnClear().setEnabled(false);
            
            view.getTxtCode().setEditable(false);
            view.getTxtDescription().setEditable(false);
            view.getTxtDiscountPercent().setEditable(false);
            view.getTxtMaxDiscount().setEditable(false);
            view.getTxtMinPurchase().setEditable(false);
            view.getTxtStartDate().setEditable(false);
            view.getTxtEndDate().setEditable(false);
            view.getChkIsActive().setEnabled(false);
        }
    }

    private void loadDataToTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        List<Promotion> list = service.getAllPromotions();
        for (Promotion p : list) {
            model.addRow(new Object[]{
                p.getId(),
                p.getCode(),
                p.getDescription(),
                p.getDiscountPercent(),
                p.getMaxDiscountAmount() != null ? p.getMaxDiscountAmount() : "N/A",
                p.getMinPurchaseAmount() != null ? p.getMinPurchaseAmount() : "N/A",
                sdf.format(p.getStartDate()),
                sdf.format(p.getEndDate()),
                p.isActive() ? "Hoạt động" : "Đã khóa"
            });
        }
    }

    private void initEvents() {
        view.getBtnAdd().addActionListener(e -> addPromotion());
        view.getBtnUpdate().addActionListener(e -> updatePromotion());
        view.getBtnDelete().addActionListener(e -> deletePromotion());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> searchPromotion());
        
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTable().getSelectedRow() != -1) {
                fillFormFromTable();
            }
        });
    }

    private Promotion getPromotionFromForm() {
        try {
            Promotion p = new Promotion();
            p.setCode(view.getTxtCode().getText().trim().toUpperCase());
            p.setDescription(view.getTxtDescription().getText().trim());
            p.setDiscountPercent(Integer.parseInt(view.getTxtDiscountPercent().getText().trim()));
            
            String maxDiscountStr = parseAmountString(view.getTxtMaxDiscount().getText());
            p.setMaxDiscountAmount(!maxDiscountStr.isEmpty() ? new BigDecimal(maxDiscountStr) : null);
            
            String minPurchaseStr = parseAmountString(view.getTxtMinPurchase().getText());
            p.setMinPurchaseAmount(!minPurchaseStr.isEmpty() ? new BigDecimal(minPurchaseStr) : null);

            Date start = sdf.parse(view.getTxtStartDate().getText().trim());
            Date end = sdf.parse(view.getTxtEndDate().getText().trim());
            p.setStartDate(start);
            p.setEndDate(end);

            p.setActive(view.getChkIsActive().isSelected());

            return p;
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Các trường số tiền/phần trăm phải là số hợp lệ!");
        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(view, "Sai định dạng ngày! Yêu cầu: dd/MM/yyyy");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ và đúng định dạng các trường yêu cầu.");
        }
        return null;
    }
    
    private String parseAmountString(String input) {
        if (input == null || input.trim().isEmpty()) return "";
        input = input.trim().toLowerCase().replace(",", "").replace(".", "");
        if (input.endsWith("k")) {
            input = input.substring(0, input.length() - 1) + "000";
        }
        return input;
    }

    private void addPromotion() {
        Promotion p = getPromotionFromForm();
        if (p == null) return;

        try {
            service.addPromotion(p);
            JOptionPane.showMessageDialog(view, "Thêm mã khuyến mãi thành công!");
            loadDataToTable();
            clearForm();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi thêm: " + e.getMessage());
        }
    }

    private void updatePromotion() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khuyến mãi cần cập nhật!");
            return;
        }

        Promotion p = getPromotionFromForm();
        if (p == null) return;
        
        Long id = (Long) view.getTableModel().getValueAt(selectedRow, 0);
        p.setId(id);

        try {
            service.updatePromotion(p);
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi khi cập nhật: " + e.getMessage());
        }
    }

    private void deletePromotion() {
        int selectedRow = view.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khuyến mãi cần xóa!");
            return;
        }

        Long id = (Long) view.getTableModel().getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa mã khuyến mãi này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                service.deletePromotion(id);
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadDataToTable();
                clearForm();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Lỗi khi xóa: " + e.getMessage());
            }
        }
    }

    private void searchPromotion() {
        String keyword = view.getTxtSearch().getText().trim();
        List<Promotion> list = service.searchPromotions(keyword);
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0);
        for (Promotion p : list) {
            model.addRow(new Object[]{
                p.getId(),
                p.getCode(),
                p.getDescription(),
                p.getDiscountPercent(),
                p.getMaxDiscountAmount() != null ? p.getMaxDiscountAmount() : "N/A",
                p.getMinPurchaseAmount() != null ? p.getMinPurchaseAmount() : "N/A",
                sdf.format(p.getStartDate()),
                sdf.format(p.getEndDate()),
                p.isActive() ? "Hoạt động" : "Đã khóa"
            });
        }
    }

    private void fillFormFromTable() {
        int row = view.getTable().getSelectedRow();
        if (row != -1) {
            Long id = (Long) view.getTableModel().getValueAt(row, 0);
            Promotion p = service.getAllPromotions().stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
            
            if (p != null) {
                view.getTxtCode().setText(p.getCode());
                view.getTxtDescription().setText(p.getDescription());
                view.getTxtDiscountPercent().setText(String.valueOf(p.getDiscountPercent()));
                view.getTxtMaxDiscount().setText(p.getMaxDiscountAmount() != null ? p.getMaxDiscountAmount().toString() : "");
                view.getTxtMinPurchase().setText(p.getMinPurchaseAmount() != null ? p.getMinPurchaseAmount().toString() : "");
                view.getTxtStartDate().setText(sdf.format(p.getStartDate()));
                view.getTxtEndDate().setText(sdf.format(p.getEndDate()));
                view.getChkIsActive().setSelected(p.isActive());
            }
        }
    }

    private void clearForm() {
        view.getTxtCode().setText("");
        view.getTxtDescription().setText("");
        view.getTxtDiscountPercent().setText("");
        view.getTxtMaxDiscount().setText("");
        view.getTxtMinPurchase().setText("");
        view.getTxtStartDate().setText("");
        view.getTxtEndDate().setText("");
        view.getChkIsActive().setSelected(true);
        view.getTable().clearSelection();
    }
}
