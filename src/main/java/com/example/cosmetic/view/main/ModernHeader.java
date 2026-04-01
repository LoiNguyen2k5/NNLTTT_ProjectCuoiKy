package com.example.cosmetic.view.main;

import com.example.cosmetic.model.entity.Staff;
import com.example.cosmetic.view.utils.UITheme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * ModernHeader — Thanh header phía trên: breadcrumb + dark mode toggle + user info.
 */
public class ModernHeader extends JPanel {

    private JLabel  lblPageTitle;
    private JButton btnThemeToggle;
    private Runnable onThemeToggle;

    public ModernHeader(Staff staff) {
        Color hdrBg    = UITheme.getHeaderColor();
        Color textMain = UITheme.getTextColor();
        Color textSec  = UITheme.getTextSec();
        Color border   = UITheme.getBorderColor();

        setBackground(hdrBg);
        setPreferredSize(new Dimension(0, UITheme.HEADER_HEIGHT));
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, border),
                new EmptyBorder(0, 20, 0, 20)
        ));
        setLayout(new BorderLayout(16, 0));

        // --- LEFT: Page title ---
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);

        lblPageTitle = new JLabel("Trang Chu");
        lblPageTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblPageTitle.setForeground(textMain);
        leftPanel.add(lblPageTitle);
        add(leftPanel, BorderLayout.WEST);

        // --- RIGHT: Theme toggle + Notification + User ---
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 0));
        rightPanel.setOpaque(false);

        // Dark/Light toggle — dung text ASCII de tranh loi emoji tren Windows
        String toggleLabel = UITheme.isDark ? "[TDi]" : "[Toi]";
        btnThemeToggle = new JButton(toggleLabel) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bgColor = UITheme.isDark
                        ? new Color(240, 180, 40)   // vang khi dark mode
                        : new Color(80,  60, 160);   // tim khi light mode
                if (getModel().isRollover()) bgColor = bgColor.darker();
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btnThemeToggle.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btnThemeToggle.setForeground(Color.WHITE);
        btnThemeToggle.setOpaque(false);
        btnThemeToggle.setContentAreaFilled(false);
        btnThemeToggle.setBorderPainted(false);
        btnThemeToggle.setFocusPainted(false);
        btnThemeToggle.setBorder(new EmptyBorder(5, 12, 5, 12));
        btnThemeToggle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnThemeToggle.setPreferredSize(new Dimension(75, 34));
        btnThemeToggle.addActionListener(e -> {
            UITheme.toggleDark();
            // Frame se duoc rebuild boi MainFrame.applyThemeToFrame()
            if (onThemeToggle != null) onThemeToggle.run();
        });

        // Notification dot
        JLabel lblBell = new JLabel("*") {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(UITheme.DANGER);
                g2.fillOval(getWidth() - 8, 2, 8, 8);
                g2.dispose();
            }
        };
        lblBell.setFont(new Font("Wingdings", Font.PLAIN, 20));
        lblBell.setForeground(textSec);
        lblBell.setPreferredSize(new Dimension(30, 38));

        // Separator
        JSeparator sep = new JSeparator(JSeparator.VERTICAL);
        sep.setPreferredSize(new Dimension(1, 28));
        sep.setForeground(border);

        // Avatar circle
        JLabel avatarLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(168, 85, 247),
                        getWidth(), getHeight(), new Color(236, 72, 153));
                g2.setPaint(gp);
                g2.fillOval(0, 0, 34, 34);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
                String initial = staff != null && !staff.getFullName().isEmpty()
                        ? String.valueOf(staff.getFullName().charAt(0)).toUpperCase() : "A";
                FontMetrics fm = g2.getFontMetrics();
                int tx = (34 - fm.stringWidth(initial)) / 2;
                int ty = (34 - fm.getHeight()) / 2 + fm.getAscent();
                g2.drawString(initial, tx, ty);
                g2.dispose();
            }
        };
        avatarLabel.setPreferredSize(new Dimension(34, 34));

        // Username label
        String displayName = staff != null ? staff.getFullName() : "Admin";
        String roleText    = staff != null ? "  [" + staff.getRole().name() + "]" : "";
        JLabel lblUserName = new JLabel(displayName + roleText + " v");
        lblUserName.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblUserName.setForeground(textMain);
        lblUserName.setCursor(new Cursor(Cursor.HAND_CURSOR));

        rightPanel.add(btnThemeToggle);
        rightPanel.add(lblBell);
        rightPanel.add(sep);
        rightPanel.add(avatarLabel);
        rightPanel.add(lblUserName);

        add(rightPanel, BorderLayout.EAST);
    }

    public void setPageTitle(String title) {
        lblPageTitle.setText(title);
    }

    /** Callback khi user nhan toggle dark/light mode */
    public void setOnThemeToggle(Runnable r) {
        this.onThemeToggle = r;
    }
}
