package com.example.fitlife;

public class UserData {
    //TODO Potentially add any weight/height/age fields for use in db and app
    //Variables
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Double weight;
    private Double height;
    private Integer age;

    //Constructor
    public UserData(String firstName, String lastName, String email, String password, Double weight, Double height, Integer age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.age = age;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setUserId(Integer id){
        this.userId = id;
    }

    public String getFname() {
        return firstName;
    }

    public void setFname(String firstName) {
        this.firstName = firstName;
    }

    public String getLname() {
        return lastName;
    }

    public void setLname(String lastName) {this.lastName = lastName; }

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
}
