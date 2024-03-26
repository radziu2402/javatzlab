package processing;

public class ReverseStringProcessor implements Processor {
    private String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        this.result = new StringBuilder(task).reverse().toString();
        // Zakładamy, że zadanie jest od razu zakończone
        sl.statusChanged(new Status(3, 100));
        return true;
    }

    @Override
    public String getInfo() {
        return "odwracanie ciągów znaków";
    }

    @Override
    public String getResult() {
        return result;
    }
}
