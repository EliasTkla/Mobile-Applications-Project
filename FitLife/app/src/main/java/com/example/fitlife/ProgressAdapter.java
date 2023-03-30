package com.example.fitlife;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProgressAdapter extends RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>{
    ArrayList<ProgressData> progressData;
    HomeFragment context;
    View view;

    public ProgressAdapter(ArrayList<ProgressData> data, HomeFragment context){
        this.progressData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProgressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_card, parent, false);
        return new ProgressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressViewHolder holder, int position) {
        ProgressData dataList = progressData.get(position);
        holder.currentTitle.setText(dataList.getCurrentTitle());
        holder.currentTitleDisplay.setText(dataList.getCurrentTitleDisplay());
        holder.goalTitle.setText(dataList.getGoalTitle());
        holder.goalTitleDisplay.setText(dataList.getGoalTitleDisplay());
        holder.startingTitle.setText(dataList.getStartingTitle());
        holder.progressLeft.setText(dataList.getProgressLeft());
        holder.progressTypeTitle.setText(dataList.getProgressTypeTitle());

    }

    @Override
    public int getItemCount() {
        return progressData.size();
    }

    public class ProgressViewHolder extends RecyclerView.ViewHolder{

        CardView progressCard;
        TextView currentTitle, goalTitle;
        TextView currentTitleDisplay, goalTitleDisplay, startingTitle, progressLeft, progressTypeTitle;
        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
            progressCard = itemView.findViewById(R.id.progressCard);
            currentTitle = itemView.findViewById(R.id.currentTitle);
            currentTitleDisplay = itemView.findViewById(R.id.currentTitleDisplay);
            goalTitle = itemView.findViewById(R.id.goalTitle);
            goalTitleDisplay = itemView.findViewById(R.id.goalTitleDisplay);
            startingTitle = itemView.findViewById(R.id.startingTitle);
            progressLeft = itemView.findViewById(R.id.progressLeft);
            progressTypeTitle = itemView.findViewById(R.id.progressTypeTitle);
        }
    }
}
