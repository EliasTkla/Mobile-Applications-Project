package com.example.fitlife;

public class UserData {
    //TODO Potentially add any weight/height/age fields for use in db and app
    //Variables
    private int user_id;
    private String fname;
    private String lname;
    private String email;
    private String username;
    private String password;

    //Constructor
    public UserData(int user_id, String fname, String lname, String email, String username, String password) {
        this.user_id = user_id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    //Getters and Setters
    public UserData() {
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {this.lname = lname; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //TOString Method
    @Override
    public String toString() {
        //TODO create method body for toString for any necessary string output <- potentially for Elias/Supreyo/Anuhya for any display needs
        return null;
    }
}
