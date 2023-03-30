package com.example.fitlife;

public class UserData {
    //TODO Potentially add any weight/height/age fields for use in db and app
    //Variables
    private String fname;
    private String lname;
    private String email;
    private String password;
    private Double weight;
    private Double height;
    private Integer age;

    //Constructor
    public UserData(String fname, String lname, String email, String password, Double weight, Double height, Integer age) {
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.age = age;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getWeight(){
        return weight;
    }

    public void setWeight(Double newWeight){
        this.weight = newWeight;
    }

    public Double getHeight(){
        return height;
    }

    public void setHeight(Double newHeight){
        this.weight = newHeight;
    }

    public Integer getAge(){
        return age;
    }

    public void setAge(Integer newAge){
        this.age = newAge;
    }

    //TOString Method
    @Override
    public String toString() {
        //TODO create method body for toString for any necessary string output <- potentially for Elias/Supreyo/Anuhya for any display needs
        return null;
    }
}