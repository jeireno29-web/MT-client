package com.mtclient;

import javax.swing.*;
import java.awt.*;

public final class ProfilesPanel extends JPanel {
    public ProfilesPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));
        add(new JLabel("Perfiles principales, Fabric y mods configurables desde esta sección.", SwingConstants.CENTER), BorderLayout.CENTER);
    }
}
