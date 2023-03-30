package com.example.fitlife;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedWorkoutsFragment extends Fragment {

    //Views
    View view;
    FrameLayout saved_routine;
    FrameLayout created_routine;
    FrameLayout new_routine;
    FrameLayout new_workout;
    RecyclerView routine_list;

    //Buttons
    FloatingActionButton new_routine_btn;
    FloatingActionButton bt_add_to_routine;
    Button bt_add_routine;
    Button add_workout;

    //Back Buttons
    ImageButton create_routine_back;
    ImageButton create_workout_back;
    ImageButton your_routines_back;

    //Inputs to store in DB

    //Add Routine Inputs
    EditText et_routine_name;
    EditText et_routine_level;
    EditText et_routine_length;
    EditText et_routine_freq;

    //Add Workout Inputs

    EditText set_Workout;
    EditText set_day;
    EditText set_reps;
    EditText set_Sets;

    RoutineData routineData;
    ArrayList<WorkoutData> workouts;

    int createdRoutineID;

    ArrayList<RoutineData> routines = new ArrayList<>();

    boolean createdRoutine; //Carries over to the routine details view to determine if workouts can be deleted and edited, true if created, false if saved

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved_workouts, container, false);
        //Views
        saved_routine = view.findViewById(R.id.saved_routines);
        new_routine = view.findViewById(R.id.new_routine);
        created_routine = view.findViewById(R.id.created_routine);
        new_workout = view.findViewById(R.id.new_workout);
        routine_list = view.findViewById(R.id.routine_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        routine_list.setLayoutManager(layoutManager);
        //Buttons
        new_routine_btn = view.findViewById(R.id.create_routine_btn);
        bt_add_routine = view.findViewById(R.id.bt_add_routine);
        bt_add_to_routine = view.findViewById(R.id.add_workout_to_routine_btn);
        add_workout = view.findViewById(R.id.bt_Add);
        create_routine_back = view.findViewById(R.id.create_routine_back);
        create_workout_back = view.findViewById(R.id.create_workout_back);
        your_routines_back = view.findViewById(R.id.your_routine_back);
        //Add Routine Inputs
        et_routine_name = view.findViewById(R.id.et_routine_name);
        et_routine_length = view.findViewById(R.id.et_routine_length);
        et_routine_level = view.findViewById(R.id.et_routine_level);
        et_routine_freq = view.findViewById(R.id.et_routine_freq);
        //Add Workout Inputs
        set_Workout = view.findViewById(R.id.set_Workout);
        set_day = view.findViewById(R.id.set_day);
        set_reps = view.findViewById(R.id.set_reps);
        set_Sets = view.findViewById(R.id.set_Sets);

        SQLiteManager db = new SQLiteManager(getActivity());

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        int userID = Integer.parseInt(sharedPreferences.getString("user_id", null));
        String user_name = sharedPreferences.getString("fname_key", null) + " " + sharedPreferences.getString("lname_key", null);

        routines = db.getUserRoutines(userID);

        RoutineAdapter adapter = new RoutineAdapter(routines, SavedWorkoutsFragment.this);
        routine_list.setAdapter(adapter);

        //OnClick handlers for navigating between views of fragment
        new_routine_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved_routine.setVisibility(View.GONE);
                new_routine.setVisibility(View.VISIBLE);
                saved_routine.invalidate();
                new_routine.invalidate();
            }
        });

        bt_add_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                routineData = new RoutineData(-1,
                        et_routine_name.getText().toString(),
                        user_name,
                        et_routine_level.getText().toString(),
                        Integer.parseInt(et_routine_freq.getText().toString()),
                        Integer.parseInt(et_routine_length.getText().toString()), null);

                db.addRoutines(routineData);
                createdRoutineID = db.getRoutineID(et_routine_name.getText().toString());
                db.addUserRoutines(createdRoutineID, userID);
                routines = db.getUserRoutines(userID);
                new_routine.setVisibility(View.GONE);
                created_routine.setVisibility(View.VISIBLE);
                new_routine.invalidate();
                created_routine.invalidate();
            }
        });

        bt_add_to_routine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                created_routine.setVisibility(View.GONE);
                new_workout.setVisibility(View.VISIBLE);
                created_routine.invalidate();
                new_workout.invalidate();
            }
        });

        add_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkoutData workoutData = new WorkoutData(-1 ,
                        set_Workout.getText().toString(),
                        set_day.getText().toString(),
                        Integer.parseInt(set_reps.getText().toString()),
                        Integer.parseInt(set_Sets.getText().toString()),
                        createdRoutineID);
                db.addWorkout(workoutData, createdRoutineID);
                routines = db.getUserRoutines(userID);

                created_routine.setVisibility(View.VISIBLE);
                new_workout.setVisibility(View.GONE);
                created_routine.invalidate();
                new_workout.invalidate();
            }
        });

        create_routine_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saved_routine.setVisibility(View.VISIBLE);
                new_routine.setVisibility(View.GONE);
                created_routine.invalidate();
                new_workout.invalidate();
            }
        });

        create_workout_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new_workout.setVisibility(View.GONE);
                created_routine.setVisibility(View.VISIBLE);
                created_routine.invalidate();
                new_workout.invalidate();
            }
        });

        your_routines_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                created_routine.setVisibility(View.GONE);
                saved_routine.setVisibility(View.VISIBLE);
                created_routine.invalidate();
                saved_routine.invalidate();
            }
        });
        return view;
    }
}