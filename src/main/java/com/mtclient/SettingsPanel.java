package com.mtclient;

import javax.swing.*;
import java.awt.*;

public final class SettingsPanel extends JPanel {
    public SettingsPanel() {
        super(new BorderLayout(0, 18));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.sectionBorder());

        JPanel intro = Theme.panel(new BorderLayout(0, 6));
        intro.add(Theme.heading("Ajustes"), BorderLayout.NORTH);
        intro.add(Theme.muted("Personaliza el comportamiento del launcher."), BorderLayout.CENTER);

        JPanel options = Theme.card(new GridLayout(0, 1, 0, 14));
        options.add(option("Mantener el launcher abierto", "No cerrar la ventana al iniciar el juego.", true));
        options.add(option("Pantalla completa", "Iniciar el juego en modo de pantalla completa.", false));
        options.add(option("Mostrar registros", "Conservar visible la salida del proceso de juego.", false));

        JLabel note = Theme.muted("Tema actual: azul eléctrico y negro medianoche.");
        add(intro, BorderLayout.NORTH);
        add(options, BorderLayout.CENTER);
        add(note, BorderLayout.SOUTH);
    }

    private JPanel option(String title, String description, boolean selected) {
        JPanel row = Theme.panel(new BorderLayout(12, 0));
        JPanel text = Theme.panel(new GridLayout(0, 1, 0, 4));
        JLabel label = new JLabel(title);
        label.setForeground(Theme.TEXT);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        text.add(label);
        text.add(Theme.muted(description));
        JCheckBox checkbox = new JCheckBox();
        checkbox.setSelected(selected);
        checkbox.setBackground(Theme.SURFACE);
        checkbox.setForeground(Theme.BLUE);
        row.add(text, BorderLayout.CENTER);
        row.add(checkbox, BorderLayout.EAST);
        return row;
    }
}
