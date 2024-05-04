package pl.pwr;

import java.util.Random;
import java.util.Scanner;

public class Main {

    static {
        System.loadLibrary("native");
    }

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            FileConverter fileConverter = new FileConverter();
            ConvolutionCounter convolutionCounter = new ConvolutionCounter();

            System.out.println("Obliczanie dyskretnej dwuwymiarowej funkcji splotu");
            System.out.println("Wybierz opcję:\n1. Wczytaj macierze z plików CSV\n2. Generuj losowe macierze");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline left-over

            Double[][] inputMatrix;
            Double[][] filterMatrix;

            if (choice == 1) {
                System.out.println("Podaj plik csv z wejściową macierzą:");
                String inputMatrixFile = scanner.nextLine();
                inputMatrix = fileConverter.csvToMatrix(inputMatrixFile);
                if (inputMatrix == null) {
                    System.out.println("Nie udało się załadować macierzy wejściowej.");
                    return;
                }

                System.out.println("Podaj plik csv z macierzą z filtrem:");
                String filterMatrixFile = scanner.nextLine();
                filterMatrix = fileConverter.csvToMatrix(filterMatrixFile);
                if (filterMatrix == null) {
                    System.out.println("Nie udało się załadować macierzy filtra.");
                    return;
                }
            } else if (choice == 2) {
                System.out.println("Podaj rozmiar macierzy wejściowej (wiersze kolumny):");
                int rowsInput = scanner.nextInt();
                int colsInput = scanner.nextInt();
                System.out.println("Podaj rozmiar kernela (wiersze kolumny):");
                int rowsKernel = scanner.nextInt();
                int colsKernel = scanner.nextInt();

                inputMatrix = generateRandomMatrix(rowsInput, colsInput);
                filterMatrix = generateRandomMatrix(rowsKernel, colsKernel);
            } else {
                System.out.println("Nieprawidłowy wybór.");
                return;
            }

            runConvolution(inputMatrix, filterMatrix, convolutionCounter);
        } catch (Exception e) {
            System.out.println("Wystąpił błąd: " + e.getMessage());
        }
    }

    public static Double[][] generateRandomMatrix(int rows, int cols) {
        Double[][] matrix = new Double[rows][cols];
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = random.nextDouble() * 100; // Random double values
            }
        }
        return matrix;
    }

    public static void runConvolution(Double[][] inputMatrix, Double[][] filterMatrix, ConvolutionCounter convolutionCounter) {
        long startTimeJava = System.nanoTime();
        Double[][] resultJava = convolutionCounter.countInJava(inputMatrix, filterMatrix);
        long endTimeJava = System.nanoTime();
        long durationJava = endTimeJava - startTimeJava;

        System.out.println("Wynik splotu w Java:");
        printMatrix(resultJava);


        long startTimeCpp = System.nanoTime();
        Double[][] resultCpp = convolutionCounter.countInCpp(inputMatrix, filterMatrix);
        long endTimeCpp = System.nanoTime();
        long durationCpp = endTimeCpp - startTimeCpp;

        System.out.println("Wynik splotu w C++:");
        printMatrix(resultCpp);

        System.out.println("Czas wykonania w Java: " + durationJava + " nanosekund");
        System.out.println("Czas wykonania w C++: " + durationCpp + " nanosekund");
    }

    public static void printMatrix(Double[][] matrix) {
        for (Double[] row : matrix) {
            for (Double val : row) {
                System.out.printf("%9.2f", val);
            }
            System.out.println();
        }
    }
}
