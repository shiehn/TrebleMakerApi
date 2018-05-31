package com.treblemaker.model.sentiment;

import java.util.List;

public class SentimentCompositionDto {
    private Integer id;
    private String compositionId;
    private List<SentimentSectionDto> sentimentSections;

    public SentimentCompositionDto(SentimentComposition sentimentComposition) {
        this.id = sentimentComposition.getId();
        this.compositionId = sentimentComposition.getCompositionId();

        for(SentimentSection sentimentSection : sentimentComposition.getSentimentSections().getSentimentSectionList()){
            this.sentimentSections.add(new SentimentSectionDto(sentimentSection));
        }
    }
}
