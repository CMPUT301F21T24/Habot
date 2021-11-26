package com.example.habot;

import java.util.ArrayList;

/**
 * This is the receive list object
 */
public class RequestRecieveList {
    public ArrayList<Request> requestArrayList;

    /**
     * this takes a request as parameter, set the request to the request list
     * @param request
     */
    public void addRequest(Request request){
        requestArrayList.add(request);
    }

    /**
     * @return
     * this will return a request from the request list
     */
    public ArrayList<Request> returnRequestList(){
        return this.requestArrayList;

    }
}
