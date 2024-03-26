package app;

import ui.TaskProcessorGUI;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(TaskProcessorGUI::new);
    }
}