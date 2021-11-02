package com.example.habot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    EditText UsernameEditText;
    EditText PasswordEdiText;

    Button CancelButton;
    Button ConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        UsernameEditText = findViewById(R.id.username);
        PasswordEdiText = findViewById(R.id.Password);

        CancelButton = findViewById(R.id.cancel_button);
        ConfirmButton = findViewById(R.id.confirm_buttom);

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Username = UsernameEditText.getText().toString();
                final String Password = PasswordEdiText.getText().toString();


                final CollectionReference collectionReference = db.collection(Username);
                HashMap<String, String> HabitList = new HashMap<>();
                collectionReference
                        .document("HabitList")
                        .set(HabitList);

                HashMap<String, String> HabitEventList = new HashMap<>();
                collectionReference
                        .document("HabitEventList")
                        .set(HabitEventList);

                HashMap<String, String> Profile = new HashMap<>();
                Profile.put("Password", Password);
                collectionReference
                        .document("UserProfile")
                        .set(Profile)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d("Username","Username and Password have been successfully added.");

                            }
                        });
                UsernameEditText.setText("");
                PasswordEdiText.setText("");

            }
        });







    }
}