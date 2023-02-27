package com.example.gymexercises;

import java.util.ArrayList;
import java.util.List;

public class Workout {

    public List<Exercise> exercies;
    public final String name;

    public Workout(String name) {
        this.name = name;
        this.exercies = new ArrayList<>();
    }

    public void add(Exercise e) {
        this.exercies.add(e);
    }
}
