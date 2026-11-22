package com.mtclient;

import javax.swing.*;
import java.awt.*;

public final class ProfilesPanel extends JPanel {
    public ProfilesPanel() {
        super(new BorderLayout(0, 18));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.sectionBorder());

        JPanel intro = Theme.panel(new BorderLayout(0, 6));
        intro.add(Theme.heading("Perfiles"), BorderLayout.NORTH);
        intro.add(Theme.muted("Organiza tus instalaciones y cargadores en un solo lugar."), BorderLayout.CENTER);

        JPanel profile = Theme.card(new BorderLayout(14, 0));
        JPanel details = Theme.panel(new GridLayout(0, 1, 0, 7));
        JLabel name = new JLabel("Perfil principal");
        name.setForeground(Theme.TEXT);
        name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        details.add(name);
        details.add(Theme.muted("Vanilla  •  Minecraft 1.21.1"));
        details.add(Theme.muted("Carpeta de juego: .mtclient"));
        profile.add(details, BorderLayout.CENTER);

        JPanel actions = Theme.panel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        JButton fabric = new JButton("Preparar Fabric");
        Theme.styleButton(fabric, true);
        JLabel message = new JLabel(" ");
        message.setForeground(new Color(132, 190, 255));
        fabric.addActionListener(event -> {
            try {
                new FabricManager().prepareProfile("1.21.1", new RuntimeManager().rootDirectory());
                message.setText("Fabric preparado");
            } catch (Exception error) {
                message.setText("Error: " + error.getMessage());
            }
        });
        actions.add(fabric);
        profile.add(actions, BorderLayout.EAST);

        JPanel bottom = Theme.panel(new BorderLayout(8, 0));
        bottom.add(Theme.muted("Los mods deben ser instalados manualmente en la carpeta mods del perfil."), BorderLayout.CENTER);
        bottom.add(message, BorderLayout.SOUTH);

        add(intro, BorderLayout.NORTH);
        add(profile, BorderLayout.CENTER);
        add(bottom, BorderLayout.SOUTH);
    }
}
