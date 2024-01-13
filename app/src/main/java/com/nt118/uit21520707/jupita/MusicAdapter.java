package com.nt118.uit21520707.jupita;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
        Bitmap bitmap = BitmapFactory.decodeByteArray(music.getEmbeddedCoverArt(),
                0, music.getEmbeddedCoverArt().length);
        holder.coverArt.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return this.musicList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener
    {
        ImageView coverArt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.coverArt = itemView.findViewById(R.id.sample_cover_art);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}
