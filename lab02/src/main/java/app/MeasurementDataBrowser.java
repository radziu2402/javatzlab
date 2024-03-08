package app;

import data.CSVDataLoader;
import factories.CSVDataModelFactory;
import model.CSVDataModelDescriptor;
import statistics.CSVStatisticsCalculator;
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
        setLocationRelativeTo(null);
        DataDisplayPanel dataDisplayPanel = new DataDisplayPanel(new CSVDataModelDescriptor(), new CSVStatisticsCalculator());

        NavigationPanel navigationPanel = new NavigationPanel("C:\\Users\\radziu2402\\Desktop\\PLIKI POMIAROWE"
                , dataDisplayPanel
                , new CSVDataLoader()
                , new CSVDataModelFactory());

        add(navigationPanel, BorderLayout.WEST);
        add(dataDisplayPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MeasurementDataBrowser::new);
    }
}
