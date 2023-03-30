package com.example.fitlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HomeFragment extends Fragment {
    View view;
    TextView welcomeUserText, lastDate;
    FloatingActionButton addNewMeasurements;
    RecyclerView progressCardView;
    ArrayList<ProgressData> progressCards = new ArrayList<>();
    GraphView graph;
    LineGraphSeries<DataPoint> weightSeries, bodyFatSeries;
    DataPoint[] getWeightDP, getBodyFatDP;
    DataPoint[] getPopulatedWeightDP, getPopulatedBodyFatDP;
    GridLabelRenderer renderer;
    Button switchGraph;
    boolean clicked = false;

    String getFirstName;
    String getFirstDate;
    String getLastDate;

    double getStartingWeight;
    double getCurrentWeight;
    double getGoalWeight;

    double getStartingBodyFat;
    double getCurrentBodyFat = 23;
    double getGoalBodyFat;

    double weightDifference, bodyFatDifference;
    String weightDifferenceOutput, bodyFatDifferenceOutput;

    SQLiteManager db;
    Cursor cursor;
    String dataStore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        welcomeUserText = view.findViewById(R.id.welcomeUser);
        addNewMeasurements = view.findViewById(R.id.recordNewMeasurements);
        lastDate = view.findViewById(R.id.lastDate);

        progressCardView = view.findViewById(R.id.progressList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        progressCardView.setLayoutManager(layoutManager);

        db = new SQLiteManager(getActivity());

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        int userID = Integer.parseInt(sharedPreferences.getString("user_id", null));

        getFirstName = sharedPreferences.getString("fname_key", null);

        getStartingWeight = Double.parseDouble(sharedPreferences.getString("weight_key", null));
        getGoalWeight = Double.parseDouble(sharedPreferences.getString("weightG_key", null));

        getStartingBodyFat = Double.parseDouble(sharedPreferences.getString("bodyFat_key", null));
        getGoalBodyFat = Double.parseDouble(sharedPreferences.getString("bodyFatG_key", null));

        cursor = db.displayUserRecords(userID);
        cursor.moveToLast();

        getCurrentWeight = Double.parseDouble(cursor.getString(0));
        getCurrentBodyFat = Double.parseDouble(cursor.getString(1));
        getLastDate = cursor.getString(2);

        getFirstDate = sharedPreferences.getString("register_date", null);
        SimpleDateFormat inputFormat = new SimpleDateFormat("MMM dd, yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM dd, yyyy");

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(inputFormat.parse(getLastDate));
            outputFormat.format(c.getTime());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        welcomeUserText.setText("Welcome " + getFirstName +"!");
        lastDate.setText("From " + outputFormat.format(c.getTime()));

        addNewMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measureActivity = new Intent(getActivity(), BodyMeasurementsActivity.class);
                startActivity(measureActivity);
            }
        });

        weightDifference = getGoalWeight - getCurrentWeight;
        weightDifference = Math.round(weightDifference * 100);
        weightDifference = weightDifference/100;

        bodyFatDifference = getGoalBodyFat - getCurrentBodyFat;
        bodyFatDifference = Math.round(bodyFatDifference * 100);
        bodyFatDifference = bodyFatDifference/100;

        if (weightDifference < 0){
            weightDifferenceOutput = Double.toString(weightDifference);
        }

        else{
            weightDifferenceOutput = "+" + Double.toString(weightDifference);
        }

        if (bodyFatDifference < 0){
            bodyFatDifferenceOutput = Double.toString(bodyFatDifference);
        }

        else{
            bodyFatDifferenceOutput = "+" + Double.toString(bodyFatDifference);
        }

        progressCards.add(new ProgressData( "Current Weight",getCurrentWeight + "lbs", "Goal Weight", getGoalWeight + "lbs", "Starting Weight: " + getStartingWeight + "lbs", "(" + weightDifferenceOutput + "lbs)", "Weight", ((Math.abs(getCurrentWeight - getStartingWeight) / Math.abs(getGoalWeight - getStartingWeight)*100))));
        progressCards.add(new ProgressData( "Current Body Fat",getCurrentBodyFat + "%", "Goal Body Fat", getGoalBodyFat + "%", "Starting Body Fat: " + getStartingBodyFat + "%", "(" + bodyFatDifferenceOutput + "%)", "Body Fat", ((Math.abs(getCurrentBodyFat - getStartingBodyFat) / Math.abs(getGoalBodyFat - getStartingBodyFat)*100))));

        ProgressAdapter adapter = new ProgressAdapter(progressCards, HomeFragment.this);
        progressCardView.setAdapter(adapter);

        getWeightDP = new DataPoint[db.getUserRecords(userID, 0).size()];
        getBodyFatDP = new DataPoint[db.getUserRecords(userID, 1).size()];

        for (int i = 0; i < db.getUserRecords(userID, 0).size(); i++){
            int x = i;
            Double y = db.getUserRecords(userID, 0).get(i);
            getWeightDP[i] = new DataPoint(x,y);
        }

        for (int i = 0; i < db.getUserRecords(userID, 1).size(); i++){
            int x = i;
            Double y = db.getUserRecords(userID, 1).get(i);
            getBodyFatDP[i] = new DataPoint(x,y);
        }

        graph = (GraphView) view.findViewById(R.id.trendGraph);
        switchGraph = view.findViewById(R.id.switchButton);

        weightSeries = new LineGraphSeries<>(getWeightDP);
        bodyFatSeries = new LineGraphSeries<>(getBodyFatDP);

        graph.setTitle("Weight Trend");
        graph.addSeries(weightSeries);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getViewport().setYAxisBoundsManual(true);
        renderer = graph.getGridLabelRenderer();
        renderer.setHorizontalAxisTitle("        " + getFirstDate + "                                                               " + getLastDate);

        weightSeries.setColor(Color.rgb(95,158,160));
        bodyFatSeries.setColor(Color.rgb(95,158,160));

        weightSeries.setDrawBackground(true);
        weightSeries.setBackgroundColor(Color.argb(50, 72,209,204));
        bodyFatSeries.setDrawBackground(true);
        bodyFatSeries.setBackgroundColor(Color.argb(50, 72,209,204));

        weightSeries.setDrawDataPoints(true);
        weightSeries.setDataPointsRadius(5);
        bodyFatSeries.setDrawDataPoints(true);
        bodyFatSeries.setDataPointsRadius(5);
        switchGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clicked){
                    graph.removeAllSeries();
                    graph.addSeries(weightSeries);
                    graph.getViewport().setMinY(weightSeries.getLowestValueY() - 5);
                    graph.getViewport().setMaxY(weightSeries.getHighestValueY() + 5);
                    graph.setTitle("Weight Trend");
                    switchGraph.setText("Weight");
                    clicked = false;
                }

                else{
                    graph.removeAllSeries();
                    graph.addSeries(bodyFatSeries);
                    graph.getViewport().setMinY(bodyFatSeries.getLowestValueY() - 2);
                    graph.getViewport().setMaxY(bodyFatSeries.getHighestValueY() + 2);
                    graph.setTitle("Body Fat Trend");
                    switchGraph.setText("Body Fat");
                    clicked = true;
                }
            }
        });

        return view;
    }

    private DataPoint[] getDataPointWeight(){
        getPopulatedWeightDP = new DataPoint[]{
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

        return getPopulatedWeightDP;
    }

    private DataPoint[] getDataPointBodyFat(){
        getPopulatedBodyFatDP = new DataPoint[]{
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

        return getPopulatedBodyFatDP;
    }
}