package com.mtclient;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/** Shared original MT Client visual theme: midnight black with electric blue accents. */
public final class Theme {
    public static final Color BACKGROUND = new Color(10, 13, 20);
    public static final Color SURFACE = new Color(19, 24, 35);
    public static final Color SURFACE_ALT = new Color(25, 32, 46);
    public static final Color BLUE = new Color(38, 119, 255);
    public static final Color BLUE_HOVER = new Color(67, 140, 255);
    public static final Color TEXT = new Color(238, 243, 252);
    public static final Color MUTED = new Color(151, 164, 185);
    public static final Color BORDER = new Color(42, 52, 70);

    private Theme() {}

    public static JPanel panel(LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBackground(BACKGROUND);
        return panel;
    }

    public static JLabel heading(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
        return label;
    }

    public static JLabel muted(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        return label;
    }

    public static JPanel card(LayoutManager layout) {
        JPanel card = new JPanel(layout);
        card.setBackground(SURFACE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)));
        return card;
    }

    public static void styleButton(AbstractButton button, boolean primary) {
        button.setForeground(TEXT);
        button.setBackground(primary ? BLUE : SURFACE_ALT);
        button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primary ? BLUE : BORDER),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        button.setOpaque(true);
    }

    public static void styleField(JComponent component) {
        component.setForeground(TEXT);
        component.setBackground(SURFACE_ALT);
        component.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 13));
        component.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)));
        if (component instanceof JSpinner spinner) {
            JComponent editor = spinner.getEditor();
            editor.setBackground(SURFACE_ALT);
            if (editor instanceof JSpinner.DefaultEditor defaultEditor) {
                defaultEditor.getTextField().setForeground(TEXT);
                defaultEditor.getTextField().setBackground(SURFACE_ALT);
                defaultEditor.getTextField().setCaretColor(TEXT);
                defaultEditor.getTextField().setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
            }
        }
    }

    public static Border sectionBorder() {
        return BorderFactory.createEmptyBorder(24, 28, 24, 28);
    }
}
