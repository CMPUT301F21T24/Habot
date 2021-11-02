package com.example.habot;

public class Habit_Event {
  package com.example.habot;

public class Habit_Event {
  public class Habit_Event {
        private Habit habit;
        private Date eventTime;
        private String comment;
        private String photo;
        private String status;
        private String Geolocation;

        Habit_Event(Habit habit, Date eventTime, String comment, String photo, String status, String Geolocation){
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

        public void setEventTime(Date eventTime) {
            this.eventTime = eventTime;
        }

        public Date getEventTime() {
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
