package pl.pwr.gui;

import pl.pwr.simulation.CellularMatrix;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ScriptManager {
    private Invocable evolveFunction = null;

    public void loadScript(File scriptFile) {
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            scriptEngine.eval(new FileReader(scriptFile));
        } catch (ScriptException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        evolveFunction = (Invocable) scriptEngine;
    }

    public void invokeEvolveFunction(CellularMatrix grid) {
        if (evolveFunction != null) {
            try {
                evolveFunction.invokeFunction("processGrid", grid);
            } catch (ScriptException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void unloadScript() {
        evolveFunction = null;
    }
}
