package com.example.gymexercises;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Workout<wrapper_exercise> workout;
        try (DBHandler dbHandler = new DBHandler(this)) {

            workout = dbHandler.get_last_workout();

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
            return;
        }

        Adapter adapter = new Adapter(this, workout);

        RecyclerView rv = findViewById(R.id.recyclerview);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add) {
            startActivity(new Intent(MainActivity.this, AddExercises.class));
            return true;
        }
        if (item.getItemId() == R.id.edit) {
            startActivity(new Intent(MainActivity.this, EditWorkout.class));
            return true;
        }
        return false;
    }
}