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

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddExercises extends AppCompatActivity implements View.OnClickListener {


    public ArrayList<wrapper_form> exercies;
    public LinearLayout list_excercises;

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public wrapper_form add_form(LinearLayout parent) {
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

        // definizione dell'EditText per le ripetizioni
        EditText reps = new EditText(this);
        reps.setLayoutParams(new LinearLayout.LayoutParams(0, dpToPx(40), 2f));
        reps.setHint("Reps");

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
            radio.setId(idx++);
            radio.setText(d);
            radio.setTextSize(13);
            radioDays.addView(radio);
        }
        form.addView(radioDays);

        EditText notes = new EditText(this);
        notes.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, dpToPx(40)));
        notes.setHint("Name of Excercise");
        form.addView(notes);

        parent.addView(form);
        return new wrapper_form(name_ex, series, reps, radioDays, notes);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        exercies = new ArrayList<>();
        this.list_excercises = findViewById(R.id.list_exercises);

        Button addExercise = findViewById(R.id.add_exercise);
        addExercise.setOnClickListener(this);

        Button save_workout = findViewById(R.id.save_workout);
        save_workout.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add_exercise:
                exercies.add(add_form(this.list_excercises));
                return;
            case R.id.save_workout:
                this.save_workout();
                return;
        }
    }

    public void save_workout() {
        for (wrapper_form w : this.exercies) {
            System.out.println("Name " + w.name_ex.getText().toString());
            System.out.println("Series " + w.series.getText().toString());
            System.out.println("Reps " + w.reps.getText().toString());
            System.out.println("Day " + w.radioDays.getCheckedRadioButtonId());
            System.out.println("Notes " + w.notes.getText().toString());

        }
    }
}