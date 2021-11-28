package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

public class CheckFollowingHabits extends AppCompatActivity {
    Button BackButton;
    ListView UserFollowingHabitsListView;
    FirebaseFirestore db;
    ArrayAdapter<Habit> FollowingUserHabitsAdapter;
    ArrayList<Habit> FollowingUserHabitList;
    TextView Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkfollowinghabits);
        final int[] stop_point = new int[1];


        BackButton = findViewById(R.id.UserHabitListToFollower);
        UserFollowingHabitsListView = findViewById(R.id.FollowingUsersHabitDetail);
        FollowingUserHabitList = new ArrayList<Habit>();
        FollowingUserHabitsAdapter = new Habitlist(this, FollowingUserHabitList);
        UserFollowingHabitsListView.setAdapter(FollowingUserHabitsAdapter);
        Title = findViewById(R.id.Title);
        // intent the username
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        String FollowingUserName = bundle.getString("FollowingUserName");
        Title.setText(FollowingUserName + " Habits");
        Log.d("TAG", ">>>> check following habits --->> FollowingUserName: "+ FollowingUserName);
        // set back button
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                // jump from check follower activity to menu activity
                Jump.setClass(CheckFollowingHabits.this, CheckFollowingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                // initiate the jump
                startActivity(Jump);
            }
        });

        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(FollowingUserName);
        DocumentReference noteRef = collectionReference.document("HabitList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                FollowingUserHabitList.clear();
                for (int i = 1;; i++){
                    //get the habit name
                    String title = (String) value.getString("habit"+Integer.toString(i)+"name");
                    //The habit can't be null
                    if(title== null){
                        stop_point[0] = i;
                        break;
                    }
                    //get Habit reson and date
                    String reason = value.getString("habit"+Integer.toString(i)+"reason");
                    String date = value.getString("habit"+Integer.toString(i)+"date");
                    String time = value.getString("habit"+Integer.toString(i)+"time");
                    String privacy = value.getString("habit" + Integer.toString(i)+"privacy");

                    if (privacy.equals("public")){
                        FollowingUserHabitList.add(new Habit(title, reason, date, time, privacy));
                    }
                }
                // add Habit to the dataset
                FollowingUserHabitsAdapter.notifyDataSetChanged();
            }
        });

        UserFollowingHabitsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                        String title = FollowingUserHabitList.get(i).gettitle();
                        String reason = FollowingUserHabitList.get(i).getreason();
                        String date = FollowingUserHabitList.get(i).getdate();
                        String time = FollowingUserHabitList.get(i).getTime();
                        String privacy = FollowingUserHabitList.get(i).getPrivacy();
                        Intent Jump = new Intent();
                        Jump.setClass(CheckFollowingHabits.this, AddNewHabitFromFollowing.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("UserName", Username);
                        bundle.putString("FollowingUserName", FollowingUserName);
                        bundle.putString("title", title);
                        bundle.putString("reason", reason);
                        bundle.putString("date", date);
                        bundle.putString("time", time);
                        bundle.putString("privacy", privacy);
                        Jump.putExtras(bundle);
                        startActivity(Jump);

            }
        });







    }
}
