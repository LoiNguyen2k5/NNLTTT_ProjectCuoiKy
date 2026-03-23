package com.example.cosmetic.controller;

import com.example.cosmetic.model.entity.*;
import com.example.cosmetic.service.*;
import com.example.cosmetic.view.product.ProductManagementPanel;
import javax.swing.*;
import com.example.cosmetic.model.enums.StaffRole;
import java.math.BigDecimal;

public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final ProductManagementPanel view;
    private final Staff currentStaff;

    public ProductController(ProductService ps, CategoryService cs, BrandService bs, ProductManagementPanel v, Staff staff) {
        this.productService = ps; this.categoryService = cs; this.brandService = bs; this.view = v; this.currentStaff = staff;
        
        loadComboBoxes();
        loadTable();
        initEvents();
        if (currentStaff.getRole() != StaffRole.ADMIN) { // Phân quyền [cite: 1, 1225]
            view.getBtnAdd().setEnabled(false); view.getBtnUpdate().setEnabled(false); view.getBtnDelete().setEnabled(false);
        }
    }

    private void loadComboBoxes() {
        categoryService.getAllCategories().forEach(c -> view.getCbCategory().addItem(c));
        brandService.getAllBrands().forEach(b -> view.getCbBrand().addItem(b));
    }

    private void loadTable() {
        view.getTableModel().setRowCount(0);
        productService.getAllProducts().forEach(p -> {
            view.getTableModel().addRow(new Object[]{
                p.getId(), p.getBarcode(), p.getName(), p.getPrice(), p.getQuantity(), p.getCategory().getName(), p.getBrand().getName()
            });
        });
    }

    private void initEvents() {
        view.getBtnAdd().addActionListener(e -> {
            try {
                Product p = new Product();
                p.setBarcode(view.getTxtBarcode().getText());
                p.setName(view.getTxtName().getText());
                p.setPrice(new BigDecimal(view.getTxtPrice().getText()));
                p.setQuantity(Integer.parseInt(view.getTxtQuantity().getText()));
                p.setCategory((Category) view.getCbCategory().getSelectedItem());
                p.setBrand((Brand) view.getCbBrand().getSelectedItem());
                productService.addProduct(p);
                loadTable();
            } catch (Exception ex) { JOptionPane.showMessageDialog(view, ex.getMessage()); }
        });
    }
}