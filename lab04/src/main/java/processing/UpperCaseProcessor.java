package processing;

public class UpperCaseProcessor implements Processor {
    private String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        this.result = task.toUpperCase();
        // Zakładamy, że zadanie jest od razu zakończone
        sl.statusChanged(new Status(2, 100));
        return true;
    }

    @Override
    public String getInfo() {
        return "zamiana małych liter na duże";
    }

    @Override
    public String getResult() {
        return result;
    }
}
