package com.treblemaker.model.eq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.treblemaker.model.IParametricEq.EqBand;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ratings {

    public static final int BAD = 0;
    public static final int OK = 1;
    public static final int GOOD = 2;

    Integer rating;
    EqBand freq;

    public Ratings(){}

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public EqBand getFreq() {
        return freq;
    }

    public void setFreq(EqBand freq) {
        this.freq = freq;
    }
}