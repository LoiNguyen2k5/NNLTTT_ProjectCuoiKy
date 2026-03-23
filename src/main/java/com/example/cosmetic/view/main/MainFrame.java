package com.example.cosmetic.view.main;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;

import com.example.cosmetic.repository.impl.ProductRepositoryImpl;
import com.example.cosmetic.service.impl.ProductServiceImpl;
import com.example.cosmetic.view.product.ProductManagementPanel;
import com.example.cosmetic.controller.ProductController;
import com.example.cosmetic.controller.SalesController;
// --- Import Module Loại Mỹ Phẩm ---
import com.example.cosmetic.repository.impl.CategoryRepositoryImpl;
import com.example.cosmetic.repository.impl.CustomerRepositoryImpl;
import com.example.cosmetic.repository.impl.InvoiceRepositoryImpl;
import com.example.cosmetic.service.impl.CategoryServiceImpl;
import com.example.cosmetic.service.impl.CustomerServiceImpl;
import com.example.cosmetic.service.impl.InvoiceServiceImpl;
import com.example.cosmetic.view.category.CategoryManagementPanel;
import com.example.cosmetic.view.invoice.SalesPanel;
import com.example.cosmetic.controller.CategoryController;

// --- Import Module Thương Hiệu ---
import com.example.cosmetic.repository.impl.BrandRepositoryImpl;
import com.example.cosmetic.service.impl.BrandServiceImpl;
import com.example.cosmetic.view.brand.BrandManagementPanel;
import com.example.cosmetic.controller.BrandController;

