package com.mtclient;

import javax.swing.*;

public final class Main {
    public static void main(String[] args) {
        System.setProperty("apple.awt.application.name", "MT Client");
        SwingUtilities.invokeLater(() -> new LauncherFrame().setVisible(true));
    }
}
