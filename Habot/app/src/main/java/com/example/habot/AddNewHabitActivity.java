package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewHabitActivity extends AppCompatActivity {
    Button CancelBackToMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewhabit);

        CancelBackToMenuButton = findViewById(R.id.add_habit_cancel_button);

        CancelBackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(AddNewHabitActivity.this, MenuActivity.class);
                startActivity(JumpToSignUp);
            }
        });

    }
}
