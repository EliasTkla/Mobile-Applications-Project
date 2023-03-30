package com.example.fitlife;

import java.io.Serializable;

public class WorkoutData implements Serializable {
    Integer workoutID;
    String workoutName;
    String workoutDay;
    Integer workoutSets;
    Integer workoutReps;
    Integer routineID;

    public WorkoutData(Integer workoutID, String workoutName, String workoutDay, Integer workoutSets, Integer workoutReps, Integer routineID){
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.workoutDay = workoutDay;
        this.workoutSets = workoutSets;
        this.workoutReps = workoutReps;
        this.routineID = routineID;
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

    public void setWorkoutDay(String workoutDay) {
        this.workoutDay = workoutDay;
    }

    public String getWorkoutDay(){
        return workoutDay;
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

    public void setWorkoutRoutineID(Integer routine) {
        this.routineID = routine;
    }

    public Integer getWorkoutRoutineID(){
        return routineID;
    }
}