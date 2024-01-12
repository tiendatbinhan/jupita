package com.nt118.uit21520707.jupita;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    // creating a variable for
    // button and media player
    Button playBtn, pauseBtn;
    MediaPlayer mediaPlayer;

    // creating a string for storing
    // our audio url from firebase.
    String audioUrl;

    // creating a variable for our Firebase Database.
    final FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;

    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);

        Intent intent = getIntent();
        String musicUrl = intent.getStringExtra("track_url");
        if (musicUrl == null)
            musicUrl = "gs://jupita-e0dce.appspot.com/Tomas Skyldeberg - In The Deep Ocean.flac";

        seekBar = findViewById(R.id.music_seek);

        storageRef = storage.getReferenceFromUrl(musicUrl);
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {

            @Override
            public void onSuccess(Uri uri) {
                audioUrl = uri.toString();
            }
        });

        // initializing our buttons
        playBtn = findViewById(R.id.idBtnPlay);
        pauseBtn = findViewById(R.id.idBtnPause);

        // setting on click listener for our play and pause buttons.
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling method to play audio.
                playAudio(audioUrl);
            }
        });
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking the media player
                // if the audio is playing or not.
                if (mediaPlayer.isPlaying()) {
                    // pausing the media player if
                    // media player is playing we are
                    // calling below line to stop our media player.
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();

                    // below line is to display a message when media player is paused.
                    Toast.makeText(MainActivity.this, "Audio has been paused", Toast.LENGTH_SHORT).show();
                } else {
                    // this method is called when media player is not playing.
                    Toast.makeText(MainActivity.this, "Audio has not played", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void playAudio(String audioUrl) {
        // initializing media player
        mediaPlayer = new MediaPlayer();

        // below line is use to set the audio stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // below line is use to set our
            // url to our media player.
            mediaPlayer.setDataSource(audioUrl);

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(audioUrl, new HashMap<String, String>());

            ImageView imageView = findViewById(R.id.music_cover_art);
            TextView textViewTitle = findViewById(R.id.music_title);
            TextView textViewArtist = findViewById(R.id.music_artist);
            TextView textViewCurTime = findViewById(R.id.music_time_current);
            TextView textViewEndTime = findViewById(R.id.music_time_end);

            byte[] bytes = mmr.getEmbeddedPicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            imageView.setImageBitmap(bitmap);

            textViewTitle.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            textViewArtist.setText(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));

            Handler handler = new Handler();

            textViewTitle.setSelected(true);
            textViewArtist.setSelected(true);

            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            seekBar.setMax(mediaPlayer.getDuration());
            int time = mediaPlayer.getDuration()/1000;
            textViewEndTime.setText(String.format("%02d",time/60)+":"+String.format("%02d",time%60));
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(mediaPlayer != null)
                    {
                        int pos = mediaPlayer.getCurrentPosition()/1000;
                        seekBar.setProgress(pos);
                        textViewCurTime.setText(String.format("%02d",pos/60)+":"+String.format("%02d",pos%60));
                    }
                    handler.postDelayed(this, 1000);
                }
            });

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(mediaPlayer != null && fromUser)
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

            mediaPlayer.start();

            // below line is use to display a toast message.
//            Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            // this line of code is use to handle error while playing our audio file.
//            Toast.makeText(this, "Error found is " + e, Toast.LENGTH_SHORT).show();
        }
    }
}
