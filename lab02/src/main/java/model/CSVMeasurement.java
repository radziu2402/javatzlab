package model;

public class CSVMeasurement implements DataModel {
    private final String time;
    private final double pressure;
    private final double temperature;
    private final double humidity;

    public CSVMeasurement(String time, double pressure, double temperature, double humidity) {
        this.time = time;
        this.pressure = pressure;
        this.temperature = temperature;
        this.humidity = humidity;
    }

    @Override
    public String[] toStringArray() {
        return new String[]{
                time,
                String.valueOf(pressure),
                String.valueOf(temperature),
                String.valueOf(humidity)
        };
    }

    public double getPressure() {
        return pressure;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }
}
