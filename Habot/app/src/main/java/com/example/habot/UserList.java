package com.example.habot;

import android.content.Context;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * This UserList is extending arrayList
 */
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

    /**
     * This will return the size of userList
     * @return
     */
    public int getCount(){
        return userArrayList.size();
    }

    /**
     * This will delete a user i from the userList
     * @param i
     */
    public void DeleteUser(int i){
        User user = userArrayList.get(i);
        if (userArrayList.contains(user)) {
            userArrayList.remove(user);
        }
    }

}
