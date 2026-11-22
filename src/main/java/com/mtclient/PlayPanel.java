package com.mtclient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public final class PlayPanel extends JPanel {
    private final JComboBox<String> versions = new JComboBox<>(new String[]{"1.21.1", "1.20.6", "1.20.1"});
    private final JComboBox<String> loaders = new JComboBox<>(new String[]{"Vanilla", "Fabric"});
    private final JSpinner ram = new JSpinner(new SpinnerNumberModel(4, 2, 16, 1));
    private final JLabel status = new JLabel("Listo");

    public PlayPanel() {
        super(new BorderLayout(12, 12));
        setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JPanel form = new JPanel(new GridLayout(5, 2, 10, 12));
        form.add(new JLabel("Versión:"));
        form.add(versions);
        form.add(new JLabel("Cargador:"));
        form.add(loaders);
        form.add(new JLabel("RAM (GB):"));
        form.add(ram);

        JButton microsoftLogin = new JButton("Iniciar sesión Microsoft");
        microsoftLogin.addActionListener(event -> new MicrosoftAuth().authenticate(this::setStatus));
        JButton launch = new JButton("JUGAR");
        launch.addActionListener(event -> launchMinecraft());

        form.add(microsoftLogin);
        form.add(launch);
        form.add(new JLabel("Estado:"));
        form.add(status);

        add(form, BorderLayout.NORTH);
        add(new JLabel("La primera ejecución descarga los archivos oficiales seleccionados.", SwingConstants.CENTER), BorderLayout.CENTER);
    }

    private void launchMinecraft() {
        String version = (String) versions.getSelectedItem();
        int memory = (Integer) ram.getValue();
        setStatus("Preparando " + version + "...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                new MinecraftRuntime().launch(version, (String) loaders.getSelectedItem(), memory);
                return null;
            }

            @Override
            protected void done() {
                try { get(); setStatus("Minecraft iniciado"); }
                catch (Exception error) { setStatus("Error: " + error.getMessage()); }
            }
        };
        worker.execute();
    }

    private void setStatus(String text) {
        SwingUtilities.invokeLater(() -> status.setText(text));
    }
}
