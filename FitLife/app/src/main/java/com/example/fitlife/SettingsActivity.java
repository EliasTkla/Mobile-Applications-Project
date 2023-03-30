package com.example.fitlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class SettingsActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private boolean isAppLockEnabled;
    private Button backButton;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backButton = findViewById(R.id.back_profile1_button);
        fragmentManager = this.getSupportFragmentManager();

        // Get the shared preferences object
        sharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);

        // Initialize the editor
        editor = sharedPreferences.edit();

        // Check if app lock is enabled or not
        isAppLockEnabled = sharedPreferences.getBoolean("isAppLockEnabled", false);

        // Initialize the weight and height radio groups
        RadioGroup weightUnitRadioGroup = findViewById(R.id.weight_unit_radio_group);
        RadioGroup heightUnitRadioGroup = findViewById(R.id.height_unit_radio_group);

        // Set the weight unit radio button based on shared preferences
        if (sharedPreferences.getString("weightUnit", "kg").equals("kg")) {
            weightUnitRadioGroup.check(R.id.kg_radio_button);
        } else {
            weightUnitRadioGroup.check(R.id.lbs_radio_button);
        }

        // Set the height unit radio button based on shared preferences
        if (sharedPreferences.getString("heightUnit", "cm").equals("cm")) {
            heightUnitRadioGroup.check(R.id.cm_radio_button);
        } else {
            heightUnitRadioGroup.check(R.id.in_radio_button);
        }

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Set the app lock button text based on shared preferences
        Button appLockButton = findViewById(R.id.app_lock_button);
        if (isAppLockEnabled) {
            appLockButton.setText("Disable App Lock");
        } else {
            appLockButton.setText("Enable App Lock");
        }

        // Set the click listener for the app lock button
        appLockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isAppLockEnabled = !isAppLockEnabled;
                if (isAppLockEnabled) {
                    appLockButton.setText("Disable App Lock");
                    Toast.makeText(getApplicationContext(), "App Lock Enabled", Toast.LENGTH_SHORT).show();
                } else {
                    appLockButton.setText("Enable App Lock");
                    Toast.makeText(getApplicationContext(), "App Lock Disabled", Toast.LENGTH_SHORT).show();
                }
                // Save the app lock status to shared preferences
                editor.putBoolean("isAppLockEnabled", isAppLockEnabled);
                editor.apply();
            }
        });

        // Set the click listener for the logout button
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear();
                editor.apply();

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

}