package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity will check if there are any other doer wants to follow user.
 */
public class CheckFollowerActivity extends AppCompatActivity {

    Button TodayHabitBackButton;
    Button FollowersPendingButton;
    ListView userFollower;

    /**
     * Action after this activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers);

        // set values to variables
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        // connect layout file with view id
        TodayHabitBackButton = findViewById(R.id.BackButton);
        FollowersPendingButton = findViewById(R.id.request_button);
        userFollower = findViewById(R.id.userFollowers);


        TodayHabitBackButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When back button on the top left of the screen is clicked, jump back to main menu page.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                // jump from check follower activity to menu activity
                Jump.setClass(CheckFollowerActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                // initiate the jump
                startActivity(Jump);
            }
        });

        FollowersPendingButton.setOnClickListener(new View.OnClickListener(){
            /**
             * When followers pending button is clicked, jump to pending list activity.
             * @param view
             */
            @Override
            public void onClick(View view){
                Intent JumpToPendingList = new Intent();
                // jump from check follower activity to pending list activity
                JumpToPendingList.setClass(CheckFollowerActivity.this,PendingListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                JumpToPendingList.putExtras(bundle);
                // initiate the jump
                startActivity(JumpToPendingList);

            }
        });

        userFollower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Check doer who is following user
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //
            }
        });

    }
}
