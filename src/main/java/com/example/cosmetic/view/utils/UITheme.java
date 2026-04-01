package com.example.cosmetic.view.utils;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * UITheme — Design System trung tâm cho toàn bộ giao diện Modern Admin.
 * Chứa tất cả màu sắc, font, và factory methods tạo component đẹp.
 */
public class UITheme {

    // =========================================================
    // DARK / LIGHT MODE STATE
    // =========================================================
    public static boolean isDark = false;

    public static void toggleDark() {
        isDark = !isDark;
    }

    // =========================================================
    // COLOR PALETTE — LIGHT MODE (fixed constants)
    // =========================================================
    public static final Color PRIMARY        = new Color(108, 63, 197);   // Tím chính
    public static final Color PRIMARY_LIGHT  = new Color(237, 233, 250);  // Tím nhạt (active bg)
    public static final Color PRIMARY_DARK   = new Color(80,  40, 160);   // Tím đậm (hover)

    public static final Color SIDEBAR_BG     = new Color(30,  30,  46);   // Nền sidebar tối
    public static final Color SIDEBAR_TEXT   = new Color(160, 160, 176);  // Text menu
    public static final Color SIDEBAR_ACTIVE_BG  = new Color(61,  43, 107); // BG item active
    public static final Color SIDEBAR_ACTIVE_TEXT = Color.WHITE;
    public static final Color SIDEBAR_HOVER_BG   = new Color(45,  45,  65);

    public static final Color HEADER_BG      = Color.WHITE;
    public static final Color CONTENT_BG     = new Color(244, 246, 249);  // Nền xám nhạt
    public static final Color CARD_BG        = Color.WHITE;

    public static final Color TEXT_PRIMARY   = new Color(26,  26,  46);   // Gần đen
    public static final Color TEXT_SECONDARY = new Color(107, 114, 128);  // Xám
    public static final Color BORDER_COLOR   = new Color(229, 231, 235);  // Viền nhạt

    // =========================================================
    // COLOR PALETTE — DARK MODE
    // =========================================================
    public static final Color DARK_CONTENT_BG  = new Color(18,  18,  28);
    public static final Color DARK_CARD_BG     = new Color(28,  28,  42);
    public static final Color DARK_HEADER_BG   = new Color(22,  22,  34);
    public static final Color DARK_TEXT        = new Color(220, 220, 235);
    public static final Color DARK_TEXT_SEC    = new Color(140, 140, 165);
    public static final Color DARK_BORDER      = new Color(50,  50,  70);
    public static final Color DARK_FIELD_BG    = new Color(35,  35,  55);
    public static final Color DARK_ROW_ALT     = new Color(33,  33,  50);

    // =========================================================
    // DYNAMIC COLOR GETTERS (trả về màu theo theme hiện tại)
    // =========================================================
    public static Color getBgColor()     { return isDark ? DARK_CONTENT_BG  : CONTENT_BG; }
    public static Color getCardColor()   { return isDark ? DARK_CARD_BG     : CARD_BG; }
    public static Color getHeaderColor() { return isDark ? DARK_HEADER_BG   : HEADER_BG; }
    public static Color getTextColor()   { return isDark ? DARK_TEXT        : TEXT_PRIMARY; }
    public static Color getTextSec()     { return isDark ? DARK_TEXT_SEC    : TEXT_SECONDARY; }
    public static Color getBorderColor() { return isDark ? DARK_BORDER      : BORDER_COLOR; }
    public static Color getFieldBg()     { return isDark ? DARK_FIELD_BG    : new Color(243, 244, 246); }

    // Status colors
    public static final Color SUCCESS        = new Color(22,  163,  74);  // Xanh lá
    public static final Color SUCCESS_BG     = new Color(220, 252, 231);
    public static final Color DANGER         = new Color(220,  38,  38);  // Đỏ
    public static final Color DANGER_BG      = new Color(254, 226, 226);
    public static final Color WARNING        = new Color(217, 119,   6);  // Cam
    public static final Color WARNING_BG     = new Color(254, 243, 199);
    public static final Color INFO           = new Color(37,  99,  235);  // Xanh dương
    public static final Color INFO_BG        = new Color(219, 234, 254);

    public static final Color BTN_ADD        = new Color(37,  99,  235);  // + New (xanh dương)
    public static final Color BTN_EDIT       = new Color(37,  99,  235);  // Sửa
    public static final Color BTN_DELETE     = new Color(220,  38,  38);  // Xóa (đỏ)
    public static final Color BTN_EXPORT     = new Color(22,  163,  74);  // Export (xanh lá)
    public static final Color BTN_SEARCH     = new Color(107, 114, 128);  // Tìm kiếm (xám)
    public static final Color BTN_CLEAR      = new Color(107, 114, 128);  // Làm mới (xám)

