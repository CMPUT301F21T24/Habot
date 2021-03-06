package com.example.habot;

import java.util.Date;

/**
 * This is a habit object
 */
public class Habit {
    public String title;
    public String reason;
    public String date;
    public String time;
    public String privacy;

    /**
     * This is the constructor of the habit object, it takes three parameter
     * @param title
     * @param reason
     * @param date
     */
    public Habit(String title, String reason, String date, String time, String privay)
    {
        this.title = title;
        this.reason = reason;
        this.date = date;
        this.time = time;
        this.privacy = privay;
    }

    /**
     * this will get the title of the habit
     * @return
     *      this will return the title of the habit
     */
    public String gettitle(){
        return this.title;
    }

    /**
     * this will get the reason of the habit
     * @return
     *      this will return the reason of the habit
     */
    public String getreason(){
        return this.reason;
    }

    /**
     * this will get the date of the habit
     * @return
     *      this will return the date of the habit
     */
    public String getdate(){
        return this.date;
    }

    /**
     * this takes a string title as parameter, and set the habit title to title
     * @param title
     */
    public void settitle(String title){
        this.title = title;
    }

    /**
     * this takes a string reason as parameter, and set the habit reason to reason
     * @param reason
     */
    public void setreason(String reason){
        this.reason = reason;
    }

    /**
     * this takes a string date as parameter, and set the habit date to date
     * @param date
     */
    public void setdate(String date){
        this.date = date;
    }

    /**
     * this will get the occur time of the habit
     * @return
     *      this will get the occur time of the habit
     */
    public String getTime() {
        return time;
    }

    /**
     * this takes a string time as input
     * @param time
     */
    public void setTime(String time) {
        time = time;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}

