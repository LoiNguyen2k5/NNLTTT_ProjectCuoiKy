package com.example.cosmetic.view.main;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.model.enums.StaffRole;

import com.example.cosmetic.repository.impl.*;
import com.example.cosmetic.service.impl.*;
import com.example.cosmetic.view.category.CategoryManagementPanel;
import com.example.cosmetic.view.brand.BrandManagementPanel;
import com.example.cosmetic.view.supplier.SupplierManagementPanel;
import com.example.cosmetic.view.product.ProductManagementPanel;
import com.example.cosmetic.view.promotion.PromotionManagementPanel;
import com.example.cosmetic.view.customer.CustomerManagementPanel;
import com.example.cosmetic.view.invoice.SalesPanel;
import com.example.cosmetic.view.invoice.InvoiceManagementPanel;
import com.example.cosmetic.view.invoice.ImportPanel;
import com.example.cosmetic.view.invoice.ImportHistoryPanel;
import com.example.cosmetic.view.statistics.StatisticsPanel;
import com.example.cosmetic.view.dashboard.DashboardPanel;
import com.example.cosmetic.view.staff.StaffManagementPanel;
import com.example.cosmetic.controller.*;
import com.example.cosmetic.view.utils.DatabaseBackupUtil;
import com.example.cosmetic.view.utils.UITheme;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainFrame extends JFrame {
    private Staff currentStaff;
    private JPanel contentArea;
    private ModernSidebar sidebar;
    private ModernHeader header;

    public MainFrame(Staff currentStaff) {
        this.currentStaff = currentStaff;

        setTitle("Hệ thống Quản lý Cửa hàng Mỹ phẩm - " + currentStaff.getFullName());
        setSize(1280, 800);
        setMinimumSize(new Dimension(1100, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(UITheme.getBgColor());

        // --- SIDEBAR (bên trái) ---
        sidebar = new ModernSidebar(currentStaff.getRole());
        add(sidebar, BorderLayout.WEST);

        // --- RIGHT PANEL: Header + Content ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(UITheme.getBgColor());

        // Header
        header = new ModernHeader(currentStaff);
        rightPanel.add(header, BorderLayout.NORTH);

        // Content area
        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(UITheme.getBgColor());
        rightPanel.add(contentArea, BorderLayout.CENTER);

        add(rightPanel, BorderLayout.CENTER);

        // Gắn sự kiện điều hướng từ sidebar
        sidebar.setNavigationListener(destination -> {
            handleNavigation(destination);
        });

        // Dark/Light mode toggle callback
        header.setOnThemeToggle(() -> applyThemeToFrame());

        // Mở Dashboard mặc định
        openDashboard();
    }

    /**
     * Dark/Light mode: Cach duy nhat de doi theme trong Swing o-style la rebuild lai toan bo frame.
     * Cac panel su dung mau tu UITheme o thoi diem construction, nen can tao moi.
     */
    private void applyThemeToFrame() {
        // UITheme.isDark da duoc toggle o header
        Staff staffRef = this.currentStaff;
        SwingUtilities.invokeLater(() -> {
            MainFrame newFrame = new MainFrame(staffRef);
            newFrame.setVisible(true);
        });
        dispose(); // dong frame cu
    }

    private void handleNavigation(String destination) {
        switch (destination) {
            case "DASHBOARD":       openDashboard();            header.setPageTitle("Trang Chủ"); break;
            case "SALES":           openSales();                header.setPageTitle("Bán Hàng › Lập Hóa Đơn"); break;
            case "INVOICE_HISTORY": openInvoiceHistory();       header.setPageTitle("Bán Hàng › Lịch Sử Hóa Đơn"); break;
            case "IMPORT":          openImportPanel();          header.setPageTitle("Quản Lý Kho › Nhập Kho"); break;
            case "IMPORT_HISTORY":  openImportHistoryPanel();   header.setPageTitle("Quản Lý Kho › Lịch Sử Nhập Kho"); break;
            case "CUSTOMER":        openCustomerManagement();   header.setPageTitle("Khách Hàng"); break;
            case "PRODUCT":         openProductManagement();    header.setPageTitle("Danh Mục › Sản Phẩm"); break;
            case "CATEGORY":        openCategoryManagement();   header.setPageTitle("Danh Mục › Loại Mỹ Phẩm"); break;
            case "BRAND":           openBrandManagement();      header.setPageTitle("Danh Mục › Thương Hiệu"); break;
            case "SUPPLIER":        openSupplierManagement();   header.setPageTitle("Danh Mục › Nhà Cung Cấp"); break;
            case "PROMOTION":       openPromotionManagement();  header.setPageTitle("Danh Mục › Mã Khuyến Mãi"); break;
            case "STATISTICS":      openStatistics();           header.setPageTitle("Thống Kê"); break;
            case "STAFF":           openStaffManagement();      header.setPageTitle("Hệ Thống › Quản Lý Nhân Viên"); break;
            case "BACKUP":          openDatabaseBackup();       header.setPageTitle("Hệ Thống › Backup Database"); break;
        }
    }

    private void switchPanel(JPanel newPanel) {
        contentArea.removeAll();
        contentArea.add(newPanel, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    // =========================================================
    // CÁC HÀM MỞ GIAO DIỆN CHỨC NĂNG (CHUẨN MVC — KHÔNG ĐỔI)
    // =========================================================

    private void openDashboard() {
        StatisticsRepositoryImpl repo = new StatisticsRepositoryImpl();
        StatisticsServiceImpl service = new StatisticsServiceImpl(repo);
        DashboardPanel view = new DashboardPanel();
        new DashboardController(view, service);
        switchPanel(view);
    }

    private void openSales() {
        try {
            ProductRepositoryImpl pRepo = new ProductRepositoryImpl();
            CustomerRepositoryImpl cRepo = new CustomerRepositoryImpl();
            InvoiceRepositoryImpl iRepo = new InvoiceRepositoryImpl();

            ProductServiceImpl pService = new ProductServiceImpl(pRepo);
            CustomerServiceImpl cService = new CustomerServiceImpl(cRepo);
            InvoiceServiceImpl iService = new InvoiceServiceImpl(iRepo);
            PromotionServiceImpl promoService = new PromotionServiceImpl(new PromotionRepositoryImpl());

            SalesPanel view = new SalesPanel();
            new SalesController(view, pService, cService, iService, promoService, currentStaff);
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

    private void openPromotionManagement() {
        PromotionRepositoryImpl repo = new PromotionRepositoryImpl();
        PromotionServiceImpl service = new PromotionServiceImpl(repo);
        PromotionManagementPanel view = new PromotionManagementPanel();
        new PromotionController(service, view, currentStaff);
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

    private void openStaffManagement() {
        try {
            StaffRepositoryImpl repo = new StaffRepositoryImpl();
            StaffServiceImpl service = new StaffServiceImpl(repo);
            StaffManagementPanel view = new StaffManagementPanel();
            new StaffController(view, service);
            switchPanel(view);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi mở giao diện nhân viên: " + e.getMessage());
        }
    }

    private void openDatabaseBackup() {
        // Dialog chon thu muc luu
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Chọn nơi lưu file Backup");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        int result = chooser.showSaveDialog(this);
        if (result != JFileChooser.APPROVE_OPTION) return;

        // Tao ten file theo thoi gian
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String saveDir  = chooser.getSelectedFile().getAbsolutePath();
        String savePath = saveDir + File.separator + "backup_cosmetics_" + timestamp + ".sql";

        // Hoi thong tin ket noi MySQL
        String dbUser = JOptionPane.showInputDialog(this, "Nhập MySQL Username:", "root");
        if (dbUser == null) return;
        JPasswordField pwField = new JPasswordField();
        int pwResult = JOptionPane.showConfirmDialog(this, pwField,
                "Nhập MySQL Password (để trống nếu không có):",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (pwResult != JOptionPane.OK_OPTION) return;
        String dbPass = new String(pwField.getPassword());

        // Thuc hien backup tren thread rieng
        SwingWorker<Boolean, Void> worker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() throws Exception {
                return DatabaseBackupUtil.backup(dbUser, dbPass, "cosmetics_management", savePath);
            }
            @Override
            protected void done() {
                try {
                    boolean ok = get();
                    if (ok) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "✅ Backup thành công!\nFile đã lưu tại:\n" + savePath,
                                "Backup Database", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "❌ Backup thất bại! Hãy kiểm tra lại:\n" +
                                "1. mysqldump đã được cài trong PATH chưa?\n" +
                                "2. Username/Password MySQL có đúng không?\n" +
                                "3. Database 'cosmetics_management' có tồn tại không?",
                                "Backup Database", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Lỗi: " + ex.getMessage());
                }
            }
        };

        JOptionPane.showMessageDialog(this,
                "⏳ Đang thực hiện backup, vui lòng chờ...",
                "Backup Database", JOptionPane.INFORMATION_MESSAGE);
        worker.execute();
    }
}