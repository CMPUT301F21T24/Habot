package com.example.habot;

import static android.content.ContentValues.TAG;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
    // EditText HabitPrivacyEditText;
    TextView TitleTextView;
    TextView habit_name_text;
    //    Button HabitOccurDateButton;
//    TextView HabitOccurDateTextView;
    Button ConfirmButton;
    FirebaseFirestore db;
    ArrayList<Habit> habitlist;
    String HabitNameInput;
    String HabitDescriptionInput;
    String dateStart;
    String timestart;
    String time;
    String privacy;
    int position;
    //EditText TimeStartEditText;
    ArrayList <Habit_Event> habiteventlist;
    RadioGroup radioGroup;
    RadioButton radioButton;
    CheckBox checkBox;


    /**
     *  Action after this activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewhabit);
        radioGroup = findViewById(R.id.privacy);
        // connect with the layout file using the ids
        CancelBackToMenuButton = findViewById(R.id.add_habit_cancel_button);
        HabitStartDateTextView = findViewById(R.id.input_habit_startDate);
        HabitStartDateButton = findViewById(R.id.habit_startDate_text);
        HabitNameEditText = findViewById(R.id.input_habit_name);
        HabitDescriptionEditText = findViewById(R.id.input_habit_description);

//        HabitOccurDateButton = findViewById(R.id.habit_occurDate_text);


        //    TimeStartEditText = findViewById(R.id.TimeStart);




        ConfirmButton = findViewById(R.id.confirm_button);
        //    HabitPrivacyEditText = findViewById(R.id.Habit_Privacy);
        TitleTextView = findViewById(R.id.Title);
        habit_name_text = findViewById(R.id.habit_name_text);
        db = FirebaseFirestore.getInstance();

        habitlist = new ArrayList<Habit>();
        habiteventlist = new ArrayList<Habit_Event>();
        // get info from the database
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");

        // retrieve data from the HabitList document from the firebase
        DocumentReference noteRef = db.collection(Username).document("HabitList");
        DocumentReference noteRef1 = db.collection(Username).document("HabitEventList");
        Boolean edit = bundle.getBoolean("edit");
        Boolean calendar = bundle.getBoolean("calendar");
        Log.d("TAG", "onCreate:2222222222222222222222calendar boolean value:"+calendar);
        Log.d("TAG", "onCreate:3333333333333333333333333calendar boolean value:"+edit);

        System.out.println("onCreate:3333333333333333333333333calendar boolean value:"+edit);

        if(edit){
            HabitNameEditText.setVisibility(View.INVISIBLE);
            habit_name_text.setVisibility(View.INVISIBLE);
            /**
             * This for checking whether it is in the edit mode
             */
            position = bundle.getInt("position");

            if(!calendar) {
/**
 * calendar is boolean to check whether it is jumped back from Calendar Activity or not.
 */
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
                                    privacy = documentSnapshot.getString("habit" + Integer.toString(position + 1)+"privacy");


//                                dateOccur = "2021-!!-!!";
                                    CancelBackToMenuButton.setText("Delete");
                                    ConfirmButton.setText("Update");
                                    HabitNameEditText.setText(HabitNameInput);
                                    HabitDescriptionEditText.setText(HabitDescriptionInput);
                                    HabitStartDateTextView.setText(dateStart);
                                    //TimeStartEditText.setText(timestart);
                                    //HabitPrivacyEditText.setText(privacy);
                                    TitleTextView.setText(HabitNameInput);
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

//                                  HabitOccurDateTextView.setText(dateOccur);
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
                privacy = bundle.getString("privacy");
                HabitNameEditText.setText(HabitNameInput);
                HabitDescriptionEditText.setText(HabitDescriptionInput);
                HabitStartDateTextView.setText(dateStart);
                //TimeStartEditText.setText(timestart);
                CancelBackToMenuButton.setText("Delete");
                ConfirmButton.setText("Update");
                TitleTextView.setText(HabitNameInput);
                Log.d(TAG, "onCreate: --------------"+privacy);
                if(privacy!= null){
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

            }

        }
        else {
            HabitNameInput = bundle.getString("HabitName");
            HabitDescriptionInput = bundle.getString("HabitDescription");
            Intent GetDate = getIntent();
            dateStart = GetDate.getStringExtra("dateStart");
//            dateOccur = bundle.getString("dateOccur");
            timestart = bundle.getString("TimeStart");
            privacy = bundle.getString("privacy");
            HabitNameEditText.setText(HabitNameInput);
            HabitDescriptionEditText.setText(HabitDescriptionInput);
            HabitStartDateTextView.setText(dateStart);
            if(privacy!= null){
            if(privacy.equals("private")){
                RadioButton radioButton = findViewById(R.id.privateButton);
                radioButton.setChecked(true);}}

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

            //TimeStartEditText.setText(timestart);
            //HabitPrivacyEditText.setText(privacy);
//            HabitOccurDateTextView.setText(dateOccur);

        }

        HabitStartDateButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Set start date when the Day to Start button is pressed.
             * jump to the Calendar Activity
             * @param v
             */
            @Override
            public void onClick(View v) {
                String HabitNameInput = HabitNameEditText.getText().toString();
                String HabitDescriptionInput = HabitDescriptionEditText.getText().toString();
                String timestart;
                timestart = "";

                checkBox = findViewById(R.id.checkbox0);
                if(checkBox.isChecked()){
                    timestart += "1";
                }
                else
                {
                    timestart += "0";
                }
                checkBox = findViewById(R.id.checkbox1);
                if(checkBox.isChecked()){
                    timestart += "1";
                }
                else
                {
                    timestart += "0";
                }
                checkBox = findViewById(R.id.checkbox2);
                if(checkBox.isChecked()){
                    timestart += "1";
                }
                else
                {
                    timestart += "0";
                }
                checkBox = findViewById(R.id.checkbox3);
                if(checkBox.isChecked()){
                    timestart += "1";
                }
                else
                {
                    timestart += "0";
                }
                checkBox = findViewById(R.id.checkbox4);
                if(checkBox.isChecked()){
                    timestart += "1";
                }
                else
                {
                    timestart += "0";
                }
                checkBox = findViewById(R.id.checkbox5);
                if(checkBox.isChecked()){
                    timestart += "1";
                }
                else
                {
                    timestart += "0";
                }
                checkBox = findViewById(R.id.checkbox6);
                if(checkBox.isChecked()){
                    timestart += "1";
                }
                else
                {
                    timestart += "0";
                }

                //String privacy = HabitPrivacyEditText.getText().toString();
                // when pressed, app will need to CalendarActivity for date selection
                Intent intent = new Intent(AddNewHabitActivity.this, CalendarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("HabitName", HabitNameInput);
                bundle.putString("HabitDescription", HabitDescriptionInput);
                bundle.putString("TimeStart", timestart);
                bundle.putBoolean("edit",edit);
                if(radioGroup.getCheckedRadioButtonId() == R.id.publicButton){
                    privacy = "public";
                }else{
                    privacy = "private";
                }
                bundle.putString("privacy",privacy);
                if(edit){
                    bundle.putInt("position",position);
                }
                bundle.putBoolean("calendar",true);
//                bundle.putString("dateOccur", dateOccur);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });




        CancelBackToMenuButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Return to the Habitdetail page when cancel button is pressed.
             * And if it is in the edit mode, the button will become delete button and delete the habit.
             * @param v
             */
            @Override
            public void onClick(View v) {
                final int[] stop_point = new int[1];
                HashMap<String, String> newhabit = new HashMap<>();
                if(edit){
                    noteRef1.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        String title;
                                        title = HabitNameEditText.getText().toString();
                                        for (int i =0; ; i++){
                                            if (habiteventlist.size()==i){
                                                stop_point[0] = i-1;
                                                break;
                                            }
                                            if (habiteventlist.get(i).getHabit_name().equals(title)) {

                                                habiteventlist.remove(i);
                                                i-=1;

                                            }

                                        }
                                        HashMap<String,String> newhabitevent = new HashMap<>();
                                        for (int i = 1; i <= stop_point[0]+1; i++) {
                                            Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));


                                            newhabitevent.put("habitevent" + Integer.toString(i) + "name", habiteventlist.get(i - 1).getHabit_name());
                                            newhabitevent.put("habitevent" + Integer.toString(i) + "comment", habiteventlist.get(i - 1).getComment());
                                            newhabitevent.put("habitevent" + Integer.toString(i) + "eventtime", habiteventlist.get(i - 1).getEventtime());
                                            newhabitevent.put("habitevent" + Integer.toString(i) + "status", habiteventlist.get(i - 1).getStatus());
                                            newhabitevent.put("habitevent" + Integer.toString(i) + "photos", habiteventlist.get(i - 1).getPhoto());
                                            newhabitevent.put("habitevent" + Integer.toString(i) + "geolocation", habiteventlist.get(i - 1).getGeolocation());
                                        }
                                        noteRef1.set(newhabitevent);

                                    }
                                }
                            });
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
                                            String privacy = documentSnapshot.getString("habit" + Integer.toString(i) + "privacy");
                                            habitlist.add(new Habit(title, reason, date, time, privacy));

                                        }
                                        habitlist.remove(position);
                                    }
                                    for (int i = 1; i < stop_point[0]-1; i++) {
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));

                                        newhabit.put("habit" + Integer.toString(i) + "name", habitlist.get(i - 1).gettitle());
                                        newhabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(i - 1).getreason());
                                        newhabit.put("habit" + Integer.toString(i) + "date", habitlist.get(i - 1).getdate());
                                        newhabit.put("habit" + Integer.toString(i) + "time", habitlist.get(i - 1).getTime());
                                        newhabit.put("habit" + Integer.toString(i) + "privacy", habitlist.get(i - 1).getPrivacy());


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
             * And if it is in the edit mode, confirm button will become the update button and update the habit.
             * @param view
             */
            @Override
            public void onClick(View view) {
                //  String TimeStart = TimeStartEditText.getText().toString();
                //  Log.d("TAG", "------------->>>>>>>>>>>>>kkkkkkkkkkkkkkkkkk Time Stored"+ TimeStart);

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
                                            String privacy = documentSnapshot.getString("habit"+Integer.toString(i)+"privacy");

                                            habitlist.add(new Habit(title, reason, date, time, privacy));

                                        }
                                    }
                                    for (int i = 1; i < stop_point[0]; i++) {
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz" + Integer.toString(i) + Integer.toString(stop_point[0]));
                                        if(position==(i-1)){
                                            newhabit.put("habit"+Integer.toString(position+1)+"name",title);
                                            newhabit.put("habit"+Integer.toString(position+1)+"reason",reason);
                                            newhabit.put("habit"+Integer.toString(position+1)+"date",date);
                                            newhabit.put("habit"+Integer.toString(position+1)+"time",starttime[0]);
                                            newhabit.put("habit"+Integer.toString(i)+"privacy",privacy);
                                        }
                                        else {
                                            newhabit.put("habit" + Integer.toString(i) + "name", habitlist.get(i - 1).gettitle());
                                            newhabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(i - 1).getreason());
                                            newhabit.put("habit" + Integer.toString(i) + "date", habitlist.get(i - 1).getdate());
                                            newhabit.put("habit" + Integer.toString(i) + "time", habitlist.get(i - 1).getTime());
                                            newhabit.put("habit" + Integer.toString(i)+ "privacy", habitlist.get(i-1).getPrivacy());


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
        noteRef1.addSnapshotListener(new EventListener<DocumentSnapshot>() {
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
            }
        });

    }
    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Log.d(TAG, "checkButton: "+ radioId);

    }

}
