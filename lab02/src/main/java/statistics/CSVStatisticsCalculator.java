package statistics;

import model.CSVMeasurement;
import model.DataModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVStatisticsCalculator implements StatisticsCalculator {
    @Override
    public Map<String, Double> calculate(List<DataModel> dataModels) {
        double sumPressure = 0;
        double sumTemperature = 0;
        double sumHumidity = 0;
        int count = 0;

        for (DataModel model : dataModels) {
            if (model instanceof CSVMeasurement measurement) {
                sumPressure += measurement.getPressure();
                sumTemperature += measurement.getTemperature();
                sumHumidity += measurement.getHumidity();
                count++;
            }
        }

        Map<String, Double> statistics = new HashMap<>();
        if (count > 0) {
            statistics.put("AVG Pressure", sumPressure / count);
            statistics.put("AVG Temperature", sumTemperature / count);
            statistics.put("AVG Humidity", sumHumidity / count);
        }

        return statistics;
    }
}
