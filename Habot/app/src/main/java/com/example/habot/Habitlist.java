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
// return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.habitdetailcontent, parent,false);
        }
        Habit habit = habits.get(position);
        TextView habitname = view.findViewById(R.id.Habit_text);

        habitname.setText(habit.gettitle());

        return view;
    }

    public int getCount(){
        return habits.size();
    }

    public void AddHabit(Habit habit){
        habits.add(habit);
    }

    public void DeleteHabit(int i){
//        Habit habit = habits.get(i);
//        if (habits.contains(habit)) {
//            habits.remove(habit);
//        }
    }



}
