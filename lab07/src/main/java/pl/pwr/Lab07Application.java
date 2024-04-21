package pl.pwr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Lab07Application {

    public static void main(String[] args) {
        SpringApplication.run(Lab07Application.class, args);
    }

}
