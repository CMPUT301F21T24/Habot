package com.example.habot;

import java.util.ArrayList;
import java.util.Date;

/**
 * This class initialize an user
 */
public class User {
    public String Username;
    public String Password;
    public ArrayList<Habit> HabitList;
    public ArrayList<Habit_Event> HabitEventList;
    public ArrayList<Request> RequestRecieveList;
    public ArrayList<Request> RequestSendList;

    /**
     * This is the constructor of User
     * @param Username
     * @param Password
     */
    public User(String Username, String Password){
        this.Username = Username;
        this.Password = Password;
    }

    /**
     * This function will get the username
     * @return
     *      This will return username
     */
    public String getUsername() {
        return Username;
    }

    /**
     * This takes username as input and set the username
     * @param Username
     *      This will return nothing
     */
    public void setUsername(String Username) {
        this.Username = Username;
    }

    /**
     * This will get the password
     * @return Password
     *      This will return the password of the user
     */
    public String getPassword() {
        return Password;
    }

    /**
     * This will take a Stirng as parameter and set the password of the user object
     * @param Password
     *
     */
    public void setPassword(String Password) {
        this.Password = Password;
    }

    /**
     * This will get habit list
     * @return HabitList
     *      This will return the habit list
     */
    public ArrayList<Habit> getHabitList() {
        return HabitList;
    }

    /**
     * This will take habitlist as parameter and set the parameter to the user object
     * @param habitList
     */
    public void setHabitList(ArrayList<Habit> habitList) {
        HabitList = habitList;
    }

    /**
     * This takes no parameter
     * @return
     *      This will return HabitEventList
     */
    public ArrayList<Habit_Event> getHabitEventList() {
        return HabitEventList;
    }

    /**
     * This takes habitEventList as parameter and set the HabitEventList to the user object
     * @param habitEventList
     */
    public void setHabitEventList(ArrayList<Habit_Event> habitEventList) {
        HabitEventList = habitEventList;
    }

    /**
     * This will return RequestReceiveList
     * @return
     */
    public ArrayList<Request> getRequestRecieveList() {
        return RequestRecieveList;
    }

    /**
     * This takes an ArrayList requestReceiveList as input and
     * this will set the receiveList to the user object
     * @param requestRecieveList
     */
    public void setRequestRecieveList(ArrayList<Request> requestRecieveList) {
        RequestRecieveList = requestRecieveList;
    }

    /**
     * This will takes no parameter
     * @return
     *      This will return RequestSendList.
     */
    public ArrayList<Request> getRequestSendList() {
        return RequestSendList;
    }

    /**
     * This takes requestSendList as parameter and set it to user object
     * @param requestSendList
     */
    public void setRequestSendList(ArrayList<Request> requestSendList) {
        RequestSendList = requestSendList;
    }
}