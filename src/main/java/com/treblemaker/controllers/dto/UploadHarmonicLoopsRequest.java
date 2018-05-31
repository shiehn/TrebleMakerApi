package com.treblemaker.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UploadHarmonicLoopsRequest {

    public int stationid;
    public boolean saveToDatabase;
    public List<Integer> genres;
    public List<Integer> characteristics;
    public List<Integer> instruments;
    public int complexity;
    public int moodLight;
    public int moodDark;
    public int audioLength;
    public List<Integer> chordRoots;
    public int bpm;
    public List<Boolean> rhythmicAccents;
}
