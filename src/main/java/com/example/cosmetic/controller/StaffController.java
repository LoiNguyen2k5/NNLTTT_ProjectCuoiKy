package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;
import com.example.cosmetic.service.StaffService;
import com.example.cosmetic.view.staff.StaffManagementPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class StaffController {
    private StaffManagementPanel view;
    private StaffService service;
    private Long currentSelectedId = null;

    public StaffController(StaffManagementPanel view, StaffService service) {
        this.view = view;
        this.service = service;
        
        loadDataToTable();
        initEvents();
    }

    private void loadDataToTable() {
        DefaultTableModel model = view.getTableModel();
        model.setRowCount(0); 
        try {
            List<Staff> staffs = service.getAllStaffs();
            for (Staff s : staffs) {
                model.addRow(new Object[]{
                        s.getId(),
                        s.getStaffCode(),
                        s.getFullName(),
                        s.getUsername(),
                        s.getRole()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi tải dữ liệu: " + e.getMessage());
        }
    }

    private void initEvents() {
        view.getTblStaff().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && view.getTblStaff().getSelectedRow() != -1) {
                int row = view.getTblStaff().getSelectedRow();
                currentSelectedId = (Long) view.getTblStaff().getValueAt(row, 0);
            }
        });

        view.getBtnAdd().addActionListener(e -> {
            try {
                Staff s = new Staff();
                s.setStaffCode(view.getTxtStaffCode().getText().trim());
                s.setFullName(view.getTxtFullName().getText().trim());
                s.setUsername(view.getTxtUsername().getText().trim());
                s.setPassword(new String(view.getTxtPassword().getPassword())); 
                s.setRole(StaffRole.valueOf(view.getCbRole().getSelectedItem().toString()));

                service.addStaff(s);
                JOptionPane.showMessageDialog(view, "Thêm nhân viên thành công!");
                loadDataToTable();
                view.getBtnClear().doClick();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getBtnUpdate().addActionListener(e -> {
            if (currentSelectedId == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên cần sửa trên bảng!");
                return;
            }
            try {
                Staff s = new Staff();
                s.setId(currentSelectedId);
                s.setStaffCode(view.getTxtStaffCode().getText().trim());
                s.setFullName(view.getTxtFullName().getText().trim());
                s.setUsername(view.getTxtUsername().getText().trim());
                
                String pass = new String(view.getTxtPassword().getPassword());
                if (!pass.equals("123456") && !pass.equals("********") && !pass.isEmpty()) {
                    s.setPassword(pass); 
                } else {
                    s.setPassword(view.getTxtPassword().getText()); 
                }
                
                s.setRole(StaffRole.valueOf(view.getCbRole().getSelectedItem().toString()));

                service.updateStaff(s);
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });

        view.getBtnDelete().addActionListener(e -> {
            if (currentSelectedId == null) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn nhân viên cần xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    service.deleteStaff(currentSelectedId);
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                    loadDataToTable();
                    view.getBtnClear().doClick();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Lỗi xóa: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        view.getBtnClear().addActionListener(e -> {
            currentSelectedId = null;
            view.getTxtStaffCode().setText("");
            view.getTxtFullName().setText("");
            view.getTxtUsername().setText("");
            view.getTxtPassword().setText("");
            view.getCbRole().setSelectedIndex(0);
            view.getTblStaff().clearSelection();
        });
    }
}