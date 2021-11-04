package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This creates TodayHabitActivity when this starts.
 */
public class TodayHabitActivity extends AppCompatActivity {
    //initialize variables
    Button TodayHabitBackButton;

    /**
     * Action after create this activity
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todayhabit);

        //get username from bundle
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //find id from layout files
        TodayHabitBackButton = findViewById(R.id.TodayHabitToMenu);

        TodayHabitBackButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This intent will direct users from Today Habit Activity to Menu Activity
             * once users pressed Back button on the left top corner
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(TodayHabitActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


    }
}
