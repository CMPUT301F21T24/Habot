package com.example.habot;

public class HabitEventList {
  package com.example.habot;

import java.util.ArrayList;
import java.util.List;

public class HabitEventList {
  private List<Habit_Event> HabitEventsList = new ArrayList<>();

    /**
     * This add a habitEvent to the habitEventList
     * @param habit_event
     *   this is a argument added to the habitEventList
     */
    public void addHabitEvent(Habit_Event habit_event){
        HabitEventsList.add(habit_event);
    }

    /**
     * this returns the HabitEventLists
     * @return
     *      it takes no argument and return a list
     */
    public List<Habit_Event> getHabitEvents(){
        List<Habit_Event> list = HabitEvents;
        return list;
    }
}

}
