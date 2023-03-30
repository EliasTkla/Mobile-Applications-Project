package com.example.fitlife;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ProfileFragment extends Fragment {
    View view;
    TextView userName, userEmail, userWeight, userBodyFat, userHeight, userAge;
    Button editProfileTextView, settingsButton;

    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        userName = view.findViewById(R.id.viewName);
        userEmail = view.findViewById(R.id.viewEmail);
        userWeight = view.findViewById(R.id.viewWeight);
        userBodyFat = view.findViewById(R.id.viewBodyFat);
        userHeight = view.findViewById(R.id.viewHeight);
        userAge = view.findViewById(R.id.viewAge);
        editProfileTextView = view.findViewById(R.id.viewEdit);
        settingsButton=view.findViewById(R.id.viewSettings);

        sharedPreferences = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        fragmentManager = getActivity().getSupportFragmentManager();

        String fullName = sharedPreferences.getString("fname_key", null)+" "+sharedPreferences.getString("lname_key", null);
        String weight = "Weight: "+sharedPreferences.getString("weight_key", null)+"lbs";
        String height = "Height: "+sharedPreferences.getString("height_key", null)+"cm";
        String bodyF = "Body Fat: "+sharedPreferences.getString("bodyFat_key", null)+"%";
        String age = "Age: "+sharedPreferences.getString("age_key", null)+" years old";

        userName.setText(fullName);
        userEmail.setText(sharedPreferences.getString("email_key", null));
        userWeight.setText(weight);
        userBodyFat.setText(bodyF);
        userHeight.setText(height);
        userAge.setText(age);

        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent details = new Intent(view.getContext(), EditProfileActivity.class);

                details.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                view.getContext().startActivity(details);
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsPage = new Intent(view.getContext(), SettingsActivity.class);

                detailsPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                view.getContext().startActivity(detailsPage);
            }
        });

        return view;
    }
}