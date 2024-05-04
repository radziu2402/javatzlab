package pl.pwr;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileConverter {

    public Double[][] csvToMatrix(String matrixFile) {
        List<Double[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(matrixFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.isEmpty()) continue;
                String[] values = line.split(",");
                Double[] row = new Double[values.length];
                for (int i = 0; i < values.length; i++) {
                    row[i] = Double.parseDouble(values[i].trim());
                }
                rows.add(row);
            }
        } catch (IOException e) {
            System.out.println("Wystąpił błąd przy odczycie pliku: " + e.getMessage());
            return null;
        } catch (NumberFormatException e) {
            System.out.println("Nie można przekonwertować tekstu na liczbę: " + e.getMessage());
            return null;
        }
        Double[][] matrix = new Double[rows.size()][];
        matrix = rows.toArray(matrix);
        return matrix;
    }
}
