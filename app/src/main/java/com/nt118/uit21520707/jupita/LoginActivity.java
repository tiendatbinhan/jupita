package com.nt118.uit21520707.jupita;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final FirebaseDatabase mUserDatabase = FirebaseDatabase.getInstance();

    private final Button loginButton = findViewById(R.id.LG);

    private final EditText editTextEmail = findViewById(R.id.edtUsn);

    private final EditText editTextPassword = findViewById(R.id.edtPw);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_logout);

        loginButton.setOnClickListener(view -> {

        });
    }
}
