package com.example.habot;

import java.util.ArrayList;
import java.util.Date;

public class User {
    private String Name;
    private Date DateOfBirth;
    private int Age;
    private String Sex;
    private String AccountName;
    private String UserLocation;
    public ArrayList<Habit> HabitList;
    public ArrayList<Habit_Event> HabitEventList;
    public ArrayList<Request> RequestRecieveList;
    public ArrayList<Request> RequestSendList;

    User(String name, Date dateOfBirth, int age, String sex, String accountName,
         String userLocation){
        this.Name = name;
        this.DateOfBirth = dateOfBirth;
        this.Age = age;
        this.Sex = sex;
        this.AccountName = accountName;
        this.UserLocation = userLocation;

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public String getUserLocation() {
        return UserLocation;
    }

    public void setUserLocation(String userLocation) {
        UserLocation = userLocation;
    }

    public ArrayList<Habit> getHabitList() {
        return HabitList;
    }

    public void setHabitList(ArrayList<Habit> habitList) {
        HabitList = habitList;
    }

    public ArrayList<Habit_Event> getHabitEventList() {
        return HabitEventList;
    }

    public void setHabitEventList(ArrayList<Habit_Event> habitEventList) {
        HabitEventList = habitEventList;
    }

    public ArrayList<Request> getRequestRecieveList() {
        return RequestRecieveList;
    }

    public void setRequestRecieveList(ArrayList<Request> requestRecieveList) {
        RequestRecieveList = requestRecieveList;
    }

    public ArrayList<Request> getRequestSendList() {
        return RequestSendList;
    }

    public void setRequestSendList(ArrayList<Request> requestSendList) {
        RequestSendList = requestSendList;
    }
}
