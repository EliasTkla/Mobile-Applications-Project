package com.example.fitlife;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SQLiteManager extends SQLiteOpenHelper {
    private final Context context;
    public static  final String DATABASE_NAME = "FitLife.db";
    public static final int DATABASE_VERSION = 1;

    public SQLiteManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    //onCreate will create all of our necessary tables
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO Add any required details for user's weight and height <- possibly for Elias or Supreyo to do
        String createUsersTable = "CREATE TABLE USERS " +
                "(USER_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_FNAME TEXT NOT NULL, " +
                "USER_LNAME TEXT NOT NULL, " +
                "USER_EMAIL TEXT UNIQUE NOT NULL, " +
                "USER_PASSWORD TEXT NOT NULL, " +
                "USER_WEIGHT DECIMAL(3,2), " +
                "USER_WEIGHT_GOAL DECIMAL(3,2)," +
                "USER_BODY_FAT DECIMAL(2,2)," +
                "USER_BODY_FAT_GOAL DECIMAL(2,2)," +
                "USER_HEIGHT DECIMAL(3,2), " +
                "USER_AGE INTEGER," +
                "USER_REGISTER_DATE TEXT)";

        String createRoutinesTable = "CREATE TABLE ROUTINES " +
                "(ROUTINE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ROUTINE_NAME TEXT, " +
                "ROUTINE_CREATOR TEXT, " +
                "ROUTINE_LEVEL TEXT, " +
                "ROUTINE_FREQUENCY INTEGER, " +
                "ROUTINE_LENGTH INTEGER)";

        String createWorkoutsTable = "CREATE TABLE WORKOUTS " +
                "(WORKOUT_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "WORKOUT_NAME TEXT, " +
                "WORKOUT_DAY TEXT, " +
                "WORKOUT_SETS INTEGER, " +
                "WORKOUT_REPS INTEGER, " +
                "ROUTINE_ID INTEGER, "+
                "FOREIGN KEY(ROUTINE_ID) REFERENCES ROUTINES(ROUTINE_ID))";

        String createUserRoutinesTable = "CREATE TABLE USER_ROUTINES " +
                "(USER_ROUTINES_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "USER_ID INTEGER NOT NULL, " +
                "ROUTINE_ID INTEGER NOT NULL, " +
                "STATUS INTEGER DEFAULT 0, " + //this is to help determine what routine the user is currently following (0 false, 1 true)
                "FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID), " +
                "FOREIGN KEY(ROUTINE_ID) REFERENCES ROUTINES(ROUTINE_ID))";

        String createUserGoalRecords = "CREATE TABLE USER_RECORDS " +
                "(USER_CURRENT_WEIGHT DECIMAL(3,2), " +
                "USER_CURRENT_BODY_FAT DECIMAL(2,2), " +
                "USER_CURRENT_DATE TEXT," +
                "USER_ID INTEGER, " +
                "FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID))";

        sqLiteDatabase.execSQL(createUsersTable);
        sqLiteDatabase.execSQL(createRoutinesTable);
        sqLiteDatabase.execSQL(createWorkoutsTable);
        sqLiteDatabase.execSQL(createUserRoutinesTable);
        sqLiteDatabase.execSQL(createUserGoalRecords);

        populateRoutines(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + "USERS");
        onCreate(sqLiteDatabase);
    }

    //Users DB Helper Methods
    public UserData getUserInfo(String email, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE USER_EMAIL = ? AND USER_PASSWORD = ?", new String[]{email, password});

        if(cursor.moveToFirst()){
            UserData user;
            String firstName = cursor.getString(1);
            String lastName = cursor.getString(2);
            String userEmail = cursor.getString(3);
            String userPassword = cursor.getString(4);
            Double weight = cursor.getDouble(5);
            Double weightG = cursor.getDouble(6);
            Double bodyFat = cursor.getDouble(7);
            Double bodyFatG = cursor.getDouble(8);
            Double height = cursor.getDouble(9);
            Integer age = cursor.getInt(10);
            String registerDate = cursor.getString(11);

            user = new UserData(firstName, lastName, userEmail, userPassword, weight, weightG, bodyFat, bodyFatG, height, age, registerDate);
            user.setUserId(cursor.getInt(0));
            return user;
        }

        cursor.close();
        sqLiteDatabase.close();

        return null;
    }

    public boolean addUser(UserData user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("USER_FNAME", user.getFname());
        values.put("USER_LNAME", user.getLname());
        values.put("USER_EMAIL", user.getEmail());
        values.put("USER_PASSWORD", user.getPassword());
        values.put("USER_WEIGHT", user.getWeight());
        values.put("USER_WEIGHT_GOAL", user.getWeightG());
        values.put("USER_BODY_FAT", user.getBodyFat());
        values.put("USER_BODY_FAT_GOAL", user.getBodyFatG());
        values.put("USER_HEIGHT", user.getHeight());
        values.put("USER_AGE", user.getAge());
        values.put("USER_REGISTER_DATE", user.getRegisteredDate());

        long status = sqLiteDatabase.insert("USERS", null, values);

        sqLiteDatabase.close();
        return status != -1;
    }

    public ArrayList<String> getUserGoals(int userID){
        ArrayList<String> goals = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER_GOALS WHERE USER_ID = ?", new String[]{String.valueOf(userID)});

        if(cursor.moveToFirst()){
            Double wGoal = cursor.getDouble(2);
            int bodyFat = cursor.getInt(3);
            int bodyFatGoal = cursor.getInt(4);

            goals.add(String.valueOf(wGoal));
            goals.add(String.valueOf(bodyFat));
            goals.add(String.valueOf(bodyFatGoal));

            cursor.close();
            sqLiteDatabase.close();
            return goals;
        }

        cursor.close();
        sqLiteDatabase.close();
        return null;
    }

    public boolean deleteUser(UserData uModel) {
        return false;
    }

    public void editUserInfo(UserData user){

    }

    public boolean userExist(String email){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE USER_EMAIL = ?", new String[]{email});

        if (cursor.moveToFirst()) {
            return true;
        }

        cursor.close();
        sqLiteDatabase.close();

        return false;
    }

    public boolean verifyUser(String email, String password){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USERS WHERE USER_EMAIL = ? AND USER_PASSWORD = ?", new String[]{email, password});

        if(cursor.moveToFirst()){
            return true;
        }

        cursor.close();
        sqLiteDatabase.close();

        return false;
    }

    //Saved Workouts DB Helper Methods
    public void addRoutines(RoutineData routine) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ROUTINE_NAME", routine.getTitle());
        values.put("ROUTINE_CREATOR", routine.getCreator());
        values.put("ROUTINE_LEVEL", routine.getLevel());
        values.put("ROUTINE_FREQUENCY", routine.getFrequency());
        values.put("ROUTINE_LENGTH", routine.getLength());

        long status = sqLiteDatabase.insert("ROUTINES", null, values);

