package com.example.learnerdemo.controller;

import com.example.learnerdemo.model.Learner;
import com.example.learnerdemo.repository.LearnerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/learners")
@CrossOrigin(origins = "http://localhost:4200")
public class LearnerController {

    private final LearnerRepository learnerRepository;

    public LearnerController(LearnerRepository learnerRepository) {
        this.learnerRepository = learnerRepository;
    }

    @GetMapping
    public List<Learner> getAllLearners() {
        return learnerRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Learner createLearner(@RequestBody Learner learner) {
        return learnerRepository.save(learner);
    }
}
