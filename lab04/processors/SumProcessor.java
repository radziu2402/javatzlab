import processing.Processor;
import processing.Status;
import processing.StatusListener;

public class SumProcessor implements Processor {
    private volatile String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        new Thread(() -> {
            try {
                String[] parts = task.split("\\+");
                int sum = 0;
                int totalParts = parts.length;
                for (int i = 0; i < totalParts; i++) {
                    sum += Integer.parseInt(parts[i].trim());
                    int progress = (int) (((i + 1) / (float) totalParts) * 100);
                    Thread.sleep(700);
                    sl.statusChanged(new Status(1, progress));
                }
                this.result = Integer.toString(sum);
            } catch (NumberFormatException e) {
                this.result = "Error: Incorrect input";
                sl.statusChanged(new Status(1, 0));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                this.result = "Error: Task interrupted";
            }
        }).start();

        return true;
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
