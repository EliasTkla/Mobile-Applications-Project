package com.example.fitlife;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    View view;
    FrameLayout workouts;
    LinearLayout new_workouts;
    FloatingActionButton add_workout;
    Button bt_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        add_workout = view.findViewById(R.id.add_workout_btn);
        workouts = view.findViewById(R.id.daily_workout);
        new_workouts = view.findViewById(R.id.new_workout);
        bt_add = view.findViewById(R.id.bt_Add);

        add_workout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workouts.setVisibility(View.GONE);
                new_workouts.setVisibility(View.VISIBLE);
            }
        });

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                workouts.setVisibility(View.VISIBLE);
                new_workouts.setVisibility(View.GONE);
            }
        });

        return view;
    }
}