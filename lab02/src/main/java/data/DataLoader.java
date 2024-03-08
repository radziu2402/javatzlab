package data;

import factories.DataModelFactory;
import model.DataModel;

import java.util.List;

public interface DataLoader {
    List<DataModel> load(String filePath, DataModelFactory factory);
}