//        ArrayList<WorkoutData> workouts = routine.getWorkoutsList();
//
//        for (WorkoutData workout: workouts) {
//            addWorkout(workout, routine.getId());
//        }

        if(status != -1){
            Toast.makeText(context.getApplicationContext(), "Successfully added Routine!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Routine not added!", Toast.LENGTH_LONG).show();
        }

        sqLiteDatabase.close();
    }

    public void deleteRoutine(int routineID) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        long status = sqLiteDatabase.delete("ROUTINES", "ROUTINE_ID = ?", new String[]{String.valueOf(routineID)});
        long status1 = sqLiteDatabase.delete("WORKOUTS", "ROUTINE_ID = ?", new String[]{String.valueOf(routineID)});
        long status2 = sqLiteDatabase.delete("USER_ROUTINES", "ROUTINE_ID = ?", new String[]{String.valueOf(routineID)});

        if(status != -1 && status1 != -1 && status2!= -1){
            Toast.makeText(context.getApplicationContext(), "Routine deleted!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Unable to delete Routine!", Toast.LENGTH_LONG).show();
        }
    }

    public ArrayList<RoutineData> getRoutines() {
        ArrayList<RoutineData> routines = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ROUTINES", null);


        if (cursor.moveToFirst()) {
            do {
                int rID = cursor.getInt(0);
                String name = cursor.getString(1);
                String creator = cursor.getString(2);
                String level = cursor.getString(3);
                int frequency = cursor.getInt(4);
                int length = cursor.getInt(5);

                Cursor workoutCursor = sqLiteDatabase.rawQuery("SELECT * FROM WORKOUTS WHERE ROUTINE_ID = ?", new String[]{String.valueOf(rID)});
                ArrayList<WorkoutData> workouts = new ArrayList<>();

                if(workoutCursor.moveToFirst()){
                    do {
                        workouts.add(new WorkoutData(workoutCursor.getInt(0), workoutCursor.getString(1), workoutCursor.getString(2), workoutCursor.getInt(3), workoutCursor.getInt(4), workoutCursor.getInt(5)));
                    }while (workoutCursor.moveToNext());
                }
                workoutCursor.close();

                routines.add(new RoutineData(rID, name, creator, level, frequency, length, workouts));
            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();

        return routines;
    }

    public boolean addUserRoutines(int routineID, int userID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("USER_ID", userID);
        values.put("ROUTINE_ID", routineID);

        long status = sqLiteDatabase.insert("USER_ROUTINES", null, values);

        sqLiteDatabase.close();

        if(status != -1){
            Toast.makeText(context.getApplicationContext(), "Added User Routine", Toast.LENGTH_LONG).show();
            Log.w("db", "Added User Routine with User_ID: " + userID + "and Routine_ID: " + routineID);
            return true;
        } else {
            Log.w("db", "Failed to add User Routine with User_ID: " + userID + "and Routine_ID: " + routineID);
        }

        return false;
    }

    public RoutineData getRoutine(int routineID){
        RoutineData routine = null;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ROUTINES WHERE ROUTINE_ID = ?", new String[]{String.valueOf(routineID)});

        if (cursor.moveToFirst()) {
            int rID = cursor.getInt(0);
            String name = cursor.getString(1);
            String creator = cursor.getString(2);
            String level = cursor.getString(3);
            int frequency = cursor.getInt(4);
            int length = cursor.getInt(5);

            Cursor workoutCursor = sqLiteDatabase.rawQuery("SELECT * FROM WORKOUTS WHERE ROUTINE_ID = ?", new String[]{String.valueOf(rID)});
            ArrayList<WorkoutData> workouts = new ArrayList<>();

            if(workoutCursor.moveToFirst()){
                do {
                    workouts.add(new WorkoutData(workoutCursor.getInt(0), workoutCursor.getString(1), workoutCursor.getString(2), workoutCursor.getInt(3), workoutCursor.getInt(4), workoutCursor.getInt(5)));
                }while (workoutCursor.moveToNext());
            }
            workoutCursor.close();

            routine = new RoutineData(rID, name, creator, level, frequency, length, workouts);
        } else {
            Toast.makeText(context.getApplicationContext(), "Routine does not exist!", Toast.LENGTH_LONG).show();
        }

        cursor.close();
        sqLiteDatabase.close();

        return routine;
    }

    //called when entering the saved routines view, gets all the user's created routines and their saved routines
    public ArrayList<RoutineData> getUserRoutines(int userID) {
        ArrayList<RoutineData> routines = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor c = sqLiteDatabase.rawQuery("SELECT ROUTINE_ID FROM USER_ROUTINES WHERE USER_ID = ?", new String[]{String.valueOf(userID)});

        if (c.moveToFirst()) {
            do {
                int uRoutineID = c.getInt(0);
                Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ROUTINES WHERE ROUTINE_ID = ?", new String[]{String.valueOf(uRoutineID)});

                if (cursor.moveToFirst()) {
                    do {
                        int rID = cursor.getInt(0);
                        String name = cursor.getString(1);
                        String creator = cursor.getString(2);
                        String level = cursor.getString(3);
                        int frequency = cursor.getInt(4);
                        int length = cursor.getInt(5);

                        Cursor workoutCursor = sqLiteDatabase.rawQuery("SELECT * FROM WORKOUTS WHERE ROUTINE_ID = ?", new String[]{String.valueOf(rID)});
                        ArrayList<WorkoutData> workouts = new ArrayList<>();

                        if (workoutCursor.moveToFirst() && workoutCursor.getCount() >= 1) {
                            do {
                                workouts.add(new WorkoutData(workoutCursor.getInt(0), workoutCursor.getString(1), workoutCursor.getString(2), workoutCursor.getInt(3), workoutCursor.getInt(4), workoutCursor.getInt(5)));
                            } while (workoutCursor.moveToNext());
                        } else {
                            workouts = null;
                        }
                        workoutCursor.close();

                        routines.add(new RoutineData(rID, name, creator, level, frequency, length, workouts));
                    } while (cursor.moveToNext());
                } else {
                    Toast.makeText(context.getApplicationContext(), "No routines available!", Toast.LENGTH_LONG).show();
                }
                cursor.close();
            } while (c.moveToNext());
        }

        c.close();
        sqLiteDatabase.close();

        return routines;
    }

    //Checks if its your created routine, to determine if you can delete it or delete workouts in it
    public boolean isRoutineCreated(RoutineData routineData, String userName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT ROUTINE_ID FROM ROUTINES WHERE ROUTINE_NAME = ? AND ROUTINE_CREATOR = ?", new String[]{String.valueOf(routineData.getTitle()), (userName)});

        int idCheck = -1;
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            idCheck = cursor.getInt(0);
        }
        cursor.close();
        sqLiteDatabase.close();

        if (idCheck == routineData.getId()) {
            return true;
        }

        return false;
    }

    public void saveUserRoutine(int userID, int routineID, boolean current){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("USER_ID", userID);
        values.put("ROUTINE_ID", routineID);

        long status = sqLiteDatabase.insert("USER_ROUTINES", null, values);

        if(status != -1){
            Toast.makeText(context.getApplicationContext(), "Routine saved!", Toast.LENGTH_LONG).show();
            Log.w("db", "Saved User Routine with User_ID: " + userID + " and Routine_ID: " + routineID);
        } else {
            Toast.makeText(context.getApplicationContext(), "Unable to save Routine!", Toast.LENGTH_LONG).show();
            Log.w("db", "Unable to save Routine with userID: " + userID + " and Routine_ID: " + routineID);
        }
        sqLiteDatabase.close();
    }

    public int getRoutineID(String routineName) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        int routineID = 0;
        Cursor c = sqLiteDatabase.rawQuery("SELECT ROUTINE_ID FROM ROUTINES WHERE ROUTINE_NAME = ?", new String[]{String.valueOf(routineName)});
        if(c.moveToFirst() && c.getCount() >= 1){
            do{
                routineID = c.getInt(0);
            }while(c.moveToNext());
        }
        return routineID;
    }

    //Individual Workouts DB Helper Methods
    public void addWorkout(WorkoutData workout, int routineID) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("WORKOUT_NAME", workout.getWorkoutName());
        values.put("WORKOUT_SETS", workout.getWorkoutSets());
        values.put("WORKOUT_REPS", workout.getWorkoutReps());
        values.put("ROUTINE_ID", routineID);

        long status = sqLiteDatabase.insert("WORKOUTS", null, values);

        if(status != -1){
            Toast.makeText(context.getApplicationContext(), "Workout added!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Workout not added!", Toast.LENGTH_LONG).show();
        }

        sqLiteDatabase.close();
    }

    public boolean deleteWorkout(WorkoutData wModel) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM WORKOUTS WHERE ID = " + wModel.getWorkoutID();
        Cursor cursor = sqLiteDatabase.rawQuery(deleteQuery, null);

