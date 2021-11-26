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
public class TodayHabitList {
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
     * the function show the today habit listview from the today habit activity
     */
    @Test
    public void showTodayList(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnView(solo.getView(R.id.log_in_button));
        solo.clickOnView(solo.getView(R.id.Menu_habit_detail_button));
        solo.clickOnView(solo.getView(R.id.newHabits_button));
        solo.enterText((EditText) solo.getView(R.id.input_habit_name),"Test");
        solo.enterText((EditText) solo.getView(R.id.input_habit_description),"TestThisDescription");
        solo.clickOnView(solo.getView(R.id.confirm_button));
        solo.clickOnView(solo.getView(R.id.HabitDetailToMenu));
        solo.clickOnView(solo.getView(R.id.Menu_today_habit_button));
        assertTrue(solo.waitForText("Test", 1, 2000));
        solo.clickOnView(solo.getView(R.id.TodayHabitToMenu));
        solo.clickOnView(solo.getView(R.id.Menu_habit_detail_button));
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.add_habit_cancel_button));
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