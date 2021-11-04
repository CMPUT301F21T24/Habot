package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HabitEventDetailActivity extends AppCompatActivity {

    FirebaseFirestore db;
    TextView habit_name;
    TextView habit_comment;
    TextView habit_status;
    TextView habit_time;
    Button backtomenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.habiteventdetail);

        Bundle bundle = getIntent().getExtras();
        String Username = bundle.getString("UserName");
        int position = bundle.getInt("position");

        habit_name = findViewById(R.id.detail_name);
        habit_comment = findViewById(R.id.detail_comment);
        habit_status = findViewById(R.id.detail_status);
        habit_time = findViewById(R.id.detail_time);
        backtomenu = findViewById(R.id.HabitDetailToMenu);
        db = FirebaseFirestore.getInstance();



        DocumentReference noteRef = db.collection(Username).document("HabitEventList");
        noteRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value.exists()){
                    String name = value.getString("habitevent"+Integer.toString(position+1)+"name");
                    String comment = value.getString("habitevent"+Integer.toString(position+1)+"comment");
                    String status = value.getString("habitevent"+Integer.toString(position+1)+"status");
                    String time = value.getString("habitevent"+Integer.toString(position+1)+"eventtime");

                    habit_name.setText(name);
                    habit_comment.setText(comment);
                    habit_status.setText(status);
                    habit_time.setText(time);

                }
            }
        });

        backtomenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Jump = new Intent();
                Jump.setClass(HabitEventDetailActivity.this, HabitEventActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("UserName", Username);
                Jump.putExtras(bundle);
                startActivity(Jump);
            }
        });


    }
}
