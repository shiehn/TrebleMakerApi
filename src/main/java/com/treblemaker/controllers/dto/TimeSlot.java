package com.treblemaker.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Transient;

public class TimeSlot
{
    public TimeSlot(){}

    @JsonProperty("Id")
    public int Id;

    @JsonProperty("StartTime")
    public String StartTime;

    @JsonProperty("Rating")
    public int Rating;

    @JsonProperty("EqRating")
    public int EqRating;

    @JsonProperty("LevelsRating")
    public int LevelsRating;

    @JsonProperty("PanningRating")
    public int PanningRating;

    @JsonProperty("ArpeggioRating")
    public int ArpeggioRating;

    @JsonProperty("CompRating")
    public int CompRating;

    @JsonProperty("BasslineRating")
    public int BasslineRating;

    @JsonProperty("KickRating")
    public int KickRating;

    @JsonProperty("HatRating")
    public int HatRating;

    @JsonProperty("SnareRating")
    public int SnareRating;

    @JsonProperty("HorizontalStart")
    public boolean HorizontalStart;

    @JsonProperty("HorizontalStop")
    public Boolean HorizontalStop;

    @JsonProperty("HorizontalCategoryHarmonic")
    public Boolean HorizontalCategoryHarmonic;

    @JsonProperty("HorizontalCategoryRhythmic")
    public Boolean HorizontalCategoryRhythmic;

    @JsonProperty("HorizontalCategoryAmbience")
    public Boolean HorizontalCategoryAmbience;

    @JsonProperty("HorizontalCategoryFill")
    public Boolean HorizontalCategoryFill;

    @JsonProperty("HorizontalRating")
    public int HorizontalRating;

    @JsonProperty("HorizontalId")
    public String HorizontalId;

    @JsonProperty("InGroup")
    public Boolean InGroup;

    @Transient
    public int selectedMelody;

    public int getSelectedMelody() {
        return selectedMelody;
    }

    public void setSelectedMelody(int selectedMelody) {
        this.selectedMelody = selectedMelody;
    }

    @JsonProperty("InGroup")
    public Boolean isInGroup() {
        return InGroup;
    }

    @JsonProperty("InGroup")
    public void setInGroup(Boolean inGroup) {
        InGroup = inGroup;
    }

    @JsonProperty("Id")
    public int getId() {
        return Id;
    }

    @JsonProperty("Id")
    public void setId(int id) {
        Id = id;
    }

    @JsonProperty("StartTime")
    public String getStartTime() {
        return StartTime;
    }

    @JsonProperty("StartTime")
    public void setStartTime(String startTime) {
        StartTime = startTime;
    }

    @JsonProperty("Rating")
    public int getRating() {
        return Rating;
    }

    @JsonProperty("Rating")
    public void setRating(int rating) {
        Rating = rating;
    }

    @JsonProperty("EqRating")
    public int getEqRating() {
        return EqRating;
    }

    @JsonProperty("EqRating")
    public void setEqRating(int eqRating) {
        EqRating = eqRating;
    }

    @JsonProperty("LevelsRating")
    public int getLevelsRating() {
        return LevelsRating;
    }

    @JsonProperty("LevelsRating")
    public void setLevelsRating(int levelsRating) {
        LevelsRating = levelsRating;
    }

    @JsonProperty("PanningRating")
    public int getPanningRating() {
        return PanningRating;
    }

    @JsonProperty("PanningRating")
    public void setPanningRating(int panningRating) {
        PanningRating = panningRating;
    }

    @JsonProperty("ArpeggioRating")
    public int getArpeggioRating() {
        return ArpeggioRating;
    }

    @JsonProperty("ArpeggioRating")
    public void setArpeggioRating(int arpeggioRating) {
        ArpeggioRating = arpeggioRating;
    }

    @JsonProperty("CompRating")
    public int getCompRating() {
        return CompRating;
    }

    @JsonProperty("CompRating")
    public void setCompRating(int compRating) {
        CompRating = compRating;
    }

    @JsonProperty("BasslineRating")
    public int getBasslineRating() {
        return BasslineRating;
    }

    @JsonProperty("BasslineRating")
    public void setBasslineRating(int basslineRating) {
        BasslineRating = basslineRating;
    }

    @JsonProperty("KickRating")
    public int getKickRating() {
        return KickRating;
    }

    @JsonProperty("KickRating")
    public void setKickRating(int kickRating) {
        KickRating = kickRating;
    }

    @JsonProperty("HatRating")
    public int getHatRating() {
        return HatRating;
    }

    @JsonProperty("HatRating")
    public void setHatRating(int hatRating) {
        HatRating = hatRating;
    }

    @JsonProperty("SnareRating")
    public int getSnareRating() {
        return SnareRating;
    }

    @JsonProperty("SnareRating")
    public void setSnareRating(int snareRating) {
        SnareRating = snareRating;
    }

    @JsonProperty("HorizontalStart")
    public Boolean isHorizontalStart() {
        return HorizontalStart;
    }

    @JsonProperty("HorizontalStart")
    public void setHorizontalStart(Boolean horizontalStart) {
        HorizontalStart = horizontalStart;
    }

    @JsonProperty("HorizontalStop")
    public Boolean isHorizontalStop() {
        return HorizontalStop;
    }

    @JsonProperty("HorizontalStop")
    public void setHorizontalStop(Boolean horizontalStop) {
        HorizontalStop = horizontalStop;
    }

    @JsonProperty("HorizontalRating")
    public int getHorizontalRating() {
        return HorizontalRating;
    }

    @JsonProperty("HorizontalRating")
    public void setHorizontalRating(int horizontalRating) {
        HorizontalRating = horizontalRating;
    }

    @JsonProperty("HorizontalId")
    public String getHorizontalId() {
        return HorizontalId;
    }

    @JsonProperty("HorizontalId")
    public void setHorizontalId(String horizontalId) {
        HorizontalId = horizontalId;
    }
}
