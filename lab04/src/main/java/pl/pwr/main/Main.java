package pl.pwr.main;


import pl.pwr.ui.TaskProcessorGUI;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TaskProcessorGUI().setVisible(true));
    }
}