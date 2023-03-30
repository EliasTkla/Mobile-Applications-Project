package com.example.fitlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;
    Button login_btn;
    TextView error_message1, register_btn;

    SharedPreferences sharedPreferences;
    SQLiteManager sqLiteManager;
    UserData user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        error_message1 = findViewById(R.id.error_message1);
        email = findViewById(R.id.user_email);
        password = findViewById(R.id.user_pwd);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.to_register_btn);

        sqLiteManager = new SQLiteManager(LoginActivity.this);
        sharedPreferences = getSharedPreferences("user_info", Context.MODE_PRIVATE);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(email.getText()) || TextUtils.isEmpty(password.getText())){
                    error_message1.setText("Please fill in the fields");
                } else {
                    boolean loggedIn = sqLiteManager.verifyUser(String.valueOf(email.getText()), String.valueOf(password.getText()));

                    if(!loggedIn){
                        error_message1.setText("Incorrect Email or Password");
                    } else {
                        user = sqLiteManager.getUserInfo(String.valueOf(email.getText()), String.valueOf(password.getText()));

                        if(user == null){
                            error_message1.setText("Problem verifying user");
                        } else {
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("user_id", String.valueOf(user.getUserId()));
                            editor.putString("fname_key", user.getFname());
                            editor.putString("lname_key", user.getLname());
                            editor.putString("email_key", user.getEmail());
                            editor.putString("password_key", user.getPassword());
                            editor.putString("weight_key", String.valueOf(user.getWeight()));
                            editor.putString("height_key", String.valueOf(user.getHeight()));
                            editor.putInt("age_key", user.getAge());

                            editor.apply();

                            error_message1.setText("");

                            Intent home = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(home);
                            finish();
                        }
                    }
                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(register);
            }
        });
    }
}
