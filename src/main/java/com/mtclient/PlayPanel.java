package com.mtclient;

import javax.swing.*;
import java.awt.*;

public final class PlayPanel extends JPanel {
    private final JComboBox<String> versions = new JComboBox<>(new String[]{"1.21.1", "1.20.6", "1.20.1"});
    private final JComboBox<String> loaders = new JComboBox<>(new String[]{"Vanilla", "Fabric"});
    private final JSpinner ram = new JSpinner(new SpinnerNumberModel(4, 2, 16, 1));
    private final JLabel status = new JLabel("●  Listo para jugar");

    public PlayPanel() {
        super(new BorderLayout(22, 22));
        setBackground(Theme.BACKGROUND);
        setBorder(Theme.sectionBorder());

        JPanel intro = Theme.panel(new BorderLayout(0, 7));
        intro.add(Theme.heading("Bienvenido a MT Client"), BorderLayout.NORTH);
        intro.add(Theme.muted("Elige tu versión y prepara tu próxima aventura."), BorderLayout.CENTER);

        JPanel setup = Theme.card(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 6, 8, 6);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.35;
        addRow(setup, c, 0, "VERSIÓN", versions);
        addRow(setup, c, 1, "CARGADOR", loaders);
        addRow(setup, c, 2, "MEMORIA RAM", ram);

        Theme.styleField(versions);
        Theme.styleField(loaders);
        Theme.styleField(ram);

        JButton login = new JButton("Iniciar sesión Microsoft");
        Theme.styleButton(login, false);
        login.addActionListener(event -> new MicrosoftAuth().authenticate(this::setStatus));

        JButton launch = new JButton("JUGAR  →");
        Theme.styleButton(launch, true);
        launch.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        launch.addActionListener(event -> launchMinecraft());

        JPanel actions = Theme.panel(new GridLayout(1, 2, 12, 0));
        actions.add(login);
        actions.add(launch);

        JPanel statusCard = Theme.card(new BorderLayout(8, 0));
        JLabel statusTitle = new JLabel("ESTADO");
        statusTitle.setForeground(Theme.MUTED);
        statusTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 10));
        status.setForeground(new Color(132, 190, 255));
        status.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 13));
        statusCard.add(statusTitle, BorderLayout.WEST);
        statusCard.add(status, BorderLayout.CENTER);

        JPanel content = Theme.panel(new BorderLayout(0, 18));
        content.add(setup, BorderLayout.NORTH);
        content.add(actions, BorderLayout.CENTER);
        content.add(statusCard, BorderLayout.SOUTH);

        add(intro, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
        add(Theme.muted("Las descargas se guardan en la carpeta de datos de MT Client."), BorderLayout.SOUTH);
    }

    private void addRow(JPanel panel, GridBagConstraints c, int row, String name, JComponent input) {
        c.gridy = row;
        c.gridx = 0;
        c.weightx = 0.3;
        JLabel label = new JLabel(name);
        label.setForeground(Theme.MUTED);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 11));
        panel.add(label, c);
        c.gridx = 1;
        c.weightx = 0.7;
        panel.add(input, c);
    }

    private void launchMinecraft() {
        String version = (String) versions.getSelectedItem();
        String loader = (String) loaders.getSelectedItem();
        int memory = (Integer) ram.getValue();
        setStatus("Preparando " + version + "...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override protected Void doInBackground() throws Exception {
                new MinecraftRuntime().launch(version, loader, memory);
                return null;
            }
            @Override protected void done() {
                try { get(); setStatus("Minecraft iniciado"); }
                catch (Exception error) { setStatus("Error: " + error.getMessage()); }
            }
        };
        worker.execute();
    }

    private void setStatus(String text) {
        SwingUtilities.invokeLater(() -> status.setText("●  " + text));
    }
}
