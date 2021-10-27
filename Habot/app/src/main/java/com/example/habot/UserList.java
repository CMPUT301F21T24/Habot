package com.example.habot;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class UserList {

    private ArrayList<User> userArrayList;

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


}
