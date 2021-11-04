package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Activity that allows user to add a new habit event activity
 */
public class AddNewHabitEventActivity extends AppCompatActivity {
    Button CancelBackHabitEventButton;

    /** on create when activity starts
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set view
        setContentView(R.layout.addnewhabitevent);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        // connect with the layout file with the id
        CancelBackHabitEventButton = findViewById(R.id.cancel_new_habit_event);

        CancelBackHabitEventButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When cancel button is pressed, return to the habit event detail activity page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(AddNewHabitEventActivity.this, HabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


    }
}
