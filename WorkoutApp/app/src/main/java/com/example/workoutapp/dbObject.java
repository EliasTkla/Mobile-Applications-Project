package com.example.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbObject extends SQLiteOpenHelper {

    public dbObject(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public dbObject(@Nullable Context context) {
        super(context, "workouts.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTables = "CREATE TABLE WORKOUTS (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "WORKOUT_NAME TEXT, " +
                "WEIGHT INT, " +
                "REPS INT, " +
                "SETS INT)";

        sqLiteDatabase.execSQL(createTables);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addToTable(WorkoutModel wModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ID", wModel.getId());
        values.put("WORKOUT_NAME", wModel.getWorkoutName());
        values.put("WEIGHT", wModel.getWeight());
        values.put("REPS", wModel.getReps());
        values.put("SETS", wModel.getSets());

        long workouts = db.insert("WORKOUTS", null, values);

        return workouts != -1;
    }

    public boolean deleteRecord(WorkoutModel wModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM WORKOUTS WHERE ID = " + wModel.getId();
        Cursor c = db.rawQuery(deleteQuery, null);

        if (c.moveToFirst()) {
            return true;
        } else return false;
    }

    public List<WorkoutModel> getWorkouts() {
        List<WorkoutModel> resultList = new ArrayList<>();

        String sqlStatement = "SELECT * FROM WORKOUTS";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(sqlStatement, null);

        if (c.moveToFirst()) {
            do {
                int wID = c.getInt(0);
                String wName = c.getString(1);
                int wWeight = c.getInt(2);
                int wReps = c.getInt(3);
                int wSets = c.getInt(4);

                WorkoutModel wModel = new WorkoutModel(wID, wName, wWeight, wReps, wSets);
                resultList.add(wModel);
            } while (c.moveToNext());
        } else {
            //fails in getting results, so empty list is returned
        }
        c.close();
        db.close();
        return resultList;
    }
}
