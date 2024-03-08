package factories;

import model.CSVMeasurement;
import model.DataModel;

public class CSVDataModelFactory implements DataModelFactory {
    @Override
    public DataModel create(String dataLine) {
        String[] parts = dataLine.split(";");

        if (parts.length < 4) {
            throw new IllegalArgumentException("Nieprawidłowa linia danych: " + dataLine);
        }

        try {
            String time = parts[0].trim();
            double pressure = Double.parseDouble(parts[1].trim().replace(",", "."));
            double temperature = Double.parseDouble(parts[2].trim().replace(",", "."));
            double humidity = Double.parseDouble(parts[3].trim().replace(",", "."));

            return new CSVMeasurement(time, pressure, temperature, humidity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Błąd podczas parsowania danych: " + dataLine, e);
        }
    }
}
