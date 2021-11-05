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
public class HabitDetailTest {
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
     * the function test the add function in the habit detail activity
     */
    @Test
    public void CheckAddNewHabit(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnView(solo.getView(R.id.log_in_button));
        solo.clickOnView(solo.getView(R.id.Menu_habit_detail_button));
        solo.clickOnView(solo.getView(R.id.newHabits_button));
        solo.enterText((EditText) solo.getView(R.id.input_habit_name),"TestThisHabit");
        solo.enterText((EditText) solo.getView(R.id.input_habit_description),"TestThisDescription");
        solo.clickOnView(solo.getView(R.id.confirm_button));
        assertTrue(solo.searchText("TestThisHabit"));
        assertTrue(solo.waitForText("TestThisHabit", 1, 2000));
        solo.clickInList(0);
    }

    /**
     * the function test delete function in the habit detail activity
     */
    @Test
    public void CheckDeleteNewHabit(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
        solo.clickOnButton("Habit Detail");
        solo.clickOnButton("Add New Habit");
        solo.enterText((EditText) solo.getView(R.id.input_habit_name),"MaybeThisDelete");
        solo.enterText((EditText) solo.getView(R.id.input_habit_description),"MaybeThisDelete");
        solo.clickOnButton("Add");

        ArrayList<View> l = solo.getCurrentViews();
        String listString = l.get(0).toString();
        solo.clickInList(0);
        solo.clearEditText((EditText) solo.getView(R.id.input_habit_name));
        solo.enterText((EditText) solo.getView(R.id.input_habit_name),"ThisMustBeDelete");
        solo.clearEditText((EditText) solo.getView(R.id.input_habit_description));
        solo.enterText((EditText) solo.getView(R.id.input_habit_description),"ThisMustBeDelete");
        solo.clickOnButton("Update");
        solo.clickInList(0);
        solo.clickOnButton("Delete");
        assertFalse(solo.waitForText(listString, 1, 2000));

    }

    /**
     * the function test the update function in the habit detail activity
     */
    @Test
    public void CheckUpdateNewHabit(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
        solo.clickOnButton("Habit Detail");
        solo.clickOnButton("Add New Habit");
        solo.enterText((EditText) solo.getView(R.id.input_habit_name),"TestThisDeleteHabit");
        solo.enterText((EditText) solo.getView(R.id.input_habit_description),"TestThisDescription");
        solo.clickOnButton("Add");
        solo.clickInList(0);
        solo.clearEditText((EditText) solo.getView(R.id.input_habit_name));
        solo.enterText((EditText) solo.getView(R.id.input_habit_name),"Renewwwwwwww");
        solo.clearEditText((EditText) solo.getView(R.id.input_habit_description));
        solo.enterText((EditText) solo.getView(R.id.input_habit_description),"Renewwwwwwww");
        solo.clickOnButton("Update");
        assertTrue(solo.waitForText("Renewwwwwwww", 1, 2000));
        solo.clickInList(0);
        solo.clickOnButton("Delete");
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
