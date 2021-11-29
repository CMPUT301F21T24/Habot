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
 * This is the habit event activity
 */
public class HabitEventActivity extends AppCompatActivity {
    Button HabitEventBackButton;
    Button AddNewEventButton;
    ArrayAdapter<Habit_Event> habiteventadapter;
    ArrayList<Habit_Event> habiteventlist;
    FirebaseFirestore db;
    ListView habiteventdetail;

    /**
     * This will create when the activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitevent);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //get id from layout files
        HabitEventBackButton = findViewById(R.id.HabitEventToMenu);
        AddNewEventButton = findViewById(R.id.newHabitsEvent_button);

        HabitEventBackButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This will intent to menu page once user press the button
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


        AddNewEventButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This will intent to add new habit event page once user press the button
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventActivity.this, AddNewHabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        //get id from the layout files
        habiteventdetail = findViewById(R.id.HabitDetail);
        habiteventlist = new ArrayList<Habit_Event>();

        habiteventadapter = new Habit_Eventlist(this,habiteventlist);

        habiteventdetail.setAdapter(habiteventadapter);

        db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection(Username).document("HabitEventList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            /**
             * This will get Snapshot from firestore database instantly.
             * @param value
             * @param error
             */
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                habiteventlist.clear();
                for (int i =1;;i++)
                {

                    String habit_name = (String) value.getString("habitevent"+Integer.toString(i)+"name");
                    if(habit_name==null){
                        break;
                    }
                    Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + habit_name);

                    //get data from the database
                    String eventtime = value.getString("habitevent"+Integer.toString(i)+"eventtime");
                    String comment = value.getString("habitevent"+Integer.toString(i)+"comment");
                    String photo = value.getString("habitevent"+Integer.toString(i)+"photo");
                    String status = value.getString("habitevent"+Integer.toString(i)+"status");
                    String geolocation = value.getString("habitevent"+Integer.toString(i)+"geolocation");

                    //add data to the habit event list
                    habiteventlist.add(new Habit_Event(habit_name, eventtime, comment, photo, status, geolocation));

                }
                habiteventadapter.notifyDataSetChanged();
            }
        });

        habiteventdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This takes 4 parameter and when user click on the button, user will be intent to
             * Habit Event Detail Page.
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventActivity.this, HabitEventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putInt("position",i);
                bundle.putBoolean("maps",false);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

    }
}
