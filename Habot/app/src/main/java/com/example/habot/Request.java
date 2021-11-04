package com.example.habot;

/**
 * This create a Request object
 */
public class Request {
    public String Receiver;
    public String Sender;
    public String RequestStatus;

    /**
     * Constructor of Request object
     */
    Request(String receiver, String sender, String requestStatus){
        this.Receiver = receiver;
        this.Sender = sender;
        this.RequestStatus = requestStatus;
    }

    /**
     * This will return the receiver of request
     * @return
     *      This return Receiver
     */
    public String getReceiver() {
        return Receiver;
    }

    /**
     * This will set receiver of request
     * @param receiver
     */
    public void setReceiver(String receiver) {
        Receiver = receiver;
    }

    /**
     * This will return the sender of request
     * @return
     *      This will return sender of request
     */
    public String getSender() {
        return Sender;
    }

    /**
     * This will set sender of request
     * @param sender
     */
    public void setSender(String sender) {
        Sender = sender;
    }

    /**
     * This will get request status of a request
     * @return
     *      This will return a request status
     */
    public String getRequestStatus() {
        return RequestStatus;
    }

    /**
     * this will set the status of a request object
     * @param requestStatus
     */
    public void setRequestStatus(String requestStatus) {
        RequestStatus = requestStatus;
    }
}