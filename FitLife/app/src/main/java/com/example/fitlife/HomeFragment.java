package com.example.fitlife;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    View view;
    TextView welcomeUserText, lastDate;
    String firstName = "Supreyo";
    String getLastDate = "March 22, 2023";
    FloatingActionButton addNewMeasurements;
    RecyclerView progressCardView;
    ArrayList<ProgressData> progressCards = new ArrayList<>();
    GraphView graphWeight, graphBodyFat;
    LineGraphSeries<DataPoint> weightSeries, bodyFatSeries;
    DataPoint[] weightDP, bodyFatDP;
    Button weightSwitchGraph, bodyFatSwitchGraph;

//    double getCurrentWeight = 165;
//    double getGoalCurrentWeight = 175;
//    double getCurrentBodyFat = 25;
//    double getGoalCurrentBodyFat = 13;

//    ProgressBar weightProgressBar, bodyFatProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeUserText = view.findViewById(R.id.welcomeUser);
        addNewMeasurements = view.findViewById(R.id.recordNewMeasurements);
        lastDate = view.findViewById(R.id.lastDate);

        progressCardView = view.findViewById(R.id.progressList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        progressCardView.setLayoutManager(layoutManager);

        weightSwitchGraph = view.findViewById(R.id.weightSwitchButton);
        bodyFatSwitchGraph = view.findViewById(R.id.bodyFatSwitchButton);

        welcomeUserText.setText("Welcome " + firstName +"!");
        lastDate.setText("From " + getLastDate);

        addNewMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measureActivity = new Intent(getActivity(), BodyMeasurementsActivity.class);
                startActivity(measureActivity);
            }
        });

        progressCards.add(new ProgressData( "Current Weight","155lbs", "Goal Weight", "170lbs", "Starting Weight: 135lbs", "(+15lbs)", "Weight", 10));
        progressCards.add(new ProgressData( "Current Body Fat","25%", "Goal Body Fat", "13%", "Starting Body Fat: 30%", "(-12%)", "Body Fat", 10));

        ProgressAdapter adapter = new ProgressAdapter(progressCards, HomeFragment.this);
        progressCardView.setAdapter(adapter);

//        weightProgressBar = view.findViewById(R.id.weightProgress);
//        bodyFatProgressBar = view.findViewById(R.id.bodyFatProgress);
//        weightProgressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(162,210,223)));
//        bodyFatProgressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(162,210,223)));

        graphWeight = (GraphView) view.findViewById(R.id.weightGraph);
        graphBodyFat = (GraphView) view.findViewById(R.id.bodyFatGraph);
        weightSeries = new LineGraphSeries<>(getDataPointWeight());
        bodyFatSeries = new LineGraphSeries<>(getDataPointBodyFat());

        graphWeight.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphWeight.getViewport().setDrawBorder(true);
        weightSeries.setColor(Color.rgb(95,158,160));
        weightSeries.setDrawBackground(true);
        weightSeries.setBackgroundColor(Color.argb(50, 72,209,204));
        weightSeries.setDrawDataPoints(true);
        weightSeries.setDataPointsRadius(5);
        graphWeight.getViewport().setYAxisBoundsManual(true);
        graphWeight.getViewport().setMinY(weightSeries.getLowestValueY());
        graphWeight.getViewport().setMaxY(weightSeries.getHighestValueY());
        graphWeight.setTitle("Weight");
        graphWeight.addSeries(weightSeries);

        if (weightDP.length < 5) {
            graphWeight.getGridLabelRenderer().setNumHorizontalLabels(weightDP.length); // only 4 because of the space
        }

        graphBodyFat.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graphBodyFat.getViewport().setDrawBorder(true);
        bodyFatSeries.setColor(Color.rgb(95,158,160));
        bodyFatSeries.setDrawBackground(true);
        bodyFatSeries.setBackgroundColor(Color.argb(50, 72,209,204));
        bodyFatSeries.setDrawDataPoints(true);
        bodyFatSeries.setDataPointsRadius(5);
        graphBodyFat.getViewport().setYAxisBoundsManual(true);
        graphBodyFat.getViewport().setMinY(bodyFatSeries.getLowestValueY());
        graphBodyFat.getViewport().setMaxY(bodyFatSeries.getHighestValueY());
        graphBodyFat.setTitle("Body Fat");
        graphBodyFat.addSeries(bodyFatSeries);

        if (bodyFatDP.length < 5) {
            graphBodyFat.getGridLabelRenderer().setNumHorizontalLabels(bodyFatDP.length); // only 4 because of the space
        }

        weightSwitchGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphWeight.setVisibility(View.GONE);
                graphBodyFat.setVisibility(View.VISIBLE);
                weightSwitchGraph.setVisibility(View.GONE);
                bodyFatSwitchGraph.setVisibility(View.VISIBLE);
            }
        });

        bodyFatSwitchGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                graphBodyFat.setVisibility(View.GONE);
                graphWeight.setVisibility(View.VISIBLE);
                bodyFatSwitchGraph.setVisibility(View.GONE);
                weightSwitchGraph.setVisibility(View.VISIBLE);
            }
        });

        return view;
    }

    private DataPoint[] getDataPointWeight(){
        weightDP = new DataPoint[]{
        new DataPoint(0, 135),
        new DataPoint(1, 137),
        new DataPoint(2, 140),
        new DataPoint(3, 140),
        new DataPoint(4, 139),
        new DataPoint(5, 143),
        new DataPoint(6, 147),
        new DataPoint(7, 150),
        new DataPoint(8, 148),
        new DataPoint(9, 148),
        new DataPoint(10, 149),
        new DataPoint(11, 152),
        new DataPoint(12, 155),
        new DataPoint(13, 154),
        new DataPoint(14, 157),
        new DataPoint(15, 159),
        new DataPoint(16, 160),
        new DataPoint(17, 159),
        new DataPoint(18, 162),
        new DataPoint(19, 164),
        new DataPoint(20, 165)
        };

        return weightDP;
    }

    private DataPoint[] getDataPointBodyFat(){
        bodyFatDP = new DataPoint[]{
        new DataPoint(0, 30),
        new DataPoint(1, 30),
        new DataPoint(2, 29),
        new DataPoint(3, 28),
        new DataPoint(4, 26),
        new DataPoint(5, 27),
        new DataPoint(6, 26),
        new DataPoint(7, 26),
        new DataPoint(8, 25),
        new DataPoint(9, 24),
        new DataPoint(10, 25),
        new DataPoint(11, 23),
        new DataPoint(12, 22),
        new DataPoint(13, 22),
        new DataPoint(14, 23),
        new DataPoint(15, 24),
        new DataPoint(16, 23),
        new DataPoint(17, 22),
        new DataPoint(18, 22),
        new DataPoint(19, 21),
        new DataPoint(20, 20)
        };

        return bodyFatDP;
    }
}