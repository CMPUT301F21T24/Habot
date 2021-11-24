package com.example.habot;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Calendar;

/**
        * This is a Habitlist that extends from ArrayAdapter
        */
public class Habitlist extends ArrayAdapter<Habit> {
    public ArrayList<Habit> habits;
    private Context context;

    /**
     * This takes context and habits as input and construct a habitlist
     * @param context
     * @param habits
     */
    public Habitlist(Context context, ArrayList<Habit> habits){
        super(context, 0, habits);
        this.habits = habits;
        this.context = context;

    }

    /**
     * This takes position, convertView, parent as input and get view of habit name in the list
     * @param position
     * @param convertView
     * @param parent
     * @return
     *      This will return the a view.
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.habitdetailcontent, parent,false);
        }
        Habit habit = habits.get(position);
        TextView habitname = view.findViewById(R.id.Habit_text);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);
        TextView textView = view.findViewById(R.id.text_view);
        habitname.setText(habit.gettitle());
        String week = habit.getTime();
        Log.d("TAG", "getView: ------------------>>>>>weekkkkk"+week);

        int Today_day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int habit_total_days = 0;
        int habit_occur = 0;
        if (week != null) {
            if (week.charAt(0) == '1') {
                habit_total_days += 1;
                if (Today_day >= 1) {
                    habit_occur += 1;
                }
            }
            if (week.charAt(1) == '1') {
                habit_total_days += 1;
                if (Today_day >= 2) {
                    habit_occur += 1;
                }
            }
            if (week.charAt(2) == '1') {
                habit_total_days += 1;
                if (Today_day >= 3) {
                    habit_occur += 1;
                }
            }
            if (week.charAt(3) == '1') {
                habit_total_days += 1;
                if (Today_day >= 4) {
                    habit_occur += 1;
                }
            }
            if (week.charAt(4) == '1') {
                habit_total_days += 1;
                if (Today_day >= 5) {
                    habit_occur += 1;
                }
            }
            if (week.charAt(5) == '1') {
                habit_total_days += 1;
                if (Today_day >= 6) {
                    habit_occur += 1;
                }
            }
            if (week.charAt(6) == '1') {
                habit_total_days += 1;
                if (Today_day >= 7) {
                    habit_occur += 1;
                }
            }
        }
            float percent = ((float)habit_occur/(float)habit_total_days)*100;
            Log.d("TAG", "getView: -------------->>>>>>habit_occur %%%%%%%%-->>>>"+ habit_occur);
            Log.d("TAG", "getView: -------------->>>>>>habit_total_days %%%%%%%%-->>>>"+ habit_total_days);
            int progressPercent = (int)percent;
            String habit_finished = Integer.toString(progressPercent);
            textView.setText(habit_finished+'%');
            Log.d("TAG", "getView: -------------->>>>>>int progressPercent %%%%%%%%-->>>>"+ progressPercent);
            progressBar.setProgress(progressPercent);




        return view;
    }

    public int getCount(){
        return habits.size();
    }

    public void AddHabit(Habit habit){
        habits.add(habit);
    }

    public void DeleteHabit(int i){
        habits.remove(i);
    }

    public Habit returnHabit(int i){
        return habits.get(i);
    }


}