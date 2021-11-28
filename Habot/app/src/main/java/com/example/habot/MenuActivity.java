package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

/**
 * create menu page when this activity starts.
 */
public class MenuActivity extends AppCompatActivity {
    Button following_button;
    Button today_habit_button;
    Button follower_button;
    Button habit_event_button;
    Button habit_detail_button;
    Button log_out_button;
    TextView usernameText;

    /**
     * Action after activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        //put username in the bundle and get data from database in every page
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //find view id from layout files
        following_button = findViewById(R.id.Menu_following_button);
        today_habit_button = findViewById(R.id.Menu_today_habit_button);
        follower_button = findViewById(R.id.Menu_follower_button);
        habit_event_button = findViewById(R.id.Menu_habit_event_button);
        habit_detail_button = findViewById(R.id.Menu_habit_detail_button);
        log_out_button = findViewById(R.id.log_out_button);
        usernameText = findViewById(R.id.displayUsername);

        usernameText.setText(Username);

        following_button.setOnClickListener(new View.OnClickListener() {
            /**
             * This intent will direct users from Menu Page to Check Following Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(MenuActivity.this, CheckFollowingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        today_habit_button.setOnClickListener(new View.OnClickListener() {
            /**
             * This intent will direct users from Menu Page to Today Habit Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(MenuActivity.this, TodayHabitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        follower_button.setOnClickListener(new View.OnClickListener() {

            /**
             * This intent will direct users from Menu Page to Check Follower Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(MenuActivity.this, CheckFollowerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        habit_event_button.setOnClickListener(new View.OnClickListener() {

            /**
             * This intent will direct users from Menu Page to Habit Event Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(MenuActivity.this, HabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        habit_detail_button.setOnClickListener(new View.OnClickListener() {

            /**
             * This intent will direct users from Menu Page to Habit Detail Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(MenuActivity.this, HabitDetailAcitivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        log_out_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(MenuActivity.this, LoginActivity.class);
                startActivity(Jump);
            }
        });


    }
}
