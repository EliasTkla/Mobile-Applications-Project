package com.example.fitlife;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    DiscoverFragment discoverFragment = new DiscoverFragment();
    SavedWorkoutsFragment savedWorkoutsFragment = new SavedWorkoutsFragment();
    ProfileFragment profileFragment = new ProfileFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.navigation_bar);

        fragmentManager.beginTransaction().replace(R.id.page_container, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.home:
                    fragmentManager.beginTransaction().replace(R.id.page_container, homeFragment).commit();
                    return true;
                case R.id.discover:
                    fragmentManager.beginTransaction().replace(R.id.page_container, discoverFragment).commit();
                    return true;
                case R.id.saved_workouts:
                    fragmentManager.beginTransaction().replace(R.id.page_container, savedWorkoutsFragment).commit();
                    return true;
                case R.id.profile:
                    fragmentManager.beginTransaction().replace(R.id.page_container, profileFragment).commit();
                    return true;
            }

            return false;
        });
    }
}