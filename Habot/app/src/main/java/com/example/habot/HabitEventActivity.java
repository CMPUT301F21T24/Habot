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

public class HabitEventActivity extends AppCompatActivity {
    Button HabitEventBackButton;
    Button AddNewEventButton;
    ArrayAdapter<Habit_Event> habiteventadapter;
    ArrayList<Habit_Event> habiteventlist;
    FirebaseFirestore db;
    ListView habiteventdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitevent);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        HabitEventBackButton = findViewById(R.id.HabitEventToMenu);
        AddNewEventButton = findViewById(R.id.newHabitsEvent_button);

        HabitEventBackButton.setOnClickListener(new View.OnClickListener() {
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

        habiteventdetail = findViewById(R.id.HabitDetail);
        habiteventlist = new ArrayList<Habit_Event>();

        habiteventadapter = new Habit_Eventlist(this,habiteventlist);

        habiteventdetail.setAdapter(habiteventadapter);

        db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection(Username).document("HabitEventList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
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
                    String eventtime = value.getString("habitevent"+Integer.toString(i)+"eventtime");
                    String comment = value.getString("habitevent"+Integer.toString(i)+"comment");
                    String photo = value.getString("habitevent"+Integer.toString(i)+"photo");
                    String status = value.getString("habitevent"+Integer.toString(i)+"status");
                    String geolocation = value.getString("habitevent"+Integer.toString(i)+"geolocation");
                    habiteventlist.add(new Habit_Event(habit_name, eventtime, comment, photo, status, geolocation));

                }
                habiteventadapter.notifyDataSetChanged();
            }
        });

        habiteventdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventActivity.this, HabitEventDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putInt("position",i);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

    }
}
