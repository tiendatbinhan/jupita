package com.nt118.uit21520707.jupita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.security.KeyStore;

public class HomepageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navi_bar);
        NavigationBarView.OnItemSelectedListener listener = new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int itemId = item.getItemId();
                if (itemId == R.id.item_home)
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new HomeFragment())
                            .commit();
                    return true;
                }
                else if (itemId == R.id.item_playlist)
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new PlaylistFragment())
                            .commit();
                    return true;
                }
                else if (itemId == R.id.item_user)
                {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new UserFragment())
                            .commit();
                    return true;
                }
                return false;
            }
        };
        bottomNavigationView.setOnItemSelectedListener(listener);
        bottomNavigationView.setSelectedItemId(R.id.item_home);
    }
}