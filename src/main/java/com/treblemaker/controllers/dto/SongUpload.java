package com.treblemaker.controllers.dto;

import java.util.List;

public class SongUpload {

    public List<String> genres;
    public String songname;
    public String timesignature;
    public String key;
    public List<Integer> chords;

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getTimesignature() {
        return timesignature;
    }

    public void setTimesignature(String timesignature) {
        this.timesignature = timesignature;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Integer> getChords() {
        return chords;
    }

    public void setChords(List<Integer> chords) {
        this.chords = chords;
    }
}
