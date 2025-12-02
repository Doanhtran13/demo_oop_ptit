package coffeeshop.ui;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import coffeeshop.util.FormatUtil;

public class Theme {
    public static final Color brownDark = new Color(68, 44, 30);
    public static final Color brown = new Color(120, 77, 45);
    public static final Color caramel = new Color(188, 136, 94);
    public static final Color cream = new Color(245, 237, 225);
    public static final Color text = new Color(34, 28, 24);

    public static JPanel gradientPanel(LayoutManager layout) {
        return new JPanel(layout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, brownDark, 0, getHeight(), cream);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
    }

    public static void stylePrimaryButton(JButton b) {
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setUI(new RoundedButtonUI(caramel, caramel.brighter(), caramel.darker(), 14));
    }

    public static void styleSecondaryButton(JButton b) {
        b.setForeground(Color.WHITE);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        b.setUI(new RoundedButtonUI(brownDark, brownDark.brighter(), brownDark.darker(), 14));
    }

    public static void styleCard(JPanel p) {
        p.setBackground(cream);
        p.setBorder(new LineBorder(new Color(220, 210, 195), 2, true));
    }

    public static void styleTitle(JLabel l) {
        l.setForeground(Color.WHITE);
        l.setFont(new Font("Segoe UI", Font.BOLD, 28));
    }

    public static void styleSubtitle(JLabel l) {
        l.setForeground(new Color(235, 225, 210));
        l.setFont(new Font("Segoe UI", Font.PLAIN, 16));
    }

    public static void styleLabel(JLabel l) {
        l.setForeground(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    public static void styleTable(JTable table) {
        table.setRowHeight(28);
        table.setGridColor(new Color(225, 215, 200));
        table.setBackground(Color.WHITE);
        table.setForeground(text);
        JTableHeader header = table.getTableHeader();
        header.setBackground(caramel);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.setDefaultRenderer(Double.class, new CurrencyRenderer());
    }

    public static class CurrencyRenderer extends DefaultTableCellRenderer {
        @Override
        protected void setValue(Object value) {
            if (value instanceof Double) {
                setHorizontalAlignment(SwingConstants.RIGHT);
                setText(FormatUtil.formatCurrency((Double) value));
            } else {
                super.setValue(value);
            }
        }
    }
}
