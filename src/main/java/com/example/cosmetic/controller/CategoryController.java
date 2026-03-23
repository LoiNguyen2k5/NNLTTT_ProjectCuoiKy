package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Category;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;
import com.example.cosmetic.service.CategoryService;
import com.example.cosmetic.view.category.CategoryManagementPanel;

import javax.swing.*;
import java.util.List;

public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryManagementPanel view;
    private final Staff currentStaff;

    public CategoryController(CategoryService categoryService, CategoryManagementPanel view, Staff currentStaff) {
        this.categoryService = categoryService;
        this.view = view;
        this.currentStaff = currentStaff;

        initController();
        handlePermissions(); // Hàm này để khóa nút
        loadDataToTable();
    }

    private void handlePermissions() {
        if (currentStaff.getRole() != StaffRole.ADMIN) {
            view.getBtnAdd().setEnabled(false);
            view.getBtnUpdate().setEnabled(false);
            view.getBtnDelete().setEnabled(false);
            view.getBtnAdd().setToolTipText("Chỉ Admin mới có quyền thay đổi danh mục");
        }
    }

    private void initController() {
        view.getBtnAdd().addActionListener(e -> addCategory());
        view.getBtnUpdate().addActionListener(e -> updateCategory());
        view.getBtnDelete().addActionListener(e -> deleteCategory());
        view.getBtnClear().addActionListener(e -> clearForm());
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTable().getSelectedRow() != -1) fillDataToForm();
        });
    }

    private void loadDataToTable() {
        view.getTableModel().setRowCount(0);
        List<Category> list = categoryService.getAllCategories();
        for (Category c : list) view.getTableModel().addRow(new Object[]{c.getId(), c.getName(), c.getDescription()});
    }

    private void fillDataToForm() {
        int row = view.getTable().getSelectedRow();
        view.getTxtId().setText(view.getTable().getValueAt(row, 0).toString());
        view.getTxtName().setText(view.getTable().getValueAt(row, 1).toString());
        view.getTxtDescription().setText(view.getTable().getValueAt(row, 2) != null ? view.getTable().getValueAt(row, 2).toString() : "");
    }

    private void clearForm() { view.getTxtId().setText(""); view.getTxtName().setText(""); view.getTxtDescription().setText(""); view.getTable().clearSelection(); }

    private void addCategory() {
        try {
            Category c = new Category(view.getTxtName().getText(), view.getTxtDescription().getText());
            categoryService.addCategory(c);
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void updateCategory() {
        try {
            Category c = new Category();
            c.setId(Long.parseLong(view.getTxtId().getText()));
            c.setName(view.getTxtName().getText());
            c.setDescription(view.getTxtDescription().getText());
            categoryService.updateCategory(c);
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            clearForm(); loadDataToTable();
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }

    private void deleteCategory() {
        try {
            int confirm = JOptionPane.showConfirmDialog(view, "Xóa loại này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                categoryService.deleteCategory(Long.parseLong(view.getTxtId().getText()));
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                clearForm(); loadDataToTable();
            }
        } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); }
    }
}