package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity is created when the activity start
 */
public class HabitEventActivity extends AppCompatActivity {
    //Initialize the variables
    Button HabitEventBackButton;
    Button AddNewEventButton;

    /**
     * Action when the activity start
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitevent);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //find id from layout files
        HabitEventBackButton = findViewById(R.id.HabitEventToMenu);
        AddNewEventButton = findViewById(R.id.newHabitsEvent_button);

        HabitEventBackButton.setOnClickListener(new View.OnClickListener() {

            /**
             * This intent will direct users from Habit Event Page to Menu Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        AddNewEventButton.setOnClickListener(new View.OnClickListener() {

            /**
             * This intent will direct users from Habit Event Page to Add New Event Activity
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventActivity.this, AddNewHabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });
    }
}
