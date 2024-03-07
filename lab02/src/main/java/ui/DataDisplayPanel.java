package ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class DataDisplayPanel extends JPanel {
    private JTextArea textArea;
    private JTable table;

    public DataDisplayPanel() {
        setLayout(new BorderLayout());

        textArea = new JTextArea("Data preview...");
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.NORTH);

        String[] columnNames = {"Time", "Pressure", "Temperature", "Humidity"};
        Object[][] data = {
                {"10:00", "960,34", "-1,2", "60"},
                {"10:02", "960,50", "-1,0", "62"},
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
}
