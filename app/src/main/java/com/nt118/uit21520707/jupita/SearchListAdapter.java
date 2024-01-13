package com.nt118.uit21520707.jupita;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.List;

public class SearchListAdapter extends ArrayAdapter<Song> {

    private Activity context;
    public SearchListAdapter(Activity context, int layoutID, List<Song>
            objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.item_song_search, null,
                            false);
        }

        Song song = getItem(position);

        TextView title = (TextView)
                convertView.findViewById(R.id.title);
        TextView artist = (TextView)
                convertView.findViewById(R.id.artist);
        ConstraintLayout itemsearch = (ConstraintLayout)
                convertView.findViewById(R.id.item_search);
        if (song.getTitle() != null)
        {
            title.setText(song.getTitle());
        }

        if (song.getArtist() != null)
        {
            artist.setText(song.getArtist());
        }
        return convertView;
    }


}
