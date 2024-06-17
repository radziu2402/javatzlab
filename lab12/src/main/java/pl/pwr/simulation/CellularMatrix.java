package pl.pwr.simulation;

public class CellularMatrix {
    private final int width;
    private final int height;
    private final boolean[][] cells;

    public CellularMatrix(int width, int height) {
        this.width = width;
        this.height = height;
        this.cells = new boolean[width][height];
        initializeGrid();
    }

    private void initializeGrid() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = false;
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[][] getMap() {
        return cells;
    }

    public void deactivateCell(int x, int y) throws IllegalArgumentException {
        validateCoordinates(x, y);
        cells[x][y] = false;
    }

    public void activateCell(int x, int y) throws IllegalArgumentException {
        validateCoordinates(x, y);
        cells[x][y] = true;
    }

    public void reset() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells[x][y] = false;
            }
        }
    }

    private void validateCoordinates(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Coordinates out of bounds: (" + x + ", " + y + ")");
        }
    }
}
