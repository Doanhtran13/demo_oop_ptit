package coffeeshop.ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RoundedButtonUI extends BasicButtonUI {
    private final Color base;
    private final Color hover;
    private final Color press;
    private final Color border;
    private final int radius;

    public RoundedButtonUI(Color base, Color hover, Color press, int radius) {
        this.base = base;
        this.hover = hover;
        this.press = press;
        this.radius = radius;
        this.border = base.darker();
    }

    @Override
    public void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setRolloverEnabled(true);
        b.setContentAreaFilled(false);
        b.setOpaque(false);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        ButtonModel m = b.getModel();
        Color fill = base;
        if (m.isPressed()) {
            fill = press;
        } else if (m.isRollover()) {
            fill = hover;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int w = c.getWidth();
        int h = c.getHeight();
        g2.setColor(fill);
        g2.fillRoundRect(0, 0, w - 1, h - 1, radius, radius);
        g2.setColor(border);
        g2.drawRoundRect(0, 0, w - 1, h - 1, radius, radius);
        g2.dispose();

        super.paint(g, c);
    }
}

