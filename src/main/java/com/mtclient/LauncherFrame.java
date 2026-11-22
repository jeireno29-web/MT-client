package com.mtclient;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public final class LauncherFrame extends JFrame {
    public LauncherFrame() {
        super("MT Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(820, 560));
        setSize(980, 640);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Theme.BACKGROUND);
        setLayout(new BorderLayout());

        JPanel header = Theme.panel(new BorderLayout());
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Theme.BORDER),
                BorderFactory.createEmptyBorder(16, 26, 16, 26)));
        JLabel brand = new JLabel("MT CLIENT");
        brand.setForeground(Theme.TEXT);
        brand.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        JLabel badge = new JLabel("  JAVA EDITION  ");
        badge.setForeground(new Color(145, 190, 255));
        badge.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        JPanel brandGroup = Theme.panel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        brandGroup.add(brand);
        brandGroup.add(Box.createHorizontalStrut(12));
        brandGroup.add(badge);
        JLabel platform = Theme.muted("MULTIPLATAFORMA");
        platform.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        header.add(brandGroup, BorderLayout.WEST);
        header.add(platform, BorderLayout.EAST);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        tabs.setForeground(Theme.TEXT);
        tabs.setBackground(Theme.BACKGROUND);
        tabs.setBorder(BorderFactory.createEmptyBorder(8, 14, 0, 14));
        tabs.setUI(new BasicTabbedPaneUI() {
            @Override protected void installDefaults() {
                super.installDefaults();
                lightHighlight = Theme.BACKGROUND;
                highlight = Theme.BACKGROUND;
                shadow = Theme.BACKGROUND;
                darkShadow = Theme.BACKGROUND;
                contentAreaColor = Theme.BACKGROUND;
                tabAreaInsets = new Insets(8, 4, 0, 4);
                contentBorderInsets = new Insets(0, 0, 0, 0);
            }
            @Override protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {}
            @Override protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex,
                    int x, int y, int w, int h, boolean isSelected) {
                g.setColor(isSelected ? Theme.BLUE : Theme.SURFACE);
                g.fillRoundRect(x + 2, y + 2, w - 4, h - 4, 10, 10);
            }
            @Override protected void paintFocusIndicator(Graphics g, int tabPlacement,
                    Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect,
                    boolean isSelected) {}
        });
        tabs.addTab("  Jugar  ", new PlayPanel());
        tabs.addTab("  Perfiles  ", new ProfilesPanel());
        tabs.addTab("  Ajustes  ", new SettingsPanel());

        add(header, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }
}
