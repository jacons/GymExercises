package com.example.gymexercises;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {


    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, "gymexercises_db", null, Integer.parseInt("1"));
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating an sqlite query and we are
        // setting our column names along with their data types.
        String workouts = " CREATE TABLE workouts (" +
                "id_program INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "name VARCHAR(50) NOT NULL ," +
                "date TIMESTAMP  NOT NULL DEFAULT CURRENT_TIMESTAMP);";

        String exercises = " CREATE TABLE exercises (" +
                "id_program INTEGER," +
                "id_exercise INTEGER," +
                "name VARCHAR(100) NOT NULL ," +
                "day INTEGER NOT NULL," +
                "time TEXT NOT NULL ," +
                "notes TEXT NOT NULL ," +
                "PRIMARY KEY (id_program, id_exercise));";

        String microcycles = "CREATE TABLE mcycles(" +
                "id_program INTEGER," +
                "id_exercise INTEGER," +
                "week INTEGER," +
                "series INTEGER NOT NULL ," +
                "reps TEXT NOT NULL ," +
                "load TEXT NOT NULL ," +
                "PRIMARY KEY (id_program, id_exercise, week));";

        // at last we are calling a exec sql method to execute above sql query
        db.execSQL(workouts);
        db.execSQL(exercises);
        db.execSQL(microcycles);

    }

    public boolean checkWorkoutExist(String workout_name) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(*) FROM workouts WHERE name == ?;", new String[]{workout_name});

        boolean flag = false;
        if (c.moveToFirst()) do {
            flag = c.getInt(0) > 0;
        } while (c.moveToNext());

        c.close();
        db.close();

        return flag;
    }

    // this method is use to add new course to our sqlite database.
    public void addNewWorkout(Workout w) {

        // on below line we are creating a variable for  our sqlite database and calling
        // writable method as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a variable for content values.
        ContentValues values = new ContentValues();
        values.put("name",w.name);
        long id_workout = db.insert("workouts", null, values);


        int id_exercise = 0, id_cycle;
        for (Exercise e : w.exercies) {

            // Adding excercise
            values.clear();
            values.put("id_program", id_workout);
            values.put("id_exercise", id_exercise);
            values.put("name", e.getName_ex());
            values.put("day", e.getDay());
            values.put("time", e.getTime());
            values.put("notes", e.getNotes());
            db.insert("exercises", null, values);

            // For each excercise adding a cycle
            id_cycle = 0;
            values.clear();
            for (String[] cycle : e.getMCycles()) {

                values.put("id_program", id_workout);
                values.put("id_exercise", id_exercise);
                values.put("week", id_cycle++);
                values.put("series", cycle[0]);
                values.put("reps", cycle[1]);
                values.put("load", "");

            }
            db.insert("mcycles", null, values);
            id_exercise++;
        }

        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS workouts");
        db.execSQL("DROP TABLE IF EXISTS exercises");
        db.execSQL("DROP TABLE IF EXISTS mcycles");
        onCreate(db);
    }
}