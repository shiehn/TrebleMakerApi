package com.treblemaker.model.sentiment;

import java.io.Serializable;
import java.util.*;

public class SentimentSectionDto implements Serializable {

    private List<TrackToLabels> trackToLabelsList = new ArrayList<>();

    private List<LabelToTracks> labelsToTracksList = new ArrayList<>();

    public SentimentSectionDto() {
    }

    public SentimentSectionDto(SentimentSection sentimentSection) {

        for (Map.Entry<String, List<String>> mapEntry : sentimentSection.getSentimentLabels().entrySet()) {
            TrackToLabels sentimentLabels = new TrackToLabels(mapEntry.getKey(), mapEntry.getValue());
            trackToLabelsList.add(sentimentLabels);
        }

        Map<String, List<String>> labelToTracks = new HashMap<>();

        for (TrackToLabels trackToLabels : trackToLabelsList) {
            for(String label : trackToLabels.getLabels()){
                if(labelToTracks.get(label) == null){
                    List<String> names = new ArrayList<>();
                    names.add(trackToLabels.getName());
                    labelToTracks.put(label, names);
                }else{
                    labelToTracks.get(label).add(trackToLabels.getName());
                }
            }
        }

        for (Map.Entry<String, List<String>> labelToTracksEntry : labelToTracks.entrySet()) {
            labelsToTracksList.add(new LabelToTracks(labelToTracksEntry.getKey(), labelToTracksEntry.getValue()));
        }
    }

    public List<TrackToLabels> getTrackToLabelsList() {
        return trackToLabelsList;
    }

    public void setTrackToLabelsList(List<TrackToLabels> trackToLabelsList) {
        this.trackToLabelsList = trackToLabelsList;
    }

    public List<LabelToTracks> getLabelsToTracksList() {
        return labelsToTracksList;
    }

    public void setLabelsToTracksList(List<LabelToTracks> labelsToTracksList) {
        this.labelsToTracksList = labelsToTracksList;
    }
}