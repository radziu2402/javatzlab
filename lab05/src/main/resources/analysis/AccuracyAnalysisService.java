package analysis;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

public class AccuracyAnalysisService implements AnalysisService {

    private DataSet lastDataSet;

    @Override
    public void setOptions(String[] options) {
        // Opcje nie są potrzebne dla tego serwisu
    }

    @Override
    public String getName() {
        return "Accuracy Analysis";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        this.lastDataSet = ds;
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        double accuracy = calculateAccuracy(lastDataSet);
        DataSet resultDataSet = new DataSet();
        resultDataSet.setHeader(new String[]{"Accuracy"});
        resultDataSet.setData(new String[][]{{String.valueOf(accuracy)}});

        if (clear) {
            this.lastDataSet = null;
        }

        return resultDataSet;
    }

    private double calculateAccuracy(DataSet dataSet) {
        String[][] data = dataSet.getData();
        int TP = 0, TN = 0, FP = 0, FN = 0;

        for (String[] row : data) {
            switch (row[1]) {
                case "TP":
                    TP = Integer.parseInt(row[2]);
                    break;
                case "TN":
                    TN = Integer.parseInt(row[2]);
                    break;
                case "FP":
                    FP = Integer.parseInt(row[2]);
                    break;
                case "FN":
                    FN = Integer.parseInt(row[2]);
                    break;
            }
        }

        return (double) (TP + TN) / (TP + TN + FP + FN);
    }
}
