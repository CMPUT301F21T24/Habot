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
 * This activity will check if there are any other doer wants to follow user.
 */
public class CheckFollowerActivity extends AppCompatActivity {

    Button TodayHabitBackButton;
    ListView userFollower;
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
        setContentView(R.layout.followers);

        // set values to variables
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        // connect layout file with view id
        TodayHabitBackButton = findViewById(R.id.BackButton);
        userFollower = findViewById(R.id.userFollowers);

        requestArrayList = new ArrayList<Request>();
        db = FirebaseFirestore.getInstance();

        //在这里写listview 显示
        requestArrayAdapter = new sentRequestList(this,requestArrayList);
        userFollower.setAdapter(requestArrayAdapter);
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(Username);
        DocumentReference noteRef = collectionReference.document("RequestRecievedList");
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
                    if (condition.equals("True")){
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
                // jump from check follower activity to menu activity
                Jump.setClass(CheckFollowerActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                // initiate the jump
                startActivity(Jump);
            }
        });


        userFollower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * Check doer who is following user
             * @param adapterView
             * @param view
             * @param position
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String followerName = requestArrayList.get(position).getSender();
                String followerStatus = requestArrayList.get(position).getCondition();
                Intent JumpToRequest = new Intent();
                // jump from check follower activity to pending list activity
                JumpToRequest.setClass(CheckFollowerActivity.this,PendingListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("followerName",followerName);
                bundle.putString("followerStatus",followerStatus);
                JumpToRequest.putExtras(bundle);
                // initiate the jump
                startActivity(JumpToRequest);
            }
        });

    }
}
