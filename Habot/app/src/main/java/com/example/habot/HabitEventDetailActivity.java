package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This activity is the habit event detail activity
 */
public class HabitEventDetailActivity extends AppCompatActivity {

    //initialize the variables
    FirebaseFirestore db;
    EditText habit_name;
    EditText habit_comment;
    EditText habit_status;
    EditText habit_time;
    Button backtomenu;
    Button deletebutton;
    Button updatebutton;
    ArrayList<Habit_Event> habit_events;

    /**
     * This activity will be created when the activity starts
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habiteventdetail);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        int position = bundle.getInt("position");

        //get id from layout files
        habit_name = findViewById(R.id.detail_name);
        habit_comment = findViewById(R.id.detail_comment);
        habit_status = findViewById(R.id.detail_status);
        habit_time = findViewById(R.id.detail_time);
        backtomenu = findViewById(R.id.HabitDetailToMenu);
        deletebutton = findViewById(R.id.delete_new_habit_event);
        updatebutton = findViewById(R.id.update_new_habit_event);
        habit_events = new ArrayList<Habit_Event>();

        db = FirebaseFirestore.getInstance();

        //get username from the database
        DocumentReference noteRef = db.collection(Username).document("HabitEventList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            /**
             * This will get the document from the firebase
              * @param value
             * @param error
             */
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    String name = value.getString("habitevent"+Integer.toString(position+1)+"name");
                    String comment = value.getString("habitevent"+Integer.toString(position+1)+"comment");
                    String status = value.getString("habitevent"+Integer.toString(position+1)+"status");
                    String time = value.getString("habitevent"+Integer.toString(position+1)+"eventtime");

                    //show the content on the surface
                    habit_name.setText(name);
                    habit_comment.setText(comment);
                    habit_status.setText(status);
                    habit_time.setText(time);

                }
            }
        });

        backtomenu.setOnClickListener(new View.OnClickListener() {
            /**
             * Users are directed back to menu page once they click on this button
             * @param view
             */
            @Override
            public void onClick(View view) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventDetailActivity.this, HabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] stop_point = new int[1];
                HashMap<String, String> updatehabitevents = new HashMap<>();
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        for (int i = 1; ; i++) {

                                            String name = (String) documentSnapshot.getString("habitevent" + Integer.toString(i) + "name");
                                            if (name == null) {
                                                stop_point[0] = i;
                                                Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i));
                                                break;
                                            }
                                            Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + name);
                                            String comment = documentSnapshot.getString("habitevent" + Integer.toString(i) + "comment");
                                            String status = documentSnapshot.getString("habitevent" + Integer.toString(i) + "status");
                                            String eventtime = documentSnapshot.getString("habitevent"+ Integer.toString(i) + "eventtime");
                                            String photos = documentSnapshot.getString("habitevent"+Integer.toString(i)+"photos");
                                            String geolocation = documentSnapshot.getString("habitevent"+Integer.toString(i)+"geolocation");


                                            habit_events.add(new Habit_Event(name, eventtime, comment, photos, status, geolocation));

                                        }
                                        habit_events.remove(position);
                                    }
                                    for (int i = 1; i < stop_point[0]-1; i++) {
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));

                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "name", habit_events.get(i - 1).getHabit_name());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "comment", habit_events.get(i - 1).getComment());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "eventtime", habit_events.get(i - 1).getEventtime());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "status", habit_events.get(i - 1).getStatus());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "photos", habit_events.get(i - 1).getPhoto());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "geolocation", habit_events.get(i - 1).getGeolocation());


                                    }
                                    noteRef.set(updatehabitevents);
                                    Intent Jump = new Intent();
                                    Jump.setClass(HabitEventDetailActivity.this, HabitEventActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("UserName", Username);
                                    Jump.putExtras(bundle);
                                    startActivity(Jump);
                                }
                        });



            }
        });


        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> updatehabitevents = new HashMap<>();

                final int[] stop_point = new int[1];
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                for (int i = 1; ; i++) {

                                    String name = (String) documentSnapshot.getString("habitevent" + Integer.toString(i) + "name");
                                    if (name == null) {
                                        stop_point[0] = i;
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i));
                                        break;
                                    }
                                    Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + name);
                                    String comment = documentSnapshot.getString("habitevent" + Integer.toString(i) + "comment");
                                    String status = documentSnapshot.getString("habitevent" + Integer.toString(i) + "status");
                                    String eventtime = documentSnapshot.getString("habitevent"+ Integer.toString(i) + "eventtime");
                                    String photos = documentSnapshot.getString("habitevent"+Integer.toString(i)+"photos");
                                    String geolocation = documentSnapshot.getString("habitevent"+Integer.toString(i)+"geolocation");


                                    habit_events.add(new Habit_Event(name, eventtime, comment, photos, status, geolocation));
                                }
                                for (int i = 1; i < stop_point[0]; i++) {
                                    Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));
                                    if(position==(i-1)){
                                        updatehabitevents.put("habitevent"+Integer.toString(i)+"name",habit_name.getText().toString());
                                        updatehabitevents.put("habitevent"+Integer.toString(i)+"comment",habit_comment.getText().toString());
                                        updatehabitevents.put("habitevent"+Integer.toString(i)+"eventtime",habit_time.getText().toString());
                                        updatehabitevents.put("habitevent"+Integer.toString(i)+"status",habit_status.getText().toString());
                                        updatehabitevents.put("habitevent"+Integer.toString(i)+"geolocation","Somewhere");
                                        updatehabitevents.put("habitevent"+Integer.toString(i)+"photos","Nothing");
                                    }
                                    else {
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "name", habit_events.get(i - 1).getHabit_name());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "comment", habit_events.get(i - 1).getComment());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "eventtime", habit_events.get(i - 1).getEventtime());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "status", habit_events.get(i - 1).getStatus());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "photos", habit_events.get(i - 1).getPhoto());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "geolocation", habit_events.get(i - 1).getGeolocation());

                                    }
                                }
                                noteRef.set(updatehabitevents);
                                Intent Jump = new Intent();
                                Jump.setClass(HabitEventDetailActivity.this, HabitEventActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("UserName", Username);
                                Jump.putExtras(bundle);
                                startActivity(Jump);
                            }
                        });
            }
        });




    }
}
