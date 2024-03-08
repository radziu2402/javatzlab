package factories;

import model.DataModel;

public interface DataModelFactory {
    DataModel create(String dataLine);
}
