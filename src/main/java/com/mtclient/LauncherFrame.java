package com.mtclient;

import javax.swing.*;
import java.awt.*;

public final class LauncherFrame extends JFrame {
    public LauncherFrame() {
        super("MT Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 620);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Jugar", new PlayPanel());
        tabs.addTab("Perfiles", new ProfilesPanel());
        tabs.addTab("Ajustes", new SettingsPanel());
        setContentPane(tabs);
    }
}
