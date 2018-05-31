package com.treblemaker.model.sentiment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LabelToTracks implements Serializable {
    private String label = "";
    private List<String> tracks = new ArrayList<>();

    public LabelToTracks(){}

    public LabelToTracks(String label, List<String> tracks) {
        this.label = label;
        this.tracks = tracks;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<String> getTracks() {
        return tracks;
    }

    public void setTracks(List<String> tracks) {
        this.tracks = tracks;
    }
}
