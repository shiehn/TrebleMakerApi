package com.treblemaker.model.request;

import java.util.List;

public class ChordProgressionPost {

    private List<Integer> genres;
    private String songname;
    private String timesignature;
    private String key;
    private List<Integer> chords;

    public ChordProgressionPost(){}

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
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
