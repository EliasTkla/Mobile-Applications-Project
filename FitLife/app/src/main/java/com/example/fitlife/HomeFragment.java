package com.example.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeFragment extends Fragment {
    View view;
    FloatingActionButton addNewMeasurements;;
    TextView welcomeUserText;
    String firstName = "Supreyo";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        addNewMeasurements = view.findViewById(R.id.recordNewMeasurements);
        welcomeUserText = view.findViewById(R.id.welcomeUser);

        welcomeUserText.setText("Welcome " + firstName +"!");

        addNewMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measureActivity = new Intent(getActivity(), BodyMeasurementsActivity.class);
                startActivity(measureActivity);
            }
        });

        return view;
    }
}