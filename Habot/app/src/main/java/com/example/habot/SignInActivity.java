package com.example.habot;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class SignInActivity extends AppCompatActivity {

    EditText UsernameSignInEditText;
    EditText PasswordSignInEdiText;

    Button CancelButton;
    Button ConfirmButton;

    Button BackToSignUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        UsernameSignInEditText = findViewById(R.id.usernameSignIn);
        PasswordSignInEdiText = findViewById(R.id.PasswordSignIn);

        CancelButton = findViewById(R.id.cancel_button);
        ConfirmButton = findViewById(R.id.confirm_buttom);
        BackToSignUpButton = findViewById(R.id.BackToSignUp);

        BackToSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(SignInActivity.this, LoginActivity.class);
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
//                final int[] Exist = {0};
//
//                collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {
//                        for(QueryDocumentSnapshot doc: queryDocumentSnapshots){
//                            if(doc.getId() == "UserProfile"){
//                                Exist[0] = 1;
//                            }
//                        }
//                    }
//                });
//                if (Exist[0] == 0) {
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
            }


        });

    }
}