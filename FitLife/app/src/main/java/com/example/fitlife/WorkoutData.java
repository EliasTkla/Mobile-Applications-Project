package com.example.fitlife;

import java.io.Serializable;

public class WorkoutData implements Serializable {
    Integer workoutID;
    String workoutName;
    Integer workoutSets;
    Integer workoutReps;

    public WorkoutData(Integer workoutID, String workoutName, Integer workoutSets, Integer workoutReps){
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.workoutSets = workoutSets;
        this.workoutReps = workoutReps;
    }

    public void setWorkoutID(Integer workoutID) {
        this.workoutID = workoutID;
    }

    public Integer getWorkoutID(){
        return workoutID;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public String getWorkoutName(){
        return workoutName;
    }

    public void setWorkoutSets(Integer workoutSets) {
        this.workoutSets = workoutSets;
    }

    public Integer getWorkoutSets(){
        return workoutSets;
    }

    public void setWorkoutReps(Integer workoutReps) {
        this.workoutReps = workoutReps;
    }

    public Integer getWorkoutReps(){
        return workoutReps;
    }
}
