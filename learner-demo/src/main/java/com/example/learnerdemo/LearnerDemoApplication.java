package com.example.learnerdemo;

import com.example.learnerdemo.model.Learner;
import com.example.learnerdemo.repository.LearnerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LearnerDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(LearnerDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner seedLearners(LearnerRepository learnerRepository) {
        return args -> {
            if (learnerRepository.count() == 0) {
                learnerRepository.save(new Learner("Ava", "Spring Boot Basics"));
                learnerRepository.save(new Learner("Noah", "Angular Fundamentals"));
            }
        };
    }
}
