package com.nt118.uit21520707.jupita;

import androidx.annotation.Nullable;

public class Music {
    @Nullable byte[] embeddedCoverArt;
    @Nullable String artist, title;
    @Nullable String streamLink;
    public Music(){};

    public Music(@Nullable byte[] embeddedCoverArt, @Nullable String artist, @Nullable String title, @Nullable String streamLink) {
        this.embeddedCoverArt = embeddedCoverArt;
        this.artist = artist;
        this.title = title;
        this.streamLink = streamLink;
    }

    @Nullable
    public String getStreamLink() {
        return streamLink;
    }

    public void setStreamLink(@Nullable String streamLink) {
        this.streamLink = streamLink;
    }

    public byte[] getEmbeddedCoverArt() {
        return embeddedCoverArt;
    }

    public void setEmbeddedCoverArt(@Nullable byte[] embeddedCoverArt) {
        this.embeddedCoverArt = embeddedCoverArt;
    }

    @Nullable
    public String getArtist() {
        return artist;
    }

    public void setArtist(@Nullable String artist) {
        this.artist = artist;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }
}
