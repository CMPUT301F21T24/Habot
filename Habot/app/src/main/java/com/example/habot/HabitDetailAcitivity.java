package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.protobuf.StringValue;
import java.util.ArrayList;

/**
 * This activity is created when the activity starts, generate Habit Detail Activity
 */
public class HabitDetailAcitivity extends AppCompatActivity {

    //Initialize the variables
    Button HabitDetailBackButton;
    Button AddHabitButton;
    ArrayAdapter<Habit> habitsadapater;
    ArrayList<Habit> habitlist;
    FirebaseFirestore db;
    ListView habitdetail;

    /**
     * Action when the activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitdetail);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //Find id from layout files
        HabitDetailBackButton = findViewById(R.id.HabitDetailToMenu);
        AddHabitButton = findViewById(R.id.newHabits_button);

        HabitDetailBackButton.setOnClickListener(new View.OnClickListener() {

            /**
             * This intent will direct users from Habit Detail Page back to Menu Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(HabitDetailAcitivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        AddHabitButton.setOnClickListener(new View.OnClickListener() {

            /**
             * This intent will direct users from Habit Detail Page to Add New Habit Page
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(HabitDetailAcitivity.this, AddNewHabitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putBoolean("edit",false);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        //set up the adapters for the data list and list view
        habitdetail = findViewById(R.id.HabitDetail);
        habitlist = new ArrayList<Habit>();

        habitsadapater = new Habitlist(this,habitlist);

        habitdetail.setAdapter(habitsadapater);

        //find username from firestore collection,
        //and find HabitList from corresponding document
        db = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = db.collection(Username);
        DocumentReference noteRef = collectionReference.document("HabitList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

            /**
             * This will get users input from the add new habit activity and
             * import them to Firestore database
             * @param value
             * @param error
             */
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                habitlist.clear();
                for (int i =1;;i++)
                {
                    //get the Habit Name
                    String title = (String) value.getString("habit"+Integer.toString(i)+"name");
                    //The Habit name cannot be null
                    if(title==null){
                        break;
                    }
                    Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + title);

                    //get Habit reson and date
                    String reason = value.getString("habit"+Integer.toString(i)+"reason");
                    String date = value.getString("habit"+Integer.toString(i)+"date");

                    //add Title, reason and date to the habitlist
                    habitlist.add(new Habit(title, reason, date));

                }
                //add Habit to the dataset
                habitsadapater.notifyDataSetChanged();
            }
        });




        habitdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This takes 4 parameter as input, once users click on the item, it jumps
             * @param adapterView
             * @param view
             * @param i
             * @param l
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent Jump = new Intent();
                Jump.setClass(HabitDetailAcitivity.this, AddNewHabitActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                bundle.putInt("position",i);
                bundle.putBoolean("edit",true);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

    }
}
