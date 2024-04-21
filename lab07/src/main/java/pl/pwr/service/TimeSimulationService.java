package pl.pwr.service;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class TimeSimulationService {
    private LocalDateTime simulatedTime = LocalDateTime.now();

    public LocalDateTime getSimulatedTime() {
        return simulatedTime;
    }

    public void advanceTimeByDays(long days) {
        simulatedTime = simulatedTime.plusDays(days);
    }
}
