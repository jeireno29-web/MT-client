package com.mtclient;

import javax.swing.*;
import java.awt.*;

public final class SettingsPanel extends JPanel {
    public SettingsPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        JCheckBox closeWhenLaunched = new JCheckBox("Mantener el launcher abierto", true);
        JCheckBox fullscreen = new JCheckBox("Pantalla completa", false);
        JPanel options = new JPanel(new GridLayout(0, 1));
        options.add(closeWhenLaunched);
        options.add(fullscreen);
        add(options, BorderLayout.NORTH);
    }
}
