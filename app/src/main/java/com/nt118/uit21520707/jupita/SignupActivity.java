package com.nt118.uit21520707.jupita;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SignupActivity extends AppCompatActivity {

    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private final FirebaseDatabase mUserDatabase = FirebaseDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Button loginButton = findViewById(R.id.SI);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        EditText editTextUsername = findViewById(R.id.UN);
        EditText editTextPassword = findViewById(R.id.PW);
        EditText editTextConfirmPassword = findViewById(R.id.CPW);
        EditText editTextEmail = findViewById(R.id.Email);

        Button signupButton = findViewById(R.id.SU);

        CheckBox checkBoxAgreement = findViewById(R.id.CB);

        signupButton.setOnClickListener(view -> {
            final String targetUsername = editTextUsername.getText().toString();
            final String password = editTextPassword.getText().toString();
            final String confirmPassword = editTextConfirmPassword.getText().toString();
            @Nullable String targetEmail = editTextEmail.getText().toString();
            if (!password.equals(confirmPassword))
            {
                Toast.makeText(SignupActivity.this,
                        "Password does not match", Toast.LENGTH_LONG).show();
                Log.i("Signup", "Signup unsuccessfully");
                return;
            }

            if (!checkBoxAgreement.isChecked())
            {
                Toast.makeText(SignupActivity.this,
                        "You must agree with the Terms of Use", Toast.LENGTH_LONG).show();
                Log.i("Signup", "Signup unsuccessfully");
                return;
            }

            final boolean[] temp = {true};

            Query userQuery = mUserDatabase.getReference("Users").orderByChild("username")
                    .equalTo(targetUsername);

            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.getChildrenCount() > 0)
                        Toast.makeText(SignupActivity.this,
                                "Already a member of Jupita, please sign up.", Toast.LENGTH_LONG).show();
                    Log.i("Signup", "Signup unsuccessfully");
                    temp[0] = false;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };

            userQuery.addValueEventListener(valueEventListener);

            if (!temp[0]) return;

            userQuery.removeEventListener(valueEventListener);

            String registerEmail;

            if (targetEmail.length() == 0) {targetEmail = null; registerEmail = targetUsername + "@jupita.com";}
            else {registerEmail = targetEmail;}

            String finalTargetEmail = targetEmail;
            mAuth.createUserWithEmailAndPassword(registerEmail, password).addOnCompleteListener(task ->
            {
                if (task.isSuccessful())
                {
                    Toast.makeText(SignupActivity.this, "Signup successfully!", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user != null;
                    DatabaseReference databaseReference = mUserDatabase.getReference("Users").child(user.getUid());
                    databaseReference.child("email").setValue(finalTargetEmail);
                    databaseReference.child("username").setValue(targetUsername);
                    finish();
                }
                else
                {
                    Toast.makeText(SignupActivity.this, "Signup unsuccessfully!", Toast.LENGTH_LONG).show();
                    finish();
                }
            });
        });
    }

}