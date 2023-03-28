package com.example.fitlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper {
    public static  final String DATABASE_NAME = "FitLife.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //onCreate will create all of our necessary tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO Add any required details for user's weight and height <- possibly for Elias or Supreyo to do
        String createUsersTable = "CREATE TABLE USERS " +
                "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FNAME TEXT, " +
                "LNAME TEXT, " +
                "EMAIL TEXT, " +
                "USERNAME TEXT, " +
                "PASSWORD TEXT)";

        String createRoutinesTable = "CREATE TABLE ROUTINES " +
                "(ROUTINE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ROUTINE_NAME TEXT, " +
                "ROUTINE_CREATOR TEXT, " +
                "ROUTINE_LEVEL TEXT, " +
                "ROUTINE_LENGTH INTEGER, " +
                "ROUTINE_FREQUENCY INTEGER)";

        String createWorkoutsTable = "CREATE TABLE WORKOUTS " +
                "(WORKOUT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "WORKOUT_NAME TEXT, " +
                "SETS INTEGER, " +
                "REPS INTEGER, " +
                "ROUTINE_ID INTEGER, "+
                "FOREIGN KEY(ROUTINE_ID) REFERENCES ROUTINES(ROUTINE_ID))";

        String createdUserRoutinesTable = "CREATE TABLE USER_ROUTINES " +
                "(USER_ROUTINES_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER NOT NULL, " +
                "ROUTINE_ID INTEGER NOT NULL, " +
                "STATUS INTEGER DEFAULT 0, " + //this is to help determine what routine the user is currently following (0 false, 1 true)
                "FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID), " +
                "FOREIGN KEY(ROUTINE_ID) REFERENCES ROUTINES(ROUTINE_ID))";

        sqLiteDatabase.execSQL(createUsersTable);
        sqLiteDatabase.execSQL(createRoutinesTable);
        sqLiteDatabase.execSQL(createWorkoutsTable);
        sqLiteDatabase.execSQL(createdUserRoutinesTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "USERS");
        onCreate(sqLiteDatabase);
    }

    //Users DB Helper Methods
    //TODO - Create an EditUsers method potentially in here or where the editing will access the db to write
    public boolean addUser(UserData uModel) {
        return false;
    }

    public boolean deleteUser(UserData uModel) {
        return false;
    }



    //Saved Workouts DB Helper Methods
    public boolean addRoutines(RoutineData rModel) {
        return false;
    }

    public boolean deleteRoutine(RoutineData rModel) {
        return false;
    }

    public List<RoutineData> getRoutines() {
        return null;
    }

    //Individual Workouts DB Helper Methods

    public boolean addWorkout(WorkoutData wModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", wModel.getWorkoutID());
        values.put("WORKOUT_NAME", wModel.getWorkoutName());
        values.put("REPS", wModel.getWorkoutReps());
        values.put("SETS", wModel.getWorkoutSets());

        long workouts = db.insert("WORKOUTS", null, values);

        return workouts != -1;
    }

    public boolean deleteWorkout(WorkoutData wModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM WORKOUTS WHERE ID = " + wModel.getWorkoutID();
        Cursor c = db.rawQuery(deleteQuery, null);

        if (c.moveToFirst()) {
            return true;
        } else return false;
    }

    public List<WorkoutData> getWorkouts() {
        List<WorkoutData> resultList = new ArrayList<>();

        String sqlStatement = "SELECT * FROM WORKOUTS";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sqlStatement, null);

        if (c.moveToFirst()) {
            do {
                int wID = c.getInt(0);
                String wName = c.getString(1);
                int wReps = c.getInt(3);
                int wSets = c.getInt(4);

                WorkoutData wModel = new WorkoutData(wID, wName, wReps, wSets);
                resultList.add(wModel);
            } while (c.moveToNext());
        } else {
            //fails in getting results, so empty list is returned
        }

        c.close();
        db.close();
        return resultList;
    }

    public void saveUserRoutine(int userID, int routineID){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("USER_ID", userID);
        values.put("ROUTINE_ID", routineID);

        db.insert("USER_ROUTINES", null, values);

        db.close();
    }

    public void unSaveUserRoutine(int userID, int routineID){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete("USER_ROUTINES", "USER_ID=" + userID + " AND ROUTINE_ID=" + routineID, null);

        db.close();
    }

}