//        if (cursor.moveToFirst()) {
//            return true;
//        } else {
//            return false;
//        }

        cursor.close();
        sqLiteDatabase.close();
        return false;
    }

    public List<WorkoutData> getWorkouts() {
        List<WorkoutData> resultList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM WORKOUTS", null);

        if (cursor.moveToFirst()) {
            do {
                int wID = cursor.getInt(0);
                String wName = cursor.getString(1);
                String wDay = cursor.getString(2);
                int wReps = cursor.getInt(3);
                int wSets = cursor.getInt(4);
                int wRoutineId = cursor.getInt(5);

                WorkoutData wModel = new WorkoutData(wID, wName, wDay, wReps, wSets, wRoutineId);
                resultList.add(wModel);
            } while (cursor.moveToNext());
        } else {
            Toast.makeText(context.getApplicationContext(), "No workouts available!", Toast.LENGTH_LONG).show();
        }

        cursor.close();
        sqLiteDatabase.close();

        return resultList;
    }

    public void saveUserRoutine(int userID, int routineID, int current){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("USER_ID", userID);
        values.put("ROUTINE_ID", routineID);
        values.put("STATUS", current);

        long status = sqLiteDatabase.insert("USER_ROUTINES", null, values);

        if(status != -1){
            Toast.makeText(context.getApplicationContext(), "Routine saved!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Unable to save Routine!", Toast.LENGTH_LONG).show();
        }

        sqLiteDatabase.close();
    }

    public void unSaveUserRoutine(int userID, int routineID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        long status = sqLiteDatabase.delete("USER_ROUTINES", "USER_ID=" + userID + " AND ROUTINE_ID=" + routineID, null);

        if(status != -1){
            Toast.makeText(context.getApplicationContext(), "Unsaved Routine!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context.getApplicationContext(), "Unable to unsave Routine!", Toast.LENGTH_LONG).show();
        }

        sqLiteDatabase.close();
    }

    public boolean isRoutineSaved(int userID, int routineID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM USER_ROUTINES WHERE USER_ID = ? AND ROUTINE_ID = ?", new String[]{String.valueOf(userID), String.valueOf(routineID)});

        if (cursor.moveToFirst()) {
            cursor.close();
            sqLiteDatabase.close();
            return true;
        }
        cursor.close();
        sqLiteDatabase.close();

        return false;
    }

    public void addRecord(double weight, double bodyFat, String dateRecorded, int userID){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("USER_CURRENT_WEIGHT", weight);
        values.put("USER_CURRENT_BODY_FAT", bodyFat);
        values.put("USER_CURRENT_DATE", dateRecorded);
        values.put("USER_ID", userID);

        long status = sqLiteDatabase.insert("USER_RECORDS", null, values);

        if(status != -1){
            Toast.makeText(context.getApplicationContext(), "Record added!", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(context.getApplicationContext(), "Record not added!", Toast.LENGTH_LONG).show();
        }

        sqLiteDatabase.close();
    }
    public Cursor displayUserRecords(int userID){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM USER_RECORDS WHERE USER_ID = ?", new String[]{String.valueOf(userID)});
        return cursor;
    }

    public ArrayList<Double> getUserRecords(int userID, int type){
        ArrayList<Double> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("SELECT * FROM USER_RECORDS WHERE USER_ID = ?", new String[]{String.valueOf(userID)});

        while (cursor.moveToNext()){
            list.add(Double.parseDouble(cursor.getString(type)));
        }

        return list;
    }

    public void populateRoutines(SQLiteDatabase sqLiteDatabase){
        ArrayList<String> routines = new ArrayList<>();

        routines.add("INSERT INTO ROUTINES (ROUTINE_NAME, ROUTINE_CREATOR, ROUTINE_LEVEL, ROUTINE_FREQUENCY, ROUTINE_LENGTH) VALUES ('10 Week Mass Building Program', 'muscleandstrength.com', 'Advanced', 4, 10)");
        routines.add("INSERT INTO ROUTINES (ROUTINE_NAME, ROUTINE_CREATOR, ROUTINE_LEVEL, ROUTINE_FREQUENCY, ROUTINE_LENGTH) VALUES ('Power Hypertrophy Upper Lower Workout', 'muscleandstrength.com', 'Intermediate', 4, 12)");
        routines.add("INSERT INTO ROUTINES (ROUTINE_NAME, ROUTINE_CREATOR, ROUTINE_LEVEL, ROUTINE_FREQUENCY, ROUTINE_LENGTH) VALUES ('Dumbbell Only Home or Gym Full Body Workout', 'muscleandstrength.com', 'Beginner', 3, 8)");

        for (String routine : routines) {
            sqLiteDatabase.execSQL(routine);
        }

        ArrayList<String> workouts = new ArrayList<>();
        //workouts for the first routine
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Barbell Bench Press', 'Monday', 4, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Incline Bench Press', 'Monday', 3, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Decline Bench Press', 'Monday', 3, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Flys', 'Monday', 2, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Pullover', 'Monday', 2, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Tricep Extension', 'Monday', 4, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Tricep Dip', 'Monday', 3, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Tricep Bench Dip', 'Monday', 3, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Deadlift', 'Tuesday', 5, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Chin Up', 'Tuesday', 2, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('One Arm Dumbbell Row', 'Tuesday', 3, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Seated Row', 'Tuesday', 2, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Close Grip Lat Pull Down', 'Tuesday', 3, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Standing Barbell Curl', 'Tuesday', 3, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Close Grip Preacher Curl', 'Tuesday', 3, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Incline Dumbbell Curl', 'Tuesday', 2, 12, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Concentration Curl', 'Tuesday', 2, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Machine Shoulder Press', 'Thursday', 3, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Reverse Fly', 'Thursday', 3, 8, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Military Press', 'Thursday', 4, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Lateral Raise', 'Thursday', 2, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Shrugs', 'Thursday', 2, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Upright Row', 'Thursday', 2, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Standing Wrist Curl', 'Thursday', 4, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Barbell Wrist Curl', 'Thursday', 4, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Squat', 'Friday', 5, 10, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Leg Extension', 'Friday', 3, 12, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Leg Curl', 'Friday', 3, 12, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Standing Calf Raise', 'Friday', 4, 12, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Seated calf Raise', 'Friday', 2, 12, 1)");

        //workouts for the second routine
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Barbell Bench Press', 'Monday', 4, 5, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Incline Dumbbell Bench Press', 'Monday', 4, 10, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Bent Over Row', 'Monday', 4, 5, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Lat Pull Down', 'Monday', 4, 10, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Overhead Press', 'Monday', 3, 8, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Barbell Curl', 'Monday', 3, 10, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Skullcrusher', 'Monday', 3, 10, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Squat', 'Tuesday', 4, 5, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Deadlift', 'Tuesday', 4, 5, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Leg Press', 'Tuesday', 5, 15, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Leg Curl', 'Tuesday', 4, 10, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Calf Exercise', 'Tuesday', 4, 10, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Incline Barbell Bench Press', 'Thursday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Flat Bench Dumbbell Flye', 'Thursday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Seated Cable Row', 'Thursday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('One Arm Dumbbell Row', 'Thursday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Lateral Raise', 'Thursday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Seated Incline Dumbbell Curl', 'Thursday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Cable Tricep Extension', 'Thursday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Front Squat', 'Friday', 4, 12, 1)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Barbell Lunge', 'Friday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Leg Extension', 'Friday', 4, 15, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Leg Curl', 'Friday', 4, 15, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Seated Calf Raise', 'Friday', 4, 12, 2)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Calf Press', 'Friday', 4, 12, 2)");

        //workouts for the third routine
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Squat', 'Monday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Bench Press', 'Monday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('One Arm Dumbbell Row', 'Monday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Standing Dumbbell Curl', 'Monday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Two Arm Seated Dumbbell Extension', 'Monday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Sit Up', 'Monday', 3, 25, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Step Up', 'Wednesday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Stiff Leg Deadlift', 'Wednesday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Seated Dumbbell', 'Wednesday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Standing One Leg Dumbbell Calf Raise', 'Wednesday', 3, 20, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Shrug', 'Wednesday', 3, 15, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Side Bends', 'Wednesday', 3, 15, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Lunge', 'Friday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Dumbbell Floor Press', 'Friday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Wide Grip Pull Up', 'Friday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Standing Hammer Curl', 'Friday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Lying Dumbbell Extension', 'Friday', 3, 12, 3)");
        workouts.add("INSERT INTO WORKOUTS (WORKOUT_NAME, WORKOUT_DAY, WORKOUT_SETS, WORKOUT_REPS, ROUTINE_ID) VALUES ('Lying Floor Leg Raise', 'Friday', 3, 25, 3)");

        for (String workout : workouts) {
            sqLiteDatabase.execSQL(workout);
        }
    }
}