    // =========================================================
    // FONTS
    // =========================================================
    public static final Font FONT_TITLE      = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FONT_SUBTITLE   = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FONT_BODY       = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SMALL      = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FONT_LABEL      = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_SIDEBAR    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FONT_SIDEBAR_HEADER = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font FONT_BTN        = new Font("Segoe UI", Font.BOLD,  13);
    public static final Font FONT_TABLE_HDR  = new Font("Segoe UI", Font.BOLD,  12);
    public static final Font FONT_TABLE_BODY = new Font("Segoe UI", Font.PLAIN, 13);

    // =========================================================
    // DIMENSIONS
    // =========================================================
    public static final int SIDEBAR_WIDTH    = 230;
    public static final int HEADER_HEIGHT    = 62;
    public static final int CORNER_RADIUS    = 10;
    public static final int BTN_CORNER       = 8;
    public static final int TABLE_ROW_HEIGHT = 38;

    // =========================================================
    // FACTORY: BUTTON
    // =========================================================
    /**
     * Tạo button hiện đại với màu nền, góc bo tròn, hover effect.
     */
    public static JButton createButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color bg = getModel().isRollover()
                        ? bgColor.darker()
                        : bgColor;
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), BTN_CORNER * 2, BTN_CORNER * 2);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(Color.WHITE);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    /**
     * Tạo button outline (viền, nền trắng).
     */
    public static JButton createOutlineButton(String text, Color borderColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isRollover()) {
                    g2.setColor(borderColor);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), BTN_CORNER * 2, BTN_CORNER * 2);
                    setForeground(Color.WHITE);
                } else {
                    g2.setColor(Color.WHITE);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), BTN_CORNER * 2, BTN_CORNER * 2);
                    g2.setColor(borderColor);
                    g2.setStroke(new BasicStroke(1.5f));
                    g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, BTN_CORNER * 2, BTN_CORNER * 2);
                    setForeground(borderColor);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(borderColor);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 18, 8, 18));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // =========================================================
    // FACTORY: STATUS BADGE (Còn hàng / Hết hàng)
    // =========================================================
    public static JLabel createBadge(String text, Color textColor, Color bgColor) {
        JLabel badge = new JLabel(text, SwingConstants.CENTER) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(textColor);
        badge.setOpaque(false);
        badge.setBorder(new EmptyBorder(3, 12, 3, 12));
        return badge;
    }

    // =========================================================
    // FACTORY: CARD PANEL (nền theo theme, shadow giả)
    // =========================================================
    public static JPanel createCard() {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Shadow
                g2.setColor(new Color(0, 0, 0, isDark ? 30 : 18));
                g2.fillRoundRect(3, 4, getWidth() - 4, getHeight() - 4, CORNER_RADIUS * 2, CORNER_RADIUS * 2);
                // Background (dynamic)
                g2.setColor(getCardColor());
                g2.fillRoundRect(0, 0, getWidth() - 4, getHeight() - 4, CORNER_RADIUS * 2, CORNER_RADIUS * 2);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        return card;
    }

    // =========================================================
    // FACTORY: STYLED TABLE
    // =========================================================
    public static void styleTable(JTable table) {
        Color rowEven  = isDark ? DARK_CARD_BG    : Color.WHITE;
        Color rowOdd   = isDark ? DARK_ROW_ALT    : new Color(249, 250, 251);
        Color hdrBg    = isDark ? new Color(35, 35, 55) : new Color(249, 250, 251);
        Color hdrFg    = isDark ? DARK_TEXT_SEC   : TEXT_SECONDARY;
        Color grid     = isDark ? DARK_BORDER      : BORDER_COLOR;
        Color selBg    = isDark ? new Color(70, 50, 140) : PRIMARY_LIGHT;
        Color selFg    = isDark ? Color.WHITE       : PRIMARY;
        Color textFg   = isDark ? DARK_TEXT        : TEXT_PRIMARY;

        table.setFont(FONT_TABLE_BODY);
        table.setRowHeight(TABLE_ROW_HEIGHT);
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setGridColor(grid);
        table.setSelectionBackground(selBg);
        table.setSelectionForeground(selFg);
        table.setBackground(rowEven);
        table.setForeground(textFg);
        table.setFillsViewportHeight(true);
        table.setIntercellSpacing(new Dimension(0, 0));

        JTableHeader header = table.getTableHeader();
        header.setFont(FONT_TABLE_HDR);
        header.setBackground(hdrBg);
        header.setForeground(hdrFg);
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, grid));
        header.setReorderingAllowed(false);
        header.setPreferredSize(new Dimension(0, 42));

        // Alternating row colors via renderer
        final Color fe = rowEven, fo = rowOdd, ft = textFg;
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(tbl, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? fe : fo);
                    c.setForeground(ft);
                }
                setBorder(new EmptyBorder(0, 12, 0, 12));
                return c;
            }
        });
    }

    // =========================================================
    // FACTORY: STYLED SEARCH FIELD
    // =========================================================
    public static JTextField createSearchField(String placeholder) {
        Color fieldBg  = getFieldBg();
        Color borderC  = isDark ? DARK_BORDER : new Color(209, 213, 219);
        Color textC    = isDark ? DARK_TEXT   : TEXT_PRIMARY;
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), BTN_CORNER * 2, BTN_CORNER * 2);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        field.setFont(FONT_BODY);
        field.setForeground(textC);
        field.setBackground(fieldBg);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(borderC, BTN_CORNER),
                new EmptyBorder(6, 12, 6, 12)
        ));
        field.setOpaque(false);
        return field;
    }

    // =========================================================
    // FACTORY: STYLED TEXT FIELD (for forms)
    // =========================================================
    public static JTextField createTextField() {
        Color fieldBg = getFieldBg();
        Color borderC = isDark ? DARK_BORDER : BORDER_COLOR;
        Color textC   = isDark ? DARK_TEXT   : TEXT_PRIMARY;
        JTextField field = new JTextField();
        field.setFont(FONT_BODY);
        field.setForeground(textC);
        field.setBackground(isDark ? fieldBg : Color.WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(borderC, 6),
                new EmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(0, 36));
        return field;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(FONT_BODY);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(BORDER_COLOR, 6),
                new EmptyBorder(6, 10, 6, 10)
        ));
        field.setPreferredSize(new Dimension(0, 36));
        return field;
    }

    public static <T> JComboBox<T> createComboBox() {
        JComboBox<T> cb = new JComboBox<>();
        cb.setFont(FONT_BODY);
        cb.setBorder(BorderFactory.createCompoundBorder(
                new RoundBorder(isDark ? DARK_BORDER : BORDER_COLOR, 6),
                new EmptyBorder(4, 6, 4, 6)
        ));
        cb.setBackground(isDark ? DARK_FIELD_BG : Color.WHITE);
        cb.setForeground(isDark ? DARK_TEXT : TEXT_PRIMARY);
        cb.setPreferredSize(new Dimension(0, 36));
        return cb;
    }

    // =========================================================
    // FACTORY: SECTION TITLE / SUBTITLE
    // =========================================================
    public static JLabel createSectionTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_TITLE);
        lbl.setForeground(getTextColor()); // dynamic
        return lbl;
    }

    public static JLabel createSubTitle(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SUBTITLE);
        lbl.setForeground(getTextSec()); // dynamic
        return lbl;
    }

    // =========================================================
    // INNER: RoundBorder
    // =========================================================
    public static class RoundBorder extends AbstractBorder {
        private final Color color;
        private final int radius;

        public RoundBorder(Color color, int radius) {
            this.color  = color;
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(1.2f));
            g2.drawRoundRect(x, y, width - 1, height - 1, radius * 2, radius * 2);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius / 2, radius / 2, radius / 2, radius / 2);
        }
    }

    // =========================================================
    // FACTORY: STATUS BADGE CELL RENDERER
    // =========================================================
    public static class StatusBadgeRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            String text = value != null ? value.toString() : "";
            JLabel badge = new JLabel(text, SwingConstants.CENTER) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color bg = isSelected ? (text.contains("Còn") ? SUCCESS.darker() : DANGER.darker())
                                          : (text.contains("Còn") ? SUCCESS_BG : DANGER_BG);
                    g2.setColor(bg);
                    g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 8, 20, 20);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
            badge.setForeground(text.contains("Còn") ? SUCCESS : DANGER);
            badge.setOpaque(false);

            // Wrap in panel to center the badge
            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.setBackground(isSelected ? PRIMARY_LIGHT : (row % 2 == 0 ? Color.WHITE : new Color(249, 250, 251)));
            wrapper.add(badge);
            return wrapper;
        }
    }

    /**
     * Renderer badge generic — dùng cho "Hoạt động"/"Không hoạt động", "Admin"/"Staff"...
     */
    public static class GenericBadgeRenderer extends DefaultTableCellRenderer {
        private final String positiveKeyword;
        public GenericBadgeRenderer(String positiveKeyword) {
            this.positiveKeyword = positiveKeyword;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            String text = value != null ? value.toString() : "";
            boolean isPos = text.contains(positiveKeyword);

            JLabel badge = new JLabel(text, SwingConstants.CENTER) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    Color bg = isSelected
                            ? (isPos ? SUCCESS.darker() : WARNING.darker())
                            : (isPos ? SUCCESS_BG : WARNING_BG);
                    g2.setColor(bg);
                    g2.fillRoundRect(2, 4, getWidth() - 4, getHeight() - 8, 20, 20);
                    g2.dispose();
                    super.paintComponent(g);
                }
            };
            badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
            badge.setForeground(isPos ? SUCCESS : WARNING);
            badge.setOpaque(false);

            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.setBackground(isSelected ? PRIMARY_LIGHT : (row % 2 == 0 ? Color.WHITE : new Color(249, 250, 251)));
            wrapper.add(badge);
            return wrapper;
        }
    }
}
