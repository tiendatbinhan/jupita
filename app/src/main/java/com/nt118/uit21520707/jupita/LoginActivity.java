package com.nt118.uit21520707.jupita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final FirebaseDatabase mUserDatabase = FirebaseDatabase.getInstance();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    private EditText editTextUsername;

    private EditText editTextPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_logout);

        Button loginButton = findViewById(R.id.LG);
        Button signupButton = findViewById(R.id.REG);
        editTextUsername = findViewById(R.id.edtUsn);
        editTextPassword = findViewById(R.id.edtPw);
        CheckBox rememberUser = findViewById(R.id.rmb);
        sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        String savedUsername = sharedPreferences.getString("username", "");
        String savedPassword = sharedPreferences.getString("password", "");
        boolean remembered = sharedPreferences.getBoolean("remembered", false);

        editTextUsername.setText(savedUsername);
        editTextPassword.setText(savedPassword);
        rememberUser.setChecked(remembered);

        loginButton.setOnClickListener(view -> {

            final String targetUsername = editTextUsername.getText().toString();

            Query userQuery = mUserDatabase.getReference("Users").orderByChild("username")
                    .equalTo(targetUsername);
            userQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() == 0)
                        Toast.makeText(LoginActivity.this,
                                "Wrong username or password. Try again.", Toast.LENGTH_LONG).show();
                    Log.i("Login", "Login unsuccessfully");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            userQuery.addChildEventListener(new ChildEventListener() {
                        String targetEmail;

                        final String targetPassword = editTextPassword.getText().toString();
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                            User user = snapshot.getValue(User.class);
                            if (user != null) {
                                targetEmail = user.email;
                                if (targetEmail == null) targetEmail = targetUsername + "@jupita.com";
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,
                                        "Wrong username or password. Try again.", Toast.LENGTH_LONG).show();
                                Log.i("Login", "Login unsuccessfully");
                                return;
                            }

                            mAuth.signInWithEmailAndPassword(targetEmail, targetPassword)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Toast.makeText(LoginActivity.this,
                                                    "Sign in successfully.", Toast.LENGTH_LONG).show();
                                            Log.i("Login", "Login successfully");

                                            if (rememberUser.isChecked())
                                            {
                                                editor.putString("username", targetUsername);
                                                editor.putString("password", targetPassword);
                                                editor.putBoolean("remembered", true);
                                                editor.apply();
                                            }
                                            else
                                            {
                                                editor.putString("username", "");
                                                editor.putString("password", "");
                                                editor.putBoolean("remembered", false);
                                                editor.apply();
                                            }
                                            Intent userInfo = new Intent(getApplicationContext(), UserInfoActivity.class);
                                            startActivity(userInfo);
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(LoginActivity.this,
                                                    "Wrong username or password. Try again.", Toast.LENGTH_LONG).show();
                                            Log.i("Login", "Login unsuccessfully");
                                        }
                                    });
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
        });
        signupButton.setOnClickListener(
                view -> {
                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);
                    finish();
                }
        );
    }
}
