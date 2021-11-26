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
public class FollowingTest {
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
    public void CheckAddRequest(){
        solo.assertCurrentActivity("Wrong Activity", LoginActivity.class);
        solo.enterText((EditText) solo.getView(R.id.UsernameLogin),"androidTest");
        solo.enterText((EditText) solo.getView(R.id.PasswordLogin),"12345");
        solo.clickOnButton("Login");
        solo.clickOnView(solo.getView(R.id.Menu_following_button));
        solo.clickOnView(solo.getView(R.id.request_button));
        solo.enterText((EditText) solo.getView(R.id.nameSearcher),"Test985");
        solo.clickOnView(solo.getView(R.id.search_button));

        assertTrue(solo.searchText("Test985"));
        assertTrue(solo.waitForText("Test985", 1, 2000));

    }
}
