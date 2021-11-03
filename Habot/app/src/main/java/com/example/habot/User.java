package com.example.habot;

import java.util.ArrayList;
import java.util.Date;

public class User {
    public String Username;
    public String Password;
    public ArrayList<Habit> HabitList;
    public ArrayList<Habit_Event> HabitEventList;
    public ArrayList<Request> RequestRecieveList;
    public ArrayList<Request> RequestSendList;

    public User(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
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