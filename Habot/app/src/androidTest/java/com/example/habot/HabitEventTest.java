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

/**
 * This class initialize the test and import the solo instrument.
 */
public class HabitEventTest {
    private Solo solo;
    @Rule
    public ActivityTestRule<LoginActivity> rule =
            new ActivityTestRule<>(LoginActivity.class,true,true);

    /**
     * the function set up the solo instrument
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception{
        solo = new Solo(InstrumentationRegistry.getInstrumentation(), rule.getActivity());
    }

    /**
     * the function start to initialize the first activity
     * @throws Exception
     */
    @Test
    public void start() throws Exception{
        Activity activity = rule.getActivity();
    }

    /**
     * The function test the login page work
     */
    @Test
    public void LoginTo(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
    }

    /**
     * the function test the add and delete function in the habit event detail activity
     */
    @Test
    public void CheckAddAndDeleteNewHabitEvent(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
        solo.clickOnButton("Habit Event");
        solo.clickOnView(solo.getView(R.id.newHabitsEvent_button));
        solo.enterText((EditText) solo.getView(R.id.habit_name_input),"TestThisHabitEvent");
        solo.enterText((EditText) solo.getView(R.id.status_input),"TestThisDescription");
        solo.clickOnView(solo.getView(R.id.upload_new_habit_event));
        assertTrue(solo.searchText("TestThisHabitEvent"));
        assertTrue(solo.waitForText("TestThisHabitEvent", 1, 2000));

        ArrayList<View> l = solo.getCurrentViews();
        String listString = l.get(0).toString();
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.cancel_new_habit_event));
        assertFalse(solo.waitForText(listString, 1, 2000));
    }

    /**
     * the function test the update function in the habit event detail activity
     */
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
        solo.clickOnView(solo.getView(R.id.upload_new_habit_event));
        assertTrue(solo.searchText("TestThisHabitEvent"));
        assertTrue(solo.waitForText("TestThisHabitEvent", 1, 2000));
        solo.clickInList(0);
        solo.enterText((EditText) solo.getView(R.id.habit_name_input),"renew");
        solo.clickOnView(solo.getView(R.id.upload_new_habit_event));
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.cancel_new_habit_event));
    }

    /**
     * the function finish use the solo instrument
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }

}