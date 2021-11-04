package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * This is log in activity that will prompt user to log in their unique identified profile
 */
public class LoginActivity extends AppCompatActivity {
    EditText UsernameLoginEditText;
    EditText PasswordLoginEditText;
    TextView IncorrectUsername;
    TextView IncorrectPassword;

    Button SignUpButton;
    Button LoginButton;

    /**
     * On create function
     * @param savedInstanceState
     * This function will run when the activity starts
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //find corresponding view in the layout files
        UsernameLoginEditText = findViewById(R.id.UsernameLogin);
        PasswordLoginEditText = findViewById(R.id.PasswordLogin);
        IncorrectUsername = findViewById(R.id.incorrectUsername);
        IncorrectPassword = findViewById(R.id.incorrectPassword);

        //initialize Firestore database
        SignUpButton = findViewById(R.id.sign_up_button);
        LoginButton = findViewById(R.id.log_in_button);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SignUpButton.setOnClickListener(new View.OnClickListener() {

            /**
             * Intent to SIGNUP activity for users to sign up a new account
             * @param v
             */
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(LoginActivity.this, SignUpActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            /**
             * check the username and password that user input match the ones in the database.
             * if not match,error will be display.
             * @param v
             */
            @Override
            public void onClick(View v) {

                //set IncorrectPassword and IncorrectUsername both error message be invisible
                IncorrectPassword.setVisibility(View.INVISIBLE);
                IncorrectUsername.setVisibility(View.INVISIBLE);

                //get user input from EditText View
                final String Username = UsernameLoginEditText.getText().toString();
                final String Password = PasswordLoginEditText.getText().toString();

                // get the username from the database, and check if it equals to the user input
                DocumentReference noteRef = db.collection(Username).document("UserProfile");
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            /**
                             * this takes documentSnapshot as parameter and
                             * if obtain data successfully, do the action
                             * @param documentSnapshot
                             */
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                //if get Snapshot in the userProfile successfully, compares the password with the user input
                                if(documentSnapshot.exists()){
                                    String password = documentSnapshot.getString("Password");
                                    // if passwords match, jump to menu page
                                    if (password.equals(Password)){
                                        Intent JumpToMenu = new Intent();
                                        JumpToMenu.setClass(LoginActivity.this, MenuActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("UserName", Username);
                                        JumpToMenu.putExtras(bundle);
                                        startActivity(JumpToMenu);
                                    }
                                    // else turn the error message visible
                                    else {
                                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                // else turn the error message visible
                                else{
                                    Toast.makeText(LoginActivity.this, "Username not found, please sign up first", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}