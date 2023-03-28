package com.example.fitlife;

import java.util.List;

public class RoutinesModel {
    //Variables
    private int id;
    private String routineName;
    private List<WorkoutModel> workoutsList;

    //Constructor
    public RoutinesModel() {
    }

    public RoutinesModel(int id, String routineName, List<WorkoutModel> workoutsList) {
        this.id = id;
        this.routineName = routineName;
        this.workoutsList = workoutsList;
    }

    //Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoutineName() {
        return routineName;
    }

    public void setRoutineName(String workoutName) {
        this.routineName = workoutName;
    }

    public List<WorkoutModel> getWorkoutsList() {
        return workoutsList;
    }

    public void setWorkoutsList(int weight) {
        this.workoutsList = workoutsList;
    }


    //TOString Method
    @Override
    public String toString() {
        return  routineName;
    }
}
