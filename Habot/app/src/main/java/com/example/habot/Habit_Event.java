package com.example.habot;

public class Habit_Event {
    public String habit_name;
    public String eventtime;
    public String comment;
    public String photo;
    public String status;
    public String geolocation;

        Habit_Event(String habit_name, String eventtime, String comment, String photo, String status, String geolocation){
            this.habit_name = habit_name;
            this.eventtime = eventtime;
            this.comment = comment;
            this.photo = photo;
            this.status = status;
            this.geolocation = geolocation;
        }

    public String getHabit_name() {
        return habit_name;
    }

    public void setHabit_name(String habit_name) {
        this.habit_name = habit_name;
    }

    public String getEventtime() {
        return eventtime;
    }

    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }
}
