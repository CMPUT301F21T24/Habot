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

/**
 * This activity prompt user to create a new account.
 */
public class SignUpActivity extends AppCompatActivity {
    // initialize variables
    EditText UsernameSignInEditText;
    EditText PasswordSignInEdiText;

    Button CancelButton;
    Button ConfirmButton;

    Button BackToSignUpButton;

    /**
     * on create the page when activity starts.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        // connect the id from layout file
        UsernameSignInEditText = findViewById(R.id.usernameSignIn);
        PasswordSignInEdiText = findViewById(R.id.PasswordSignIn);

        CancelButton = findViewById(R.id.cancel_button);
        ConfirmButton = findViewById(R.id.confirm_buttom);
        BackToSignUpButton = findViewById(R.id.BackToSignUp);

        CancelButton.setOnClickListener(new View.OnClickListener() {

            /**
             * when user press the cancel button, reset the username and password input.
             * @param v
             */
            @Override
            public void onClick(View v) {
                // set username and password to empty string
                UsernameSignInEditText.setText("");
                PasswordSignInEdiText.setText("");
            }
        });

        BackToSignUpButton.setOnClickListener(new View.OnClickListener() {
            /**
             * when user click the back button on the top left corner,
             * user are direct back to the log in page.
             * @param v
             */
            @Override
            public void onClick(View v) {
                // set intent and jump from sign up page to log in page
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(SignUpActivity.this, LoginActivity.class);
                startActivity(JumpToSignUp);
            }
        });
        // open and connect with firebase
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        ConfirmButton.setOnClickListener(new View.OnClickListener() {

            /**
             * copy the user input and create a new account.
             * @param view
             */
            @Override
            public void onClick(View view) {
                // copy user input for username and password
                final String Username = UsernameSignInEditText.getText().toString();
                final String Password = PasswordSignInEdiText.getText().toString();

                // set username as the collection name
                final CollectionReference collectionReference = db.collection(Username);

                // copy password to Userprofile document as a snapshot
                DocumentReference noteRef = db.collection(Username).document("UserProfile");
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            /**
                             * create user documents after user pressed confirm button.
                             * @param documentSnapshot
                             */
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {

                                }
                                else{

                                    //create RequestSendList document
                                    HashMap<String, String> RequestSendList = new HashMap<>();
                                    collectionReference
                                            .document("RequestSendList")
                                            .set(RequestSendList);

                                    //create RequestReceivedList document
                                    HashMap<String, String> RequestRecievedList = new HashMap<>();
                                    collectionReference
                                            .document("RequestRecievedList")
                                            .set(RequestRecievedList);

                                    //create HabitList document
                                    HashMap<String, String> HabitList = new HashMap<>();
                                    collectionReference
                                            .document("HabitList")
                                            .set(HabitList);

                                    //create HabitEventList document
                                    HashMap<String, String> HabitEventList = new HashMap<>();
                                    collectionReference
                                            .document("HabitEventList")
                                            .set(HabitEventList);

                                    //create Profile document
                                    HashMap<String, String> Profile = new HashMap<>();
                                    Profile.put("Password", Password);
                                    collectionReference
                                            .document("UserProfile")
                                            .set(Profile)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {

                                                /**
                                                 * if username and password are added, display the Log message
                                                 * @param unused
                                                 */
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("Username", "Username and Password have been successfully added.");

                                                }
                                            });

                                    //after sign up account successfully, clear the user input
                                    UsernameSignInEditText.setText("");
                                    PasswordSignInEdiText.setText("");
                                }
                            }
                        });
            }
        });
    }
}