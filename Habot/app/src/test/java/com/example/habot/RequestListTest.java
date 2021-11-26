package com.example.habot;
import static org.junit.Assert.assertEquals;

import android.util.Log;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;

public class RequestListTest {
    public sentRequestList requestRecieveList;
    @Before
    public void CreateRequestList(){
        requestRecieveList = new sentRequestList(null, new ArrayList<Request>());


    }

    /**
     * the function test the adding function of habit list
     */
    @Test
    public void AddRequestTest(){
        int listSize = requestRecieveList.getCount();
        requestRecieveList = new sentRequestList(null, new ArrayList<Request>());
        requestRecieveList.AddRequest(new Request("Wenhao","True"));
        assertEquals(requestRecieveList.getCount(), listSize+1);
        requestRecieveList.AddRequest(new Request("Keli","False"));
        assertEquals(requestRecieveList.getCount(), listSize+2);
    }

    /**
     * the function test the deleting function of habit list
     */
    @Test
    public void DeleteHabitTest(){
        int listSize = requestRecieveList.getCount();
        requestRecieveList = new sentRequestList(null, new ArrayList<Request>());
        requestRecieveList.AddRequest(new Request("Wenhao","True"));
        assertEquals(requestRecieveList.getCount(), listSize+1);
        requestRecieveList.DeleteRequest(0);
        assertEquals(requestRecieveList.getCount(), listSize);
    }

    /**
     * the function test the listview function from the habit list
     */
    @Test
    public void GetHabitTest(){
        int listSize = requestRecieveList.getCount();
        requestRecieveList = new sentRequestList(null, new ArrayList<Request>());
        requestRecieveList.AddRequest(new Request("Wenhao","True"));
        String title = requestRecieveList.returnRequest(0).getSender();
        assertEquals(title,"Wenhao");

    }
}
