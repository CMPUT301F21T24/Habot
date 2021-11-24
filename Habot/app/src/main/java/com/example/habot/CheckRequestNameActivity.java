package com.example.habot;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Check other doer's follow request.
 */
public class CheckRequestNameActivity extends AppCompatActivity {
    // set initial variables
    Button returnToFollowers;
    Button searchTheName;
    EditText nameSearcher;
    ListView nameList;
    FirebaseFirestore db;
    ArrayList<Request> requestlist;
    ArrayList<Request> requestlist1;
    ArrayList<Request> showRequestSend;
    ArrayAdapter<Request> showSendAdapter;


    /**
     * Action after this activity is created.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // find the view checkrequestname layout file and connect it with view id
        setContentView(R.layout.checkrequestname);
        // set values to variables

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);
        // connect layout file with view id
        returnToFollowers = findViewById(R.id.return_button);
        searchTheName = findViewById(R.id.search_button);
        nameSearcher = findViewById(R.id.nameSearcher);
        nameList = findViewById(R.id.listName);
        db = FirebaseFirestore.getInstance();
        requestlist = new ArrayList<Request>();
        requestlist1 = new ArrayList<Request>();
        showRequestSend = new ArrayList<Request>();





        //在这里写listview的内容
        final int[] stop_point = new int[1];
        showSendAdapter = new sentRequestList(this,showRequestSend);
        nameList.setAdapter(showSendAdapter);
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(Username);
        DocumentReference noteRef = collectionReference.document("RequestSendList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            showRequestSend.clear();
                                            for (int i = 1 ; ; i++){

                                                String title = (String) value.getString("UserRequest"+Integer.toString(i)+"SenderName");
                                                if(title==null){
                                                    stop_point[0] = i;
                                                    break;
                                                }


                                                showRequestSend.add(new Request(title));

                                            }
                                            showSendAdapter.notifyDataSetChanged();

                                        }
                                    });



        returnToFollowers.setOnClickListener(new View.OnClickListener() {
            /**
             * When back button on the top left of the screen is clicked, jump back to check following activity page.
             *
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                // jump from check request activity to check following activity
                Jump.setClass(CheckRequestNameActivity.this, CheckFollowingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                // initiate the jump
                startActivity(Jump);
            }
        });



        searchTheName.setOnClickListener(new View.OnClickListener() {
            /**
             * Input name and press search button to search other doer
             * @param view
             */
            @Override
            public void onClick(View view) {
                final String theID = nameSearcher.getText().toString();

                if (theID.length() == 0){
                    Toast.makeText(CheckRequestNameActivity.this, "User ID can not be empty", Toast.LENGTH_SHORT).show();
                }else{
                    DocumentReference documentReference = db.collection(theID).document("RequestRecievedList");
                    HashMap<String,String> newRequest = new HashMap<>();
                    final int[] stop_point = new int[1];
                    documentReference.get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()){
                                        Toast.makeText(CheckRequestNameActivity.this, "Request already sent", Toast.LENGTH_SHORT).show();
                                        for (int i = 1; ; i++){
                                            String sender = (String) documentSnapshot.getString("UserRequest" + Integer.toString(i) + "SenderName");
                                            if (sender == null){
                                                stop_point[0] = i;
                                                break;
                                            }

                                            requestlist.add(new Request(sender));
                                        }
                                        Log.d(TAG, "onSuccess: fsadfsfdsf"+Username);



                                        Boolean isSame = false;
                                        for (int i = 0 ; i < requestlist.size() ; i++){
                                            if (requestlist.get(i).getSender().equals(Username)){
                                                isSame = true;
                                                break;
                                            }else{
                                                isSame = false;

                                            }
                                        }
                                        if (isSame){
                                            for (int i = 1; i < stop_point[0]; i++){
                                                newRequest.put("UserRequest" + Integer.toString(i) + "SenderName", requestlist.get(i - 1).getSender());

                                            }

                                        }else{
                                            requestlist.add(new Request(Username));
                                            for (int i = 1; i <= stop_point[0]; i++){
                                                newRequest.put("UserRequest" + Integer.toString(i) + "SenderName", requestlist.get(i - 1).getSender());

                                            }
                                        }

                                        documentReference.set(newRequest);


                                        DocumentReference documentReference1 = db.collection(Username).document("RequestSendList");
                                        HashMap<String,String> newRequest1 = new HashMap<>();
                                        final int[] stop_point = new int[1];
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

                                                            requestlist1.add(new Request(sender));
                                                        }


                                                        Boolean issame = false;
                                                        for (int i = 0 ; i < requestlist1.size() ; i++){
                                                            if (requestlist1.get(i).getSender().equals(theID)){
                                                                issame = true;
                                                                break;
                                                            }else{
                                                                issame = false;

                                                            }

                                                        }
                                                        if (issame){
                                                            for (int i = 1; i < stop_point[0]; i++){
                                                                newRequest1.put("UserRequest" + Integer.toString(i) + "SenderName", requestlist1.get(i - 1).getSender());

                                                            }

                                                        }else{
                                                            requestlist1.add(new Request(theID));
                                                            for (int i = 1; i <= stop_point[0]; i++){
                                                                newRequest1.put("UserRequest" + Integer.toString(i) + "SenderName", requestlist1.get(i - 1).getSender());

                                                            }
                                                        }


                                                        documentReference1.set(newRequest1);
                                                        Intent Jump = new Intent();
                                                        Jump.setClass(CheckRequestNameActivity.this, CheckFollowingActivity.class);
                                                        Bundle bundle = new Bundle();
                                                        bundle.putString("UserName", Username);
                                                        Jump.putExtras(bundle);
                                                        startActivity(Jump);

                                                    }
                                                });













                                    }else{
                                        Toast.makeText(CheckRequestNameActivity.this, "User ID can not be found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }




            }
        });







    }
}