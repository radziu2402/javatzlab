package analysis;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

public class PrecisionAnalysisService implements AnalysisService {

    private DataSet lastDataSet;

    @Override
    public void setOptions(String[] options) {
        // Opcje nie są potrzebne
    }

    @Override
    public String getName() {
        return "Precision Analysis";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        this.lastDataSet = ds;
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        double precision = calculatePrecision(lastDataSet);
        DataSet resultDataSet = new DataSet();
        resultDataSet.setHeader(new String[]{"Precision"});
        resultDataSet.setData(new String[][]{{String.valueOf(precision)}});

        if (clear) {
            this.lastDataSet = null;
        }

        return resultDataSet;
    }

    private double calculatePrecision(DataSet dataSet) {
        String[][] data = dataSet.getData();
        int TP = 0, FP = 0;

        for (String[] row : data) {
            if ("TP".equals(row[1])) {
                TP = Integer.parseInt(row[2]);
            } else if ("FP".equals(row[1])) {
                FP = Integer.parseInt(row[2]);
            }
        }

        return (double) TP / (TP + FP);
    }
}
