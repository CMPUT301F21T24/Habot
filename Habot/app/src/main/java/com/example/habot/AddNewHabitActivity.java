package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddNewHabitActivity extends AppCompatActivity {
    Button CancelBackToMenuButton;
    TextView HabitStartDateTextView;
    Button HabitStartDateButton;
    EditText HabitNameEditText;
    EditText HabitDescriptionEditText;
    Button HabitOccurDateButton;
    TextView HabitOccurDateTextView;

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


        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        String HabitNameInput = bundle.getString("HabitName");
        String HabitDescriptionInput = bundle.getString("HabitDescription");


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

    }
}
