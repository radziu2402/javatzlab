package analysis;

import ex.api.AnalysisException;
import ex.api.AnalysisService;
import ex.api.DataSet;

public class KappaAnalysisService implements AnalysisService {

    private DataSet lastDataSet;

    @Override
    public void setOptions(String[] options) throws AnalysisException {
        // Opcje nie są potrzebne dla tego serwisu
    }

    @Override
    public String getName() {
        return "Kappa Analysis";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        this.lastDataSet = ds;
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        double kappa = calculateKappa(lastDataSet);
        DataSet resultDataSet = new DataSet();
        resultDataSet.setHeader(new String[]{"Kappa"});
        resultDataSet.setData(new String[][]{{String.valueOf(kappa)}});

        if (clear) {
            this.lastDataSet = null;
        }

        return resultDataSet;
    }
    private double calculateKappa(DataSet dataSet) {
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

        int total = TP + TN + FP + FN;
        double p0 = (double) (TP + TN) / total;
        double pe = ((TP + FP) * (TP + FN) + (FN + TN) * (FP + TN)) / (double) (total * total);
        return (p0 - pe) / (1 - pe);
    }


}
