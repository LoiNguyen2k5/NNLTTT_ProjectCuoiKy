package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Brand;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;
import com.example.cosmetic.service.BrandService;
import com.example.cosmetic.view.brand.BrandManagementPanel;

import javax.swing.*;
import java.util.List;

public class BrandController {
    private final BrandService brandService;
    private final BrandManagementPanel view;
    private final Staff currentStaff;

    public BrandController(BrandService brandService, BrandManagementPanel view, Staff currentStaff) {
        this.brandService = brandService;
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
        view.getBtnAdd().addActionListener(e -> addBrand());
        view.getBtnUpdate().addActionListener(e -> updateBrand());
        view.getBtnDelete().addActionListener(e -> deleteBrand());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTable().getSelectedRow() != -1) fillDataToForm();
        });
    }

    private void loadDataToTable() {
        view.getTableModel().setRowCount(0);
        List<Brand> list = brandService.getAllBrands();
        for (Brand b : list) view.getTableModel().addRow(new Object[]{b.getId(), b.getName(), b.getDescription()});
    }

    private void fillDataToForm() {
        int row = view.getTable().getSelectedRow();
        view.getTxtId().setText(view.getTable().getValueAt(row, 0).toString());
        view.getTxtName().setText(view.getTable().getValueAt(row, 1).toString());
        view.getTxtDescription().setText(view.getTable().getValueAt(row, 2) != null ? view.getTable().getValueAt(row, 2).toString() : "");
    }

    private void clearForm() { view.getTxtId().setText(""); view.getTxtName().setText(""); view.getTxtDescription().setText(""); view.getTable().clearSelection(); }

    private void addBrand() {
        try {
            Brand b = new Brand();
            b.setName(view.getTxtName().getText());
            b.setDescription(view.getTxtDescription().getText());
            brandService.addBrand(b);
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void updateBrand() {
        try {
            Brand b = new Brand();
            b.setId(Long.parseLong(view.getTxtId().getText()));
            b.setName(view.getTxtName().getText());
            b.setDescription(view.getTxtDescription().getText());
            brandService.updateBrand(b);
            JOptionPane.showMessageDialog(view, "Sửa thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void deleteBrand() {
        try {
            brandService.deleteBrand(Long.parseLong(view.getTxtId().getText()));
            JOptionPane.showMessageDialog(view, "Xóa thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Không thể xóa!", "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }
}