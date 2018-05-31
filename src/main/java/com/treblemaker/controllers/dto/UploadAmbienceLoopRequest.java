package com.treblemaker.controllers.dto;

import java.util.List;

public class UploadAmbienceLoopRequest {

    public List<Integer> genres;
    public List<Integer> characteristics;
    public List<Integer> rootChords;

    public int stationid;
    public int complexity;
    public int moodLight;
    public int moodDark;
    public int audioLength;

    public List<Integer> getGenres() {
        return genres;
    }

    public void setGenres(List<Integer> genres) {
        this.genres = genres;
    }

    public List<Integer> getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(List<Integer> characteristics) {
        this.characteristics = characteristics;
    }

    public List<Integer> getRootChords() {
        return rootChords;
    }

    public void setRootChords(List<Integer> rootChords) {
        this.rootChords = rootChords;
    }

    public int getStationid() {
        return stationid;
    }

    public void setStationid(int stationid) {
        this.stationid = stationid;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }

    public int getMoodLight() {
        return moodLight;
    }

    public void setMoodLight(int moodLight) {
        this.moodLight = moodLight;
    }

    public int getMoodDark() {
        return moodDark;
    }

    public void setMoodDark(int moodDark) {
        this.moodDark = moodDark;
    }

    public int getAudioLength() {
        return audioLength;
    }

    public void setAudioLength(int audioLength) {
        this.audioLength = audioLength;
    }
}
