package model;

import descriptors.DataModelDescriptor;

public class CSVDataModelDescriptor implements DataModelDescriptor {
    @Override
    public String[] getColumnNames() {
        return new String[]{"Time", "Pressure [hPa]", "Temperature [°C]", "Humidity [%]"};
    }
}
