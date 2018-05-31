package com.treblemaker.model.sentiment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SentimentAndUrls implements Serializable {

    private String trackUrlPath = "https://s3-us-west-2.amazonaws.com/signalsandsocery/";
    private String trackExtention = ".mp3";
    private List<String> trackUrls = new ArrayList<>();
    private List<SentimentSectionDto> sentimentSectionDtos = new ArrayList<>();

    public SentimentAndUrls() {
    }

    public SentimentAndUrls(SentimentSections sentimentSections) {

        for(SentimentSection sentimentSection : sentimentSections.getSentimentSectionList()){
            sentimentSectionDtos.add(new SentimentSectionDto(sentimentSection));
        }

        for(SentimentSectionDto sentimentSectionDto : sentimentSectionDtos){
            for(TrackToLabels trackToLabels : sentimentSectionDto.getTrackToLabelsList()){
                if(!trackUrls.contains(trackToLabels.getName())){
                    trackUrls.add(trackToLabels.getName());
                }
            }
        }
    }

    public List<String> getTrackUrls() {
        return trackUrls;
    }

    public void setTrackUrls(List<String> trackUrls) {
        this.trackUrls = trackUrls;
    }

    public List<SentimentSectionDto> getSentimentSectionDtos() {
        return sentimentSectionDtos;
    }

    public void setSentimentSectionDtos(List<SentimentSectionDto> sentimentSectionDtos) {
        this.sentimentSectionDtos = sentimentSectionDtos;
    }
}
