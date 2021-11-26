package com.example.habot;

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
import java.util.HashMap;

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
    Button SwapButton;
    EditText Swaphabit1;
    EditText Swaphabit2;

    /**
     * Action when the activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habitdetail);
        final int[] stop_point = new int[1];

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //Find id from layout files
        HabitDetailBackButton = findViewById(R.id.HabitDetailToMenu);
        AddHabitButton = findViewById(R.id.newHabits_button);
        SwapButton = findViewById(R.id.swap_button);
        Swaphabit1 = findViewById(R.id.swaphabit1);
        Swaphabit2 = findViewById(R.id.swaphabit2);

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
                        stop_point[0] = i;
                        break;
                    }
                    Log.d("TAG", "onEvent: !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!" + title);

                    //get Habit reson and date
                    String reason = value.getString("habit"+Integer.toString(i)+"reason");
                    String date = value.getString("habit"+Integer.toString(i)+"date");
                    String time = value.getString("habit"+Integer.toString(i)+"time");
                    String privacy = value.getString("habit" + Integer.toString(i)+"privacy");

                    //add Title, reason and date to the habitlist
                    habitlist.add(new Habit(title, reason, date, time, privacy));

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
                bundle.putBoolean("calendar",false);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });

        SwapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String habit1name = Swaphabit1.getText().toString();
                String habit2name = Swaphabit2.getText().toString();

                int position1 = -1,position2 = -1;

                for(int i = 0;i<(habitlist.size());i++){
                    if(habitlist.get(i).gettitle().equals(habit1name)){
                        position1 = i;
                    }
                    if (habitlist.get(i).gettitle().equals(habit2name)){
                        position2 = i;
                    }
                }
                if (position1!=-1 && position2 != -1) {
                    HashMap<String,String> updatehabit = new HashMap<>();
                    for (int i =1 ; i < stop_point[0]; i++){
                        if((i-1) == position1){
                            updatehabit.put("habit" + Integer.toString(i) + "name", habitlist.get(position2).gettitle());
                            updatehabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(position2).getreason());
                            updatehabit.put("habit" + Integer.toString(i) + "date", habitlist.get(position2).getdate());
                            updatehabit.put("habit" + Integer.toString(i) + "time", habitlist.get(position2).getTime());
                            updatehabit.put("habit" + Integer.toString(i) + "privacy", habitlist.get(position2).getPrivacy());
                        }
                        else if((i-1) == position2){
                            updatehabit.put("habit" + Integer.toString(i) + "name", habitlist.get(position1).gettitle());
                            updatehabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(position1).getreason());
                            updatehabit.put("habit" + Integer.toString(i) + "date", habitlist.get(position1).getdate());
                            updatehabit.put("habit" + Integer.toString(i) + "time", habitlist.get(position1).getTime());
                            updatehabit.put("habit" + Integer.toString(i) + "privacy", habitlist.get(position1).getPrivacy());
                        }
                        else {
                            updatehabit.put("habit" + Integer.toString(i) + "name", habitlist.get(i-1).gettitle());
                            updatehabit.put("habit" + Integer.toString(i) + "reason", habitlist.get(i-1).getreason());
                            updatehabit.put("habit" + Integer.toString(i) + "date", habitlist.get(i-1).getdate());
                            updatehabit.put("habit" + Integer.toString(i) + "time", habitlist.get(i-1).getTime());
                            updatehabit.put("habit" + Integer.toString(i) + "privacy", habitlist.get(i-1).getPrivacy());
                        }
                        noteRef.set(updatehabit);
                    }
                }else{
                    Toast.makeText(HabitDetailAcitivity.this, "habit not found", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
