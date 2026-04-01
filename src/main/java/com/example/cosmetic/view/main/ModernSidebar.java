package com.example.cosmetic.view.main;

import com.example.cosmetic.view.utils.UITheme;
import com.example.cosmetic.model.enums.StaffRole;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * ModernSidebar — Thanh điều hướng bên trái kiểu modern admin dashboard.
 */
public class ModernSidebar extends JPanel {

    public interface NavigationListener {
        void onNavigate(String destination);
    }

    private NavigationListener listener;
    private List<JPanel> allMenuItems = new ArrayList<>();
    private JPanel activeItem = null;

    // Submenu panel for Danh Mục
    private JPanel subMenuDanhMuc;
    private boolean danhMucExpanded = false;
    private JPanel danhMucParent;

    // Submenu panel for Bán Hàng  
    private JPanel subMenuBanHang;
    private boolean banHangExpanded = false;

    // Submenu panel for Quản Lý Kho
    private JPanel subMenuKho;
    private boolean khoExpanded = false;

    private StaffRole staffRole;

    public ModernSidebar(StaffRole role) {
        this.staffRole = role;
        setPreferredSize(new Dimension(UITheme.SIDEBAR_WIDTH, 0));
        setBackground(UITheme.SIDEBAR_BG);
        setLayout(new BorderLayout());

        // Logo section
        JPanel logoPanel = buildLogoPanel();
        add(logoPanel, BorderLayout.NORTH);

        // Menu section
        JPanel menuPanel = buildMenuPanel();
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setBorder(null);
        scrollPane.setBackground(UITheme.SIDEBAR_BG);
        scrollPane.getViewport().setBackground(UITheme.SIDEBAR_BG);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel buildLogoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 16));
        panel.setBackground(UITheme.SIDEBAR_BG);
        panel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(50, 50, 70)));

        // Logo icon (gradient circle)
        JLabel logoIcon = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradient tím-hồng
                GradientPaint gp = new GradientPaint(0, 0, new Color(168, 85, 247),
                        getWidth(), getHeight(), new Color(236, 72, 153));
                g2.setPaint(gp);
                g2.fillOval(0, 0, 38, 38);
                // Chữ M
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
                FontMetrics fm = g2.getFontMetrics();
                String txt = "M";
                int tx = (38 - fm.stringWidth(txt)) / 2;
                int ty = (38 - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(txt, tx, ty);
                g2.dispose();
            }
        };
        logoIcon.setPreferredSize(new Dimension(38, 38));

        JLabel logoText = new JLabel("QUẢN LÝ MỸ PHẨM");
        logoText.setFont(UITheme.FONT_SIDEBAR_HEADER);
        logoText.setForeground(Color.WHITE);

        panel.add(logoIcon);
        panel.add(logoText);
        panel.setPreferredSize(new Dimension(UITheme.SIDEBAR_WIDTH, 72));
        return panel;
    }

    private JPanel buildMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UITheme.SIDEBAR_BG);
        panel.setBorder(new EmptyBorder(12, 0, 12, 0));

        // --- Menu items ---
        addMenuItem(panel, "🏠", "Trang Chủ", "DASHBOARD");
        addSeparatorLabel(panel, "QUẢN LÝ");

        // Bán Hàng (có submenu)
        addExpandableItem(panel, "🛒", "Bán Hàng", "banHang");
        subMenuBanHang = new JPanel();
        subMenuBanHang.setLayout(new BoxLayout(subMenuBanHang, BoxLayout.Y_AXIS));
        subMenuBanHang.setBackground(new Color(22, 22, 38));
        subMenuBanHang.setVisible(false);
        addSubMenuItem(subMenuBanHang, "Lập Hóa Đơn", "SALES");
        addSubMenuItem(subMenuBanHang, "Lịch Sử Hóa Đơn", "INVOICE_HISTORY");
        panel.add(subMenuBanHang);

        // Quản Lý Kho (có submenu)
        addExpandableItem(panel, "📦", "Quản Lý Kho", "kho");
        subMenuKho = new JPanel();
        subMenuKho.setLayout(new BoxLayout(subMenuKho, BoxLayout.Y_AXIS));
        subMenuKho.setBackground(new Color(22, 22, 38));
        subMenuKho.setVisible(false);
        addSubMenuItem(subMenuKho, "Nhập Kho", "IMPORT");
        addSubMenuItem(subMenuKho, "Lịch Sử Nhập Kho", "IMPORT_HISTORY");
        panel.add(subMenuKho);

        addMenuItem(panel, "👥", "Khách Hàng", "CUSTOMER");

        addSeparatorLabel(panel, "DANH MỤC");

        // Danh Mục (có submenu)
        addExpandableItem(panel, "≡", "Danh Mục", "danhMuc");
        subMenuDanhMuc = new JPanel();
        subMenuDanhMuc.setLayout(new BoxLayout(subMenuDanhMuc, BoxLayout.Y_AXIS));
        subMenuDanhMuc.setBackground(new Color(22, 22, 38));
        subMenuDanhMuc.setVisible(false);
        addSubMenuItem(subMenuDanhMuc, "Sản Phẩm", "PRODUCT");
        addSubMenuItem(subMenuDanhMuc, "Loại Mỹ Phẩm", "CATEGORY");
        addSubMenuItem(subMenuDanhMuc, "Thương Hiệu", "BRAND");
        addSubMenuItem(subMenuDanhMuc, "Nhà Cung Cấp", "SUPPLIER");
        addSubMenuItem(subMenuDanhMuc, "Mã Khuyến Mãi", "PROMOTION");
        panel.add(subMenuDanhMuc);

        addSeparatorLabel(panel, "BÁO CÁO");
        addMenuItem(panel, "📊", "Thống Kê", "STATISTICS");

        // Admin-only
        if (staffRole == StaffRole.ADMIN) {
            addSeparatorLabel(panel, "HỆ THỐNG");
            addMenuItem(panel, "⚙", "Quản Lý Nhân Viên", "STAFF");
            addMenuItem(panel, "💾", "Backup Database", "BACKUP");
        }

        panel.add(Box.createVerticalGlue());
        return panel;
    }

    private void addSeparatorLabel(JPanel parent, String text) {
        JLabel lbl = new JLabel("  " + text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lbl.setForeground(new Color(90, 90, 110));
        lbl.setBorder(new EmptyBorder(14, 8, 4, 8));
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        parent.add(lbl);
    }

    private void addMenuItem(JPanel parent, String icon, String label, String destination) {
        JPanel item = createMenuItemPanel(icon, label);
        item.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                setActiveItem(item);
                if (listener != null) listener.onNavigate(destination);
            }
            @Override public void mouseEntered(MouseEvent e) {
                if (item != activeItem) item.setBackground(UITheme.SIDEBAR_HOVER_BG);
            }
            @Override public void mouseExited(MouseEvent e) {
                if (item != activeItem) item.setBackground(UITheme.SIDEBAR_BG);
            }
        });
        allMenuItems.add(item);
        parent.add(item);

        // Dashboard là item mặc định active
        if ("DASHBOARD".equals(destination)) {
            setActiveItem(item);
        }
    }

    private void addExpandableItem(JPanel parent, String icon, String label, String key) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(UITheme.SIDEBAR_BG);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        item.setPreferredSize(new Dimension(UITheme.SIDEBAR_WIDTH, 44));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        iconLbl.setForeground(UITheme.SIDEBAR_TEXT);
        iconLbl.setBorder(new EmptyBorder(0, 16, 0, 10));
        iconLbl.setPreferredSize(new Dimension(48, 44));
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel textLbl = new JLabel(label);
        textLbl.setFont(UITheme.FONT_SIDEBAR);
        textLbl.setForeground(UITheme.SIDEBAR_TEXT);

        JLabel arrowLbl = new JLabel("›");
        arrowLbl.setFont(new Font("Segoe UI", Font.BOLD, 16));
        arrowLbl.setForeground(UITheme.SIDEBAR_TEXT);
        arrowLbl.setBorder(new EmptyBorder(0, 0, 0, 14));

        item.add(iconLbl,  BorderLayout.WEST);
        item.add(textLbl,  BorderLayout.CENTER);
        item.add(arrowLbl, BorderLayout.EAST);

        item.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                toggleExpand(key, item, arrowLbl);
            }
            @Override public void mouseEntered(MouseEvent e) { item.setBackground(UITheme.SIDEBAR_HOVER_BG); }
            @Override public void mouseExited(MouseEvent e)  { item.setBackground(UITheme.SIDEBAR_BG); }
        });

        parent.add(item);
    }

    private void toggleExpand(String key, JPanel item, JLabel arrowLbl) {
        switch (key) {
            case "danhMuc":
                danhMucExpanded = !danhMucExpanded;
                subMenuDanhMuc.setVisible(danhMucExpanded);
                arrowLbl.setText(danhMucExpanded ? "⌄" : "›");
                break;
            case "banHang":
                banHangExpanded = !banHangExpanded;
                subMenuBanHang.setVisible(banHangExpanded);
                arrowLbl.setText(banHangExpanded ? "⌄" : "›");
                break;
            case "kho":
                khoExpanded = !khoExpanded;
                subMenuKho.setVisible(khoExpanded);
                arrowLbl.setText(khoExpanded ? "⌄" : "›");
                break;
        }
        revalidate();
        repaint();
    }

    private void addSubMenuItem(JPanel parent, String label, String destination) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(new Color(22, 22, 38));
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        item.setPreferredSize(new Dimension(UITheme.SIDEBAR_WIDTH, 38));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel dot = new JLabel("•");
        dot.setFont(new Font("Segoe UI", Font.BOLD, 10));
        dot.setForeground(new Color(120, 120, 140));
        dot.setBorder(new EmptyBorder(0, 44, 0, 8));

        JLabel textLbl = new JLabel(label);
        textLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textLbl.setForeground(new Color(140, 140, 160));

        item.add(dot,    BorderLayout.WEST);
        item.add(textLbl, BorderLayout.CENTER);

        item.addMouseListener(new MouseAdapter() {
            @Override public void mouseClicked(MouseEvent e) {
                setActiveSubItem(item, textLbl, dot);
                if (listener != null) listener.onNavigate(destination);
            }
            @Override public void mouseEntered(MouseEvent e) {
                item.setBackground(new Color(35, 35, 55));
                textLbl.setForeground(Color.WHITE);
            }
            @Override public void mouseExited(MouseEvent e) {
                if (!item.getBackground().equals(UITheme.SIDEBAR_ACTIVE_BG)) {
                    item.setBackground(new Color(22, 22, 38));
                    textLbl.setForeground(new Color(140, 140, 160));
                }
            }
        });

        allMenuItems.add(item);
        parent.add(item);
    }

    private JPanel createMenuItemPanel(String icon, String label) {
        JPanel item = new JPanel(new BorderLayout());
        item.setBackground(UITheme.SIDEBAR_BG);
        item.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        item.setPreferredSize(new Dimension(UITheme.SIDEBAR_WIDTH, 44));
        item.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLbl = new JLabel(icon);
        iconLbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        iconLbl.setForeground(UITheme.SIDEBAR_TEXT);
        iconLbl.setBorder(new EmptyBorder(0, 16, 0, 10));
        iconLbl.setPreferredSize(new Dimension(48, 44));
        iconLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel textLbl = new JLabel(label);
        textLbl.setFont(UITheme.FONT_SIDEBAR);
        textLbl.setForeground(UITheme.SIDEBAR_TEXT);

        item.add(iconLbl, BorderLayout.WEST);
        item.add(textLbl, BorderLayout.CENTER);
        item.putClientProperty("textLabel", textLbl);
        item.putClientProperty("iconLabel", iconLbl);
        return item;
    }

    private void setActiveItem(JPanel item) {
        // Reset tất cả
        for (JPanel mi : allMenuItems) {
            mi.setBackground(UITheme.SIDEBAR_BG);
            JLabel txt = (JLabel) mi.getClientProperty("textLabel");
            JLabel ico = (JLabel) mi.getClientProperty("iconLabel");
            if (txt != null) txt.setForeground(UITheme.SIDEBAR_TEXT);
            if (ico != null) ico.setForeground(UITheme.SIDEBAR_TEXT);
        }
        // Active item
        item.setBackground(UITheme.SIDEBAR_ACTIVE_BG);
        JLabel txt = (JLabel) item.getClientProperty("textLabel");
        JLabel ico = (JLabel) item.getClientProperty("iconLabel");
        if (txt != null) {
            txt.setFont(new Font("Segoe UI", Font.BOLD, 13));
            txt.setForeground(UITheme.SIDEBAR_ACTIVE_TEXT);
        }
        if (ico != null) ico.setForeground(Color.WHITE);
        activeItem = item;
    }

    private void setActiveSubItem(JPanel item, JLabel textLbl, JLabel dot) {
        // Reset all
        for (JPanel mi : allMenuItems) {
            mi.setBackground(UITheme.SIDEBAR_BG);
            JLabel t = (JLabel) mi.getClientProperty("textLabel");
            JLabel i2 = (JLabel) mi.getClientProperty("iconLabel");
            if (t != null) t.setForeground(UITheme.SIDEBAR_TEXT);
            if (i2 != null) i2.setForeground(UITheme.SIDEBAR_TEXT);
        }
        item.setBackground(UITheme.SIDEBAR_ACTIVE_BG);
        textLbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        textLbl.setForeground(Color.WHITE);
        dot.setForeground(UITheme.PRIMARY);
        activeItem = item;
    }

    public void setNavigationListener(NavigationListener l) {
        this.listener = l;
    }
}
