package com.example.habot;

public class Request {
    public String Receiver;
    public String Sender;
    public String RequestStatus;
    Request(String receiver, String sender, String requestStatus){
        this.Receiver = receiver;
        this.Sender = sender;
        this.RequestStatus = requestStatus;
    }

    public String getReceiver() {
        return Receiver;
    }

    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getRequestStatus() {
        return RequestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }
}