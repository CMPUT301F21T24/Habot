package com.example.habot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    EditText UsernameSignInEditText;
    EditText PasswordSignInEdiText;

    Button CancelButton;
    Button ConfirmButton;

    Button BackToSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        UsernameSignInEditText = findViewById(R.id.usernameSignIn);
        PasswordSignInEdiText = findViewById(R.id.PasswordSignIn);

        CancelButton = findViewById(R.id.cancel_button);
        ConfirmButton = findViewById(R.id.confirm_buttom);
        BackToSignUpButton = findViewById(R.id.BackToSignUp);

        CancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsernameSignInEditText.setText("");
                PasswordSignInEdiText.setText("");
            }
        });

        BackToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(SignUpActivity.this, LoginActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Username = UsernameSignInEditText.getText().toString();
                final String Password = PasswordSignInEdiText.getText().toString();


                final CollectionReference collectionReference = db.collection(Username);

                DocumentReference noteRef = db.collection(Username).document("UserProfile");
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {

                                }
                                else{
                                    HashMap<String, String> RequestSendList = new HashMap<>();
                                    collectionReference
                                            .document("RequestSendList")
                                            .set(RequestSendList);

                                    HashMap<String, String> RequestRecievedList = new HashMap<>();
                                    collectionReference
                                            .document("RequestRecievedList")
                                            .set(RequestRecievedList);

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
                                                    Log.d("Username", "Username and Password have been successfully added.");

                                                }
                                            });
                                    UsernameSignInEditText.setText("");
                                    PasswordSignInEdiText.setText("");
                                    Intent JumpToLogIn = new Intent();
                                    JumpToLogIn.setClass(SignUpActivity.this, LoginActivity.class);
                                    startActivity(JumpToLogIn);
                                }
                            }
                        });
            }
        });
    }
}