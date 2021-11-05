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

/**
 * This initialize a habit event list by extending from arrayadapter
 */
public class Habit_Eventlist extends ArrayAdapter<Habit_Event> {

    public ArrayList<Habit_Event> habitevents;

    private Context context;

    /**
     * This takes 2 parameter and construct the object
     * @param context
     * @param habitevents
     */
    public Habit_Eventlist(Context context, ArrayList<Habit_Event> habitevents){
        super(context, 0, habitevents);
        this.habitevents = habitevents;
        this.context = context;

    }

    /**
     * This takes 3 parameter as input and return the view
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.eachhabitevent, parent,false);
        }
        Habit_Event habit_event = habitevents.get(position);
        TextView habitname = view.findViewById(R.id.habitevent_name);

        //set habit name
        habitname.setText(habit_event.getHabit_name());

        return view;
    }

    /**
     * This will return the size of habitevents
     * @return
     */
    public int getCount(){
        return habitevents.size();
    }

    /**
     * This will add habit_event to the habitevent list
     * @param habit_event
     */
    public void AddHabit_Event(Habit_Event habit_event){
        habitevents.add(habit_event);
    }

    /**
     * This will delete a habit event by index
     * @param i
     */
    public void DeleteHabitEvent(int i) {
        habitevents.remove(i);
    }

    /**
     * This will return the habit event
     * @param i
     * @return
     */
    public Habit_Event returnEvent(int i){
        return habitevents.get(i);
    }
}