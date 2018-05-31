package com.treblemaker.controllers.dto;

import java.util.List;

public class UploadBeatsRequest{

    public int stationid;
    public List<Integer> genres;
    public List<Integer> characteristics;
    public List<Integer> instruments;
    public List<Integer> rootChords;
    public int complexity;
    public int moodLight;
    public int moodDark;
    public int tempo;
    public int barcount;
    public String timeSignature;
    public List<Boolean> rhythmicAccents;
}
