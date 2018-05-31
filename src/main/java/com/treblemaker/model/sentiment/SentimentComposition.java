package com.treblemaker.model.sentiment;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.persistence.*;

import static com.treblemaker.utils.json.JsonUtils.*;

@Entity
@Table(name = "sentiment_composition")
public class SentimentComposition {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(name = "composition_id")
    private String compositionId;

    @Column(name = "sentiment_json")
    private String sentimentJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(String compositionId) {
        this.compositionId = compositionId;
    }

    @Transient
    public SentimentSections getSentimentSections() {

        SentimentSections sentimentSections = null;

        try {
            sentimentSections = mapper.readValue(this.sentimentJson, SentimentSections.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sentimentSections;
    }

    @Transient
    public void setSentimentJson(SentimentSections sentimentSections) throws JsonProcessingException {
            this.sentimentJson = mapper.writeValueAsString(sentimentSections);
    }
}
