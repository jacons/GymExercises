package com.example.gymexercises;

import java.util.ArrayList;

public class Workout<T> {

    public ArrayList<T> exercises;
    public final String name;
    public String date;

    public Workout(String name) {
        this.name = name;
        this.exercises = new ArrayList<>();
    }

    public Workout(String name, String date) {
        this.name = name;
        this.date = date;
        this.exercises = new ArrayList<>();
    }

    public void add(T e) {
        this.exercises.add(e);
    }
}
