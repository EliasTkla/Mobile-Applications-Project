package com.example.fitlife;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class DiscoverFragment extends Fragment {
    View view;
    TextView routinesQuantity;
    EditText searchInput;
    Button filterButton, searchButton, clearButton;
    RecyclerView routinesView;
    BottomSheetDialog dialogFragment;
    ArrayList<String> selectedLevels;
    ArrayList<RoutineData> routines = new ArrayList<>();
    ArrayList<WorkoutData> workouts = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_discover, container, false);
        routinesView = view.findViewById(R.id.routine_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        routinesView.setLayoutManager(layoutManager);
        searchInput = view.findViewById(R.id.search_input);
        filterButton = view.findViewById(R.id.filter_button);
        searchButton = view.findViewById(R.id.search_button);
        clearButton = view.findViewById(R.id.clear_button);
        routinesQuantity = view.findViewById(R.id.result_number);
        dialogFragment = new BottomSheetDialog(requireActivity());

        for(int i = 1; i < 11; i++){
            workouts.add(new WorkoutData(i, "workout "+i, 5, 12));
        }

        routines.add(new RoutineData(1, "PPL x2 Program", "r/Fitness", "Intermediate", 6, 12, workouts));
        routines.add(new RoutineData(2, "Upp/Low x2 Program", "r/Fitness", "Beginner", 4, 6, workouts));
        routines.add(new RoutineData(3, "Strongman Program", "r/Fitness", "Advanced", 3, 24, workouts));
        routines.add(new RoutineData(4, "Upp/Low x2 Program", "r/Fitness", "Beginner", 4, 6, workouts));
        routines.add(new RoutineData(5, "Strongman Program", "r/Fitness", "Advanced", 3, 24, workouts));
        routines.add(new RoutineData(6, "Strongman Program", "r/Fitness", "Advanced", 3, 24, workouts));
        routines.add(new RoutineData(7, "Upp/Low x2 Program", "r/Fitness", "Beginner", 4, 6, workouts));
        routines.add(new RoutineData(8, "Strongman Program", "r/Fitness", "Advanced", 3, 24, workouts));

        String resultsNum = routines.size()+" results";
        routinesQuantity.setText(resultsNum);
        RoutineAdapter adapter = new RoutineAdapter(routines, DiscoverFragment.this);
        routinesView.setAdapter(adapter);

        searchInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    filterButton.setVisibility(View.GONE);
                    searchButton.setVisibility(View.VISIBLE);
                } else if(TextUtils.isEmpty(searchInput.getText())){
                    filterButton.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(searchInput.getText())){
                    searchButton.setVisibility(View.GONE);
                    filterButton.setVisibility(View.GONE);
                    clearButton.setVisibility(View.VISIBLE);

                    ArrayList<RoutineData> filteredRoutines = new ArrayList<>();

                    for(RoutineData routine : routines){
                        if(routine.getTitle().toUpperCase().contains(String.valueOf(searchInput.getText()).toUpperCase())){
                            filteredRoutines.add(routine);
                        }
                    }

                    String resultsNum = filteredRoutines.size()+" results";
                    routinesQuantity.setText(resultsNum);
                    RoutineAdapter adapter = new RoutineAdapter(filteredRoutines, DiscoverFragment.this);
                    routinesView.setAdapter(adapter);
                } else {
                    clearButton.setVisibility(View.GONE);
                    filterButton.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearButton.setVisibility(View.GONE);
                filterButton.setVisibility(View.VISIBLE);
                searchInput.setText(null);

                ArrayList<RoutineData> filteredRoutines = new ArrayList<>(routines);

                String resultsNum = filteredRoutines.size()+" results";
                routinesQuantity.setText(resultsNum);
                RoutineAdapter adapter = new RoutineAdapter(filteredRoutines, DiscoverFragment.this);
                routinesView.setAdapter(adapter);
            }
        });

        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
                dialogFragment.show();
            }
        });

        return view;
    }

    public void filteredRoutines(){
        Log.d("filter levels", String.valueOf(selectedLevels));
        ArrayList<RoutineData> filteredRoutines = new ArrayList<>();

        for(RoutineData routine : routines){
            if(selectedLevels.get(0) == null && selectedLevels.get(1) == null && selectedLevels.get(2) == null){
                filteredRoutines.add(routine);
            } else {
                for (int i = 0; i < 3; i++) {
                    if (routine.getLevel().equalsIgnoreCase(selectedLevels.get(i))) {
                        filteredRoutines.add(routine);
                    }
                }
            }
        }

        String resultsNum = filteredRoutines.size()+" results";
        routinesQuantity.setText(resultsNum);
        RoutineAdapter adapter = new RoutineAdapter(filteredRoutines, DiscoverFragment.this);
        routinesView.setAdapter(adapter);
    }

    private void levelsSelected(CheckBox l1, CheckBox l2, CheckBox l3){
        selectedLevels.clear();
        if(l1.isChecked()){
            selectedLevels.add((String) l1.getText());
        } else {
            selectedLevels.add(null);
        }

        if(l2.isChecked()){
            selectedLevels.add((String) l2.getText());
        } else {
            selectedLevels.add(null);
        }

        if(l3.isChecked()){
            selectedLevels.add((String) l3.getText());
        } else {
            selectedLevels.add(null);
        }
    }

    private void createDialog(){
        Button applyFilter;
        TextView resetFilter;
        CheckBox l1, l2, l3;
        @SuppressLint("InflateParams") View view = getLayoutInflater().inflate(R.layout.bottom_sheet_filter, null, false);

        l1 = view.findViewById(R.id.beginner_level);
        l2 = view.findViewById(R.id.intermediate_level);
        l3 = view.findViewById(R.id.advanced_level);
        applyFilter = view.findViewById(R.id.apply_filter);
        resetFilter = view.findViewById(R.id.reset_filter);

        if(!selectedLevels.isEmpty()) {
            if (selectedLevels.get(0) != null) {
                l1.setChecked(true);
            }

            if (selectedLevels.get(1) != null) {
                l2.setChecked(true);
            }

            if (selectedLevels.get(2) != null) {
                l3.setChecked(true);
            }
        }

        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelsSelected(l1, l2, l3);
                filteredRoutines();

                dialogFragment.dismiss();
            }
        });

        resetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                l1.setChecked(false);
                l2.setChecked(false);
                l3.setChecked(false);

                levelsSelected(l1, l2, l3);
                filteredRoutines();

                dialogFragment.dismiss();
            }
        });

        dialogFragment.setContentView(view);
    }
}