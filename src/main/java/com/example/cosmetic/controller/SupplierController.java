package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Supplier;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;
import com.example.cosmetic.service.SupplierService;
import com.example.cosmetic.view.supplier.SupplierManagementPanel;

import javax.swing.*;
import java.util.List;

public class SupplierController {
    private final SupplierService service;
    private final SupplierManagementPanel view;
    private final Staff currentStaff;

    public SupplierController(SupplierService service, SupplierManagementPanel view, Staff currentStaff) {
        this.service = service;
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
        }
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> addSupplier());
        view.getBtnUpdate().addActionListener(e -> updateSupplier());
        view.getBtnDelete().addActionListener(e -> deleteSupplier());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTable().getSelectedRow() != -1) fillDataToForm();
        });
    }

    private void loadDataToTable() {
        view.getTableModel().setRowCount(0);
        List<Supplier> list = service.getAllSuppliers();
        for (Supplier s : list) view.getTableModel().addRow(new Object[]{s.getId(), s.getName(), s.getAddress(), s.getPhone()});
    }

    private void fillDataToForm() {
        int row = view.getTable().getSelectedRow();
        view.getTxtId().setText(view.getTable().getValueAt(row, 0).toString());
        view.getTxtName().setText(view.getTable().getValueAt(row, 1).toString());
        view.getTxtAddress().setText(view.getTable().getValueAt(row, 2).toString());
        view.getTxtPhone().setText(view.getTable().getValueAt(row, 3).toString());
    }

    private void clearForm() { view.getTxtId().setText(""); view.getTxtName().setText(""); view.getTxtAddress().setText(""); view.getTxtPhone().setText(""); view.getTable().clearSelection(); }

    private void addSupplier() {
        try {
            Supplier s = new Supplier();
            s.setName(view.getTxtName().getText());
            s.setAddress(view.getTxtAddress().getText());
            s.setPhone(view.getTxtPhone().getText());
            service.addSupplier(s);
            JOptionPane.showMessageDialog(view, "Thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void updateSupplier() {
        try {
            Supplier s = new Supplier();
            s.setId(Long.parseLong(view.getTxtId().getText()));
            s.setName(view.getTxtName().getText());
            s.setAddress(view.getTxtAddress().getText());
            s.setPhone(view.getTxtPhone().getText());
            service.updateSupplier(s);
            JOptionPane.showMessageDialog(view, "Thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void deleteSupplier() {
        try {
            service.deleteSupplier(Long.parseLong(view.getTxtId().getText()));
            JOptionPane.showMessageDialog(view, "Thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Lỗi!", "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }
}