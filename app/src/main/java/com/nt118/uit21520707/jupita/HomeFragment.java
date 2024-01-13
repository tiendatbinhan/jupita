package com.nt118.uit21520707.jupita;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeFragment extends Fragment {
    public HomeFragment()
    {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerViewFavourite = view.findViewById(R.id.list_favourite);
        List<Music> musicArrayList = MusicHelper.getMusicWithoutArtRecommendation();
        MusicAdapter musicAdapter = new MusicAdapter(this.getContext(), musicArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewFavourite.setLayoutManager(layoutManager);
        recyclerViewFavourite.setAdapter(musicAdapter);
        Handler handler = new Handler();

        this.requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                musicAdapter.notifyDataSetChanged();
                handler.postDelayed(this, 1000);
            }
        });

        ImageButton btnSearch = view.findViewById(R.id.search_btn);
        btnSearch.setOnClickListener(v -> {
            Intent intent = new Intent(this.requireActivity(), SongSearchActivity.class);
            startActivity(intent);
        });
        return view;
    }
}
