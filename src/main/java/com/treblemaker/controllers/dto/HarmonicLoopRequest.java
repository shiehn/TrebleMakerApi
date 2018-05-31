package com.treblemaker.controllers.dto;

import java.util.List;

public class HarmonicLoopRequest
{
    public boolean saveToDatabase;
    public List<Integer> genres;
    public List<Integer> characteristics;
    public List<Integer> instruments;
    public int complexity;
    public int moodDark;
    public int moodLight;
    public int AudioLength;
    public int Bpm;
    public List<Integer> chordRoots;
    public List<Boolean> rhythmicAccents;
}
