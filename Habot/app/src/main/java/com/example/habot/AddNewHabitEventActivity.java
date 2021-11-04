package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddNewHabitEventActivity extends AppCompatActivity {
    Button CancelBackHabitEventButton;
    Button Uploadbutton;
    EditText comment;
    EditText status;
    EditText time;
    EditText habit_name;
    FirebaseFirestore db;
    ArrayList<Habit_Event> habit_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewhabitevent);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        comment = findViewById(R.id.comment_input);
        status = findViewById(R.id.status_input);
        time = findViewById(R.id.time_input);
        habit_name = findViewById(R.id.habit_name_input);

        db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection(Username).document("HabitEventList");
        CancelBackHabitEventButton = findViewById(R.id.cancel_new_habit_event);
        Uploadbutton = findViewById(R.id.upload_new_habit_event);
        habit_events = new ArrayList<Habit_Event>();

        CancelBackHabitEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(AddNewHabitEventActivity.this, HabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        Uploadbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int[] stop_point = new int[1];
                HashMap<String,String> newhabitevent = new HashMap<>();
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    for(int i = 1;;i++){
                                        String name = documentSnapshot.getString("habitevent"+Integer.toString(i)+"name");
                                        if(name == null){
                                            stop_point[0]=i;
                                            break;
                                        }
                                        String habitcomment = documentSnapshot.getString("habitevent"+Integer.toString(i)+"comment");

                                        String habiteventtime= documentSnapshot.getString("habitevent"+Integer.toString(i)+"eventtime");
                                        String habitstatus = documentSnapshot.getString("habitevent"+Integer.toString(i)+"status");
                                        String habitphoto = documentSnapshot.getString("habitevent"+Integer.toString(i)+"photos");
                                        String habitgeolocation = documentSnapshot.getString("habitevent"+Integer.toString(i)+"geolocation");
                                        habit_events.add(new Habit_Event(name,habiteventtime,habitcomment, habitphoto, habitstatus, habitgeolocation));
                                    }
                                    habit_events.add(new Habit_Event(habit_name.getText().toString(),time.getText().toString(),comment.getText().toString(),"nothing",status.getText().toString(),"somewhere"));
                                    for (int i = 1; i <= stop_point[0]; i++) {
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));


                                        newhabitevent.put("habitevent" + Integer.toString(i) + "name", habit_events.get(i - 1).getHabit_name());
                                        newhabitevent.put("habitevent" + Integer.toString(i) + "comment", habit_events.get(i - 1).getComment());
                                        newhabitevent.put("habitevent" + Integer.toString(i) + "eventtime", habit_events.get(i - 1).getEventtime());
                                        newhabitevent.put("habitevent" + Integer.toString(i) + "status", habit_events.get(i - 1).getStatus());
                                        newhabitevent.put("habitevent" + Integer.toString(i) + "photos", habit_events.get(i - 1).getPhoto());
                                        newhabitevent.put("habitevent" + Integer.toString(i) + "geolocation", habit_events.get(i - 1).getGeolocation());
                                    }
                                    noteRef.set(newhabitevent);
                                    Intent Jump = new Intent();
                                    Jump.setClass(AddNewHabitEventActivity.this, HabitEventActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("UserName", Username);
                                    Jump.putExtras(bundle);
                                    startActivity(Jump);
                                }
                            }
                        });
            }
        });

    }
}
