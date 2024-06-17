package pl.pwr.gui;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import pl.pwr.simulation.CellularMatrix;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public class Drawer {
    private final CellularMatrix grid;
    private Canvas canvas;
    private volatile boolean running = true;
    private Thread drawThread;

    public Drawer(CellularMatrix grid, Canvas canvas) {
        this.grid = grid;
        this.canvas = canvas;
    }

    public void startDrawing(Consumer<CellularMatrix> evolveFunction, BooleanSupplier isColoring) {
        if (drawThread != null && drawThread.isAlive()) {
            return;
        }
        running = true;
        drawThread = new Thread(() -> {
            while (running) {
                if (canvas == null) {
                    continue;
                }
                var ctx = canvas.getGraphicsContext2D();
                ctx.setFill(Color.GREENYELLOW);
                ctx.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                double cellWidth = canvas.getWidth() / grid.getWidth();
                double cellHeight = canvas.getHeight() / grid.getHeight();
                for (int x = 0; x < grid.getWidth(); x++) {
                    for (int y = 0; y < grid.getHeight(); y++) {
                        if (grid.getMap()[x][y]) {
                            ctx.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                        }
                    }
                }
                try {
                    if (!isColoring.getAsBoolean()) {
                        evolveFunction.accept(grid);
                    }
                    Thread.sleep(1000 / 15);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception ignored) {
                }
            }
        });
        drawThread.start();
    }

    public void stopDrawing() {
        running = false;
        if (drawThread != null) {
            drawThread.interrupt();
        }
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }
}
