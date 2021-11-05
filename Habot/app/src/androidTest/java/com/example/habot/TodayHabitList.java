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

public class TodayHabitList {
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
    public void showTodayList(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnView(solo.getView(R.id.log_in_button));
        solo.clickOnView(solo.getView(R.id.Menu_habit_detail_button));
        solo.clickOnView(solo.getView(R.id.newHabits_button));
        solo.enterText((EditText) solo.getView(R.id.input_habit_name),"TestThisHabit");
        solo.enterText((EditText) solo.getView(R.id.input_habit_description),"TestThisDescription");
        solo.clickOnView(solo.getView(R.id.confirm_button));
        solo.clickOnView(solo.getView(R.id.HabitDetailToMenu));
        solo.clickOnView(solo.getView(R.id.Menu_today_habit_button));
        assertTrue(solo.waitForText("TestThisHabit", 1, 2000));
        solo.clickOnView(solo.getView(R.id.TodayHabitToMenu));
        solo.clickOnView(solo.getView(R.id.Menu_habit_detail_button));
        solo.clickInList(0);
        solo.clickOnView(solo.getView(R.id.cancel_new_habit_event));
    }

    @After
    public void tearDown() throws Exception{
        solo.finishOpenedActivities();
    }
}