package com.example.gymexercises;

import java.util.ArrayList;
import java.util.List;

public class Workout {

    public List<Excercise> exercies ;
    public final String name;
    public final String date;

    public Workout(String name, String date) {
        this.name = name;
        this.date = date;
        this.exercies = new ArrayList<>();
    }
    public void add(Excercise e) {
        this.exercies.add(e);
    }
}
