package com.example.gymexercises;


import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.ArrayList;


public class Exercise {

    private final EditText name, notes, time;
    private final ArrayList<EditText[]> m_cycles;
    private final RadioGroup radioDays;
    private ArrayList<String[]> cache = null;

    public Exercise(EditText name, ArrayList<EditText[]> m_cycles, RadioGroup radioDays, EditText time, EditText notes) {
        this.name = name;
        this.m_cycles = m_cycles;
        this.radioDays = radioDays;
        this.notes = notes;
        this.time = time;
    }

    public ArrayList<String[]> getMCycles() {

        if (this.cache == null) {

            ArrayList<String[]> result = new ArrayList<>(this.m_cycles.size());
            for (EditText[] cycle : this.m_cycles) {
                result.add(new String[]{
                        cycle[0].getText().toString().trim(),
                        cycle[1].getText().toString().trim()
                });
            }
            this.cache = result;
        }

        return this.cache;
    }

    public String getName() {
        return name.getText().toString().trim();
    }

    public String getNotes() {
        return notes.getText().toString().trim();
    }

    public int getDay() {
        return radioDays.getCheckedRadioButtonId();
    }

    public String getTime() {
        return time.getText().toString().trim();
    }


}
