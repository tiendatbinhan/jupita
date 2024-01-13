package com.nt118.uit21520707.jupita;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SongSearchActivity extends AppCompatActivity {

    List<Music> str, str1;
    SearchListAdapter adt;
    ListView searchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.search_bar);
        str = MusicHelper.getMusicWithoutArt();
        str1 = MusicHelper.getMusicWithoutArt();

        searchList = findViewById(R.id.search_list);

        adt = new SearchListAdapter (SongSearchActivity.this, R.layout.item_song_search, str);

        searchList.setAdapter(adt);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                adt.clear();
                adt.addAll(str1);
                adt.getFilter().filter(newText);
                return true;
            }
        });
    }
}
