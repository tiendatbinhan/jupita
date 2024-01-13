package com.nt118.uit21520707.jupita;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchListAdapter extends ArrayAdapter<Music> {

    private Activity context;
    private Filter filter;
    private List<Music> original, fItems;
    public SearchListAdapter(Activity context, int layoutID, List<Music>
            objects) {
        super(context, layoutID, objects);
        this.context = context;
        this.original = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.item_song_search, null,
                            false);
        }

        Music song = getItem(position);

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

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) filter = new MusicFilter();
        return filter;
    }

    class MusicFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            CharSequence prefix = constraint.toString().toLowerCase();
            ArrayList<Music> filterItems;
            if (constraint == null || constraint.toString().length() == 0)
            {
                results.count = original.size();
                results.values = original;
            }
            else
            {
                filterItems = new ArrayList<>();
                original.forEach(music -> {
                    String artistLower = music.getArtist().toLowerCase();
                    String titleLower = music.getTitle().toLowerCase();
                    if (artistLower.contains(prefix) || titleLower.contains(prefix))
                    {
                        filterItems.add(music);
                    }
                });
                results.count = filterItems.size();
                results.values = filterItems;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            for(Music item : (List<Music>) results.values)
            {
                add(item);
            }
            notifyDataSetChanged();
        }
    }
}
