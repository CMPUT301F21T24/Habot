package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

/**
 * This activity will check doers that user is following.
 */
public class CheckFollowingActivity extends AppCompatActivity {
    // initialize variables
    Button TodayHabitBackButton;
    Button FollowersPendingButton;
    ListView userFollowing;
    FirebaseFirestore db;
    ArrayList<Request> requestArrayList;
    ArrayAdapter<Request> requestArrayAdapter;






    /**
     * Action after this activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.following);
        // set values to variables
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        // connect layout file with view id
        TodayHabitBackButton = findViewById(R.id.back_buttom);
        FollowersPendingButton = findViewById(R.id.request_button);
        userFollowing = findViewById(R.id.userFollowing);
        requestArrayList = new ArrayList<Request>();
        db = FirebaseFirestore.getInstance();

        //在这里写listview 显示
        requestArrayAdapter = new sentRequestList(this,requestArrayList);
        userFollowing.setAdapter(requestArrayAdapter);
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(Username);
        DocumentReference noteRef = collectionReference.document("RequestSendList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                requestArrayList.clear();
                for (int i = 1 ; ; i++){

                    String title = (String) value.getString("UserRequest"+Integer.toString(i)+"SenderName");
                    if(title==null){
                        break;
                    }
                    String condition = (String) value.getString("UserRequest"+Integer.toString(i)+"ConditionStatus");
                    if (condition.equals("False")){
                        continue;
                    }


                    requestArrayList.add(new Request(title,condition));

                }
                requestArrayAdapter.notifyDataSetChanged();

            }
        });





        TodayHabitBackButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When back button on the top left of the screen is clicked, jump back to main menu page.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                // jump from check following activity to menu activity
                Jump.setClass(CheckFollowingActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                // initiate the jump
                startActivity(Jump);
            }
        });

        FollowersPendingButton.setOnClickListener(new View.OnClickListener() {
            /**
             * When followers pending button is clicked, jump to pending list activity.
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent JumpToRequest = new Intent();
                // jump from check follower activity to pending list activity
                JumpToRequest.setClass(CheckFollowingActivity.this,CheckRequestNameActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                JumpToRequest.putExtras(bundle);
                // initiate the jump
                startActivity(JumpToRequest);
            }
        });

        userFollowing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Check doer who is following user
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
