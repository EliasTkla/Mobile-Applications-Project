package com.example.fitlife;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager = getSupportFragmentManager();
    BottomNavigationView bottomNavigationView;

    HomeFragment homeFragment = new HomeFragment();
    DiscoverFragment discoverFragment = new DiscoverFragment();
    SavedWorkoutsFragment savedWorkoutsFragment = new SavedWorkoutsFragment();
    ProfileFragment profileFragment = new ProfileFragment();
    Fragment currentActive;
    FragmentTransaction ft;
    SQLiteManager sqLiteManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteManager = new SQLiteManager(MainActivity.this);

        bottomNavigationView = findViewById(R.id.navigation_bar);

        fragmentManager.beginTransaction().add(R.id.page_container, homeFragment).hide(homeFragment).commit();
        fragmentManager.beginTransaction().add(R.id.page_container, discoverFragment).hide(discoverFragment).commit();
        fragmentManager.beginTransaction().add(R.id.page_container, savedWorkoutsFragment).hide(savedWorkoutsFragment).commit();
        fragmentManager.beginTransaction().add(R.id.page_container, profileFragment).hide(profileFragment).commit();

        fragmentManager.beginTransaction().show(homeFragment).commit();
        currentActive = homeFragment;

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        int userID = Integer.parseInt(sharedPreferences.getString("user_id", null));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.home){
                    fragmentManager.beginTransaction().hide(currentActive).show(homeFragment).commit();
                    currentActive = homeFragment;
                    return true;
                } else if(item.getItemId() == R.id.discover){
                    fragmentManager.beginTransaction().hide(currentActive).show(discoverFragment).commit();
                    currentActive = discoverFragment;
                    return true;
                } else if(item.getItemId() == R.id.saved_workouts){
                    if (savedWorkoutsFragment != null) {
                    fragmentManager.beginTransaction().hide(currentActive).show(savedWorkoutsFragment).commit();
                    currentActive = savedWorkoutsFragment;
                    }
                    return true;
                } else if(item.getItemId() == R.id.profile){
                    fragmentManager.beginTransaction().hide(currentActive).show(profileFragment).commit();
                    currentActive = profileFragment;
                    return true;
                }

                return false;
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();

            if (view instanceof EditText) {
                Rect outRect = new Rect();
                view.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int)ev.getRawX(), (int)ev.getRawY())) {
                    view.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }

        return super.dispatchTouchEvent(ev);
    }
}