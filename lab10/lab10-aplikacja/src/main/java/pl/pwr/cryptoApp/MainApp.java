package pl.pwr.cryptoApp;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Crypto App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 510);
            frame.setContentPane(new MainController().getMainPanel());
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        });
    }
}
