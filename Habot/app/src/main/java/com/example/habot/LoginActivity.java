package com.example.habot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    EditText UsernameLoginEditText;
    EditText PasswordLoginEditText;

    Button SignUpButton;
    Button LoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        UsernameLoginEditText = findViewById(R.id.UsernameLogin);
        PasswordLoginEditText = findViewById(R.id.PasswordLogin);

        SignUpButton = findViewById(R.id.sign_up_button);
        LoginButton = findViewById(R.id.log_in_button);

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent JumpToSignUp = new Intent();
                JumpToSignUp.setClass(LoginActivity.this, SignInActivity.class);
                startActivity(JumpToSignUp);
            }
        });
    }
}
