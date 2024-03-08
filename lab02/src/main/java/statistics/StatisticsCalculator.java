package statistics;

import model.DataModel;
import java.util.List;
import java.util.Map;

public interface StatisticsCalculator {
    Map<String, Double> calculate(List<DataModel> dataModels);
}