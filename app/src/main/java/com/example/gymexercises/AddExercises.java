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
import android.widget.Toast;

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

        this.list_excercises = findViewById(R.id.list_exercises);
        this.mcycles = findViewById(R.id.microcycles);

        Button addExercise = findViewById(R.id.add_exercise);
        addExercise.setOnClickListener(this);

        Button save_workout = findViewById(R.id.save_workout);
        save_workout.setOnClickListener(this);


        this.exercies = new ArrayList<>();
        this.dbHandler = new DBHandler(this);

        this.exercies.add(add_form(this.list_excercises));

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

        String workout_name, name, day, notes;

        // Retrieve a workout_name
        workout_name = ((EditText) findViewById(R.id.workout_name)).getText().toString().trim();
        // Check first of all if there is another workout with the same name
        if (dbHandler.checkWorkoutExist(workout_name)) {
            Toast.makeText(getApplicationContext(), workout_name + " already exist!", Toast.LENGTH_SHORT).show();
            return;
        }

        // workout object used to db updates
        Workout workout = new Workout(workout_name);

        // We retrieve the fields and check if they are null
        for (Exercise w : this.exercies) {

            name = w.getName_ex();
            day = String.valueOf(w.getDay());
            notes = w.getNotes();

            for (String[] cycle : w.getMCycles()) {
                if (cycle[0].equals("") | cycle[1].equals("")) {
                    Toast.makeText(getApplicationContext(), "You must complete all informations! (Microcycles)", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if (name.equals("") | day.equals("") | notes.equals("")) {
                Toast.makeText(getApplicationContext(), "You must complete all informations! (Excercise)", Toast.LENGTH_SHORT).show();
                return;
            }
            workout.add(w);
        }
        if (workout.exercies.size() < 1) {
            Toast.makeText(getApplicationContext(), "Add at least one exercise", Toast.LENGTH_SHORT).show();
            return;
        }
        dbHandler.addNewWorkout(workout);
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
        form.addView(cycles);

        ArrayList<EditText[]> list_cycles = new ArrayList<>(n_cycles);
        for (int i = 1; i <= n_cycles; i++) {

            LinearLayout ll_SeriesReps = new LinearLayout(this);
            ll_SeriesReps.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
            ll_SeriesReps.setOrientation(LinearLayout.HORIZONTAL);


            TextView num = new TextView(this);
            num.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
            num.setText(String.valueOf(i));
            num.setTextColor(Color.BLACK);
            num.setGravity(Gravity.CENTER);

            // definizione dell'EditText per le serie
            EditText series = new EditText(this);
            params = new LinearLayout.LayoutParams(0, dpToPx(36), 1f);
            series.setLayoutParams(params);
            series.setInputType(+InputType.TYPE_CLASS_NUMBER);
            series.setHint("Series");
            series.setTextSize(15);
            series.setText("2");
            series.setGravity(Gravity.CENTER);

            EditText reps = new EditText(this);
            reps.setLayoutParams(new LinearLayout.LayoutParams(0, dpToPx(36), 1f));
            reps.setHint("Reps");
            reps.setTextSize(15);
            reps.setGravity(Gravity.CENTER);
            reps.setText("C");

            ll_SeriesReps.addView(num);
            ll_SeriesReps.addView(series);
            ll_SeriesReps.addView(reps);
            form.addView(ll_SeriesReps);

            list_cycles.add(new EditText[]{series, reps});
        }

        LinearLayout ll_times = new LinearLayout(this);
        ll_times.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        ll_times.setOrientation(LinearLayout.HORIZONTAL);


        TextView time_tv = new TextView(this);
        time_tv.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
        time_tv.setText("Time:");


        EditText time = new EditText(this);
        time.setLayoutParams(new LinearLayout.LayoutParams(0, dpToPx(36), 1f));
        time.setHint("Times");
        time.setText("1-2''");
        time.setTextSize(15);
        time.setGravity(Gravity.CENTER);


        ll_times.addView(time_tv);
        ll_times.addView(time);

        form.addView(ll_times);

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
        return new Exercise(name_ex, list_cycles, radioDays, notes, time);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

}