package com.example.gymexercises;

public class Excercise {

    public enum Days {Mo, Tu, We, Th, Fr, Sa}

    public final String name;
    public final Integer series;
    public final String reps;
    public final String note;
    public final int day;

    public Excercise(String name, Integer series, String reps, String Note, int day) {

        this.name = name;
        this.series = series;
        this.reps = reps;
        this.note = Note;
        this.day = day;
    }

}
