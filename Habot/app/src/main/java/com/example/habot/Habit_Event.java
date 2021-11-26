package com.example.habot;

/**
 *This is a Habit_Event object
 */
public class Habit_Event {

    public String habit_name;
    public String eventtime;
    public String comment;
    public String photo;
    public String status;
    public String geolocation;

    /**
     * This is the constructor of habit_event object
     * @param habit_name
     * @param eventtime
     * @param comment
     * @param photo
     * @param status
     * @param geolocation
     */
        Habit_Event(String habit_name, String eventtime, String comment, String photo, String status, String geolocation){
            this.habit_name = habit_name;
            this.eventtime = eventtime;
            this.comment = comment;
            this.photo = photo;
            this.status = status;
            this.geolocation = geolocation;
        }

    /**
     * This will return the habit name
     * @return
     */
    public String getHabit_name() {
        return habit_name;
    }

    /**
     * This will set the parameter habit_name to the object
     * @param habit_name
     */
    public void setHabit_name(String habit_name) {
        this.habit_name = habit_name;
    }

    /**
     * This will return event time
     * @return
     */
    public String getEventtime() {
        return eventtime;
    }

    /**
     * This will set the parameter eventtime to eventtime
     * @param eventtime
     */
    public void setEventtime(String eventtime) {
        this.eventtime = eventtime;
    }

    /**
     * This will return the comment of the object
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     * This will set the parameter comment to the comment
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * This will return the photo
     * @return
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * This will take the parameter photo as input and set to the object
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * This will return the status of habit event
     * @return
     */
    public String getStatus() {
        return status;
    }

    /**
     * This will set the status to the status
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This will return the geolocation of habit event
     * @return
     */
    public String getGeolocation() {
        return geolocation;
    }

    /**
     * This will set the habit event geolocation to parameter geolocation
     * @param geolocation
     */
    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }
}
