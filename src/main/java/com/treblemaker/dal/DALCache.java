package com.treblemaker.dal;

import com.treblemaker.model.BeatLoop;
import com.treblemaker.model.HarmonicLoop;
import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.arpeggio.Arpeggio;
import com.treblemaker.model.bassline.Bassline;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.composition.CompositionTimeSlotLevels;
import com.treblemaker.model.fx.FXArpeggioDelay;
import com.treblemaker.model.fx.FXArpeggioDelayRating;

import java.util.List;

public class DALCache {

    private static class SingletonContainer {
        static final DALCache INSTANCE = new DALCache();
    }

    public static DALCache getInstance() {
        return SingletonContainer.INSTANCE;
    }

    List<AnalyticsVertical> analyticsVertical;
    List<BeatLoop> beatLoops;
    List<HarmonicLoop> harmonicLoops;
    List<Composition> compositions;
    List<Bassline> basslines;
    List<Arpeggio> arpeggios;
    List<CompositionTimeSlot> compositionTimeSlots;
    List<CompositionTimeSlotLevels> compositionTimeSlotLevels;
    List<FXArpeggioDelay> fxArpeggioDelays;
    List<FXArpeggioDelayRating> fxArpeggioDelayRatings;

    public List<AnalyticsVertical> getAnalyticsVertical() {
        return analyticsVertical;
    }

    public void setAnalyticsVertical(List<AnalyticsVertical> analyticsVertical) {
        this.analyticsVertical = analyticsVertical;
    }

    public List<BeatLoop> getBeatLoops() {
        return beatLoops;
    }

    public void setBeatLoops(List<BeatLoop> beatLoops) {
        this.beatLoops = beatLoops;
    }

    public List<HarmonicLoop> getHarmonicLoops() {
        return harmonicLoops;
    }

    public void setHarmonicLoops(List<HarmonicLoop> harmonicLoops) {
        this.harmonicLoops = harmonicLoops;
    }

    public List<Composition> getCompositions() {
        return compositions;
    }

    public void setCompositions(List<Composition> compositions) {
        this.compositions = compositions;
    }

    public List<Bassline> getBasslines() {
        return basslines;
    }

    public void setBasslines(List<Bassline> basslines) {
        this.basslines = basslines;
    }

    public List<Arpeggio> getArpeggios() {
        return arpeggios;
    }

    public void setArpeggios(List<Arpeggio> arpeggios) {
        this.arpeggios = arpeggios;
    }

    public List<CompositionTimeSlot> getCompositionTimeSlots() {
        return compositionTimeSlots;
    }

    public void setCompositionTimeSlots(List<CompositionTimeSlot> compositionTimeSlots) {
        this.compositionTimeSlots = compositionTimeSlots;
    }

    public List<CompositionTimeSlotLevels> getCompositionTimeSlotLevels() {
        return compositionTimeSlotLevels;
    }

    public void setCompositionTimeSlotLevels(List<CompositionTimeSlotLevels> compositionTimeSlotLevels) {
        this.compositionTimeSlotLevels = compositionTimeSlotLevels;
    }

    public List<FXArpeggioDelay> getFxArpeggioDelays() {
        return fxArpeggioDelays;
    }

    public void setFxArpeggioDelays(List<FXArpeggioDelay> fxArpeggioDelays) {
        this.fxArpeggioDelays = fxArpeggioDelays;
    }

    public List<FXArpeggioDelayRating> getFxArpeggioDelayRatings() {
        return fxArpeggioDelayRatings;
    }

    public void setFxArpeggioDelayRatings(List<FXArpeggioDelayRating> fxArpeggioDelayRatings) {
        this.fxArpeggioDelayRatings = fxArpeggioDelayRatings;
    }
}
