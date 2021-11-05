package com.example.habot;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.robotium.solo.Solo;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class HabitEventTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class,true,true);

    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    @Test
    public void LoginTo(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
    }

    @Test
    public void CheckAddAndDeleteNewHabitEvent(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
        solo.clickOnButton("Habit Event");
        solo.clickOnButton("Add New Habit Event");
        solo.enterText((EditText) solo.getView(R.id.habit_name_input),"TestThisHabitEvent");
        solo.enterText((EditText) solo.getView(R.id.status_input),"TestThisDescription");
        solo.clickOnButton("Upload");
        assertTrue(solo.searchText("TestThisHabitEvent"));
        assertTrue(solo.waitForText("TestThisHabitEvent", 1, 2000));

        ArrayList<View> l = solo.getCurrentViews();
        String listString = l.get(0).toString();
        solo.clickInList(0);
        solo.clickOnButton("Delete");
        assertFalse(solo.waitForText(listString, 1, 2000));
    }


    @Test
    public void testEventUpdate(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
        solo.clickOnButton("Habit Event");
        solo.clickOnView(solo.getView(R.id.newHabitsEvent_button));

        solo.enterText((EditText) solo.getView(R.id.habit_name_input),"TestThisHabitEvent");
        solo.enterText((EditText) solo.getView(R.id.status_input),"TestThisDescription");
        solo.clickOnButton("Upload");
        assertTrue(solo.searchText("TestThisHabitEvent"));
        assertTrue(solo.waitForText("TestThisHabitEvent", 1, 2000));
        solo.clickInList(0);
        solo.enterText((EditText) solo.getView(R.id.habit_name_input),"renew");
        solo.clickOnButton("Update");
        solo.clickInList(0);
        solo.clickOnButton("Delete");
    }
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}