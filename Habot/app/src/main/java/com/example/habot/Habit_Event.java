package com.example.habot;

/**
 * This is a Habit_Event object
 */
public class Habit_Event {
    public Habit habit;
    public String eventTime;
    public String comment;
    public String photo;
    public String status;
    public String Geolocation;

    /**
     * This is the constructor of habit event and takes six parameter
     * @param habit
     * @param eventTime
     * @param comment
     * @param photo
     * @param status
     * @param Geolocation
     */
    Habit_Event(Habit habit, String eventTime, String comment, String photo, String status, String Geolocation){
        this.habit = habit;
        this.eventTime = eventTime;
        this.comment = comment;
        this.photo = photo;
        this.status = status;
        this.Geolocation = Geolocation;
    }

    /**
     * This takes a Habit as parameter, set the habit to habitevent
     * @param habit
     */
    public void setHabit(Habit habit) {
        this.habit = habit;
    }

    /**
     * This will get the habit of the habit event
     * @return
     *      This will return the habit of the habit event
     */
    public Habit getHabit() {
        return habit;
    }

    /**
     * This takes a string as parameterr and it will set the time of habit event
     * @param eventTime
     */
    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * This will get the time of habit event
     * @return
     *      This will return the time of habit event
     */
    public String getEventTime() {
        return eventTime;
    }

    /**
     * This takes a string of comment as parameter and this will set comment for the habit event
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * This will get the comment of the habit event
     * @return
     *      This will return the comment of the habit event
     */
    public String getComment() {
        return comment;
    }

    /**
     * This takes a photo as input and set the photo as the photo of the habit event
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * This will get the photo of the habit event
     * @return
     *      This will return the photo of the habit event
     */
    public String getPhoto() {
        return photo;

    }
}