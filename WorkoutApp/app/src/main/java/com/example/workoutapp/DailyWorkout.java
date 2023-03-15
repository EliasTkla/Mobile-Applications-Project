package com.example.workoutapp;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DailyWorkout extends AppCompatActivity {

    ListView l_Workouts;
    ArrayAdapter workoutsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_workout2);
        l_Workouts = findViewById(R.id.l_Workouts);
        dbObject db = new dbObject(DailyWorkout.this);
        updateList(db);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyWorkout.this, MainActivity.class);
                startActivity(intent);
            }
        });

        l_Workouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 WorkoutModel wModel = (WorkoutModel) adapterView.getItemAtPosition(i);
                 db.deleteRecord(wModel);
                 updateList(db);
                 Toast.makeText(DailyWorkout.this, "Successfully deleted Workout", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateList(dbObject db) {
        workoutsAdapter = new ArrayAdapter<WorkoutModel>(DailyWorkout.this, android.R.layout.simple_list_item_1, db.getWorkouts());
        l_Workouts.setAdapter(workoutsAdapter);
    }
}