package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Prompt user to create a new habit activity
 */
public class AddNewHabitActivity extends AppCompatActivity {
    Button CancelBackToMenuButton;
    TextView HabitStartDateTextView;
    Button HabitStartDateButton;
    EditText HabitNameEditText;
    EditText HabitDescriptionEditText;
//    Button HabitOccurDateButton;
//    TextView HabitOccurDateTextView;
    Button ConfirmButton;
    FirebaseFirestore db;
    ArrayList<Habit> habitlist;
    String HabitNameInput;
    String HabitDescriptionInput;
    String dateStart;
    String timestart;
    int position;
    EditText TimeStartEditText;

    /**
     *  Action after this activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewhabit);

        // connect with the layout file using the ids
        CancelBackToMenuButton = findViewById(R.id.add_habit_cancel_button);
        HabitStartDateTextView = findViewById(R.id.input_habit_startDate);
        HabitStartDateButton = findViewById(R.id.habit_startDate_text);
        HabitNameEditText = findViewById(R.id.input_habit_name);
        HabitDescriptionEditText = findViewById(R.id.input_habit_description);
//        HabitOccurDateButton = findViewById(R.id.habit_occurDate_text);
        TimeStartEditText = findViewById(R.id.TimeStart);
        ConfirmButton = findViewById(R.id.confirm_button);
        db = FirebaseFirestore.getInstance();

        habitlist = new ArrayList<Habit>();

        // get info from the database
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");

        // retrieve data from the HabitList document from the firebase
        DocumentReference noteRef = db.collection(Username).document("HabitList");
        Boolean edit = bundle.getBoolean("edit");
        Boolean calendar = bundle.getBoolean("calendar");
        Log.d("TAG", "onCreate:2222222222222222222222calendar boolean value:"+calendar);
        Log.d("TAG", "onCreate:3333333333333333333333333calendar boolean value:"+edit);

        System.out.println("onCreate:3333333333333333333333333calendar boolean value:"+edit);

        if(edit){
            position = bundle.getInt("position");
            if(!calendar) {

                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {


                                    HabitNameInput = documentSnapshot.getString("habit" + Integer.toString(position + 1) + "name");
                                    Log.d("TAG", "onSuccess:zzzzzzzzzzzzzs" + HabitNameInput);
                                    HabitDescriptionInput = documentSnapshot.getString("habit" + Integer.toString(position + 1) + "reason");
                                    dateStart = documentSnapshot.getString("habit" + Integer.toString(position + 1) + "date");
                                    timestart = documentSnapshot.getString("habit" + Integer.toString(position + 1) + "time");

//                                dateOccur = "2021-!!-!!";
                                    CancelBackToMenuButton.setText("Delete");
                                    ConfirmButton.setText("Update");
                                    HabitNameEditText.setText(HabitNameInput);
                                    HabitDescriptionEditText.setText(HabitDescriptionInput);
                                    HabitStartDateTextView.setText(dateStart);
                                    TimeStartEditText.setText(timestart);
//                                HabitOccurDateTextView.setText(dateOccur);
                                }
                            }
                        });
            }else{
                HabitNameInput = bundle.getString("HabitName");
                HabitDescriptionInput = bundle.getString("HabitDescription");
                Intent GetDate = getIntent();
                dateStart = GetDate.getStringExtra("dateStart");
//            dateOccur = bundle.getString("dateOccur");
                timestart = bundle.getString("TimeStart");
                HabitNameEditText.setText(HabitNameInput);
                HabitDescriptionEditText.setText(HabitDescriptionInput);
                HabitStartDateTextView.setText(dateStart);
                TimeStartEditText.setText(timestart);
                CancelBackToMenuButton.setText("Delete");
                ConfirmButton.setText("Update");

            }

        }
        else {
            HabitNameInput = bundle.getString("HabitName");
            HabitDescriptionInput = bundle.getString("HabitDescription");
            Intent GetDate = getIntent();
            dateStart = GetDate.getStringExtra("dateStart");
//            dateOccur = bundle.getString("dateOccur");
            timestart = bundle.getString("TimeStart");
            HabitNameEditText.setText(HabitNameInput);
            HabitDescriptionEditText.setText(HabitDescriptionInput);
            HabitStartDateTextView.setText(dateStart);
            TimeStartEditText.setText(timestart);
//            HabitOccurDateTextView.setText(dateOccur);

        }

        HabitStartDateButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Set start date when the Day to Start button is pressed.
             * @param v
             */
            @Override
            public void onClick(View v) {
                String HabitNameInput = HabitNameEditText.getText().toString();
                String HabitDescriptionInput = HabitDescriptionEditText.getText().toString();
                String timestart = TimeStartEditText.getText().toString();
                // when pressed, app will need to CalendarActivity for date selection
                Intent intent = new Intent(AddNewHabitActivity.this, CalendarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("HabitName", HabitNameInput);
                bundle.putString("HabitDescription", HabitDescriptionInput);
                bundle.putString("TimeStart", timestart);
                bundle.putBoolean("edit",edit);
                if(edit){
                    bundle.putInt("position",position);
                }
                bundle.putBoolean("calendar",true);
//                bundle.putString("dateOccur", dateOccur);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

//        HabitOccurDateButton.setOnClickListener(new View.OnClickListener() {
//            /**
//             * Set Occur date when the Day to Start button is pressed.
//             * @param v
//             */
//            @Override
//            public void onClick(View v) {
//                String HabitNameInput = HabitNameEditText.getText().toString();
//                String HabitDescriptionInput = HabitDescriptionEditText.getText().toString();
//                Intent  intent = new Intent(AddNewHabitActivity.this, CalendarDateOccurActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("UserName", Username);
//                bundle.putString("HabitName", HabitNameInput);
//                bundle.putString("HabitDescription", HabitDescriptionInput);
//                bundle.putString("dateStart", dateStart);
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });


        CancelBackToMenuButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Return to the Habitdetail page when cancel button is pressed.
             * @param v
             */
            @Override
            public void onClick(View v) {
                final int[] stop_point = new int[1];
                HashMap<String, String> newhabit = new HashMap<>();
                if(edit){
                    noteRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        for (int i = 1; ; i++) {

                                            String title = (String) documentSnapshot.getString("habit" + Integer.toString(i) + "name");
                                            if (title == null) {
                                                stop_point[0] = i;
                                                Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i));
                                                break;
                                            }
                                            Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + title);
                                            String reason = documentSnapshot.getString("habit" + Integer.toString(i) + "reason");
                                            String date = documentSnapshot.getString("habit" + Integer.toString(i) + "date");
                                            String time = documentSnapshot.getString("habit"+ Integer.toString(i) + "time");
                                            habitlist.add(new Habit(title, reason, date, time));

                                        }
                                        habitlist.remove(position);
                                    }
                                    for (int i = 1; i < stop_point[0]-1; i++) {
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));

                                        newhabit.put("habit" + Integer.toString(i) + "name", habitlist.get(i - 1).gettitle());
                                        newhabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(i - 1).getreason());
                                        newhabit.put("habit" + Integer.toString(i) + "date", habitlist.get(i - 1).getdate());
                                        newhabit.put("habit" + Integer.toString(i) + "time", habitlist.get(i - 1).getTime());


                                    }
                                    noteRef.set(newhabit);
                                    Intent Jump = new Intent();
                                    Jump.setClass(AddNewHabitActivity.this, HabitDetailAcitivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("UserName", Username);
                                    Jump.putExtras(bundle);
                                    startActivity(Jump);
                                }
                            });


                }
                else {
                    Intent Jump = new Intent();
                    Jump.setClass(AddNewHabitActivity.this, HabitDetailAcitivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("UserName", Username);
                    Jump.putExtras(bundle);
                    startActivity(Jump);
                }
            }
        });

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Add new habit when confirm button is pressed.
             * @param view
             */
            @Override
            public void onClick(View view) {
                String TimeStart = TimeStartEditText.getText().toString();
                Log.d("TAG", "------------->>>>>>>>>>>>>kkkkkkkkkkkkkkkkkk Time Stored"+ TimeStart);

                String title = HabitNameEditText.getText().toString();
                String reason = HabitDescriptionEditText.getText().toString();
                String date = HabitStartDateTextView.getText().toString();
                String time = TimeStartEditText.getText().toString();
                HashMap<String, String> newhabit = new HashMap<>();

                final int[] stop_point = new int[1];
                if(!edit){
                    noteRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        for (int i = 1; ; i++) {

                                            String title = (String) documentSnapshot.getString("habit" + Integer.toString(i) + "name");
                                            if (title == null) {
                                                stop_point[0] = i;
                                                Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i));
                                                break;
                                            }
                                            Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + title);
                                            String reason = documentSnapshot.getString("habit" + Integer.toString(i) + "reason");
                                            String date = documentSnapshot.getString("habit" + Integer.toString(i) + "date");
                                            String time = documentSnapshot.getString("habit"+Integer.toString(i)+"time");
                                            habitlist.add(new Habit(title, reason, date, time));

                                        }
                                        habitlist.add(new Habit(title, reason, date, time));
                                        for (int i = 1; i <= stop_point[0]; i++) {
                                            Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));


                                            newhabit.put("habit" + Integer.toString(i) + "name", habitlist.get(i - 1).gettitle());
                                            newhabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(i - 1).getreason());
                                            newhabit.put("habit" + Integer.toString(i) + "date", habitlist.get(i - 1).getdate());
                                            newhabit.put("habit" + Integer.toString(i) + "time", habitlist.get(i - 1).getTime());

                                        }
                                        noteRef.set(newhabit);
                                        Intent Jump = new Intent();
                                        Jump.setClass(AddNewHabitActivity.this, HabitDetailAcitivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("UserName", Username);
                                        Jump.putExtras(bundle);
                                        startActivity(Jump);
                                    }
                                }
                            });


                }
                else{
                    noteRef.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        for (int i = 1; ; i++) {

                                            String title = (String) documentSnapshot.getString("habit" + Integer.toString(i) + "name");
                                            if (title == null) {
                                                stop_point[0] = i;
                                                Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i));
                                                break;
                                            }
                                            Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + title);
                                            String reason = documentSnapshot.getString("habit" + Integer.toString(i) + "reason");
                                            String date = documentSnapshot.getString("habit" + Integer.toString(i) + "date");
                                            String time = documentSnapshot.getString("habit"+Integer.toString(i)+"time");
                                            habitlist.add(new Habit(title, reason, date, time));

                                        }
                                    }
                                    for (int i = 1; i < stop_point[0]; i++) {
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));
                                        if(position==(i-1)){
                                            newhabit.put("habit"+Integer.toString(position+1)+"name",title);
                                            newhabit.put("habit"+Integer.toString(position+1)+"reason",reason);
                                            newhabit.put("habit"+Integer.toString(position+1)+"date",date);
                                            newhabit.put("habit"+Integer.toString(position+1)+"time",time);
                                        }
                                        else {
                                            newhabit.put("habit" + Integer.toString(i) + "name", habitlist.get(i - 1).gettitle());
                                            newhabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(i - 1).getreason());
                                            newhabit.put("habit" + Integer.toString(i) + "date", habitlist.get(i - 1).getdate());
                                            newhabit.put("habit" + Integer.toString(i) + "time", habitlist.get(i - 1).getTime());


                                        }
                                    }
                                    noteRef.set(newhabit);
                                    Intent Jump = new Intent();
                                    Jump.setClass(AddNewHabitActivity.this, HabitDetailAcitivity.class);
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
}
