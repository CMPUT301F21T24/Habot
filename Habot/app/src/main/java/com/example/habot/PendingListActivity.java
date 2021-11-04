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
 * This activity is created when the activity starts.
 */
public class PendingListActivity extends AppCompatActivity {

    //initialize the variables
    Button returnToFollowers;
    ListView pendingList;

    /**
     * Action when the activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendinglist);
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //find id from the layout files
        returnToFollowers = findViewById(R.id.return_button);
        pendingList = findViewById(R.id.nameList);

        returnToFollowers.setOnClickListener(new View.OnClickListener() {
            /**
             * This intent will direct users from Pending List Page to Check Follower Page
             * once users pressed the return button on the top left corner.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(PendingListActivity.this, CheckFollowerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        pendingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This allows users to check on each item in the Pending List
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }
}