package analysis;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

public class RecallAnalysisService implements AnalysisService {

    private DataSet lastDataSet;

    @Override
    public void setOptions(String[] options) {
        // Opcje nie są potrzebne
    }

    @Override
    public String getName() {
        return "Recall Analysis";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        this.lastDataSet = ds;
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        double recall = calculateRecall(lastDataSet);
        DataSet resultDataSet = new DataSet();
        resultDataSet.setHeader(new String[]{"Recall"});
        resultDataSet.setData(new String[][]{{String.valueOf(recall)}});

        if (clear) {
            this.lastDataSet = null;
        }

        return resultDataSet;
    }

    private double calculateRecall(DataSet dataSet) {
        String[][] data = dataSet.getData();
        int TP = 0, FN = 0;

        for (String[] row : data) {
            if ("TP".equals(row[1])) {
                TP = Integer.parseInt(row[2]);
            } else if ("FN".equals(row[1])) {
                FN = Integer.parseInt(row[2]);
            }
        }

        return (double) TP / (TP + FN);
    }
}
