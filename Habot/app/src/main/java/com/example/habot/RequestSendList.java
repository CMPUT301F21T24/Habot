package com.example.habot;

import java.util.ArrayList;

/**
 * This class initialize a requestSendList
 */
public class RequestSendList {
    public ArrayList<Request> requestArrayList;

    /**
     * This takes Request as input and add to the requestArrayList
     * @param request
     */
    public void addRequest(Request request){
        requestArrayList.add(request);
    }

    /**
     * a
     * @return
     */
    public ArrayList<Request> returnRequestList(){
        return this.requestArrayList;

    }
}
