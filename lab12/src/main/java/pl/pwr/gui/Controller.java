package pl.pwr.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import pl.pwr.simulation.CellularMatrix;

import java.io.File;

public class Controller {
    @FXML
    public Canvas canvas;
    public Button unloadButton;
    public Button loadButton;

    private boolean coloring;

    private final CellularMatrix grid = new CellularMatrix(100, 100);
    private Drawer drawer;
    private final ScriptManager scriptManager = new ScriptManager();

    @FXML
    public void initialize() {
        drawer = new Drawer(grid, canvas);
        drawer.startDrawing(scriptManager::invokeEvolveFunction, this::isColoring);
    }

    private boolean isColoring() {
        return coloring;
    }

    public void colour(MouseEvent event) {
        try {
            double mouseX = event.getSceneX();
            double mouseY = event.getSceneY();
            int x = (int) ((mouseX / canvas.getWidth()) * grid.getWidth());
            int y = (int) ((mouseY / canvas.getHeight()) * grid.getHeight());
            grid.activateCell(x, y);
        } catch (Exception ignored) {
        }
    }

    public void mousePressed() {
        coloring = true;
    }

    public void mouseReleased() {
        coloring = false;
    }

    public void loadJavascript() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Wybierz plik");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Pliki javascript", "*.js")
            );

            File selectedFile = fileChooser.showOpenDialog(canvas.getParent().getScene().getWindow());
            if (selectedFile != null) {
                scriptManager.loadScript(selectedFile);
                System.out.println("Skrypt został załadowany.");
                drawer.startDrawing(scriptManager::invokeEvolveFunction, this::isColoring);
            } else {
                System.out.println("Nie wybrano pliku. Załadowanie skryptu anulowane.");
            }
        } catch (Exception e) {
            System.out.println("Wystąpił błąd podczas ładowania skryptu");
        }
    }

    public void unloadJavascript() {
        drawer.stopDrawing();
        scriptManager.unloadScript();
        grid.reset();
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        System.out.println("Skrypt został wyładowany.");
    }
}
