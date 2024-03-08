package ui;

import statistics.StatisticsCalculator;
import model.DataModel;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsPanel extends JPanel {
    private Map<String, JLabel> statisticsLabels = new HashMap<>();
    private StatisticsCalculator calculator;

    public StatisticsPanel(StatisticsCalculator calculator) {
        this.calculator = calculator;
        setLayout(new GridLayout(0, 1));
    }

    public void updateStatistics(List<DataModel> dataModels) {
        Map<String, Double> stats = calculator.calculate(dataModels);

        this.removeAll();
        statisticsLabels.clear();

        stats.forEach((statName, value) -> {
            JLabel label = statisticsLabels.getOrDefault(statName, new JLabel());
            label.setText(String.format("%s: %.2f", statName, value));
            this.add(label);
            statisticsLabels.put(statName, label);
        });

        this.revalidate();
        this.repaint();
    }
}


