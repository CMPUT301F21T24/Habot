package com.example.habot;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserList extends ArrayList<User>{

    public ArrayList<User> userArrayList;
    public Context context;
    public UserList(Context context, ArrayList<User> userArrayList){
        super();
        this.userArrayList = userArrayList;
        this.context = context;

    }


    /**
     * This add a User to the userArrayList
     * @param user
     *   this is a argument added to the userArrayList
     */
    public void adduser(User user){
        userArrayList.add(user);
    }

    /**
     * this returns the userList
     * @return
     *      it takes no argument and return a list
     */
    public ArrayList<User> getHabitEvents() {

        return this.userArrayList;
    }

    public int getCount(){
        return userArrayList.size();
    }

    public void DeleteUser(int i){
        User user = userArrayList.get(i);
        if (userArrayList.contains(user)) {
            userArrayList.remove(user);
        }
    }

}
