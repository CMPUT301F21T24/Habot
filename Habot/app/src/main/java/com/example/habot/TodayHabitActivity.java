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

/**
 * This creates TodayHabitActivity when this starts.
 */
public class TodayHabitActivity extends AppCompatActivity {
    //initialize variables
    Button TodayHabitBackButton;
    ListView Todayhabitdetail;
    FirebaseFirestore db;
    ArrayAdapter<Habit> Todayhabitsadapater;
    ArrayList<Habit> Todayhabitlist;


    /**
     * Action after create this activity
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todayhabit);

        //set up the adapters for the data list and list view
        Todayhabitdetail = findViewById(R.id.TodayHabitListView);

        Todayhabitlist = new ArrayList<Habit>();

        Todayhabitsadapater = new Habitlist(this,Todayhabitlist);

        Todayhabitdetail.setAdapter(Todayhabitsadapater);

        //get username from bundle
        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        Log.d("TAG", "----------------> Username is :"+Username);

        //find id from layout files
        TodayHabitBackButton = findViewById(R.id.TodayHabitToMenu);

        TodayHabitBackButton.setOnClickListener(new View.OnClickListener() {
            /**
             * This intent will direct users from Today Habit Activity to Menu Activity
             * once users pressed Back button on the left top corner
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent Jump = new Intent();
                Jump.setClass(TodayHabitActivity.this, MenuActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });






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
                Todayhabitlist.clear();
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
                    String time = value.getString("habit"+Integer.toString(i)+"time");
                    String privacy = value.getString("habit" + Integer.toString(i) + "privacy");

                    //add Title, reason and date to the habitlist
                    Todayhabitlist.add(new Habit(title, reason, date, time, privacy));

                }
                //add Habit to the dataset
                Todayhabitsadapater.notifyDataSetChanged();
            }
        });




//        Todayhabitdetail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            /**
//             * This takes 4 parameter as input, once users click on the item, it jumps
//             * @param adapterView
//             * @param view
//             * @param i
//             * @param l
//             */
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent Jump = new Intent();
//                Jump.setClass(TodayHabitActivity.this, AddNewHabitActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("UserName", Username);
//                bundle.putInt("position",i);
//                bundle.putBoolean("edit",true);
//                Jump.putExtras(bundle);
//                startActivity(Jump);
//            }
//        });


    }
}
