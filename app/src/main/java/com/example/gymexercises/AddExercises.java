package com.example.gymexercises;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddExercises extends AppCompatActivity implements View.OnClickListener {


    private ArrayList<Exercise> exercies;
    private LinearLayout list_excercises;
    private EditText mcycles;
    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        this.exercies = new ArrayList<>();
        this.list_excercises = findViewById(R.id.list_exercises);

        this.dbHandler = new DBHandler(this);

        Button addExercise = findViewById(R.id.add_exercise);
        addExercise.setOnClickListener(this);

        Button save_workout = findViewById(R.id.save_workout);
        save_workout.setOnClickListener(this);

        this.mcycles = (EditText) findViewById(R.id.microcycles);
        //this.exercies.add(add_form(this.list_excercises));
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.add_exercise) {
            this.exercies.add(add_form(this.list_excercises));
        }
        if (id == R.id.save_workout) {
            this.save_workout();
        }
    }

    public void save_workout() {
        /*
        String workout_name, name, series, reps, day, notes;

        // Retrieve a workout_name
        workout_name = ((EditText) findViewById(R.id.workout_name)).getText().toString().trim();

        // Check first of all if there is another workout with the same name
        if (dbHandler.checkWorkoutExist(workout_name)) {
            Toast.makeText(getApplicationContext(), workout_name + " already exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        // workout object used to db updates
        Workout workout = new Workout(workout_name);

        // we want to avoid dupliace name's exercises, so we check before to save on db
        HashSet<String> duplicate = new HashSet<>();
        // We retrieve the fields and check if they are null
        for (Exercise w : this.exercies) {

            name = w.getName_ex();
            series = w.getSeries();
            reps = w.getReps();
            day = String.valueOf(w.getDay());
            notes = w.getNotes();

            // All field must contains a value, and duplicate are ignored
            if (!name.equals("") & !series.equals("") & !reps.equals("") & !day.equals("")
                    & !notes.equals("") & !duplicate.contains(name)) {

                workout.add(w);
                duplicate.add(name);
            }
        }

        dbHandler.addNewWorkout(workout);
         */
    }

    public Exercise add_form(LinearLayout parent) {

        String tmp = this.mcycles.getText().toString();
        int n_cycles = tmp.equals("") ? 1 : Integer.parseInt(tmp);

        this.mcycles.setEnabled(false);

        // definizione del LinearLayout principale
        LinearLayout form = new LinearLayout(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        form.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        params.setMargins(0, dpToPx(10), 0, 0);
        form.setLayoutParams(params);
        form.setBackgroundResource(R.drawable.form_background);
        form.setOrientation(LinearLayout.VERTICAL);

        EditText name_ex = new EditText(this);
        name_ex.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(36)));
        name_ex.setHint("Name of Exercise");
        name_ex.setTextSize(15);
        name_ex.setText("A");
        form.addView(name_ex);

        TextView cycles = new TextView(this);
        cycles.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        cycles.setText("Microcycles");
        cycles.setTextColor(Color.BLACK);
        form.addView(cycles);

        ArrayList<EditText[]> list_cycles = new ArrayList<>(n_cycles);
        for (int i = 0; i < n_cycles; i++) {

            LinearLayout ll_SeriesReps = new LinearLayout(this);
            ll_SeriesReps.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            ll_SeriesReps.setOrientation(LinearLayout.HORIZONTAL);


            TextView num = new TextView(this);
            num.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
            num.setText(String.valueOf(i + 1));
            num.setTextColor(Color.BLACK);
            num.setGravity(Gravity.CENTER);

            // definizione dell'EditText per le serie
            EditText series = new EditText(this);
            params = new LinearLayout.LayoutParams(0, dpToPx(36), 2f);
            series.setLayoutParams(params);
            series.setInputType(+InputType.TYPE_CLASS_NUMBER);
            series.setHint("Series");
            series.setTextSize(15);
            series.setText("2");
            series.setGravity(Gravity.CENTER);

            EditText reps = new EditText(this);
            reps.setLayoutParams(new LinearLayout.LayoutParams(0, dpToPx(36), 4f));
            reps.setHint("Reps");
            reps.setTextSize(15);
            reps.setText("C");

            ll_SeriesReps.addView(num);
            ll_SeriesReps.addView(series);
            ll_SeriesReps.addView(reps);
            ll_SeriesReps.getId();
            form.addView(ll_SeriesReps);

            list_cycles.add(new EditText[]{series, reps});
        }
        RadioGroup radioDays = new RadioGroup(this);
        radioDays.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(40)));
        radioDays.setOrientation(LinearLayout.HORIZONTAL);
        radioDays.setGravity(Gravity.CENTER);

        int idx = 0;
        for (String d : new String[]{"Mo", "Tu", "We", "Th", "Fr", "Sa"}) {
            RadioButton radio = new RadioButton(this);
            if (idx == 0) radio.toggle();
            radio.setId(idx++);
            radio.setText(d);
            radio.setTextSize(13);
            radioDays.addView(radio);

        }
        form.addView(radioDays);

        EditText notes = new EditText(this);
        notes.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(36)));
        notes.setHint("Notes");
        notes.setText("D");
        notes.setTextSize(15);
        form.addView(notes);

        parent.addView(form);
        return new Exercise(name_ex, list_cycles, radioDays, notes);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}