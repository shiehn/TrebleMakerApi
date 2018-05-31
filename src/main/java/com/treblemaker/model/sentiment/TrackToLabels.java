package com.treblemaker.model.sentiment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TrackToLabels implements Serializable {
    private String name = "";
    private String labelIconUrl = "";
    private List<String> labels = new ArrayList<>();

    public TrackToLabels() {
    }

    public TrackToLabels(String name, List<String> labels) {
        this.name = name;
        this.labels = labels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelIconUrl() {
        return labelIconUrl;
    }

    public void setLabelIconUrl(String labelIconUrl) {
        this.labelIconUrl = labelIconUrl;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
