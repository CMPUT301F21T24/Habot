package com.example.habot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

/**
 * This is a sentRequestList that extends from ArrayAdapter
 */
public class sentRequestList extends ArrayAdapter<Request> {
    public ArrayList<Request> requests;
    private Context context;

    public sentRequestList(@NonNull Context context, ArrayList<Request> requests) {
        super(context, 0, requests);
        this.context = context;
        this.requests = requests;
    }

    /**
     * use getView to adapter the the arrayListAdapter
     * @param position
     * @param convertView
     * @param parent
     * @return view
     */
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.sentrequestcontent, parent,false);
        }
        Request request = requests.get(position);
        TextView requestName = view.findViewById(R.id.requestsend);


        //set habit name
        requestName.setText(request.getSender());

        return view;
    }

    /**
     *
     * @return int size of the list
     */
    public int getCount(){
        return requests.size();
    }

    /**
     * add a new request in the list
     * @param request
     */
    public void AddRequest(Request request){
        requests.add(request);
    }

    /**
     * Delete a request from the list
     * @param i
     */
    public void DeleteRequest(int i){
        requests.remove(i);
    }

    /**
     *This will return a request from the list index i
     * @param i
     * @return a request from the ith item
     */
    public Request returnRequest(int i){
        return requests.get(i);
    }
}
