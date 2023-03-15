package com.example.workoutapp;

public class WorkoutModel {

    //Variables
    private int id;
    private String workoutName;
    private int weight;
    private int reps;
    private int sets;

    //Constructor
    public WorkoutModel(int id, String workoutName, int weight, int reps, int sets) {
        this.id = id;
        this.workoutName = workoutName;
        this.weight = weight;
        this.reps = reps;
        this.sets = sets;
    }

    //Getters and Setters
    public WorkoutModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    //TOString Method
    @Override
    public String toString() {
        return  workoutName + " "+
                + weight +
                "LBs, " + reps +
                "x" + sets;
    }
}
