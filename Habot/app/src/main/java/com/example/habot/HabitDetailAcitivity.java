package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HabitDetailAcitivity extends AppCompatActivity {
    Button HabitDetailBackButton;
    Button AddHabitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitdetail);

        HabitDetailBackButton = findViewById(R.id.HabitDetailToMenu);
        AddHabitButton = findViewById(R.id.newHabits_button);

        HabitDetailBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(HabitDetailAcitivity.this, MenuActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        AddHabitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(HabitDetailAcitivity.this, AddNewHabitActivity.class);
                startActivity(JumpToSignUp);
            }
        });
    }
}
