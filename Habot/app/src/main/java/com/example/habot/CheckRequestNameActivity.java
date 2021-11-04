package com.example.habot;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Check other doer's follow request.
 */
public class CheckRequestNameActivity extends AppCompatActivity {
    // set initial variables
    Button returnToFollowers;
    Button searchTheName;
    EditText nameSearcher;
    ListView nameList;

    /**
     * Action after this activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // find the view checkrequestname layout file and connect it with view id
        setContentView(R.layout.checkrequestname);
        // set values to variables

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        // connect layout file with view id
        returnToFollowers = findViewById(R.id.return_button);
        searchTheName = findViewById(R.id.search_button);
        nameSearcher = findViewById(R.id.nameSearcher);
        nameList = findViewById(R.id.listName);

        returnToFollowers.setOnClickListener(new View.OnClickListener() {
            /**
             * When back button on the top left of the screen is clicked, jump back to check following activity page.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                // jump from check request activity to check following activity
                Jump.setClass(CheckRequestNameActivity.this, CheckFollowingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                // initiate the jump
                startActivity(Jump);
            }
        });

        final String theID = nameSearcher.getText().toString();
        searchTheName.setOnClickListener(new View.OnClickListener() {
            /**
             * Input name and press search button to search other doer
             * @param view
             */
            @Override
            public void onClick(View view) {
                //use theID to find
            }
        });

        nameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * List all the doers that user have followed
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