package com.nt118.uit21520707.jupita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.ViewHolder> {
    Context context;

    private final List<Music> musicList;

    public MusicAdapter(Context context, List<Music> list)
    {
        this.context = context;
        this.musicList = list;
    }

    @NonNull
    @Override
    public MusicAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_music, null, false);
        return new MusicAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.ViewHolder holder, int position) {
        Music music = musicList.get(position);
        assert music != null;
        holder.title.setText(music.getTitle());
        holder.artist.setText(music.getArtist());
    }

    @Override
    public int getItemCount() {
        return this.musicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        ImageView coverArt;
        TextView artist, title;
        View.OnClickListener onClickListener;
        View.OnLongClickListener onLongClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.artist = itemView.findViewById(R.id.artist);
            this.onClickListener = null;
            this.onLongClickListener = null;
        }

        public void setOnClickListener(View.OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
        }

        @Override
        public void onClick(View v) {
            v.setOnClickListener(this.onClickListener);
            v.callOnClick();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
