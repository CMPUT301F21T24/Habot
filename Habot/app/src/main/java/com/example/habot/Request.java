package com.example.habot;

/**
 * This create a Request object
 */
public class Request {

    public String Sender;
    public String Condition;


    /**
     * Constructor of Request object
     */
    Request( String sender, String condition){

        this.Sender = sender;
        this.Condition = condition;

    }

    public String getCondition() {
        return Condition;
    }

    public void setCondition(String condition) {
        Condition = condition;
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