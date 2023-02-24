package com.example.gymexercises;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class AddExercises extends AppCompatActivity {
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercises);

        LinearLayout linearLayout = findViewById(R.id.list_exercises);

        // Creazione di un oggetto ConstraintLayout
        ConstraintLayout constraintLayout = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.setMargins(dpToPx(15), dpToPx(20), dpToPx(20), dpToPx(15));
        constraintLayout.setLayoutParams(layoutParams);
        constraintLayout.setBackgroundResource(R.drawable.form_background);
        constraintLayout.setPadding(dpToPx(2), dpToPx(2), dpToPx(2), dpToPx(2));

        EditText name_et = new EditText(this);
        name_et.setId(View.generateViewId());
        name_et.setHint("Name of Exercise");
        name_et.setLayoutParams(new ConstraintLayout.LayoutParams(dpToPx(310), dpToPx(40)));
        constraintLayout.addView(name_et);

        EditText serie_et = new EditText(this);
        serie_et.setId(View.generateViewId());
        serie_et.setHint("Series");
        serie_et.setInputType(InputType.TYPE_CLASS_NUMBER);
        serie_et.setLayoutParams(new ConstraintLayout.LayoutParams(dpToPx(100), dpToPx(40)));
        constraintLayout.addView(serie_et);

        EditText reps_et = new EditText(this);
        reps_et.setId(View.generateViewId());
        reps_et.setHint("Reps");
        reps_et.setLayoutParams(new ConstraintLayout.LayoutParams(dpToPx(157), dpToPx(40)));
        constraintLayout.addView(reps_et);

        // Creazione del RadioGroup per i giorni della settimana
        RadioGroup dayOfWeekRadioGroup = new RadioGroup(this);
        dayOfWeekRadioGroup.setId(View.generateViewId());
        dayOfWeekRadioGroup.setOrientation(RadioGroup.HORIZONTAL);
        RadioGroup.LayoutParams radioGroupLayoutParams = new RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT, dpToPx(25));
        dayOfWeekRadioGroup.setLayoutParams(radioGroupLayoutParams);
        constraintLayout.addView(dayOfWeekRadioGroup);

        String[] days = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};

        for (String d : days) {
            RadioButton mondayRadioButton = new RadioButton(this);
            mondayRadioButton.setId(View.generateViewId());
            mondayRadioButton.setText(d);
            dayOfWeekRadioGroup.addView(mondayRadioButton);
        }

        // Creazione dell'oggetto EditText per le note
        EditText notesEditText = new EditText(this);
        notesEditText.setId(View.generateViewId());
        notesEditText.setHint("Notes");
        notesEditText.setLayoutParams(new ConstraintLayout.LayoutParams(dpToPx(310), dpToPx(40)));
        constraintLayout.addView(notesEditText);

        // Impostazione dei vincoli di posizionamento degli oggetti
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);

        constraintSet.connect(name_et.getId(), ConstraintSet.END,
                constraintLayout.getId(), ConstraintSet.END);
        constraintSet.connect(name_et.getId(), ConstraintSet.START,
                constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(name_et.getId(), ConstraintSet.TOP,
                constraintLayout.getId(), ConstraintSet.TOP);

        constraintSet.connect(serie_et.getId(), ConstraintSet.END,
                reps_et.getId(), ConstraintSet.START);
        constraintSet.connect(serie_et.getId(), ConstraintSet.START,
                constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(serie_et.getId(), ConstraintSet.TOP,
                name_et.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(reps_et.getId(), ConstraintSet.END,
                constraintLayout.getId(), ConstraintSet.END);
        constraintSet.connect(reps_et.getId(), ConstraintSet.START,
                serie_et.getId(), ConstraintSet.END);
        constraintSet.connect(reps_et.getId(), ConstraintSet.TOP,
                name_et.getId(), ConstraintSet.BOTTOM);


        constraintSet.connect(dayOfWeekRadioGroup.getId(), ConstraintSet.END,
                constraintLayout.getId(), ConstraintSet.END);
        constraintSet.connect(dayOfWeekRadioGroup.getId(), ConstraintSet.START,
                constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(dayOfWeekRadioGroup.getId(), ConstraintSet.TOP,
                serie_et.getId(), ConstraintSet.BOTTOM);

        constraintSet.connect(notesEditText.getId(), ConstraintSet.END,
                constraintLayout.getId(), ConstraintSet.END);
        constraintSet.connect(notesEditText.getId(), ConstraintSet.START,
                constraintLayout.getId(), ConstraintSet.START);
        constraintSet.connect(notesEditText.getId(), ConstraintSet.TOP,
                dayOfWeekRadioGroup.getId(), ConstraintSet.BOTTOM);

        constraintSet.applyTo(constraintLayout);
        linearLayout.addView(constraintLayout);

    }

}