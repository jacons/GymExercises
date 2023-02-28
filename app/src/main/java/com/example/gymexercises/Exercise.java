package com.example.gymexercises;


import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class Exercise {

    private final EditText name_ex;
    private final ArrayList<EditText[]> list_mcycles;
    private final RadioGroup radioDays;
    private final EditText notes;

    public Exercise(EditText name_ex, ArrayList<EditText[]> list_mcycles, RadioGroup radioDays, EditText notes) {
        this.name_ex = name_ex;
        this.list_mcycles = list_mcycles;
        this.radioDays = radioDays;
        this.notes = notes;
    }

    public String getName_ex() {
        return name_ex.getText().toString().trim();
    }

    public ArrayList<EditText[]> getMCycles() {
        return this.list_mcycles;
    }

    public int getDay() {
        return radioDays.getCheckedRadioButtonId();
    }

    public String getNotes() {
        return notes.getText().toString().trim();
    }

}
