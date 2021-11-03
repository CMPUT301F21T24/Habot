package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    EditText UsernameLoginEditText;
    EditText PasswordLoginEditText;
    TextView IncorrectUsername;
    TextView IncorrectPassword;

    Button SignUpButton;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        UsernameLoginEditText = findViewById(R.id.UsernameLogin);
        PasswordLoginEditText = findViewById(R.id.PasswordLogin);
        IncorrectUsername = findViewById(R.id.incorrectUsername);
        IncorrectPassword = findViewById(R.id.incorrectPassword);

        SignUpButton = findViewById(R.id.sign_up_button);
        LoginButton = findViewById(R.id.log_in_button);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(LoginActivity.this, SignInActivity.class);
                startActivity(JumpToSignUp);
            }
        });

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncorrectPassword.setVisibility(View.INVISIBLE);
                IncorrectUsername.setVisibility(View.INVISIBLE);
                final String Username = UsernameLoginEditText.getText().toString();
                final String Password = PasswordLoginEditText.getText().toString();


                DocumentReference noteRef = db.collection(Username).document("UserProfile");
                noteRef.get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if(documentSnapshot.exists()){
                                    String password = documentSnapshot.getString("Password");
                                    if (password.equals(Password)){
                                        Intent JumpToMenu = new Intent();
                                        JumpToMenu.setClass(LoginActivity.this, MenuActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("UserName", Username);
                                        JumpToMenu.putExtras(bundle);
                                        startActivity(JumpToMenu);
                                    }
                                    else {
                                        IncorrectPassword.setVisibility(View.VISIBLE);
                                    }
                                }
                                else{
                                    IncorrectUsername.setVisibility(View.VISIBLE);
                                }
                            }
                        });
            }
        });
    }
}