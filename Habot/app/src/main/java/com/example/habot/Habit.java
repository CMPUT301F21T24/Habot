package com.example.habot;

import java.util.Date;

public class Habit {
    private String title;
    private String reason;
    private String date;

    Habit(String title, String reason, String date)
    {
        this.title = title;
        this.reason = reason;
        this.date = date;
    }

    public String gettitle(){
        return this.title;
    }
    public String getreason(){
        return this.reason;
    }
    public String getdate(){
        return this.date;
    }
    public void settitle(String title){
        this.title = title;
    }
    public void setreason(String reason){
        this.reason = reason;
    }
    public void setdate(String date){
        this.date = date;
    }
}
