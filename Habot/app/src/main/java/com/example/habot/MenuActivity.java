package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    Button following_button;
    Button today_habit_button;
    Button follower_button;
    Button habit_event_button;
    Button habit_detail_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        following_button = findViewById(R.id.Menu_following_button);
        today_habit_button = findViewById(R.id.Menu_today_habit_button);
        follower_button = findViewById(R.id.Menu_follower_button);
        habit_event_button = findViewById(R.id.Menu_habit_event_button);
        habit_detail_button = findViewById(R.id.Menu_habit_detail_button);

        following_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(MenuActivity.this, CheckFollowingActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        today_habit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(MenuActivity.this, TodayHabitActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        follower_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(MenuActivity.this, CheckFollowerActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        habit_event_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(MenuActivity.this, HabitEventActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        habit_detail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(MenuActivity.this, HabitDetailAcitivity.class);
                startActivity(JumpToSignUp);
            }
        });



    }
}
