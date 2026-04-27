package com.example.claimprocessing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ClaimProcessingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClaimProcessingApplication.class, args);
    }
}
