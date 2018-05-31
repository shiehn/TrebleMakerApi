package com.treblemaker.model.composition;

public class CompositionTimeSlotLevelsWithRating extends CompositionTimeSlotLevels {

    public CompositionTimeSlotLevelsWithRating(){}

    public CompositionTimeSlotLevelsWithRating(CompositionTimeSlotLevels compositionTimeSlotLevels){

        this.setId(compositionTimeSlotLevels.getId());
        this.setCompositionId(compositionTimeSlotLevels.getCompositionId());
        this.setCompHi(compositionTimeSlotLevels.getCompHi());
        this.setCompHiAlt(compositionTimeSlotLevels.getCompHiAlt());
        this.setCompMid(compositionTimeSlotLevels.getCompMid());
        this.setCompMidAlt(compositionTimeSlotLevels.getCompMidAlt());
        this.setCompLow(compositionTimeSlotLevels.getCompLow());
        this.setCompLowAlt(compositionTimeSlotLevels.getCompLowAlt());
        this.setBeat(compositionTimeSlotLevels.getBeat());
        this.setBeatAlt(compositionTimeSlotLevels.getBeatAlt());
        this.setHarmonic(compositionTimeSlotLevels.getHarmonic());
        this.setHarmonicAlt(compositionTimeSlotLevels.getHarmonicAlt());
        this.setAmbient(compositionTimeSlotLevels.getAmbient());
        this.setFills(compositionTimeSlotLevels.getFills());
        this.setHits(compositionTimeSlotLevels.getHits());
        this.setLevelBeforeMixed(compositionTimeSlotLevels.getLevelBeforeMixed());
    }

    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
