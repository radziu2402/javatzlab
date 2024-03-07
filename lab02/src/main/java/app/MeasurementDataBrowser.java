package app;
import ui.DataDisplayPanel;
import ui.NavigationPanel;

import javax.swing.*;
import java.awt.*;

public class MeasurementDataBrowser extends JFrame {
    public MeasurementDataBrowser() {
        super("Measurement Data Browser");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(new NavigationPanel(), BorderLayout.WEST);
        add(new DataDisplayPanel(), BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MeasurementDataBrowser::new);
    }
}
