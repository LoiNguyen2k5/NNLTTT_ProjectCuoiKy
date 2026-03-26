package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Supplier;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;
import com.example.cosmetic.service.SupplierService;
import com.example.cosmetic.view.supplier.SupplierManagementPanel;

import javax.swing.*;
import java.util.List;

public class SupplierController {
    private final SupplierService supplierService;
    private final SupplierManagementPanel view;
    private final Staff currentStaff;

    public SupplierController(SupplierService supplierService, SupplierManagementPanel view, Staff currentStaff) {
        this.supplierService = supplierService;
        this.view = view;
        this.currentStaff = currentStaff;

        initController();
        handlePermissions(); 
        loadDataToTable();
    }

    private void handlePermissions() {
        if (currentStaff.getRole() != StaffRole.ADMIN) {
            view.getBtnAdd().setEnabled(false);
            view.getBtnUpdate().setEnabled(false);
            view.getBtnDelete().setEnabled(false);
            view.getBtnAdd().setToolTipText("Chỉ Admin mới có quyền thao tác");
        }
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> addSupplier());
        view.getBtnUpdate().addActionListener(e -> updateSupplier());
        view.getBtnDelete().addActionListener(e -> deleteSupplier());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getBtnSearch().addActionListener(e -> searchSupplier());

        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTable().getSelectedRow() != -1) fillDataToForm();
        });
    }

    private void loadDataToTable() {
        view.getTableModel().setRowCount(0);
        List<Supplier> list = supplierService.getAllSuppliers();
        for (Supplier s : list) view.getTableModel().addRow(new Object[]{s.getId(), s.getName(), s.getPhone(), s.getAddress()});
    }

    private void searchSupplier() {
        String keyword = view.getTxtSearch().getText();
        view.getTableModel().setRowCount(0);
        try {
            List<Supplier> list = supplierService.searchSuppliers(keyword);
            for (Supplier s : list) {
                view.getTableModel().addRow(new Object[]{s.getId(), s.getName(), s.getPhone(), s.getAddress()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi tìm kiếm: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void fillDataToForm() {
        int row = view.getTable().getSelectedRow();
        view.getTxtId().setText(view.getTable().getValueAt(row, 0).toString());
        view.getTxtName().setText(view.getTable().getValueAt(row, 1).toString());
        view.getTxtPhone().setText(view.getTable().getValueAt(row, 2) != null ? view.getTable().getValueAt(row, 2).toString() : "");
        view.getTxtAddress().setText(view.getTable().getValueAt(row, 3) != null ? view.getTable().getValueAt(row, 3).toString() : "");
    }

    private void clearForm() { 
        view.getTxtId().setText(""); 
        view.getTxtName().setText(""); 
        view.getTxtPhone().setText(""); 
        view.getTxtAddress().setText(""); 
        view.getTxtSearch().setText("");
        view.getTable().clearSelection(); 
        loadDataToTable(); 
    }

    private void addSupplier() {
        try {
            Supplier s = new Supplier();
            s.setName(view.getTxtName().getText());
            s.setPhone(view.getTxtPhone().getText());
            s.setAddress(view.getTxtAddress().getText());
            supplierService.addSupplier(s);
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            clearForm(); 
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void updateSupplier() {
        try {
            Supplier s = new Supplier();
            s.setId(Long.parseLong(view.getTxtId().getText()));
            s.setName(view.getTxtName().getText());
            s.setPhone(view.getTxtPhone().getText());
            s.setAddress(view.getTxtAddress().getText());
            supplierService.updateSupplier(s);
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            clearForm(); 
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void deleteSupplier() {
        try {
            int confirm = JOptionPane.showConfirmDialog(view, "Xóa NCC này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                supplierService.deleteSupplier(Long.parseLong(view.getTxtId().getText()));
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                clearForm(); 
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }
}