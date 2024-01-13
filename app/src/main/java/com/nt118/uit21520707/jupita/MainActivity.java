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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

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
    ImageButton playBtn, pauseBtn;
    MediaPlayer mediaPlayer;

    // creating a string for storing
    // our audio url from firebase.
    String audioUrl;

    // creating a variable for our Firebase Database.
    final FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef;

    SeekBar seekBar;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playmusic);

//        if (MediaPlayerHelper.isPrepared)
//        {
//            if (mediaPlayer.isPlaying()) mediaPlayer.stop();
//            mediaPlayer.reset();
//            mediaPlayer.release();
//        }

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
                Log.i("uri", uri.toString());
                audioUrl = uri.toString();
                if (mediaPlayer == null) playAudio(audioUrl);
                else mediaPlayer.start();
            }
        });

        // initializing button
        playBtn = findViewById(R.id.idBtnPlay);
        final Boolean[] isPlaying = {true};

        playBtn.setImageResource(R.drawable.icon_pause);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying[0]) {
                    mediaPlayer.pause();
                    isPlaying[0] = false;
                    playBtn.setImageResource(R.drawable.icon_play);
                }
                else {
                    mediaPlayer.start();
                    isPlaying[0] = true;
                    playBtn.setImageResource(R.drawable.icon_pause);
                }
            }
        });

        ImageButton btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(view -> this.onBackPressed());

        ToggleButton btnLoop = findViewById(R.id.btnLoop);
        btnLoop.setOnClickListener(v -> {
            mediaPlayer.setLooping(btnLoop.isChecked());
            MediaPlayerHelper.isLoop = btnLoop.isChecked();
        });
    }

    private void playAudio(String audioUrl) {
        mediaPlayer = MediaPlayerHelper.getMediaPlayer();
        // below line is use to set the audio stream type for our media player.
        try {
            // below line is use to set our
            // url to our media player.
            mediaPlayer.setDataSource(audioUrl);

            mediaPlayer.prepare();
            setUI();

            mediaPlayer.start();

        } catch (IOException ignored) {
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        //debug code
//        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
//        startActivity(intent);
    }

    private void setUI()
    {
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

        handler = new Handler();

        textViewTitle.setSelected(true);
        textViewArtist.setSelected(true);

        seekBar.setMax(mediaPlayer.getDuration()/1000);
        int time = mediaPlayer.getDuration()/1000;
        textViewEndTime.setText(String.format("%02d",time/60)+":"+String.format("%02d",time%60));
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null || !MediaPlayerHelper.isPrepared)
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
