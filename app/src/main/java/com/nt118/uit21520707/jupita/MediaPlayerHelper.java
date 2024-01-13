package com.nt118.uit21520707.jupita;

import android.app.Application;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

public class MediaPlayerHelper {
    public static MediaPlayer mediaPlayer;
    public static boolean isPrepared = false;
    public static boolean isLoop = false;
    public static Deque<Music> trackList = new ArrayDeque<>();
    public static Stack<Music> prevTrackList = new Stack<>();

    public MediaPlayerHelper()
    {
    }

    public static MediaPlayer getMediaPlayer() {
        if (mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(mp -> {
                MediaPlayerHelper.isPrepared = true;
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                Music prevTrack = trackList.pop();
                MediaPlayerHelper.prevTrackList.push(prevTrack);
                if (!isLoop && trackList.isEmpty())
                    MediaPlayerHelper.isPrepared = false;
            });
        }
        return mediaPlayer;
    }
}
