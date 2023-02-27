package com.example.gymexercises;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

public class AddExercises extends AppCompatActivity implements View.OnClickListener {


    public ArrayList<Exercise> exercies;
    public LinearLayout list_excercises;

    private DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        exercies = new ArrayList<>();
        this.list_excercises = findViewById(R.id.list_exercises);

        this.dbHandler = new DBHandler(this);

        Button addExercise = findViewById(R.id.add_exercise);
        addExercise.setOnClickListener(this);

        Button save_workout = findViewById(R.id.save_workout);
        save_workout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.add_exercise) {
            exercies.add(add_form(this.list_excercises));
        }
        if (id == R.id.save_workout) {
            this.save_workout();
        }
    }

    public void save_workout() {

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
    }

    public Exercise add_form(LinearLayout parent) {
        // definizione del LinearLayout principale
        LinearLayout form = new LinearLayout(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        form.setPadding(dpToPx(5), dpToPx(5), dpToPx(5), dpToPx(5));
        params.setMargins(0, dpToPx(10), 0, 0);
        form.setLayoutParams(params);
        form.setBackgroundResource(R.drawable.form_background);
        form.setOrientation(LinearLayout.VERTICAL);

        EditText name_ex = new EditText(this);
        name_ex.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(40)));
        name_ex.setHint("Name of Exercise");
        name_ex.setText("A");
        form.addView(name_ex);

        LinearLayout ll_SeriesReps = new LinearLayout(this);
        ll_SeriesReps.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        ll_SeriesReps.setOrientation(LinearLayout.HORIZONTAL);

        // definizione dell'EditText per le serie
        EditText series = new EditText(this);
        params = new LinearLayout.LayoutParams(0, dpToPx(40), 1f);
        series.setLayoutParams(params);
        series.setInputType(+InputType.TYPE_CLASS_NUMBER);
        series.setHint("Series");
        series.setText("2");

        EditText reps = new EditText(this);
        reps.setLayoutParams(new LinearLayout.LayoutParams(0, dpToPx(40), 2f));
        reps.setHint("Reps");
        reps.setText("C");

        ll_SeriesReps.addView(series);
        ll_SeriesReps.addView(reps);
        form.addView(ll_SeriesReps);

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
        notes.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(40)));
        notes.setHint("Notes");
        notes.setText("D");
        form.addView(notes);

        parent.addView(form);
        return new Exercise(name_ex, series, reps, radioDays, notes);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}