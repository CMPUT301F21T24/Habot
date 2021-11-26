package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddNewHabitFromFollowing extends AppCompatActivity {
    Button CancelBackToCheckFollowingButton;
    TextView HabitStartDateTextView;
    Button HabitStartDateButton;
    EditText HabitNameEditText;
    EditText HabitDescriptionEditText;
    TextView TitleTextView;
    TextView habit_name_text;
    Button ConfirmButton;
    FirebaseFirestore db;
    ArrayList<Habit> habitlist;

    //EditText TimeStartEditText;
    ArrayList <Habit_Event> habiteventlist;
    RadioGroup radioGroup;
    RadioButton radioButton;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfollowinghabit);

        radioGroup = findViewById(R.id.privacy);
        // connect with the layout file using the ids
        CancelBackToCheckFollowingButton = findViewById(R.id.add_habit_cancel_button);
        HabitStartDateTextView = findViewById(R.id.input_habit_startDate);
        HabitStartDateButton = findViewById(R.id.habit_startDate_text);
        HabitNameEditText = findViewById(R.id.input_habit_name);
        HabitDescriptionEditText = findViewById(R.id.input_habit_description);
        ConfirmButton = findViewById(R.id.confirm_button);
        TitleTextView = findViewById(R.id.Title);
        habit_name_text = findViewById(R.id.habit_name_text);
        db = FirebaseFirestore.getInstance();




        habitlist = new ArrayList<Habit>();
        habiteventlist = new ArrayList<Habit_Event>();

        // get info from the database
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        String FollowingUserName = bundle.getString("FollowingUserName");
        String title = bundle.getString("title");
        String reason = bundle.getString("reason");
        String date = bundle.getString("date");
        String timestart = bundle.getString("time");
        String privacy = bundle.getString("privacy");

        CancelBackToCheckFollowingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                // jump from check follower activity to menu activity
                Jump.setClass(AddNewHabitFromFollowing.this, CheckFollowingHabits.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("FollowingUserName", FollowingUserName);
                Jump.putExtras(bundle);
                // initiate the jump
                startActivity(Jump);
            }
        });

        DocumentReference noteRef = db.collection(Username).document("HabitList");


        HabitNameEditText.setText(title);
        HabitDescriptionEditText.setText(reason);
        HabitStartDateButton.setText(date);

        if(privacy != null){
            if(privacy.equals("private")){
                RadioButton radioButton = findViewById(R.id.privateButton);
                radioButton.setChecked(true);
            }}

        if(timestart != null){
            if(timestart.charAt(0) == '1'){
                checkBox = findViewById(R.id.checkbox0);
                checkBox.setChecked(true);
            }
            if(timestart.charAt(1) == '1'){
                checkBox = findViewById(R.id.checkbox1);
                checkBox.setChecked(true);
            }
            if(timestart.charAt(2) == '1'){
                checkBox = findViewById(R.id.checkbox2);
                checkBox.setChecked(true);
            }
            if(timestart.charAt(3) == '1'){
                checkBox = findViewById(R.id.checkbox3);
                checkBox.setChecked(true);
            }
            if(timestart.charAt(4) == '1'){
                checkBox = findViewById(R.id.checkbox4);
                checkBox.setChecked(true);
            }
            if(timestart.charAt(5) == '1'){
                checkBox = findViewById(R.id.checkbox5);
                checkBox.setChecked(true);
            }
            if(timestart.charAt(6) == '1'){
                checkBox = findViewById(R.id.checkbox6);
                checkBox.setChecked(true);
            }}


        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = HabitNameEditText.getText().toString();
                String reason = HabitDescriptionEditText.getText().toString();
                String date = HabitStartDateTextView.getText().toString();
                String starttime[] = new String[1];
                String privacy;
                if(radioGroup.getCheckedRadioButtonId() == R.id.publicButton){
                    privacy = "public";
                }else{
                    privacy = "private";
                }
                starttime[0] = "";
                checkBox = findViewById(R.id.checkbox0);
                if(checkBox.isChecked()){
                    starttime[0] += "1";
                }
                else
                {
                    starttime[0] += "0";
                }
                checkBox = findViewById(R.id.checkbox1);
                if(checkBox.isChecked()){
                    starttime[0] += "1";
                }
                else
                {
                    starttime[0] += "0";
                }
                checkBox = findViewById(R.id.checkbox2);
                if(checkBox.isChecked()){
                    starttime[0] += "1";
                }
                else
                {
                    starttime[0] += "0";
                }
                checkBox = findViewById(R.id.checkbox3);
                if(checkBox.isChecked()){
                    starttime[0] += "1";
                }
                else
                {
                    starttime[0] += "0";
                }
                checkBox = findViewById(R.id.checkbox4);
                if(checkBox.isChecked()){
                    starttime[0] += "1";
                }
                else
                {
                    starttime[0] += "0";
                }
                checkBox = findViewById(R.id.checkbox5);
                if(checkBox.isChecked()){
                    starttime[0] += "1";
                }
                else
                {
                    starttime[0] += "0";
                }
                checkBox = findViewById(R.id.checkbox6);
                if(checkBox.isChecked()){
                    starttime[0] += "1";
                }
                else
                {
                    starttime[0] += "0";
                }


                HashMap<String, String> newhabit = new HashMap<>();

                final int[] stop_point = new int[1];
                if(title.equals("")){
                    Toast.makeText(AddNewHabitFromFollowing.this, "Missing habit name", Toast.LENGTH_SHORT).show();
                }else{
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
                                            String privacy = documentSnapshot.getString("habit" + Integer.toString(i)+"privacy");
                                            habitlist.add(new Habit(title, reason, date, time, privacy));

                                        }
                                        habitlist.add(new Habit(title, reason, date, starttime[0], privacy));
                                        for (int i = 1; i <= stop_point[0]; i++) {
                                            Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));


                                            newhabit.put("habit" + Integer.toString(i) + "name", habitlist.get(i - 1).gettitle());
                                            newhabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(i - 1).getreason());
                                            newhabit.put("habit" + Integer.toString(i) + "date", habitlist.get(i - 1).getdate());
                                            newhabit.put("habit" + Integer.toString(i) + "time", habitlist.get(i - 1).getTime());
                                            newhabit.put("habit" + Integer.toString(i) + "privacy", habitlist.get(i - 1).getPrivacy());


                                        }
                                        noteRef.set(newhabit);
                                        Intent Jump = new Intent();
                                        Jump.setClass(AddNewHabitFromFollowing.this, HabitDetailAcitivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("UserName", Username);
                                        Jump.putExtras(bundle);
                                        startActivity(Jump);
                                    }
                                }
                            });
                }
            }
        });





    }

}
