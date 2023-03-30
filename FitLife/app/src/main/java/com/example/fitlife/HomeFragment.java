package com.example.fitlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    String firstName, weight, weightGoal, bodyFat, bodyFatGoal;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf;
    String getLastDate;
    FloatingActionButton addNewMeasurements;
    RecyclerView progressCardView;
    ArrayList<ProgressData> progressCards = new ArrayList<>();
    GraphView graph;
    LineGraphSeries<DataPoint> weightSeries, bodyFatSeries;
    DataPoint[] weightDP, bodyFatDP;
    GridLabelRenderer renderer;
    Button switchGraph;
    boolean clicked = false;

//    ProgressBar weightProgressBar, bodyFatProgressBar;

    SharedPreferences sharedPreferences;
    SQLiteManager sqLiteManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        welcomeUserText = view.findViewById(R.id.welcomeUser);
        addNewMeasurements = view.findViewById(R.id.recordNewMeasurements);
        lastDate = view.findViewById(R.id.lastDate);
        progressCardView = view.findViewById(R.id.progressList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        progressCardView.setLayoutManager(layoutManager);

        sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        getLastDate = sdf.format(calendar.getTime());

        sharedPreferences = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        firstName = sharedPreferences.getString("fname_key", null);
        weight = sharedPreferences.getString("weight_key", null);


        welcomeUserText.setText("Welcome " + firstName +"!");
        lastDate.setText("From " + getLastDate);

        addNewMeasurements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent measureActivity = new Intent(getActivity(), BodyMeasurementsActivity.class);
                startActivity(measureActivity);
            }
        });

        progressCards.add(new ProgressData( "Current Weight", String.valueOf(weight), "Goal Weight", "170lbs", "Starting Weight: 135lbs", "(+15lbs)", "Weight"));
        progressCards.add(new ProgressData( "Current Body Fat","25%", "Goal Body Fat", "13%", "Starting Body Fat: 30%", "(-12%)", "Body Fat"));

        ProgressAdapter adapter = new ProgressAdapter(progressCards, HomeFragment.this);
        progressCardView.setAdapter(adapter);

//        weightProgressBar = view.findViewById(R.id.weightProgress);
//        bodyFatProgressBar = view.findViewById(R.id.bodyFatProgress);
//        weightProgressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(162,210,223)));
//        bodyFatProgressBar.setProgressTintList(ColorStateList.valueOf(Color.rgb(162,210,223)));

        graph = (GraphView) view.findViewById(R.id.trendGraph);
        switchGraph = view.findViewById(R.id.switchButton);

        weightSeries = new LineGraphSeries<>(getDataPointWeight());
        bodyFatSeries = new LineGraphSeries<>(getDataPointBodyFat());

        graph.setTitle("Weight Trend");
        graph.addSeries(weightSeries);

        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);
        graph.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.VERTICAL);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);
        graph.getViewport().setYAxisBoundsManual(true);
        renderer = graph.getGridLabelRenderer();
        renderer.setHorizontalAxisTitle("        "+"Mar 23, 2023" + "                                                               " + "Apr 25, 2023");

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