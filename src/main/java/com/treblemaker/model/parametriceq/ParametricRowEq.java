package com.treblemaker.model.parametriceq;

public class ParametricRowEq {

    public Integer rating;
    public Integer type;
    public int bandEq;

    public double harmonicLoop;

    public double harmonicLoopAlt;

    public double beatLoop;

    public double beatLoopAlt;

    public double synthHi;

    public double synthMid;

    public double synthLow;

    public double ambience;

    public double ambienceAlt;

    public double fills;

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public int getBandEq() {
        return bandEq;
    }

    public void setBandEq(int bandEq) {
        this.bandEq = bandEq;
    }

    public double getHarmonicLoop() {
        return harmonicLoop;
    }

    public void setHarmonicLoop(double harmonicLoop) {
        this.harmonicLoop = harmonicLoop;
    }

    public double getHarmonicLoopAlt() {
        return harmonicLoopAlt;
    }

    public void setHarmonicLoopAlt(double harmonicLoopAlt) {
        this.harmonicLoopAlt = harmonicLoopAlt;
    }

    public double getBeatLoop() {
        return beatLoop;
    }

    public void setBeatLoop(double beatLoop) {
        this.beatLoop = beatLoop;
    }

    public double getBeatLoopAlt() {
        return beatLoopAlt;
    }

    public void setBeatLoopAlt(double beatLoopAlt) {
        this.beatLoopAlt = beatLoopAlt;
    }

    public double getSynthHi() {
        return synthHi;
    }

    public void setSynthHi(double synthHi) {
        this.synthHi = synthHi;
    }

    public double getSynthMid() {
        return synthMid;
    }

    public void setSynthMid(double synthMid) {
        this.synthMid = synthMid;
    }

    public double getSynthLow() {
        return synthLow;
    }

    public void setSynthLow(double synthLow) {
        this.synthLow = synthLow;
    }

    public double getAmbience() {
        return ambience;
    }

    public void setAmbience(double ambience) {
        this.ambience = ambience;
    }

    public double getAmbienceAlt() {
        return ambienceAlt;
    }

    public void setAmbienceAlt(double ambienceAlt) {
        this.ambienceAlt = ambienceAlt;
    }

    public double getFills() {
        return fills;
    }

    public void setFills(double fills) {
        this.fills = fills;
    }

    /*
            2) harmonicloops
            3) beat loops

            4)  ***************** ?Synth template
                - at this point the
                    """--- queueState = setAlternatesEvent.set(queueState); ---""" should decide which is alternative 		based on the EQStatisitics !!!!!!!!!!!!!!!!!
            4A) synth_hi
            4B) synth_mid
            4C) synth_low

            5) harmonic loops alt
            6) beat loops alt
            7) ambience loops
            8) ambience loops alt
            9) fills
         */


}