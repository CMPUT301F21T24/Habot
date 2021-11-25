package com.example.habot;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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
    TextView geolocation;

    Button deletebutton;
    Button updatebutton;
    ArrayList<Habit_Event> habit_events;

    ImageView displayImage;

    RadioGroup status_group;
    RadioButton select_status;


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
                if(value.exists()){
                    String name = value.getString("habitevent"+Integer.toString(position+1)+"name");
                    String comment = value.getString("habitevent"+Integer.toString(position+1)+"comment");
                    String status = value.getString("habitevent"+Integer.toString(position+1)+"status");
                    String time = value.getString("habitevent"+Integer.toString(position+1)+"eventtime");
                    String Dgeolocation = value.getString("habitevent"+Integer.toString(position+1)+"geolocation");
                    //show the content on the surface
                    habit_name.setText(name);
                    habit_comment.setText(comment);
                    habit_time.setText(time);
                    geolocation.setText("Geolocation: "+Dgeolocation);

                    if (status != null){
                        if (status.equals("Done")){
                            RadioButton Done = findViewById(R.id.radio_Done);
                            Done.setChecked(true);
                        }
                        else if (status.equals("In Progress")){
                            RadioButton inProgress = findViewById(R.id.radio_IP);
                            inProgress.setChecked(true);
                        }
                        else if (status.equals("Not Done")){
                            RadioButton notDone = findViewById(R.id.radio_NotDone);
                            notDone.setChecked(true);
                        }
                    }


                    FirebaseStorage mStorageRef = FirebaseStorage.getInstance();

                    String imageName = habit_name.getText().toString() + "-" + time;
                    StorageReference imageReference = mStorageRef.getReference().child(Username+"/"+imageName);


                    Glide.with(getApplicationContext())
                            .load(imageReference)
                            .into(displayImage);

                }

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
                                            updatehabitevents.put("habitevent" + Integer.toString(i) + "geolocation", "Somewhere");
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


    }

    public void checkButton(View v){
        int radioId = status_group.getCheckedRadioButtonId();
        select_status = findViewById(radioId);

    }
}