// --- Import Module Nhà Cung Cấp ---
import com.example.cosmetic.repository.impl.SupplierRepositoryImpl;
import com.example.cosmetic.service.impl.SupplierServiceImpl;
import com.example.cosmetic.view.supplier.SupplierManagementPanel;
import com.example.cosmetic.controller.SupplierController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Staff currentStaff;
    private JPanel centerPanel; // Khay chứa trung tâm để tráo đổi các Panel

    public MainFrame(Staff currentStaff) {
        this.currentStaff = currentStaff;
        
        setTitle("Hệ thống Quản lý Cửa hàng Mỹ phẩm - " + currentStaff.getFullName());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Khởi tạo khay chứa trung tâm với câu chào mừng
        centerPanel = new JPanel(new BorderLayout());
        JLabel lblWelcome = new JLabel("CHÀO MỪNG BẠN ĐẾN VỚI HỆ THỐNG (" + currentStaff.getRole() + ")", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 20));
        centerPanel.add(lblWelcome, BorderLayout.CENTER);

        // Gắn khay chứa vào chính giữa MainFrame
        add(centerPanel, BorderLayout.CENTER);

        setupMenuBasedOnRole();
    }

    private void setupMenuBasedOnRole() {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuSales = new JMenu("Bán Hàng");
        JMenu menuCatalog = new JMenu("Danh Mục");
        JMenu menuStats = new JMenu("Thống Kê");

        // --- Tạo các Menu Con (Item) ---
        JMenuItem itemCategory = new JMenuItem("Loại Mỹ Phẩm");
        JMenuItem itemBrand = new JMenuItem("Thương Hiệu");
        JMenuItem itemSupplier = new JMenuItem("Nhà Cung Cấp");
        JMenuItem itemProduct = new JMenuItem("Sản Phẩm");
        itemProduct.addActionListener(e -> openProductManagement());

        // Nhét Menu Con vào Menu Cha "Danh Mục"
        menuCatalog.add(itemCategory);
        menuCatalog.add(itemBrand);
        menuCatalog.add(itemSupplier);
        menuCatalog.addSeparator(); // Đường kẻ ngang phân cách
        menuCatalog.add(itemProduct);

        menuBar.add(menuSales);
        menuBar.add(menuCatalog);
        menuBar.add(menuStats);

        // Phân quyền: Nếu là nhân viên thường, ẩn tab Thống Kê đi
        if (currentStaff.getRole() == StaffRole.STAFF) {
            menuStats.setVisible(false);
        }

        setJMenuBar(menuBar);

        // --- GẮN SỰ KIỆN CLICK CHUYỂN TRANG CHO MENU ---
        itemCategory.addActionListener(e -> openCategoryManagement());
        itemBrand.addActionListener(e -> openBrandManagement());
        itemSupplier.addActionListener(e -> openSupplierManagement());
    }
  private void openSales() {
    try {
        // 1. Khởi tạo đủ bộ Repo
        ProductRepositoryImpl pRepo = new ProductRepositoryImpl();
        CustomerRepositoryImpl cRepo = new CustomerRepositoryImpl();
        InvoiceRepositoryImpl iRepo = new InvoiceRepositoryImpl();

        // 2. Khởi tạo đủ bộ Service
        ProductServiceImpl pService = new ProductServiceImpl(pRepo);
        CustomerServiceImpl cService = new CustomerServiceImpl(cRepo);
        InvoiceServiceImpl iService = new InvoiceServiceImpl(iRepo);

        // 3. Khởi tạo View
        SalesPanel view = new SalesPanel();
        
        // 4. Khởi tạo Controller - KHÚC NÀY DỄ SAI NHẤT:
        // Đảm bảo thứ tự: view, pService, cService, iService, currentStaff
        new SalesController(view, pService, cService, iService, currentStaff);
        
        switchPanel(view);
    } catch (Exception e) {
        e.printStackTrace(); // In lỗi ra console để mình biết đường sửa
        JOptionPane.showMessageDialog(this, "Lỗi khi mở màn hình bán hàng: " + e.getMessage());
    }
}
    // Hàm tiện ích: Xóa ruột cũ, thay ruột mới và vẽ lại màn hình
    private void switchPanel(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    // =======================================================
    // CÁC HÀM KHỞI TẠO MVC CHO TỪNG MODULE
    // =======================================================

    private void openCategoryManagement() {
        CategoryRepositoryImpl repo = new CategoryRepositoryImpl();
        CategoryServiceImpl service = new CategoryServiceImpl(repo);
        CategoryManagementPanel view = new CategoryManagementPanel();
        new CategoryController(service, view,currentStaff);
        switchPanel(view);
    }

    private void openBrandManagement() {
        BrandRepositoryImpl repo = new BrandRepositoryImpl();
        BrandServiceImpl service = new BrandServiceImpl(repo);
        BrandManagementPanel view = new BrandManagementPanel();
        new BrandController(service, view,currentStaff);
        switchPanel(view);
    }

    private void openSupplierManagement() {
        SupplierRepositoryImpl repo = new SupplierRepositoryImpl();
        SupplierServiceImpl service = new SupplierServiceImpl(repo);
        SupplierManagementPanel view = new SupplierManagementPanel();
        new SupplierController(service, view,currentStaff);
        switchPanel(view);
    }
 
    private void openProductManagement() {
    // 1. Khởi tạo các Repository
    ProductRepositoryImpl productRepo = new ProductRepositoryImpl();
    CategoryRepositoryImpl categoryRepo = new CategoryRepositoryImpl();
    BrandRepositoryImpl brandRepo = new BrandRepositoryImpl();
    
    // 2. Khởi tạo các Service tương ứng
    ProductServiceImpl productService = new ProductServiceImpl(productRepo);
    CategoryServiceImpl categoryService = new CategoryServiceImpl(categoryRepo);
    BrandServiceImpl brandService = new BrandServiceImpl(brandRepo);
    
    // 3. Khởi tạo View
    ProductManagementPanel view = new ProductManagementPanel();
    
    // 4. Khởi tạo Controller và truyền tất cả vào (bao gồm cả currentStaff để phân quyền)
    new ProductController(productService, categoryService, brandService, view, currentStaff);
    
    // 5. Hiển thị lên màn hình chính
    switchPanel(view);
}
}