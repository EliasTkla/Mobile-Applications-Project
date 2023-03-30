package com.example.fitlife;

public class ProgressData {
    private String currentTitle, goalTitle;
    private String currentTitleDisplay, goalTitleDisplay, startingTitle, progressLeft, progressTypeTitle;
    private double currentProgress;

    public ProgressData(String currentTitle, String currentTitleDisplay, String goalTitle, String goalTitleDisplay, String startingTitle, String progressLeft, String progressTypeTitle, double currentProgress){
        this.currentTitle = currentTitle;
        this.currentTitleDisplay = currentTitleDisplay;
        this.goalTitle = goalTitle;
        this.goalTitleDisplay = goalTitleDisplay;
        this.startingTitle = startingTitle;
        this.progressLeft = progressLeft;
        this.progressTypeTitle = progressTypeTitle;
        this.currentProgress = currentProgress;
    }

    public String getCurrentTitle(){
        return currentTitle;
    }

    public String getCurrentTitleDisplay(){
        return currentTitleDisplay;
    }

    public String getGoalTitle(){
        return goalTitle;
    }
    public String getGoalTitleDisplay(){
        return goalTitleDisplay;
    }

    public String getStartingTitle(){
        return startingTitle;
    }

    public String getProgressLeft(){
        return progressLeft;
    }

    public String getProgressTypeTitle(){
        return progressTypeTitle;
    }

    public double getProgress(){
        return currentProgress;
    }
}
