package com.example.workoutapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button bt_Add, bt_View;
    EditText et_Workout, et_Weight, et_reps, et_Sets;
    ConstraintLayout mainLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_Add = findViewById(R.id.bt_Add);
        bt_View = findViewById(R.id.bt_View);
        et_Workout = findViewById(R.id.et_Workout);
        et_Weight = findViewById(R.id.et_Weight);
        et_reps = findViewById(R.id.et_reps);
        et_Sets = findViewById(R.id.et_Sets);
        mainLayout = (ConstraintLayout) findViewById(R.id.myLayout);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        bt_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkoutModel wModel = null;
                try {
                    wModel = new WorkoutModel(-1, et_Workout.getText().toString(),
                            Integer.parseInt(et_Weight.getText().toString()),
                            Integer.parseInt(et_reps.getText().toString()),
                            Integer.parseInt(et_Sets.getText().toString()));
                    Toast.makeText(MainActivity.this, "Successfully Added Workout", Toast.LENGTH_SHORT).show();
                }catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error adding Workout", Toast.LENGTH_SHORT).show();
                }
                dbObject db = new dbObject(MainActivity.this);
                db.addToTable(wModel);
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mainLayout.getWindowToken(), 0);
            }
        });

        bt_View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DailyWorkout.class);
                startActivity(intent);
            }
        });
    }

}