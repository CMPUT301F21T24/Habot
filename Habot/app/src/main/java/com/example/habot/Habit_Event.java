package com.example.habot;

public class Habit_Event {
    public Habit habit;
    public String eventTime;
    public String comment;
    public String photo;
    public String status;
    public String Geolocation;

        Habit_Event(Habit habit, String eventTime, String comment, String photo, String status, String Geolocation){
            this.habit = habit;
            this.eventTime = eventTime;
            this.comment = comment;
            this.photo = photo;
            this.status = status;
            this.Geolocation = Geolocation;
        }

        public void setHabit(Habit habit) {
            this.habit = habit;
        }

        public Habit getHabit() {
            return habit;
        }

        public void setEventTime(String eventTime) {
            this.eventTime = eventTime;
        }

        public String getEventTime() {
            return eventTime;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getComment() {
            return comment;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getPhoto() {
            return photo;

        }
}
