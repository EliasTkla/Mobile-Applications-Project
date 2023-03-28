package com.example.fitlife;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class WorkoutsAdapter extends RecyclerView.Adapter<WorkoutsAdapter.WorkoutViewHolder> {
    ArrayList<WorkoutData> workoutData;
    Context context;
    View view;

    public WorkoutsAdapter(ArrayList<WorkoutData> data, Context context){
        this.workoutData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_workouts, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutViewHolder holder, int position) {
        WorkoutData dataList = workoutData.get(position);
        holder.nameView.setText(dataList.getWorkoutName());
        String sets = dataList.getWorkoutSets()+" sets";
        String reps = dataList.getWorkoutReps()+" reps";
        holder.setsView.setText(sets);
        holder.repsView.setText(reps);
    }

    @Override
    public int getItemCount() {
        return workoutData.size();
    }

    public static class WorkoutViewHolder extends RecyclerView.ViewHolder{

        CardView card_view;
        TextView nameView, setsView, repsView;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.workout_view);
            nameView = itemView.findViewById(R.id.workout_name);
            setsView = itemView.findViewById(R.id.workout_sets);
            repsView = itemView.findViewById(R.id.workout_reps);
        }
    }
}
