package com.nt118.uit21520707.jupita;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class UserFragment extends Fragment {
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public UserFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        String email = firebaseUser.getEmail();
        assert email != null;
        final String[] username = new String[1];
        final String[] userId = new String[1];
        if (email.endsWith("@jupita.com"))
        {
            username[0] = email.replaceAll("@jupita.com", "");
        }
        else {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            Query userQuery = databaseReference.orderByChild("email")
                    .equalTo(email);
            final User[] user = new User[1];
            userQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    user[0] = snapshot.getValue(User.class);
                    userId[0] = snapshot.getKey();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            username[0] = user[0].username;
        }
        TextView textViewUsername = view.findViewById(R.id.username);
        textViewUsername.setText(username[0]);
        TextView textViewUserId = view.findViewById(R.id.userid);
        textViewUserId.setText(userId[0]);

        Button logoutButton = view.findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserFragment.this.requireActivity(), LoginActivity.class);
            startActivity(intent);
            firebaseAuth.signOut();
            UserFragment.this.requireActivity().finish();
        });
        return view;
    }
}
