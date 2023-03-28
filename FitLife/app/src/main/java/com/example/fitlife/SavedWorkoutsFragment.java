package com.example.fitlife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.fragment.app.Fragment;

public class SavedWorkoutsFragment extends Fragment {

    //Views
    View view;
    FrameLayout saved_routine;
    FrameLayout created_routine;
    LinearLayout new_routine;
    LinearLayout new_workout;
    //Buttons
    FloatingActionButton new_routine_btn;
    FloatingActionButton bt_add_to_routine;
    Button bt_add_routine;
    Button add_workout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_saved_workouts, container, false);
        //Views
        saved_routine = view.findViewById(R.id.saved_routines);
        new_routine = view.findViewById(R.id.new_routine);
        created_routine = view.findViewById(R.id.created_routine);
        new_workout = view.findViewById(R.id.new_workout);
        //Buttons
        new_routine_btn = view.findViewById(R.id.create_routine_btn);
        bt_add_routine = view.findViewById(R.id.bt_add_routine);
        bt_add_to_routine = view.findViewById(R.id.add_workout_to_routine_btn);
        add_workout = view.findViewById(R.id.bt_Add);

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
                created_routine.setVisibility(View.VISIBLE);
                new_workout.setVisibility(View.GONE);
                created_routine.invalidate();
                new_workout.invalidate();
            }
        });

        return view;
    }
}