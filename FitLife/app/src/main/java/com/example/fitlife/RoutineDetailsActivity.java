package com.example.fitlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoutineDetailsActivity extends AppCompatActivity {
    TextView title, createdBy, level, frequency, length;
    Button backButton, saveButton;
    RecyclerView workoutsView;
    boolean saved = false;
    ArrayList<WorkoutData> workouts = new ArrayList<>();
    SQLiteManager sqLiteManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_details);
        workoutsView = findViewById(R.id.workout_list);
        workoutsView.setLayoutManager(new LinearLayoutManager(this));
        title = findViewById(R.id.program_name);
        createdBy = findViewById(R.id.program_creator);
        level = findViewById(R.id.program_level);
        frequency = findViewById(R.id.program_frequency);
        length = findViewById(R.id.program_length);
        backButton = findViewById(R.id.back_button);
        saveButton = findViewById(R.id.save_routine_button);
        sqLiteManager = new SQLiteManager(getApplicationContext());

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        int userID = Integer.parseInt(sharedPreferences.getString("user_id", null));

        Intent details = getIntent();

        int id = details.getIntExtra("routine id", 0);

        RoutineData routine = sqLiteManager.getRoutine(id);

        title.setText(routine.getTitle());
        createdBy.setText(routine.getCreator());
        level.setText(routine.getLevel());
        String freq = routine.getFrequency()+"x week";
        String len = routine.getLength()+" weeks long";
        frequency.setText(freq);
        length.setText(len);

        workouts = routine.getWorkoutsList();

        WorkoutsAdapter adapter = new WorkoutsAdapter(workouts, RoutineDetailsActivity.this);
        workoutsView.setAdapter(adapter);

        sqLiteManager.isRoutineSaved(routine.getId(), routine.getId());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saved) {
                    sqLiteManager.saveUserRoutine(userID, id,  false);
                    saveButton.setBackgroundResource(R.drawable.ic_bookmarked);
                    saved = true;
                } else {
                    sqLiteManager.unSaveUserRoutine(userID ,id);
                    saveButton.setBackgroundResource(R.drawable.ic_bookmark);
                    saved = false;
                }
            }
        });
    }
}
