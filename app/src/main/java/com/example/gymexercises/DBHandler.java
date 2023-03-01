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

    public Boolean checkWorkoutExist(String workout_name) {

        SQLiteDatabase db = this.getReadableDatabase();
        boolean flag;
        try (Cursor c = db.rawQuery("SELECT COUNT(*) FROM workouts WHERE name == ?;", new String[]{workout_name})) {
            flag = false;
            if (c.moveToFirst()) do {
                flag = c.getInt(0) > 0;
            } while (c.moveToNext());
        } catch (Exception e) {
            return null;
        }

        db.close();
        return flag;
    }

    // this method is use to add new course to our sqlite database.
    public void addNewWorkout(Workout<Exercise> w) {

        // on below line we are creating a variable for  our sqlite database and calling
        // writable method as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a variable for content values.
        ContentValues values = new ContentValues();
        values.put("name", w.name);
        long id_workout = db.insert("workouts", null, values);


        int id_exercise = 0, id_cycle;
        for (Exercise e : w.exercises) {

            // Adding exercise
            values.clear();
            values.put("id_program", id_workout);
            values.put("id_exercise", id_exercise);
            values.put("name", e.getName());
            values.put("day", e.getDay());
            values.put("time", e.getTime());
            values.put("notes", e.getNotes());
            db.insert("exercises", null, values);

            // For each exercise adding a cycle
            id_cycle = 0;
            for (String[] cycle : e.getMCycles()) {
                values.clear();
                values.put("id_program", id_workout);
                values.put("id_exercise", id_exercise);
                values.put("week", id_cycle++);
                values.put("series", cycle[0]);
                values.put("reps", cycle[1]);
                values.put("load", "");
                db.insert("mcycles", null, values);
            }
            id_exercise++;
        }

        db.close();

    }

    public Workout<wrapper_exercise> get_last_workout() {

        SQLiteDatabase db = this.getReadableDatabase();

        String id_program = "", name = "", date = "", time, notes, reps, load;
        int day, series, id_exercise;

        String sql_query = "SELECT id_program,name,date FROM workouts ORDER BY id_program DESC LIMIT 1";
        try (Cursor c = db.rawQuery(sql_query, null)) {

            if (c.moveToFirst()) do {
                id_program = String.valueOf(c.getInt(0));
                name = c.getString(1);
                date = c.getString(2);

            } while (c.moveToNext());
        } catch (Exception e) {
            return null;
        }

        Workout<wrapper_exercise> workout = new Workout<>(name);
        workout.addDate(date);

        sql_query = "SELECT name,day,time,notes FROM exercises WHERE id_program == ? ORDER BY id_exercise ASC";
        try (Cursor c = db.rawQuery(sql_query, new String[]{id_program})) {

            if (c.moveToFirst()) do {

                name = c.getString(0);
                day = c.getInt(1);
                time = c.getString(2);
                notes = c.getString(3);

                workout.add(new wrapper_exercise(name, notes, day, time));

            } while (c.moveToNext());
        } catch (Exception e) {
            return null;
        }


        sql_query = "SELECT id_exercise, series, reps, load FROM mcycles WHERE id_program == ? ORDER BY id_exercise, week ASC";
        try (Cursor c = db.rawQuery(sql_query, new String[]{id_program})) {

            if (c.moveToFirst()) do {

                id_exercise = c.getInt(0);
                series = c.getInt(1);
                reps = c.getString(2);
                load = c.getString(3);

                workout.exercises.get(id_exercise).add_cycle(series, reps, load);

            } while (c.moveToNext());
        } catch (Exception e) {
            return null;
        }


        db.close();
        return workout;
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