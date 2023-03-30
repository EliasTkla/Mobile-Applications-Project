package com.example.fitlife;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ProfileFragment extends Fragment {
    View view;
    FragmentManager fragmentManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        fragmentManager = getActivity().getSupportFragmentManager();

        TextView editProfileTextView = view.findViewById(R.id.viewEdit);
        Button settingsButton=view.findViewById(R.id.viewSettings);
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditProfileFragment editProfileFragment = new EditProfileFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.page_container, editProfileFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingsFragment settingsFragment = new SettingsFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.page_container, settingsFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}
