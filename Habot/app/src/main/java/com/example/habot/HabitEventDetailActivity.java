package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This activity is the habit event detail activity
 */
public class HabitEventDetailActivity extends AppCompatActivity {

    //initialize the variables
    FirebaseFirestore db;

    TextView habit_name;

    EditText habit_comment;
    EditText habit_time;
    Button geolocation;

    Button deletebutton;
    Button updatebutton;
    ArrayList<Habit_Event> habit_events;

    ImageView displayImage;

    RadioGroup status_group;
    RadioButton select_status;
    String name, comment, status, time, Dgeolocation;
    Bundle bundle;



    /**
     * This activity will be created when the activity starts
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habiteventdetail);

        bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Boolean maps = bundle.getBoolean("maps");
        int position = bundle.getInt("position");

        //get id from layout files
        habit_name = findViewById(R.id.detail_name);
        habit_comment = findViewById(R.id.detail_comment);
        status_group = findViewById(R.id.status_radio);
        habit_time = findViewById(R.id.detail_time);
        deletebutton = findViewById(R.id.delete_new_habit_event);
        updatebutton = findViewById(R.id.update_new_habit_event);
        displayImage = findViewById(R.id.display_image);
        geolocation = findViewById(R.id.geolocation);
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
                if(value.exists()) {

                    if (!maps) {
                        name = value.getString("habitevent" + Integer.toString(position + 1) + "name");
                        comment = value.getString("habitevent" + Integer.toString(position + 1) + "comment");
                        status = value.getString("habitevent" + Integer.toString(position + 1) + "status");
                        time = value.getString("habitevent" + Integer.toString(position + 1) + "eventtime");
                        Dgeolocation = value.getString("habitevent" + Integer.toString(position + 1) + "geolocation");
                        //show the content on the surface


                    } else {
                        name = bundle.getString("habitname");
                        comment = bundle.getString("habitcomment");
                        status = bundle.getString("status");
                        time = bundle.getString("habittime");
                        Dgeolocation = bundle.getString("Address");
                    }
                    habit_name.setText(name);
                    habit_comment.setText(comment);
                    habit_time.setText(time);
                    geolocation.setText(Dgeolocation);

                    if (status != null) {
                        if (status.equals("Done")) {
                            RadioButton Done = findViewById(R.id.radio_Done);
                            Done.setChecked(true);

                        } else if (status.equals("In Progress")) {
                            RadioButton inProgress = findViewById(R.id.radio_IP);
                            inProgress.setChecked(true);

                        } else if (status.equals("Not Done")) {
                            RadioButton notDone = findViewById(R.id.radio_NotDone);
                            notDone.setChecked(true);


                        }


                        FirebaseStorage mStorageRef = FirebaseStorage.getInstance();

                        String imageName = habit_name.getText().toString() + "-" + time;
                        StorageReference imageReference = mStorageRef.getReference().child(Username + "/" + imageName);


                        Glide.with(getApplicationContext())
                                .load(imageReference)
                                .into(displayImage);

                    }
                }

            }
        });

        /**
         * This onClickListener will delete a habit event
         */
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
                                                break;
                                            }
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

                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "name", habit_events.get(i - 1).getHabit_name());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "comment", habit_events.get(i - 1).getComment());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "eventtime", habit_events.get(i - 1).getEventtime());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "status", habit_events.get(i - 1).getStatus());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "photos", habit_events.get(i - 1).getPhoto());
                                        updatehabitevents.put("habitevent" + Integer.toString(i) + "geolocation", habit_events.get(i - 1).getGeolocation());


                                    }

                                    //Delete the corresponding image from Firestore Storage
                                    FirebaseStorage mStorageRef = FirebaseStorage.getInstance();

                                    String imageName = habit_name.getText().toString() + "-" + habit_time.getText().toString();
                                    StorageReference imageReference = mStorageRef.getReference().child(Username+"/"+imageName);

                                    //Delete image
                                    imageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(HabitEventDetailActivity.this, "Cloud Image Deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(HabitEventDetailActivity.this, "Cloud Image Delete Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    });

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

        /**
         * This will update the details of a habit event after the user edit the habit event
         */
        updatebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> updatehabitevents = new HashMap<>();

                final int[] stop_point = new int[1];

                if (habit_time.getText().toString().equals("")) {
                    Toast.makeText(HabitEventDetailActivity.this, "Time Cannot be Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    noteRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                /**
                                 * if success, do the actions
                                 * @param documentSnapshot
                                 */
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {

                                    String statusText;
                                    if (status_group.getCheckedRadioButtonId() == R.id.radio_Done) {
                                        statusText = "Done";
                                    } else if (status_group.getCheckedRadioButtonId() == R.id.radio_IP) {
                                        statusText = "In Progress";
                                    } else {
                                        statusText = "Not Done";
                                    }

                                    for (int i = 1; ; i++) {

                                        String name = (String) documentSnapshot.getString("habitevent" + Integer.toString(i) + "name");
                                        if (name == null) {
                                            stop_point[0] = i;
                                            break;
                                        }
                                        String comment = documentSnapshot.getString("habitevent" + Integer.toString(i) + "comment");
                                        String status = documentSnapshot.getString("habitevent" + Integer.toString(i) + "status");
                                        String eventtime = documentSnapshot.getString("habitevent" + Integer.toString(i) + "eventtime");
                                        String photos = documentSnapshot.getString("habitevent" + Integer.toString(i) + "photos");
                                        String geolocation = documentSnapshot.getString("habitevent" + Integer.toString(i) + "geolocation");


                                        habit_events.add(new Habit_Event(name, eventtime, comment, photos, status, geolocation));
                                    }
                                    for (int i = 1; i < stop_point[0]; i++) {
                                        if (position == (i - 1)) {
                                            updatehabitevents.put("habitevent" + Integer.toString(i) + "name", habit_name.getText().toString());
                                            updatehabitevents.put("habitevent" + Integer.toString(i) + "comment", habit_comment.getText().toString());
                                            updatehabitevents.put("habitevent" + Integer.toString(i) + "eventtime", habit_time.getText().toString());
                                            updatehabitevents.put("habitevent" + Integer.toString(i) + "status", statusText);
                                            updatehabitevents.put("habitevent" + Integer.toString(i) + "geolocation", geolocation.getText().toString());
                                            updatehabitevents.put("habitevent" + Integer.toString(i) + "photos", "Nothing");
                                        } else {
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
            }
        });

        geolocation.setOnClickListener(new View.OnClickListener() {
            /**
             * Geolication button will be jump to the HabiteventDetailActivity and get a clear view of that location.
             * @param v
             */
            @Override
            public void onClick(View v) {
                bundle.putString("habitname",habit_name.getText().toString());
                bundle.putString("habitcomment",habit_comment.getText().toString());
                bundle.putString("habittime",habit_time.getText().toString());
                bundle.putString("Address",geolocation.getText().toString());
                RadioButton radioButton = findViewById(R.id.radio_Done);
                if(radioButton.isChecked()){
                    bundle.putString("status","Done");
                }
                radioButton = findViewById(R.id.radio_IP);
                if(radioButton.isChecked()){
                    bundle.putString("status","In Progress");
                }
                radioButton = findViewById(R.id.radio_NotDone);
                if(radioButton.isChecked()){
                    bundle.putString("status","Not Done");
                }
                Intent Jump = new Intent();
                Jump.setClass(HabitEventDetailActivity.this, HabitDetailMaps.class );
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

    }

    /**
     * get the radio button id when the user click the radio button
     * @param v
     */
    public void checkButton(View v){
        int radioId = status_group.getCheckedRadioButtonId();
        select_status = findViewById(radioId);

    }
}
