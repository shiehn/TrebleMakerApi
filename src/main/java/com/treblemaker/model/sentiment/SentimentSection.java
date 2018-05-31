package com.treblemaker.model.sentiment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentimentSection implements Serializable {

    public Map<String, List<String>> sentimentLabels = new HashMap<>();

    public Map<String, List<String>> getSentimentLabels() {
        return sentimentLabels;
    }

    public SentimentSection() {
    }

    public void setSentimentLabels(Map<String, List<String>> sentimentLabels) {
        this.sentimentLabels = sentimentLabels;
    }
}
