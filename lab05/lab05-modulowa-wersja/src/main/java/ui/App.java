package ui;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

import javax.swing.*;

public class App {

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
//    }
        public static void main(String[] args) {
            System.out.println(calculateKappa());
        }
    private static double calculateKappa() {
        int TP = 35, TN = 10, FP = 1, FN = 10;

        int total = TP + TN + FP + FN;
        double p0 = (double) (TP + TN) / total;
        double pe = ((TP + FP) * (TP + FN) + (FN + TN) * (FP + TN)) / (double) (total * total);
        return (p0 - pe) / (1 - pe);
    }


    }

