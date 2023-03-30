package com.example.fitlife;

import java.util.ArrayList;

public class RoutineData {
    private int id;
    private String title;
    private String creator;
    private String level;
    private int frequency;
    private int length;
    private ArrayList<WorkoutData> workoutsList;

    public RoutineData(int id, String title, String creator, String level, int frequency, int length, ArrayList<WorkoutData> workoutsList){
        this.id = id;
        this.title  = title;
        this.creator = creator;
        this.level = level;
        this.frequency = frequency;
        this.length = length;
        this.workoutsList = workoutsList;
    }

    public void setID(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setCreator(String creator){
        this.creator = creator;
    }

    public String getCreator(){
        return creator;
    }

    public void setLevel(String level){
        this.level = level;
    }

    public String getLevel(){
        return level;
    }

    public void setFrequency(Integer frequency){
        this.frequency= frequency;
    }

    public Integer getFrequency(){
        return frequency;
    }

    public void setLength(Integer length){
        this.length = length;
    }

    public Integer getLength(){
        return length;
    }

    public void setWorkoutsList(ArrayList<WorkoutData> workoutsList) {
        this.workoutsList = workoutsList;
    }

    public ArrayList<WorkoutData> getWorkoutsList() {
        return workoutsList;
    }
}
