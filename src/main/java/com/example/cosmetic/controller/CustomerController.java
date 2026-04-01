package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.Customer;
import com.example.cosmetic.model.enums.CustomerGender;
import com.example.cosmetic.model.enums.StaffRole;
import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.service.CustomerService;
import com.example.cosmetic.view.customer.CustomerManagementPanel;
import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class CustomerController {
    private final CustomerService customerService;
    private final CustomerManagementPanel view;
    private final Staff currentStaff;

    public CustomerController(CustomerService service, CustomerManagementPanel view, Staff staff) {
        this.customerService = service;
        this.view = view;
        this.currentStaff = staff;

        loadTable(customerService.getAllCustomers());
        initEvents();

        // Admin có full quyền, Nhân viên (STAFF) chỉ được Thêm (để phục vụ lúc bán hàng) nhưng không được Sửa/Xóa
        if (currentStaff.getRole() != StaffRole.ADMIN) {
            view.getBtnUpdate().setEnabled(false);
            view.getBtnDelete().setEnabled(false);
        }
    }

    private void loadTable(List<Customer> list) {
        view.getTableModel().setRowCount(0);
        if (list != null) {
            list.forEach(c -> view.getTableModel().addRow(new Object[]{
                c.getId(), c.getFullName(), c.getPhoneNumber(), c.getPoints(), c.getGender().toString()
            }));
        }
    }

    private void initEvents() {
        // Đổ dữ liệu từ bảng lên form
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.getTable().getSelectedRow();
                if (row != -1) {
                    view.getTxtFullName().setText(view.getTable().getValueAt(row, 1).toString());
                    view.getTxtPhone().setText(view.getTable().getValueAt(row, 2).toString());
                    view.getTxtPoints().setText(view.getTable().getValueAt(row, 3).toString());
                    
                    String genderStr = view.getTable().getValueAt(row, 4).toString();
                    for (int i = 0; i < view.getCbGender().getItemCount(); i++) {
                        if (view.getCbGender().getItemAt(i).toString().equals(genderStr)) {
                            view.getCbGender().setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        // Thêm
        view.getBtnAdd().addActionListener(e -> {
            try {
                customerService.addCustomer(getCustomerFromForm());
                loadTable(customerService.getAllCustomers());
                clearForm();
                JOptionPane.showMessageDialog(view, "Thêm khách hàng thành công!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage()); }
        });

        // Sửa
        view.getBtnUpdate().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view, "Hãy chọn 1 dòng để sửa!"); return; }
            try {
                Customer c = getCustomerFromForm();
                c.setId(Long.parseLong(view.getTable().getValueAt(row, 0).toString()));
                customerService.updateCustomer(c);
                loadTable(customerService.getAllCustomers());
                clearForm();
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage()); }
        });

        // Xóa
        view.getBtnDelete().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(view, "Hãy chọn 1 dòng để xóa!"); return; }
            
            int confirm = JOptionPane.showConfirmDialog(view, "Xóa khách hàng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Long id = Long.parseLong(view.getTable().getValueAt(row, 0).toString());
                    customerService.deleteCustomer(id);
                    loadTable(customerService.getAllCustomers());
                    clearForm();
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                } catch (Exception ex) { JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage()); }
            }
        });

        // Làm mới
        view.getBtnClear().addActionListener(e -> clearForm());

        // Tìm kiếm — Button click
        view.getBtnSearch().addActionListener(e -> doSearch());

        // Tìm kiếm — Enter từ bàn phím
        view.getTxtSearch().addKeyListener(new KeyAdapter() {
            @Override public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) doSearch();
            }
        });
    }

    /** Thực hiện tìm kiếm theo nội dung ô tìm kiếm */
    private void doSearch() {
        String keyword = view.getTxtSearch().getText().trim();
        if (keyword.isEmpty()) {
            loadTable(customerService.getAllCustomers());
        } else {
            List<Customer> result = customerService.searchCustomerByPhone(keyword);
            loadTable(result);
            if (result == null || result.isEmpty()) {
                JOptionPane.showMessageDialog(view,
                    "Không tìm thấy khách hàng với số điện thoại: " + keyword,
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private Customer getCustomerFromForm() {
        Customer c = new Customer();
        c.setFullName(view.getTxtFullName().getText());
        c.setPhoneNumber(view.getTxtPhone().getText());
        c.setGender((CustomerGender) view.getCbGender().getSelectedItem());
        return c;
    }

    private void clearForm() {
        view.getTxtFullName().setText("");
        view.getTxtPhone().setText("");
        view.getTxtPoints().setText("");
        view.getCbGender().setSelectedIndex(0);
        view.getTxtSearch().setText("");
        view.getTable().clearSelection();
        loadTable(customerService.getAllCustomers()); // reset bảng về toàn bộ
    }
}