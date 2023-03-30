package com.example.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    EditText fname, lname, email, password, weight, weightGoal, bodyFat, bodyFatGoal, height, age;
    Button next_btn, register_btn;
    TextView error_message, login_btn;
    LinearLayout personal_details, physical_details;
    SQLiteManager sqLiteManager;

    String firstName, lastName, userEmail, userPassword;
    UserData user;

    String registeredData = new SimpleDateFormat("MMM dd, yyyy").format(Calendar.getInstance().getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = findViewById(R.id.user_fname);
        lname = findViewById(R.id.user_lname);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_pwd);
        next_btn = findViewById(R.id.next_btn);
        weight = findViewById(R.id.user_weight);
        weightGoal = findViewById(R.id.user_weight_goal);
        bodyFat = findViewById(R.id.user_bodyFat);
        bodyFatGoal = findViewById(R.id.user_bodyFat_goal);
        height = findViewById(R.id.user_height);
        age = findViewById(R.id.user_age);
        register_btn = findViewById(R.id.register_btn);
        personal_details = findViewById(R.id.personal_info);
        physical_details = findViewById(R.id.physical_info);
        error_message = findViewById(R.id.error_message);
        login_btn = findViewById(R.id.to_login_btn);

        sqLiteManager = new SQLiteManager(RegisterActivity.this);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(fname.getText()) || TextUtils.isEmpty(lname.getText()) || TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText())) {
                    error_message.setText("Please fill in the fields");
                } else {
                    boolean exist = sqLiteManager.userExist(String.valueOf(email.getText()));

                    if(exist){
                        error_message.setText("Email already in use");
                    } else {
                        firstName = String.valueOf(fname.getText());
                        lastName = String.valueOf(lname.getText());
                        userEmail = String.valueOf(email.getText());
                        userPassword = String.valueOf(password.getText());

                        personal_details.setVisibility(View.GONE);
                        physical_details.setVisibility(View.VISIBLE);
                        error_message.setText("");
                    }
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(weight.getText()) || TextUtils.isEmpty(height.getText()) || TextUtils.isEmpty(age.getText())){
                    error_message.setText("Please fill in the fields");
                } else {
                    Double userWeight = Double.valueOf(String.valueOf(weight.getText()));
                    Double userWeightG = Double.valueOf(String.valueOf(weightGoal.getText()));

                    Double userBodyFat = Double.valueOf(String.valueOf(bodyFat.getText()));
                    Double userBodyFatG = Double.valueOf(String.valueOf(bodyFatGoal.getText()));

                    Double userHeight = Double.valueOf(String.valueOf(height.getText()));
                    Integer userAge = Integer.valueOf(String.valueOf(age.getText()));

                    user = new UserData(firstName, lastName, userEmail, userPassword, userWeight, userWeightG, userBodyFat, userBodyFatG, userHeight, userAge, registeredData);

                    boolean registered = sqLiteManager.addUser(user);

                    if(!registered){
                        error_message.setText("Problem registering account");
                    } else {
                        error_message.setText("");

                        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(login);
                        finish();
                    }
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
            }
        });
    }
}