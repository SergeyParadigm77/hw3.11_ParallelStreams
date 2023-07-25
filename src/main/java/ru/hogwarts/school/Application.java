package ru.hogwarts.school;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
   private static Logger logger = LoggerFactory.getLogger(SpringApplication.class);

    public static void main(String[] args) {
        logger.info("Trying to start application");
        SpringApplication.run(Application.class, args);
        logger.info("Application has been successfully started");
    }

}
