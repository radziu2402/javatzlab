package processing;

public class SumProcessor implements Processor {
    private String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        try {
            String[] parts = task.split("\\+");
            int sum = 0;
            for (String part : parts) {
                sum += Integer.parseInt(part.trim());
            }
            this.result = Integer.toString(sum);
            // Zakładamy, że zadanie jest od razu zakończone
            sl.statusChanged(new Status(1, 100));
            return true;
        } catch (NumberFormatException e) {
            this.result = "Error: Incorrect input";
            return false;
        }
    }

    @Override
    public String getInfo() {
        return "sumowanie";
    }

    @Override
    public String getResult() {
        return result;
    }
}
