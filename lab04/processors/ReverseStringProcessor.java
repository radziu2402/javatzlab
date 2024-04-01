import processing.Processor;
import processing.Status;
import processing.StatusListener;

public class ReverseStringProcessor implements Processor {
    private volatile String result;

    @Override
    public boolean submitTask(String task, StatusListener sl) {
        new Thread(() -> {
            try {
                StringBuilder partialResult = new StringBuilder(task);
                for (int progress = 10; progress <= 100; progress += 10) {
                    Thread.sleep(700);
                    sl.statusChanged(new Status(3, progress));
                }
                this.result = partialResult.reverse().toString();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                this.result = "Error: Task interrupted";
            }
        }).start();

        return true;
    }

    @Override
    public String getInfo() {
        return "odwracanie ciagow znakow";
    }

    @Override
    public String getResult() {
        return result;
    }
}
