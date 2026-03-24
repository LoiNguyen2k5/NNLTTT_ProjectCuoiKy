package com.example.cosmetic.view.main;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;

import com.example.cosmetic.repository.impl.*;
import com.example.cosmetic.service.impl.*;
import com.example.cosmetic.view.category.CategoryManagementPanel;
import com.example.cosmetic.view.brand.BrandManagementPanel;
import com.example.cosmetic.view.supplier.SupplierManagementPanel;
import com.example.cosmetic.view.product.ProductManagementPanel;
import com.example.cosmetic.view.customer.CustomerManagementPanel;
import com.example.cosmetic.view.invoice.SalesPanel;
import com.example.cosmetic.view.invoice.InvoiceManagementPanel;
import com.example.cosmetic.controller.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Staff currentStaff;
    private JPanel centerPanel; 

    public MainFrame(Staff currentStaff) {
        this.currentStaff = currentStaff;
        
        setTitle("Hệ thống Quản lý Cửa hàng Mỹ phẩm - " + currentStaff.getFullName());
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        centerPanel = new JPanel(new BorderLayout());
        JLabel lblWelcome = new JLabel("CHÀO MỪNG BẠN ĐẾN VỚI HỆ THỐNG (" + currentStaff.getRole() + ")", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(lblWelcome, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        setupMenuBasedOnRole();
    }

    private void setupMenuBasedOnRole() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuSales = new JMenu("Bán Hàng");
        JMenuItem itemSales = new JMenuItem("Lập Hóa Đơn");
        JMenuItem itemInvoiceHistory = new JMenuItem("Lịch Sử Hóa Đơn");
        menuSales.add(itemSales);
        menuSales.add(itemInvoiceHistory);
        
        JMenu menuCatalog = new JMenu("Danh Mục");
        JMenu menuStats = new JMenu("Thống Kê");

        JMenuItem itemCategory = new JMenuItem("Loại Mỹ Phẩm");
        JMenuItem itemBrand = new JMenuItem("Thương Hiệu");
        JMenuItem itemSupplier = new JMenuItem("Nhà Cung Cấp");
        JMenuItem itemCustomer = new JMenuItem("Khách Hàng");
        JMenuItem itemProduct = new JMenuItem("Sản Phẩm");

        menuCatalog.add(itemCategory);
        menuCatalog.add(itemBrand);
        menuCatalog.add(itemSupplier);
        menuCatalog.add(itemCustomer); 
        menuCatalog.addSeparator(); 
        menuCatalog.add(itemProduct);

        menuBar.add(menuSales);
        menuBar.add(menuCatalog);
        menuBar.add(menuStats);

        if (currentStaff.getRole() == StaffRole.STAFF) {
            menuStats.setVisible(false);
        }

        setJMenuBar(menuBar);

        itemSales.addActionListener(e -> openSales());
        itemInvoiceHistory.addActionListener(e -> openInvoiceHistory());
        itemCategory.addActionListener(e -> openCategoryManagement());
        itemBrand.addActionListener(e -> openBrandManagement());
        itemSupplier.addActionListener(e -> openSupplierManagement());
        itemCustomer.addActionListener(e -> openCustomerManagement());
        itemProduct.addActionListener(e -> openProductManagement());
    }

    private void switchPanel(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private void openSales() {
        try {
            ProductRepositoryImpl pRepo = new ProductRepositoryImpl();
            CustomerRepositoryImpl cRepo = new CustomerRepositoryImpl();
            InvoiceRepositoryImpl iRepo = new InvoiceRepositoryImpl();
            ProductServiceImpl pService = new ProductServiceImpl(pRepo);
            CustomerServiceImpl cService = new CustomerServiceImpl(cRepo);
            InvoiceServiceImpl iService = new InvoiceServiceImpl(iRepo);

            SalesPanel view = new SalesPanel();
            new SalesController(view, pService, cService, iService, currentStaff);
            switchPanel(view);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi mở màn hình bán hàng: " + e.getMessage());
        }
    }

    private void openInvoiceHistory() {
        InvoiceRepositoryImpl repo = new InvoiceRepositoryImpl();
        InvoiceServiceImpl service = new InvoiceServiceImpl(repo);
        InvoiceManagementPanel view = new InvoiceManagementPanel();
        new InvoiceController(service, view);
        switchPanel(view);
    }

    private void openCategoryManagement() {
        CategoryRepositoryImpl repo = new CategoryRepositoryImpl();
        CategoryServiceImpl service = new CategoryServiceImpl(repo);
        CategoryManagementPanel view = new CategoryManagementPanel();
        new CategoryController(service, view, currentStaff);
        switchPanel(view);
    }

    private void openBrandManagement() {
        BrandRepositoryImpl repo = new BrandRepositoryImpl();
        BrandServiceImpl service = new BrandServiceImpl(repo);
        BrandManagementPanel view = new BrandManagementPanel();
        new BrandController(service, view, currentStaff);
        switchPanel(view);
    }

    private void openSupplierManagement() {
        SupplierRepositoryImpl repo = new SupplierRepositoryImpl();
        SupplierServiceImpl service = new SupplierServiceImpl(repo);
        SupplierManagementPanel view = new SupplierManagementPanel();
        new SupplierController(service, view, currentStaff);
        switchPanel(view);
    }
    
    private void openCustomerManagement() {
        CustomerRepositoryImpl repo = new CustomerRepositoryImpl();
        CustomerServiceImpl service = new CustomerServiceImpl(repo);
        CustomerManagementPanel view = new CustomerManagementPanel();
        new CustomerController(service, view, currentStaff);
        switchPanel(view);
    }
 
    private void openProductManagement() {
        ProductRepositoryImpl productRepo = new ProductRepositoryImpl();
        CategoryRepositoryImpl categoryRepo = new CategoryRepositoryImpl();
        BrandRepositoryImpl brandRepo = new BrandRepositoryImpl();
        ProductServiceImpl productService = new ProductServiceImpl(productRepo);
        CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepo);
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepo);
        ProductManagementPanel view = new ProductManagementPanel();
        new ProductController(productService, categoryService, brandService, view, currentStaff);
        switchPanel(view);
    }
}