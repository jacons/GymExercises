package com.example.gymexercises;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private final Context context;
    private final ArrayList<wrapper_exercise> list_exercises;
    private final int m_cycle;

    public Adapter(Context c, ArrayList<wrapper_exercise> list_exercises, int m_cycle) {

        this.context = c;
        this.list_exercises = list_exercises;
        this.m_cycle = m_cycle;
    }

    public static TextView[] add_cycle(Context c, LinearLayout parser) {

        LinearLayout ll_cycle = new LinearLayout(c);
        ll_cycle.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
        ll_cycle.setOrientation(LinearLayout.HORIZONTAL);

        TextView num_week = new TextView(c);
        num_week.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
        num_week.setText(R.string.week);
        num_week.setTextSize(15);
        num_week.setTextColor(Color.BLACK);
        num_week.setGravity(Gravity.CENTER);
        ll_cycle.addView(num_week);

        TextView series_ = new TextView(c);
        series_.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
        series_.setTextSize(15);
        series_.setGravity(Gravity.CENTER);
        series_.setText(R.string.series);
        ll_cycle.addView(series_);

        TextView series_value = new TextView(c);
        series_value.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
        series_.setTextSize(15);
        series_value.setTextColor(Color.BLACK);
        series_value.setGravity(Gravity.CENTER);
        ll_cycle.addView(series_value);

        TextView reps_ = new TextView(c);
        reps_.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
        reps_.setTextSize(15);
        reps_.setGravity(Gravity.CENTER);
        reps_.setText(R.string.reps);
        ll_cycle.addView(reps_);

        TextView reps_value = new TextView(c);
        reps_value.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
        reps_.setTextSize(15);
        reps_value.setTextColor(Color.BLACK);
        reps_value.setGravity(Gravity.CENTER);
        ll_cycle.addView(reps_value);

        EditText load_value = new EditText(c);
        load_value.setLayoutParams(new LinearLayout.LayoutParams(0, WRAP_CONTENT, 1f));
        load_value.setTextSize(12);
        load_value.setTextColor(Color.BLACK);
        load_value.setGravity(Gravity.CENTER);
        ll_cycle.addView(load_value);

        parser.addView(ll_cycle);
        return new TextView[]{series_value, reps_value, load_value};
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_exercise, parent, false);
        return new ViewHolder(v, this.m_cycle);
    }

    @Override
    public int getItemCount() {
        return list_exercises.size();
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {

        wrapper_exercise wrapper = list_exercises.get(position);

        holder.name_ex.setText(wrapper.getName());
        holder.notes.setText(wrapper.getNotes());
        holder.day.setText(wrapper.getDay());
        holder.time.setText(wrapper.getTime());

        int idx = 0;
        for (TextView[] cycle : holder.list_m_cycle) {
            cycle[0].setText(wrapper.getSeries(idx));
            cycle[1].setText(wrapper.getReps(idx));
            cycle[2].setText(wrapper.getLoad(idx));
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_ex, notes, day, time;
        ArrayList<TextView[]> list_m_cycle;

        public ViewHolder(@NonNull View itemView, int m_cycle) {

            super(itemView);
            list_m_cycle = new ArrayList<>(m_cycle);

            name_ex = itemView.findViewById(R.id.card_name_ex);
            notes = itemView.findViewById(R.id.card_notes);
            day = itemView.findViewById(R.id.card_day_of_week);
            time = itemView.findViewById(R.id.card_recovery_time);

            LinearLayout ll_m_cycles = itemView.findViewById(R.id.card_micro_cycles);
            for (int i = 0; i < m_cycle; i++) list_m_cycle.add(add_cycle(context, ll_m_cycles));
        }
    }
}
