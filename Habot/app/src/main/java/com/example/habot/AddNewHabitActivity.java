package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AddNewHabitActivity extends AppCompatActivity {
    Button CancelBackToMenuButton;
    TextView HabitStartDateTextView;
    Button HabitStartDateButton;
    EditText HabitNameEditText;
    EditText HabitDescriptionEditText;
    Button HabitOccurDateButton;
    TextView HabitOccurDateTextView;
    Button ConfirmButton;
    FirebaseFirestore db;
    ArrayList<Habit> habitlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addnewhabit);


        CancelBackToMenuButton = findViewById(R.id.add_habit_cancel_button);
        HabitStartDateTextView = findViewById(R.id.input_habit_startDate);
        HabitStartDateButton = findViewById(R.id.habit_startDate_text);
        HabitNameEditText = findViewById(R.id.input_habit_name);
        HabitDescriptionEditText = findViewById(R.id.input_habit_description);
        HabitOccurDateButton = findViewById(R.id.habit_occurDate_text);
        HabitOccurDateTextView = findViewById(R.id.input_habit_occurDate);
        ConfirmButton = findViewById(R.id.confirm_button);
        db = FirebaseFirestore.getInstance();
        habitlist = new ArrayList<Habit>();


        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        String HabitNameInput = bundle.getString("HabitName");
        String HabitDescriptionInput = bundle.getString("HabitDescription");

        DocumentReference noteRef = db.collection(Username).document("HabitList");

        HabitNameEditText.setText(HabitNameInput);
        HabitDescriptionEditText.setText(HabitDescriptionInput);


        Intent GetDate = getIntent();
        String dateStart = GetDate.getStringExtra("dateStart");
        String dateOccur = bundle.getString("dateOccur");

        HabitStartDateTextView.setText(dateStart);
        HabitOccurDateTextView.setText(dateOccur);

        HabitStartDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String HabitNameInput = HabitNameEditText.getText().toString();
                String HabitDescriptionInput = HabitDescriptionEditText.getText().toString();
                Intent  intent = new Intent(AddNewHabitActivity.this, CalendarActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("HabitName", HabitNameInput);
                bundle.putString("HabitDescription", HabitDescriptionInput);
                bundle.putString("dateOccur", dateOccur);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        HabitOccurDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String HabitNameInput = HabitNameEditText.getText().toString();
                String HabitDescriptionInput = HabitDescriptionEditText.getText().toString();
                Intent  intent = new Intent(AddNewHabitActivity.this, CalendarDateOccurActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("HabitName", HabitNameInput);
                bundle.putString("HabitDescription", HabitDescriptionInput);
                bundle.putString("dateStart", dateStart);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        CancelBackToMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(AddNewHabitActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = HabitNameEditText.getText().toString();
                String reason = HabitDescriptionEditText.getText().toString();
                String date = HabitStartDateTextView.getText().toString();
                HashMap<String,String> newhabit = new HashMap<>();

                final int[] stop_point = new int[1];
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    for (int i =1;;i++)
                                    {

                                        String title = (String) documentSnapshot.getString("habit"+Integer.toString(i)+"name");
                                        if(title==null){
                                            stop_point[0] = i;
                                            Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz"+Integer.toString(i));
                                            break;
                                        }
                                        Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + title);
                                        String reason = documentSnapshot.getString("habit"+Integer.toString(i)+"reason");
                                        String date = documentSnapshot.getString("habit"+Integer.toString(i)+"date");
                                        habitlist.add(new Habit(title, reason, date));

                                    }
                                    habitlist.add(new Habit(title, reason, date));
                                    for (int i =1; i<=stop_point[0];i++){
                                        Log.d("TAG", "onSuccess: zzzzzzzzzzzzzzzzzzzzzzzzz"+Integer.toString(i)+Integer.toString(stop_point[0]));

                                        newhabit.put("habit"+Integer.toString(i)+"name",habitlist.get(i-1).gettitle());
                                        newhabit.put("habit"+Integer.toString(i)+"reason",habitlist.get(i-1).getreason());
                                        newhabit.put("habit"+Integer.toString(i)+"date",habitlist.get(i-1).getdate());
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
        });
    }
}
