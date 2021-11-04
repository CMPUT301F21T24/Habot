package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import javax.annotation.Nullable;

/**
 * In this activity, prompt user to select start date.
 */
public class CalendarDateOccurActivity extends AppCompatActivity {

    public CalendarView mCalendarView;

    /**
     * on create when this activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        // define values for variables
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        String HabitNameInput = bundle.getString("HabitName");
        String HabitDescriptionInput = bundle.getString("HabitDescription");
        String dateStart = bundle.getString("dateStart");
        // connect layout file with view id
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            /**
             * Prompt user to select year month and day.
             * @param view
             * @param year
             * @param month
             * @param dayOfMonth
             */
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // set string date display format
                String dateOccur = year + "-" + (month+1) + "-" + dayOfMonth;
                Log.d("TAG", "-------------------------> Get Date:" + dateOccur);

                // jump back to add new habit activity page after date is selected
                Intent intent = new Intent(CalendarDateOccurActivity.this, AddNewHabitActivity.class);
                intent.putExtra("dateOccur", dateOccur);
                // re-input all the info

                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putString("HabitName", HabitNameInput);
                bundle.putString("HabitDescription", HabitDescriptionInput);
                bundle.putString("dateStart", dateStart);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
