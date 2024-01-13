package com.nt118.uit21520707.jupita;

import android.app.Application;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicHelper extends Application {
    public MusicHelper(){}
    @Deprecated
    static List<Music> getMusicList()
    {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Songs");
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference[] reference = new StorageReference[1];
        Query query = ref.orderByChild("location");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> {
                    if (musicArrayList.size() < 3)
                    {
                        String url = dataSnapshot.child("location").getValue(String.class);
                        Log.i("uri", url);
                        final String[] audio_url = new String[1];
                        reference[0] = storage.getReferenceFromUrl(url);

                        reference[0].getDownloadUrl().addOnSuccessListener(uri -> {
                            audio_url[0] = uri.toString();
                            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                            mmr.setDataSource(audio_url[0], new HashMap<>());
                            byte[] bytes;
                            bytes = mmr.getEmbeddedPicture();

                        });
                        Music newMusic = new Music(null, null, null, url);
                        musicArrayList.add(newMusic);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return musicArrayList;
    }

    static List<Music> getMusicWithoutArt()
    {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Songs");
        Query query = ref.orderByChild("location");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> {
                    String artist = dataSnapshot.child("artist").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String url = dataSnapshot.child("location").getValue(String.class);
                    Music music = new Music(null, artist, title, url);
                    musicArrayList.add(music);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return musicArrayList;
    }

    static List<Music> getMusicWithoutArtRecommendation()
    {
        ArrayList<Music> musicArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Songs");
        Query query = ref.orderByChild("location");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                snapshot.getChildren().forEach(dataSnapshot -> {
                    String artist = dataSnapshot.child("artist").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String url = dataSnapshot.child("location").getValue(String.class);
                    Music music = new Music(null, artist, title, url);
                    musicArrayList.add(music);
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Collections.shuffle(musicArrayList);
        ArrayList<Music> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            result.add(musicArrayList.get(i));
        }
        return result;
    }
}
