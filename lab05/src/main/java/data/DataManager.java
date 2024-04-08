package data;

import ex.api.DataSet;

import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataManager {

    private DefaultTableModel tableModel;

    public DataManager() {
        this.tableModel = new DefaultTableModel();
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public DataSet loadDataFromFile(File file) throws IOException {
        DataSet dataSet = new DataSet();

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            String[] headers = br.readLine().split(",");
            dataSet.setHeader(headers);

            String[][] data = new String[4][headers.length]; // Zakładamy, że znamy liczbę wierszy
            int rowIndex = 0;

            while ((line = br.readLine()) != null && rowIndex < 4) {
                String[] row = line.split(",");
                data[rowIndex++] = row;
            }

            dataSet.setData(data);
        }

        return dataSet;
    }


    public DataSet getDataSetFromTableModel() {
        DataSet dataSet = new DataSet();

        int columnCount = tableModel.getColumnCount();
        int rowCount = tableModel.getRowCount();

        String[] headers = new String[columnCount];
        for (int i = 0; i < columnCount; i++) {
            headers[i] = tableModel.getColumnName(i);
        }
        dataSet.setHeader(headers);

        String[][] data = new String[rowCount][columnCount];
        for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
            for (int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
                data[rowIndex][columnIndex] = String.valueOf(tableModel.getValueAt(rowIndex, columnIndex));
            }
        }
        dataSet.setData(data);

        return dataSet;
    }


}
