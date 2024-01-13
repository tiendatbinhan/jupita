package com.nt118.uit21520707.jupita;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SongSearchActivity extends AppCompatActivity {

    TextView title;
    TextView artist;
    List<Music> str;
    SearchListAdapter adt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchView searchView = findViewById(R.id.search_bar);
        str = str = MusicHelper.getMusicWithoutArt();

        ListView searchList = findViewById(R.id.search_list);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (searchList.getAdapter() == null)
                {
                    adt = new SearchListAdapter (SongSearchActivity.this, R.layout.item_song_search, str);
                    searchList.setAdapter(adt);
                }
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (searchList.getAdapter() == null)
                {
                    adt = new SearchListAdapter (SongSearchActivity.this, R.layout.item_song_search, str);
                    searchList.setAdapter(adt);
                }
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String query)
    {
        adt.getFilter().filter(query);
    }
}
