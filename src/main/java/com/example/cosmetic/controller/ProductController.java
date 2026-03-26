package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.*;
import com.example.cosmetic.service.*;
import com.example.cosmetic.view.product.ProductManagementPanel;
import javax.swing.*;
import com.example.cosmetic.model.enums.StaffRole;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductManagementPanel view;
    private final Staff currentStaff;

    public ProductController(ProductService ps, CategoryService cs, BrandService bs, ProductManagementPanel v, Staff staff) {
        this.productService = ps; 
        this.categoryService = cs; 
        this.brandService = bs; 
        this.view = v; 
        this.currentStaff = staff;
        
        loadComboBoxes();
        loadTable(productService.getAllProducts());
        initEvents();
        
        // Phân quyền: Chỉ ADMIN mới được Thêm/Sửa/Xóa
        if (currentStaff.getRole() != StaffRole.ADMIN) {
            view.getBtnAdd().setEnabled(false);
            view.getBtnUpdate().setEnabled(false);
            view.getBtnDelete().setEnabled(false);
        }
    }

    private void loadComboBoxes() {
        view.getCbCategory().removeAllItems();
        view.getCbBrand().removeAllItems();
        categoryService.getAllCategories().forEach(c -> view.getCbCategory().addItem(c));
        brandService.getAllBrands().forEach(b -> view.getCbBrand().addItem(b));
    }

    private void loadTable(List<Product> list) {
        view.getTableModel().setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (list != null) {
            list.forEach(p -> {
                String expDateStr = p.getExpirationDate() != null ? sdf.format(p.getExpirationDate()) : "";
                view.getTableModel().addRow(new Object[]{
                    p.getId(), 
                    p.getBarcode(), 
                    p.getName(), 
                    p.getPrice(), 
                    p.getQuantity(), 
                    p.getCategory() != null ? p.getCategory().getName() : "", 
                    p.getBrand() != null ? p.getBrand().getName() : "",
                    expDateStr
                });
            });
        }
    }

    private void initEvents() {
        // Sự kiện đổ dữ liệu từ Table lên Form khi click
        view.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = view.getTable().getSelectedRow();
                if (row != -1) {
                    // CHÚ Ý: Dùng ngoặc đơn () cho hàm getValueAt, KHÔNG dùng ngoặc vuông []
                    view.getTxtBarcode().setText(view.getTable().getValueAt(row, 1).toString());
                    view.getTxtName().setText(view.getTable().getValueAt(row, 2).toString());
                    view.getTxtPrice().setText(view.getTable().getValueAt(row, 3).toString());
                    view.getTxtQuantity().setText(view.getTable().getValueAt(row, 4).toString());
                    
                    Object expObj = view.getTable().getValueAt(row, 7);
                    view.getTxtExpirationDate().setText(expObj != null ? expObj.toString() : "");
                }
            }
        });

        // Xử lý nút Thêm
        view.getBtnAdd().addActionListener(e -> {
            try {
                productService.addProduct(getProductFromForm());
                loadTable(productService.getAllProducts());
                clearForm();
                JOptionPane.showMessageDialog(view, "Thêm thành công!");
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage()); 
            }
        });

        // Xử lý nút Sửa (Update)
        view.getBtnUpdate().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Hãy chọn 1 dòng để sửa!");
                return;
            }
            try {
                Product p = getProductFromForm();
                // Lấy ID an toàn bằng ngoặc đơn ()
                p.setId(Long.parseLong(view.getTable().getValueAt(row, 0).toString())); 
                productService.updateProduct(p);
                loadTable(productService.getAllProducts());
                clearForm();
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            } catch (Exception ex) { 
                JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage()); 
            }
        });

        // Xử lý nút Xóa
        view.getBtnDelete().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Hãy chọn 1 dòng để xóa!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa sản phẩm này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    Long id = Long.parseLong(view.getTable().getValueAt(row, 0).toString());
                    productService.deleteProduct(id);
                    loadTable(productService.getAllProducts());
                    clearForm();
                    JOptionPane.showMessageDialog(view, "Xóa thành công!");
                } catch (Exception ex) { 
                    JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage()); 
                }
            }
        });

        // Xử lý nút Clear
        view.getBtnClear().addActionListener(e -> clearForm());

        // Xử lý nút Tìm kiếm
        view.getBtnSearch().addActionListener(e -> {
            String keyword = view.getTxtSearch().getText().trim();
            if (keyword.isEmpty()) {
                loadTable(productService.getAllProducts());
            } else {
                loadTable(productService.searchProducts(keyword));
            }
        });
    }

    private Product getProductFromForm() throws Exception {
        Product p = new Product();
        p.setBarcode(view.getTxtBarcode().getText());
        p.setName(view.getTxtName().getText());
        try {
            p.setPrice(new BigDecimal(view.getTxtPrice().getText()));
            p.setQuantity(Integer.parseInt(view.getTxtQuantity().getText()));
        } catch (NumberFormatException e) {
            throw new Exception("Vui lòng nhập số hợp lệ cho Giá và Số lượng!");
        }
        p.setCategory((Category) view.getCbCategory().getSelectedItem());
        p.setBrand((Brand) view.getCbBrand().getSelectedItem());
        
        String dateStr = view.getTxtExpirationDate().getText().trim();
        if (!dateStr.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                sdf.setLenient(false);
                p.setExpirationDate(sdf.parse(dateStr));
            } catch (Exception e) {
                throw new Exception("Định dạng Hạn SD không hợp lệ (Phải là dd/MM/yyyy)!");
            }
        }
        
        return p;
    }

    private void clearForm() {
        view.getTxtBarcode().setText("");
        view.getTxtName().setText("");
        view.getTxtPrice().setText("");
        view.getTxtQuantity().setText("");
        view.getTxtExpirationDate().setText("");
        view.getTxtSearch().setText("");
        view.getTable().clearSelection();
        loadTable(productService.getAllProducts());
    }
}