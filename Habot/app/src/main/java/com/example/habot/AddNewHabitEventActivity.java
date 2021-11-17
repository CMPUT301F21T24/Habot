package com.example.habot;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * This activity create to add new habit event
 */
public class AddNewHabitEventActivity extends AppCompatActivity {

    //initialize variables
    Button CancelBackHabitEventButton;
    Button Uploadbutton;
    EditText comment;
    EditText status;
    EditText time;
    EditText habit_name;
    FirebaseFirestore db;
    ArrayList<Habit_Event> habit_events;
    Button addlocationbutton;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> addresses;
    TextView geolocationtextview;

    /**
     * This will create when the activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewhabitevent);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //get id from layout files
        comment = findViewById(R.id.comment_input);
        status = findViewById(R.id.status_input);
        time = findViewById(R.id.time_input);
        habit_name = findViewById(R.id.habit_name_input);
        addlocationbutton = findViewById(R.id.add_location_button);
        geolocationtextview = findViewById(R.id.display_geolocation);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection(Username).document("HabitEventList");
        CancelBackHabitEventButton = findViewById(R.id.cancel_new_habit_event);
        Uploadbutton = findViewById(R.id.upload_new_habit_event);
        habit_events = new ArrayList<Habit_Event>();

        CancelBackHabitEventButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This takes a view as parameter, when users click on Cancel Button
             * this will intent to HabitEvent Activity
             * @param v
             */
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

        addlocationbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(AddNewHabitEventActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(AddNewHabitEventActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }

            }
        });

        Uploadbutton.setOnClickListener(new View.OnClickListener() {
            /**
             * This will upload Habit Event Detail to firebase and intent back to Habit Event Activity
             * once users click on the Upload Button
             * @param view
             */
            @Override
            public void onClick(View view) {
                final int[] stop_point = new int[1];
                HashMap<String,String> newhabitevent = new HashMap<>();
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            /**
                             * Check if DocumentSnapshot in the firestore successful obtained
                             * @param documentSnapshot
                             */
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    for(int i = 1;;i++){
                                        String name = documentSnapshot.getString("habitevent"+Integer.toString(i)+"name");
                                        if(name == null){
                                            stop_point[0]=i;
                                            break;
                                        }

                                        //get data from firestore database
                                        String habitcomment = documentSnapshot.getString("habitevent"+Integer.toString(i)+"comment");

                                        String habiteventtime= documentSnapshot.getString("habitevent"+Integer.toString(i)+"eventtime");
                                        String habitstatus = documentSnapshot.getString("habitevent"+Integer.toString(i)+"status");
                                        String habitphoto = documentSnapshot.getString("habitevent"+Integer.toString(i)+"photos");
                                        String habitgeolocation = documentSnapshot.getString("habitevent"+Integer.toString(i)+"geolocation");
                                        habit_events.add(new Habit_Event(name,habiteventtime,habitcomment, habitphoto, habitstatus, habitgeolocation));
                                    }

                                    //add to habit event list
                                    habit_events.add(new Habit_Event(habit_name.getText().toString(),time.getText().toString(),comment.getText().toString(),"nothing",status.getText().toString(),addresses.get(0).getAddressLine(0)));
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

                                    //intnet to Habit Event Page
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

    private void getLocation(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {


                    try {
                        Geocoder geocoder = new Geocoder(AddNewHabitEventActivity.this, Locale.getDefault());
                        addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        geolocationtextview.setText(addresses.get(0).getAddressLine(0));

                        Log.d("TAG", "onComplete: "+ addresses.get(0).getAddressLine(0));

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }
}
