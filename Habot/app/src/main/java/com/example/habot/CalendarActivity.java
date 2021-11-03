package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import javax.annotation.Nullable;

public class CalendarActivity extends AppCompatActivity {

    public CalendarView mCalendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        String HabitNameInput = bundle.getString("HabitName");
        String HabitDescriptionInput = bundle.getString("HabitDescription");
        String dateOccur = bundle.getString("dateOccur");

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                String dateStart = year + "-" + (month+1) + "-" + dayOfMonth;
                Log.d("TAG", "-------------------------> Get Date:" + dateStart);

                Intent intent = new Intent(CalendarActivity.this, AddNewHabitActivity.class);
                intent.putExtra("dateStart", dateStart);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("HabitName", HabitNameInput);
                bundle.putString("HabitDescription", HabitDescriptionInput);
                bundle.putString("dateOccur", dateOccur);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
