package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * This activity is created when the activity starts.
 */
public class PendingListActivity extends AppCompatActivity {

    //initialize the variables
    Button returnToFollowers;
    Button acceptButton;
    Button declineButton;
    FirebaseFirestore db;
    /**
     * Action when the activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pendinglist);
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        String theID = bundle.getString("followerName");
        String condition = bundle.getString("followerStatus");
        Log.d("TAG", "----------------> Username is :"+Username);
        db = FirebaseFirestore.getInstance();
        //find id from the layout files
        acceptButton = findViewById(R.id.acceptButton);
        declineButton = findViewById(R.id.declineButton);
        returnToFollowers = findViewById(R.id.return_button);


        returnToFollowers.setOnClickListener(new View.OnClickListener() {
            /**
             * This intent will direct users from Pending List Page to Check Follower Page
             * once users pressed the return button on the top left corner.
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(PendingListActivity.this, CheckFollowerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] stop_point = new int[1];
                ArrayList<Request> requestList = new ArrayList<Request>();
                DocumentReference documentReference = db.collection(Username).document("RequestRecievedList");
                HashMap<String,String> newRequest = new HashMap<>();
                documentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                for (int i = 1; ; i++){
                                    String sender = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "SenderName");
                                    if (sender == null){
                                        stop_point[0] = i;
                                        break;
                                    }
                                    String conditionStatus = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "ConditionStatus");

                                    requestList.add(new Request(sender,conditionStatus));
                                }

                                for (int i = 1; i < stop_point[0]; i++){
                                    String tempCondition = requestList.get(i - 1).getCondition();
                                    String tempName = requestList.get(i - 1).getSender();
                                    if (tempName.equals(theID)){
                                        tempCondition = "True";
                                    }
                                    newRequest.put("UserRequest" + Integer.toString(i) + "SenderName", tempName);
                                    newRequest.put("UserRequest" + Integer.toString(i) + "ConditionStatus", tempCondition);
                                }
                                documentReference.set(newRequest);

                            }
                        });


                final int[] stop_point1 = new int[1];
                ArrayList<Request> requestList1 = new ArrayList<Request>();
                DocumentReference documentReference1 = db.collection(theID).document("RequestSendList");
                HashMap<String,String> newRequest1 = new HashMap<>();
                documentReference1.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                for (int i = 1; ; i++){
                                    String sender = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "SenderName");
                                    if (sender == null){
                                        stop_point[0] = i;
                                        break;
                                    }
                                    String conditionStatus = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "ConditionStatus");

                                    requestList1.add(new Request(sender,conditionStatus));
                                }

                                for (int i = 1; i < stop_point[0]; i++){
                                    String tempCondition = requestList1.get(i - 1).getCondition();
                                    String tempName = requestList1.get(i - 1).getSender();
                                    if (tempName.equals(Username)){
                                        tempCondition = "True";
                                    }
                                    newRequest1.put("UserRequest" + Integer.toString(i) + "SenderName", tempName);
                                    newRequest1.put("UserRequest" + Integer.toString(i) + "ConditionStatus", tempCondition);
                                }
                                documentReference1.set(newRequest1);

                            }
                        });

                Intent Jump = new Intent();
                Jump.setClass(PendingListActivity.this, CheckFollowerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);

            }
        });

        declineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int[] stop_point = new int[1];
                ArrayList<Request> requestList = new ArrayList<Request>();
                DocumentReference documentReference = db.collection(Username).document("RequestRecievedList");
                HashMap<String,String> newRequest = new HashMap<>();
                documentReference.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                for (int i = 1; ; i++){
                                    String sender = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "SenderName");
                                    if (sender == null){
                                        stop_point[0] = i;
                                        break;
                                    }
                                    String conditionStatus = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "ConditionStatus");

                                    requestList.add(new Request(sender,conditionStatus));
                                }

                                for (int i = 1; i < stop_point[0]; i++){
                                    String tempCondition = requestList.get(i - 1).getCondition();
                                    String tempName = requestList.get(i - 1).getSender();
                                    if (tempName.equals(theID)){
                                        continue;
                                    }
                                    newRequest.put("UserRequest" + Integer.toString(i) + "SenderName", tempName);
                                    newRequest.put("UserRequest" + Integer.toString(i) + "ConditionStatus", tempCondition);
                                }
                                documentReference.set(newRequest);

                            }
                        });


                final int[] stop_point1 = new int[1];
                ArrayList<Request> requestList1 = new ArrayList<Request>();
                DocumentReference documentReference1 = db.collection(theID).document("RequestSendList");
                HashMap<String,String> newRequest1 = new HashMap<>();
                documentReference1.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                for (int i = 1; ; i++){
                                    String sender = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "SenderName");
                                    if (sender == null){
                                        stop_point[0] = i;
                                        break;
                                    }
                                    String conditionStatus = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "ConditionStatus");

                                    requestList1.add(new Request(sender,conditionStatus));
                                }

                                for (int i = 1; i < stop_point[0]; i++){
                                    String tempCondition = requestList1.get(i - 1).getCondition();
                                    String tempName = requestList1.get(i - 1).getSender();
                                    if (tempName.equals(Username)){
                                        continue;
                                    }
                                    newRequest1.put("UserRequest" + Integer.toString(i) + "SenderName", tempName);
                                    newRequest1.put("UserRequest" + Integer.toString(i) + "ConditionStatus", tempCondition);
                                }
                                documentReference1.set(newRequest1);

                            }
                        });








                Intent Jump = new Intent();
                Jump.setClass(PendingListActivity.this, CheckFollowerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });




    }
}