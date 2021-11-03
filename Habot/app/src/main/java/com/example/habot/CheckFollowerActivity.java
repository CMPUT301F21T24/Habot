package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class CheckFollowerActivity extends AppCompatActivity {

    Button TodayHabitBackButton;
    Button FollowersPendingButton;
    ListView userFollower;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.followers);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        TodayHabitBackButton = findViewById(R.id.BackButton);
        FollowersPendingButton = findViewById(R.id.request_button);
        userFollower = findViewById(R.id.userFollowers);


        TodayHabitBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(CheckFollowerActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        FollowersPendingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent JumpToPendingList = new Intent();
                JumpToPendingList.setClass(CheckFollowerActivity.this,PendingListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                JumpToPendingList.putExtras(bundle);
                startActivity(JumpToPendingList);

            }
        });

        userFollower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //
            }
        });

    }
}
