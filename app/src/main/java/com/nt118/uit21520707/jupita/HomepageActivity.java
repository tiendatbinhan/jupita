package com.nt118.uit21520707.jupita;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

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

        Handler handler = new Handler();

        ConstraintLayout currentPlaying = findViewById(R.id.current);
        MediaPlayer mediaPlayer = MediaPlayerHelper.getMediaPlayer();
        if (!MediaPlayerHelper.isPrepared)
        {
            currentPlaying.setVisibility(View.GONE);
        }
        else
        {
            TextView textViewCurTime = findViewById(R.id.music_time_current);
            TextView textViewEndTime = findViewById(R.id.music_time_end);
            currentPlaying.setVisibility(View.VISIBLE);
            SeekBar seekBar = findViewById(R.id.music_seek);
            seekBar.setMax(mediaPlayer.getDuration()/1000);
            int time = mediaPlayer.getDuration()/1000;

            textViewEndTime.setText(String.format("%02d",time/60)+":"+String.format("%02d",time%60));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null)
                    {
                        int pos = mediaPlayer.getCurrentPosition() / 1000;
                        seekBar.setProgress(pos);
                        textViewCurTime.setText(String.format("%02d",pos/60)+":"+String.format("%02d",pos%60));

                    }
                    handler.postDelayed(this, 1000);
                }
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser)
                    {
                        mediaPlayer.seekTo(progress*1000);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        MediaPlayer mediaPlayer = MediaPlayerHelper.getMediaPlayer();
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
    }
}