package com.example.habot;

import java.util.ArrayList;

public class RequestSendList {
    private ArrayList<Request> requestArrayList;

    public void addRequest(Request request){
        requestArrayList.add(request);
    }

    public ArrayList<Request> returnRequestList(){
        return this.requestArrayList;

    }
}
