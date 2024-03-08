package data;

import factories.DataModelFactory;
import model.DataModel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVDataLoader implements DataLoader {
    @Override
    public List<DataModel> load(String filePath, DataModelFactory factory) {
        List<DataModel> dataModels = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("#")) {
                    continue;
                }
                DataModel dataModel = factory.create(line);
                dataModels.add(dataModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataModels;
    }
}
