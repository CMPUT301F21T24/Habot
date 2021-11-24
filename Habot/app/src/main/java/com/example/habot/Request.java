package com.example.habot;

/**
 * This create a Request object
 */
public class Request {

    public String Sender;


    /**
     * Constructor of Request object
     */
    Request( String sender){

        this.Sender = sender;

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

}