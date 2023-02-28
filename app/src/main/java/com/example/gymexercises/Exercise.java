package com.example.gymexercises;


import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class Exercise {

    private final EditText name_ex;
    private final ArrayList<EditText[]> list_mcycles;
    private final EditText time;
    private final RadioGroup radioDays;
    private final EditText notes;
    private ArrayList<String[]> cache = null;

    public Exercise(EditText name_ex, ArrayList<EditText[]> list_mcycles, RadioGroup radioDays, EditText time, EditText notes) {
        this.name_ex = name_ex;
        this.list_mcycles = list_mcycles;
        this.radioDays = radioDays;
        this.notes = notes;
        this.time = time;
    }

    public String getName_ex() {
        return name_ex.getText().toString().trim();
    }

    public ArrayList<String[]> getMCycles() {

        if (this.cache == null) {

            ArrayList<String[]> result = new ArrayList<>(this.list_mcycles.size());
            for (EditText[] cycle : this.list_mcycles) {
                result.add(new String[]{
                        cycle[0].getText().toString().trim(),
                        cycle[1].getText().toString().trim()
                });
            }
            this.cache = result;
        }

        return this.cache;
    }

    public int getDay() {
        return radioDays.getCheckedRadioButtonId();
    }

    public String getTime() {
        return time.getText().toString().trim();
    }

    public String getNotes() {
        return notes.getText().toString().trim();
    }


}
