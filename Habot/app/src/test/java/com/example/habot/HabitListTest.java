package com.example.habot;
import static android.content.ContentValues.TAG;
import static org.junit.Assert.assertEquals;

import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class HabitListTest {
    private Habitlist habitlist;

    @Before
    public void CreateHabitList(){
        habitlist = new Habitlist(null, new ArrayList<Habit>());

    }

    @Test
    public void AddHabitTest(){
        int listSize = habitlist.getCount();
        habitlist.AddHabit(new Habit("Google", "Study","8-8"));
        assertEquals(habitlist.getCount(), listSize+1);
        habitlist.AddHabit(new Habit("Reading", "Writing","8-9"));
        assertEquals(habitlist.getCount(), listSize+2);
    }

    @Test
    public void DeleteHabitTest(){
        int listSize = habitlist.getCount();
        habitlist = new Habitlist(null, new ArrayList<Habit>());
        habitlist.AddHabit(new Habit("Basketball","exercising","9-13"));
        assertEquals(habitlist.getCount(), listSize+1);
        habitlist.DeleteHabit(0);
        assertEquals(habitlist.getCount(), listSize);
    }

    @Test
    public void GetHabitTest(){
        int listSize = habitlist.getCount();
        habitlist = new Habitlist(null, new ArrayList<Habit>());
        habitlist.AddHabit(new Habit("Basketball","exercising","9-13"));
        String title = habitlist.returnHabit(0).gettitle();
        assertEquals(title,"Basketball");

    }
}