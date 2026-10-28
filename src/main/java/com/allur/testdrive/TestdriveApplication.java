package com.allur.testdrive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TestdriveApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestdriveApplication.class, args);
    }
}
