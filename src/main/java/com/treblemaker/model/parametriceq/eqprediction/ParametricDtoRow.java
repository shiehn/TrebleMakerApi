package com.treblemaker.model.parametriceq.eqprediction;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParametricDtoRow {

    /*
        public static final String HARMONIC_LOOP = "harm";
    public static final String HARMONIC_LOOP_ALT = "harm_alt";
    public static final String BEAT_LOOP = "beat";
    public static final String BEAT_LOOP_ALT = "beat_alt";
    public static final String SYNTH_HI = "synth_h";
    public static final String SYNTH_MID = "synth_m";
    public static final String SYNTH_LOW = "synth_l";
    public static final String AMBIENCE_LOOP = "ambi";
    public static final String AMBIENCE_LOOP_ALT = "ambi_alt";
    public static final String FILLS = "fill";
     */

    private String rating = null;
    private Double beat = null;
    private Double harm = null;
    private Double synth_m = null;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getBeat() {
        return beat;
    }

    public void setBeat(Double beat) {
        this.beat = beat;
    }

    public Double getHarm() {
        return harm;
    }

    public void setHarm(Double harm) {
        this.harm = harm;
    }

    public Double getSynth_m() {
        return synth_m;
    }

    public void setSynth_m(Double synth_m) {
        this.synth_m = synth_m;
    }
}
