package com.example.fitlife;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder> {
    ArrayList<RoutineData> routineData;
    Fragment context;
    View view;

    boolean createdFlag;

    public RoutineAdapter(ArrayList<RoutineData> data, Fragment context){
        this.routineData = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.routine_card, parent, false);
        return new RoutineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineViewHolder holder, int position) {
        RoutineData dataList = routineData.get(position);
        holder.titleView.setText(dataList.getTitle());
        String creator = "By: "+dataList.getCreator();
        holder.creatorView.setText(creator);
        holder.levelView.setText(dataList.getLevel());
        String freq = dataList.getFrequency()+"x week";
        String length = dataList.getLength()+" weeks long";
        holder.frequencyView.setText(freq);
        holder.lengthView.setText(length);

        holder.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent detailsPage = new Intent(view.getContext(), RoutineDetailsActivity.class);

                detailsPage.putExtra("routine id", dataList.getId());

                detailsPage.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                view.getContext().startActivity(detailsPage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return routineData.size();
    }

    public static class RoutineViewHolder extends RecyclerView.ViewHolder{

        CardView card_view;
        TextView titleView, creatorView, levelView, frequencyView, lengthView;

        public RoutineViewHolder(@NonNull View itemView) {
            super(itemView);
            card_view = itemView.findViewById(R.id.card_view);
            titleView = itemView.findViewById(R.id.routine_name);
            creatorView = itemView.findViewById(R.id.routine_creator);
            levelView = itemView.findViewById(R.id.routine_level);
            frequencyView = itemView.findViewById(R.id.routine_frequency);
            lengthView = itemView.findViewById(R.id.routine_length);
        }
    }
}