package ui;

import data.DataManager;
import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.ServiceLoader;

public class MainFrame extends JFrame {

    private JTable table;
    private JComboBox<String> algorithmComboBox;
    private JTextArea resultsTextArea;
    private List<AnalysisService> services;
    private final DataManager dataManager;

    public MainFrame() {
        initializeUI();
        loadAnalysisServices();
        dataManager = new DataManager();
    }

    private void initializeUI() {
        setTitle("Analiza Statystyczna");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new BorderLayout());
        algorithmComboBox = new JComboBox<>();
        topPanel.add(algorithmComboBox, BorderLayout.CENTER);
        JButton loadButton = new JButton("Wczytaj z pliku");
        loadButton.addActionListener(e -> loadFromFile());
        topPanel.add(loadButton, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        DefaultTableModel tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        resultsTextArea = new JTextArea();
        resultsTextArea.setEditable(false);
        bottomPanel.add(new JScrollPane(resultsTextArea), BorderLayout.CENTER);

        JButton analyzeButton = new JButton("Analizuj");
        analyzeButton.addActionListener(e -> performAnalysis());
        bottomPanel.add(analyzeButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadFromFile() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedFile = fileChooser.getSelectedFile();
                DataSet dataSet = dataManager.loadDataFromFile(selectedFile);

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                model.setColumnIdentifiers(dataSet.getHeader());

                for (String[] rowData : dataSet.getData()) {
                    model.addRow(rowData);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd podczas wczytywania pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void loadAnalysisServices() {
        services = ServiceLoader.load(AnalysisService.class).stream()
                .map(ServiceLoader.Provider::get)
                .toList();

        for (AnalysisService service : services) {
            algorithmComboBox.addItem(service.getName());
        }
    }

    private void performAnalysis() {
        int selectedServiceIndex = algorithmComboBox.getSelectedIndex();
        AnalysisService selectedService = services.get(selectedServiceIndex);

        try {
            dataManager.setTableModel((DefaultTableModel) table.getModel());
            DataSet dataSet = dataManager.getDataSetFromTableModel();

            selectedService.submit(dataSet);

            DataSet results = selectedService.retrieve(true);

            if (results != null && results.getData().length > 0) {
                resultsTextArea.setText(Arrays.deepToString(results.getData()));
            } else {
                resultsTextArea.setText("Nie otrzymano wyników.");
            }
        } catch (AnalysisException e) {
            e.printStackTrace();
            resultsTextArea.setText("Wystąpił błąd podczas analizy: " + e.getMessage());
        }
    }
}
