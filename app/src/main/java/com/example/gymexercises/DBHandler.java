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
                "reps INTEGER NOT NULL ," +
                "notes TEXT NOT NULL ," +
                "day INTEGER NOT NULL," +
                "PRIMARY KEY (id_program, id_exercise));";

        // at last we are calling a exec sql method to execute above sql query
        db.execSQL(workouts);
        db.execSQL(exercises);

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

        values.clear();

        int id_exercise = 0;
        for (Exercise e : w.exercies) {

            values.put("id_program", id_workout);
            values.put("id_exercise", id_exercise++);
            values.put("name", e.getName_ex());
            values.put("reps", e.getReps());
            values.put("notes", e.getNotes());
            values.put("day", e.getDay());

            db.insert("exercises", null, values);
        }

        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS workouts");
        db.execSQL("DROP TABLE IF EXISTS exercises");
        onCreate(db);
    }
}