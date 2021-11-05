package com.example.habot;

import static org.junit.Assert.assertEquals;
import com.example.habot.User;
import com.example.habot.UserList;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

public class UserListTest {
    private UserList userList;

    @Before
    public void CreateUserList(){
        userList = new UserList(null, new ArrayList<User>());
        userList.adduser(new User("TestUser1", "123456"));
        userList.adduser(new User("TestUser2", "123456"));
        userList.adduser(new User("TestUser3", "123456"));
    }

    @Test
    public void AddUserTest(){
        int listSize = userList.getCount();
        userList.adduser(new User("TestUser4","123456"));
        assertEquals(userList.getCount(),listSize+1);
    }

    @Test
    public void DeleteUserTest(){
        int listSize = userList.getCount();
        if (listSize > 0){
            userList.DeleteUser((listSize-1));
            assertEquals(userList.getCount(), listSize-1);
        }
    }
}
