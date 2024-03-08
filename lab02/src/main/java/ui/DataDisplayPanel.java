package ui;

import data.DataLoader;
import data.MeasurementCache;
import descriptors.DataModelDescriptor;
import factories.DataModelFactory;
import model.DataModel;
import statistics.StatisticsCalculator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class DataDisplayPanel extends JPanel {
    private final JTable dataTable;
    private final JLabel dataStatus;
    private final DefaultTableModel tableModel;
    private final StatisticsPanel statisticsPanel;
    private final JPanel bottomPanel;

    public DataDisplayPanel(DataModelDescriptor descriptor, StatisticsCalculator calculator) {
        setLayout(new BorderLayout());
        String[] columnNames = descriptor.getColumnNames();
        tableModel = new DefaultTableModel(columnNames, 0);
        dataTable = new JTable(tableModel);

        add(new JScrollPane(dataTable), BorderLayout.CENTER);
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());

        dataStatus = new JLabel("Data status: ");
        bottomPanel.add(dataStatus, BorderLayout.NORTH);

        statisticsPanel = new StatisticsPanel(calculator);
        bottomPanel.add(statisticsPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateDataPreview(String filePath, DataLoader dataLoader, DataModelFactory factory, MeasurementCache cache) {
        List<DataModel> dataModels = cache.getCachedData(filePath);

        String status;
        if (dataModels == null) {
            dataModels = dataLoader.load(filePath, factory);
            cache.cacheData(filePath, dataModels);
            status = "Loaded from file";
        } else {
            status = "Retrieved from cache";
        }

        tableModel.setRowCount(0);
        for (DataModel dataModel : dataModels) {
            tableModel.addRow(dataModel.toStringArray());
        }

        statisticsPanel.updateStatistics(dataModels);
        dataStatus.setText("Data status: " + status);
    }

}
