package com.example.gymexercises;

import android.widget.EditText;
import android.widget.RadioGroup;

public class wrapper_form {

    public final EditText name_ex;
    public final EditText series;
    public final EditText reps;
    public final RadioGroup radioDays;
    public final EditText notes;

    public wrapper_form(EditText name_ex, EditText series, EditText reps, RadioGroup radioDays, EditText notes) {
        this.name_ex = name_ex;
        this.series = series;
        this.reps = reps;
        this.radioDays = radioDays;
        this.notes = notes;
    }

}
