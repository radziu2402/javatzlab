package external;

import processing.Processor;
import processing.Status;
import processing.StatusListener;

public class UpperCaseProcessor implements Processor {
    private volatile String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        new Thread(() -> {
            try {
                for (int progress = 0; progress < 100; progress += 10) {
                    Thread.sleep(1000);
                    sl.statusChanged(new Status(2, progress));
                }
                this.result = task.toUpperCase();

                Thread.sleep(100);
                sl.statusChanged(new Status(2, 100));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                this.result = "Error: Task interrupted";
            }
        }).start();

        return true;
    }

    @Override
    public String getInfo() {
        return "zamiana malych liter na duze";
    }

    @Override
    public String getResult() {
        return result;
    }
}
