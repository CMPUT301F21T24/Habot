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

public class CheckRequestNameActivity extends AppCompatActivity {
    Button returnToFollowers;
    Button searchTheName;
    EditText nameSearcher;
    ListView nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkrequestname);
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        returnToFollowers = findViewById(R.id.return_button);
        searchTheName = findViewById(R.id.search_button);
        nameSearcher = findViewById(R.id.nameSearcher);
        nameList = findViewById(R.id.listName);

        returnToFollowers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(CheckRequestNameActivity.this, CheckFollowingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        final String theID = nameSearcher.getText().toString();
        searchTheName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //用theID找到
            }
        });

        nameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //
            }
        });




    }
}