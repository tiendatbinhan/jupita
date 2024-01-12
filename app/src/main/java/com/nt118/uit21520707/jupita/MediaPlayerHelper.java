package com.nt118.uit21520707.jupita;

import android.media.MediaPlayer;

public class MediaPlayerHelper {
    public static MediaPlayer mediaPlayer = new MediaPlayer();

    public MediaPlayerHelper()
    {

    }

    public static MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}
