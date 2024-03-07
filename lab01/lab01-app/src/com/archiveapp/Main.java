package com.archiveapp;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Archive Utility");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Archive", new ArchiveView());
            tabbedPane.addTab("Validate", new ValidateView());

            frame.add(tabbedPane);
            frame.setVisible(true);
        });
    }
}
