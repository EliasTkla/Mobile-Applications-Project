package com.example.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    EditText fname, lname, email, username, password;
    Button next_btn, register_btn, login_btn;
    LinearLayout personal_details, physical_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fname = findViewById(R.id.user_fname);
        lname = findViewById(R.id.user_lname);
        email = findViewById(R.id.user_email);
        username = findViewById(R.id.user_username);
        password = findViewById(R.id.user_pwd);
        next_btn = findViewById(R.id.next_btn);
        register_btn = findViewById(R.id.register_btn);
        personal_details = findViewById(R.id.personal_info);
        physical_details = findViewById(R.id.physical_info);
        login_btn = findViewById(R.id.to_login_btn);

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                personal_details.setVisibility(View.GONE);
                physical_details.setVisibility(View.VISIBLE);
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(login);
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
