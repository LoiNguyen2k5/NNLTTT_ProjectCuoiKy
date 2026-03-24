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
import com.example.cosmetic.view.invoice.ImportPanel;
import com.example.cosmetic.view.invoice.ImportHistoryPanel;
import com.example.cosmetic.view.statistics.StatisticsPanel;
import com.example.cosmetic.controller.*;
import com.example.cosmetic.view.utils.DatabaseBackupUtil;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lblWelcome.setForeground(new Color(50, 150, 250));
        centerPanel.add(lblWelcome, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);
        setupMenuBasedOnRole();
    }

    private void setupMenuBasedOnRole() {
        JMenuBar menuBar = new JMenuBar();
        
        // 1. Menu Bán Hàng
        JMenu menuSales = new JMenu("Bán Hàng");
        JMenuItem itemSales = new JMenuItem("Lập Hóa Đơn");
        JMenuItem itemInvoiceHistory = new JMenuItem("Lịch Sử Hóa Đơn");
        menuSales.add(itemSales);
        menuSales.add(itemInvoiceHistory);
        
        // 2. Menu Quản Lý Kho
        JMenu menuKho = new JMenu("Quản Lý Kho");
        JMenuItem itemImport = new JMenuItem("Nhập Kho (Import)");
        JMenuItem itemImportHistory = new JMenuItem("Lịch Sử Nhập Kho");
        menuKho.add(itemImport);
        menuKho.add(itemImportHistory);

        // 3. Menu Danh Mục
        JMenu menuCatalog = new JMenu("Danh Mục");
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

        // 4. Menu Thống Kê
        JMenu menuStats = new JMenu("Thống Kê");
        JMenuItem itemStats = new JMenuItem("Xem Thống Kê Báo Cáo");
        menuStats.add(itemStats);

        // 5. Menu Hệ Thống (Admin)
        JMenu menuSystem = new JMenu("Hệ Thống");
        JMenuItem itemBackup = new JMenuItem("Sao lưu dữ liệu (Backup DB)");
        menuSystem.add(itemBackup);

        // 6. Menu Giao Diện (Thanh gạt Dark Mode)
        JMenu menuView = new JMenu("Giao Diện");
        JCheckBoxMenuItem itemDarkMode = new JCheckBoxMenuItem("Chế độ Tối (Dark Mode)");
        
        // Bắt sự kiện click vào nút Dark Mode
        itemDarkMode.addActionListener(e -> {
            try {
                if (itemDarkMode.isSelected()) {
                    UIManager.setLookAndFeel(new FlatDarkLaf());
                } else {
                    UIManager.setLookAndFeel(new FlatLightLaf());
                }
                // Cập nhật lại giao diện ngay lập tức
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        menuView.add(itemDarkMode);

        // Thêm các Menu chính vào MenuBar
        menuBar.add(menuSales);
        menuBar.add(menuKho);
        menuBar.add(menuCatalog);
        menuBar.add(menuStats);
        menuBar.add(menuView); // Menu giao diện ai cũng thấy được

        // Phân quyền
        if (currentStaff.getRole() == StaffRole.STAFF) {
            menuStats.setVisible(false);
        } else {
            menuBar.add(menuSystem);
        }

        setJMenuBar(menuBar);

        // --- Gắn sự kiện click mở Panel ---
        itemSales.addActionListener(e -> openSales());
        itemInvoiceHistory.addActionListener(e -> openInvoiceHistory());
        
        itemImport.addActionListener(e -> openImportPanel());
        itemImportHistory.addActionListener(e -> openImportHistoryPanel());
        
        itemCategory.addActionListener(e -> openCategoryManagement());
        itemBrand.addActionListener(e -> openBrandManagement());
        itemSupplier.addActionListener(e -> openSupplierManagement());
        itemCustomer.addActionListener(e -> openCustomerManagement());
        itemProduct.addActionListener(e -> openProductManagement());
        
        itemStats.addActionListener(e -> openStatistics());
        itemBackup.addActionListener(e -> performDatabaseBackup());
    }

    private void switchPanel(JPanel newPanel) {
        centerPanel.removeAll();
        centerPanel.add(newPanel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    // =========================================================
    // CÁC HÀM MỞ GIAO DIỆN CHỨC NĂNG
    // =========================================================

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
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void openInvoiceHistory() {
        InvoiceRepositoryImpl repo = new InvoiceRepositoryImpl();
        InvoiceServiceImpl service = new InvoiceServiceImpl(repo);
        InvoiceManagementPanel view = new InvoiceManagementPanel();
        new InvoiceController(service, view);
        switchPanel(view);
    }

    private void openImportPanel() {
        try {
            ProductRepositoryImpl pRepo = new ProductRepositoryImpl();
            SupplierRepositoryImpl sRepo = new SupplierRepositoryImpl();
            ImportReceiptRepositoryImpl iRepo = new ImportReceiptRepositoryImpl();
            
            ProductServiceImpl pService = new ProductServiceImpl(pRepo);
            SupplierServiceImpl sService = new SupplierServiceImpl(sRepo);
            ImportReceiptServiceImpl iService = new ImportReceiptServiceImpl(iRepo);
            
            ImportPanel view = new ImportPanel();
            new ImportController(view, pService, sService, iService, currentStaff);
            switchPanel(view);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
        }
    }

    private void openImportHistoryPanel() {
        ImportReceiptRepositoryImpl repo = new ImportReceiptRepositoryImpl();
        ImportReceiptServiceImpl service = new ImportReceiptServiceImpl(repo);
        ImportHistoryPanel view = new ImportHistoryPanel();
        new ImportHistoryController(service, view);
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

    private void openStatistics() {
        StatisticsRepositoryImpl repo = new StatisticsRepositoryImpl();
        StatisticsServiceImpl service = new StatisticsServiceImpl(repo);
        StatisticsPanel view = new StatisticsPanel();
        new StatisticsController(service, view);
        switchPanel(view);
    }

    // =========================================================
    // HÀM XỬ LÝ SAO LƯU DỮ LIỆU (BACKUP)
    // =========================================================
    private void performDatabaseBackup() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn nơi lưu file Backup Database");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String defaultFileName = "Cosmetic_Backup_" + sdf.format(new Date()) + ".sql";
        fileChooser.setSelectedFile(new File(defaultFileName));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String savePath = fileToSave.getAbsolutePath();

            String dbUser = "root"; 
            String dbPass = "12345"; // Đổi thành mật khẩu MySQL của bạn
            String dbName = "cosmetics_management";

            boolean success = DatabaseBackupUtil.backup(dbUser, dbPass, dbName, savePath);

            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Tuyệt vời! Đã sao lưu toàn bộ dữ liệu thành công tại:\n" + savePath, 
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Lỗi sao lưu! \nNguyên nhân thường gặp: Máy tính chưa được cấu hình biến môi trường PATH cho thư mục 'bin' của MySQL.", 
                    "Lỗi Backup", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}