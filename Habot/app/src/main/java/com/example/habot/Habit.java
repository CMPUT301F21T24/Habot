package com.example.habot;

public class Habit {
    private Stirng title;
    private String reason;
    private Date date;

    Habit(String title, Stirng Reason, Date date)
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
    public String settitle(String title){
        this.title = title;
    }
    public String setreason(String reason){
        this.reason = reason;
    }
    public String setdate(String date){
        this.date = date;
    }
}
