package com.example.habot;
import static org.junit.Assert.assertEquals;

import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

/**
 * This class initialize the test
 */
public class HabitEventListTest {
    public Habit_Eventlist habit_eventlist;

    /**
     * the function initialize the habit event arrayList
     */

    @Before
    public void CreateHabitList(){
        habit_eventlist = new Habit_Eventlist(null, new ArrayList<Habit_Event>());
        habit_eventlist.add(new Habit_Event("google","this","interesting","None","good","here"));

    }

    /**
     * the function test the add function of habit event list
     */
    @Test
    public void AddHabitTest(){
        int listSize = habit_eventlist.getCount();
        assertEquals(habit_eventlist.getCount(), listSize);
        habit_eventlist.AddHabit_Event(new Habit_Event("google1","this","interesting","None","good","here"));
        assertEquals(habit_eventlist.getCount(), listSize+1);
        habit_eventlist.AddHabit_Event(new Habit_Event("google2","this","interesting","None","good","here"));
        assertEquals(habit_eventlist.getCount(), listSize+2);
    }

    /**
     * the function test the delete function of habit event list
     */
    @Test
    public void DeleteHabitEventTest(){
        habit_eventlist.AddHabit_Event(new Habit_Event("google","this","interesting","None","good","here"));
        int listSize = habit_eventlist.getCount();
        assertEquals(habit_eventlist.getCount(), listSize);
        habit_eventlist.DeleteHabitEvent(0);
        assertEquals(habit_eventlist.getCount(), listSize-1);
    }

    /**
     * the function test the function of listView after the adding function
     */
    @Test
    public void GetHabitEventTest(){
        habit_eventlist = new Habit_Eventlist(null, new ArrayList<Habit_Event>());
        habit_eventlist.AddHabit_Event(new Habit_Event("google","this","interesting","None","good","here"));
        int listSize = habit_eventlist.getCount();
        String title = habit_eventlist.returnEvent(0).getHabit_name();
        assertEquals("google",title);
    }

}