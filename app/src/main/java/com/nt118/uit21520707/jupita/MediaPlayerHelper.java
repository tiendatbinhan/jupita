package com.nt118.uit21520707.jupita;

import android.app.Application;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class MediaPlayerHelper {
    public static MediaPlayer mediaPlayer;
    public static boolean isPrepared = false;
    public static boolean isLoop = false;

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
                if (!isLoop)
                    MediaPlayerHelper.isPrepared = false;
            });
        }
        return mediaPlayer;
    }
}
