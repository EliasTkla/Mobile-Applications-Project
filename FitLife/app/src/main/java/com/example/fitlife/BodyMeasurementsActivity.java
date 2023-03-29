package com.example.fitlife;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class BodyMeasurementsActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    HomeFragment homeFragment = new HomeFragment();
    Fragment currentActive;
    Button addButton;
    EditText addWeight, addBodyFat, addDate;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_measurements);

        addButton = findViewById(R.id.addBtn);
        addWeight = findViewById(R.id.updateWeight);
        addBodyFat = findViewById(R.id.updateBodyFat);
        addDate = findViewById(R.id.updateDate);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double getWeight = Double.parseDouble(addWeight.getText().toString());
                double getBodyFat = Double.parseDouble(addBodyFat.getText().toString());

                Intent homePage = new Intent(BodyMeasurementsActivity.this, MainActivity.class);
                startActivity(homePage);

                Toast addedMessage = Toast.makeText(BodyMeasurementsActivity.this, "Added your new information", Toast.LENGTH_LONG);
                addedMessage.setGravity(Gravity.BOTTOM,0,200);
                addedMessage.show();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH,day);
                updateLabel();
            }
        };

        addDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(BodyMeasurementsActivity.this,date,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy", Locale.CANADA);
        addDate.setText(dateFormat.format(myCalendar.getTime()));
    }
}