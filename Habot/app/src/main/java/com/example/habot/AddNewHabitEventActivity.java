package com.example.habot;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

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
    Button addlocationbutton;
    Button addImageButton;
    EditText comment;
    EditText status;
    EditText time;
    EditText habit_name;
    FirebaseFirestore db;
    ArrayList<Habit_Event> habit_events;
    FusedLocationProviderClient fusedLocationProviderClient;
    List<Address> addresses;
    TextView geolocationtextview;
    ArrayList<Habit> habitlist;
    TextView geolocation1;
    String URI;
    Uri imageUri;

    String Bundle2HabitName;
    String Bundle2Address;
    String Bundle2Comment;
    String Bundle2Status;
    String Bundle2Time;


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
        addImageButton = findViewById(R.id.add_image_button);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Boolean image = bundle.getBoolean("addImage");

        db = FirebaseFirestore.getInstance();
        DocumentReference noteRef = db.collection(Username).document("HabitEventList");
        DocumentReference noteRef_2 = db.collection(Username).document("HabitList");
        CancelBackHabitEventButton = findViewById(R.id.cancel_new_habit_event);
        Uploadbutton = findViewById(R.id.upload_new_habit_event);
        habit_events = new ArrayList<Habit_Event>();
        habitlist = new ArrayList<Habit>();

        noteRef_2.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            /**
             * This will get users input from the add new habit activity and
             * import them to Firestore database
             * @param value
             * @param error
             */
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                final int[] stop_point = new int[1];
                habitlist.clear();
                for (int i =1;;i++)
                {
                    //get the Habit Name
                    String title = (String) value.getString("habit"+Integer.toString(i)+"name");
                    //The Habit name cannot be null
                    if(title==null){
                        stop_point[0] = i;
                        break;
                    }
                    Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + title);

                    //get Habit reson and date
                    String reason = value.getString("habit"+Integer.toString(i)+"reason");
                    String date = value.getString("habit"+Integer.toString(i)+"date");
                    String time = value.getString("habit"+Integer.toString(i)+"time");
                    String privacy = value.getString("habit" + Integer.toString(i)+"privacy");

                    //add Title, reason and date to the habitlist
                    habitlist.add(new Habit(title, reason, date, time, privacy));

                }
            }
        });

        if (image){
            String URI = bundle.getString("Uri");
            imageUri = Uri.parse(URI);

            Bundle2HabitName = bundle.getString("HabitName");
            Bundle2Address = bundle.getString("Address");
            Bundle2Comment = bundle.getString("Comment");
            Bundle2Status = bundle.getString("Status");
            Bundle2Time = bundle.getString("Time");

            Log.d("TAG","++++++++++++++++++++++++"+habit_name.getText().toString());

            habit_name.setText(Bundle2HabitName);
            geolocationtextview.setText(Bundle2Address);
            comment.setText(Bundle2Comment);
            status.setText(Bundle2Status);
            time.setText(Bundle2Time);

            Log.d("TAG","++++++++++++++++++++++++"+habit_name.getText().toString());
        }


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

        addImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String HabitNameInput = habit_name.getText().toString();
                String CommentInput = comment.getText().toString();
                String StatusInput = status.getText().toString();
                String TimeInput = time.getText().toString();
                String AddressInput = geolocationtextview.getText().toString();

                Log.d("TAG","++++++++++++++++++++++++"+HabitNameInput);

                Intent Jump = new Intent();
                Jump.setClass(AddNewHabitEventActivity.this, AddImageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("HabitName", HabitNameInput);
                bundle.putString("Comment", CommentInput);
                bundle.putString("Status", StatusInput);
                bundle.putString("Time", TimeInput);
                bundle.putString("Address", AddressInput);
                bundle.putBoolean("addImage",true);

                Jump.putExtras(bundle);
                startActivity(Jump);
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
                boolean find = false;

                HashMap<String,String> newhabitevent = new HashMap<>();
                for (int s = 0; s <habitlist.size(); s++){
                    Log.d(TAG, "onClick: ssssssssssss  "+habitlist.get(s).gettitle());
                    Log.d(TAG,"!!!!!!!!dwvwwwv  "+habit_name.getText().toString());
                    if(habitlist.get(s).gettitle().equals(habit_name.getText().toString())){
                        find = true;
                    }
                }
                if(find == true){

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

                                        if (imageUri != null){
                                            //Upload Image Stages
                                            FirebaseStorage mStorageRef = FirebaseStorage.getInstance();

                                            String imageName = habit_name.getText().toString() + "-" + time.getText().toString();
                                            StorageReference imageReference = mStorageRef.getReference().child(Username+"/"+imageName);

                                            //Set Metadata to the Uri
                                            StorageMetadata metadata = new StorageMetadata.Builder()
                                                    .setContentType("image/jpg")
                                                    .build();

                                            //Put file to the Firestore Storage
                                            imageReference.putFile(imageUri,metadata)
                                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                        @Override
                                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                            Toast.makeText(AddNewHabitEventActivity.this, "Image Uploaded to Cloud", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }


                                        //add to habit event list
                                        habit_events.add(new Habit_Event(habit_name.getText().toString(),time.getText().toString(),comment.getText().toString(),"imageName",status.getText().toString(),geolocationtextview.getText().toString()));
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

                                        //intent to Habit Event Page
                                        Intent Jump = new Intent();
                                        Jump.setClass(AddNewHabitEventActivity.this, HabitEventActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("UserName", Username);
                                        Jump.putExtras(bundle);
                                        startActivity(Jump);
                                    }
                                }
                            });
                }else{
                    Toast.makeText(AddNewHabitEventActivity.this, "Incorrect habit_name", Toast.LENGTH_SHORT).show();

                }
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
