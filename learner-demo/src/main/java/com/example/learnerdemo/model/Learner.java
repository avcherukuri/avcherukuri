package com.example.learnerdemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Learner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String track;

    public Learner() {
    }

    public Learner(String name, String track) {
        this.name = name;
        this.track = track;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTrack() {
        return track;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrack(String track) {
        this.track = track;
    }
}
