package com.example.learnerdemo.repository;

import com.example.learnerdemo.model.Learner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearnerRepository extends JpaRepository<Learner, Long> {
}
