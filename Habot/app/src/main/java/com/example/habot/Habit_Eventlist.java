package com.example.habot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Habit_Eventlist extends ArrayAdapter<Habit_Event> {

    public ArrayList<Habit_Event> habitevents;

    private Context context;

    public Habit_Eventlist(Context context, ArrayList<Habit_Event> habitevents){
        super(context, 0, habitevents);
        this.habitevents = habitevents;
        this.context = context;

    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
// return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.eachhabitevent, parent,false);
        }
        Habit_Event habit_event = habitevents.get(position);
        TextView habitname = view.findViewById(R.id.habitevent_name);

        habitname.setText(habit_event.getHabit_name());

        return view;
    }

    public int getCount(){
        return habitevents.size();
    }

    public void AddHabit_Event(Habit_Event habit_event){
        habitevents.add(habit_event);
    }

    public void DeleteHabitEvent(int i) {
        habitevents.remove(i);
    }

    public Habit_Event returnEvent(int i){
        return habitevents.get(i);
    }
}