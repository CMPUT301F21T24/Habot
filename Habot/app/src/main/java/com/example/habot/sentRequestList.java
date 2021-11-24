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

public class sentRequestList extends ArrayAdapter<Request> {
    public ArrayList<Request> requests;
    private Context context;

    public sentRequestList(@NonNull Context context, ArrayList<Request> requests) {
        super(context, 0, requests);
        this.context = context;
        this.requests = requests;
    }


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
}
