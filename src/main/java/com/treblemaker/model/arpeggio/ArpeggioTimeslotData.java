package com.treblemaker.model.arpeggio;

public class ArpeggioTimeslotData {

    private int timeSlotId;
    private int beatLoopId;
    private int[] beatLoopRhythm;
    private int harmonicLoopId;
    private int[] harmonicLoopRhythm;
    private int chordType;
    private int basslineId;
    private double basslineRating;
    private double[] basslineIntervals;
    private double[] basslineDurations;
    private int arpeggioId;
    private double arpeggioRating;
    private double[] arpeggioIntervals;

    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public int getBeatLoopId() {
        return beatLoopId;
    }

    public void setBeatLoopId(int beatLoopId) {
        this.beatLoopId = beatLoopId;
    }

    public int getHarmonicLoopId() {
        return harmonicLoopId;
    }

    public void setHarmonicLoopId(int harmonicLoopId) {
        this.harmonicLoopId = harmonicLoopId;
    }

    public int getChordType() {
        return chordType;
    }

    public void setChordType(int chordType) {
        this.chordType = chordType;
    }

    public double getBasslineRating() {
        return basslineRating;
    }

    public void setBasslineRating(double basslineRating) {
        this.basslineRating = basslineRating;
    }

    public int[] getBeatLoopRhythm() {
        return beatLoopRhythm;
    }

    public void setBeatLoopRhythm(int[] beatLoopRhythm) {
        this.beatLoopRhythm = beatLoopRhythm;
    }

    public int[] getHarmonicLoopRhythm() {
        return harmonicLoopRhythm;
    }

    public void setHarmonicLoopRhythm(int[] harmonicLoopRhythm) {
        this.harmonicLoopRhythm = harmonicLoopRhythm;
    }

    public int getBasslineId() {
        return basslineId;
    }

    public void setBasslineId(int basslineId) {
        this.basslineId = basslineId;
    }

    public double[] getBasslineIntervals() {
        return basslineIntervals;
    }

    public void setBasslineIntervals(double[] basslineIntervals) {
        this.basslineIntervals = basslineIntervals;
    }

    public double[] getBasslineDurations() {
        return basslineDurations;
    }

    public void setBasslineDurations(double[] basslineDurations) {
        this.basslineDurations = basslineDurations;
    }

    public int getArpeggioId() {
        return arpeggioId;
    }

    public void setArpeggioId(int arpeggioId) {
        this.arpeggioId = arpeggioId;
    }

    public double getArpeggioRating() {
        return arpeggioRating;
    }

    public void setArpeggioRating(double arpeggioRating) {
        this.arpeggioRating = arpeggioRating;
    }

    public double[] getArpeggioIntervals() {
        return arpeggioIntervals;
    }

    public void setArpeggioIntervals(double[] arpeggioIntervals) {
        this.arpeggioIntervals = arpeggioIntervals;
    }
}
