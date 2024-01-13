package com.nt118.uit21520707.jupita;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class SongSearchActivity extends AppCompatActivity {

    TextView title;
    TextView artist;
    ArrayList<Song> str = new ArrayList<>();
    SearchListAdapter adt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        adt = new SearchListAdapter (this, R.layout.item_song_search, str);
        ListView searchList = findViewById(R.id.search_list);
        searchList.setAdapter(adt);
    }
}
