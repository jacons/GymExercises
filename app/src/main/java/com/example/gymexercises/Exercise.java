package com.example.gymexercises;


import android.widget.EditText;
import android.widget.RadioGroup;


public class Exercise {

    private final EditText name_ex;
    private final EditText series;
    private final EditText reps;
    private final RadioGroup radioDays;
    private final EditText notes;

    public Exercise(EditText name_ex, EditText series, EditText reps, RadioGroup radioDays, EditText notes) {
        this.name_ex = name_ex;
        this.series = series;
        this.reps = reps;
        this.radioDays = radioDays;
        this.notes = notes;
    }

    public String getName_ex() {
        return name_ex.getText().toString().trim();
    }

    public String getSeries() {
        return series.getText().toString().trim();
    }

    public String getReps() {
        return reps.getText().toString().trim();
    }

    public int getDay() {
        return radioDays.getCheckedRadioButtonId();
    }

    public String getNotes() {
        return notes.getText().toString().trim();
    }

}
