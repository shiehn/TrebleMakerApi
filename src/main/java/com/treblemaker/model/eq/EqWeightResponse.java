package com.treblemaker.model.eq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.treblemaker.model.IParametricEq;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EqWeightResponse {

    List<Ratings> ratings = new ArrayList<>();

    public EqWeightResponse() {
    }

    public List<Ratings> getRatings() {
        return ratings;
    }

    public void setRatings(List<Ratings> ratings) {
        this.ratings = ratings;
    }

    public void addRating(int rating, IParametricEq.EqBand freq) {

        if (ratings == null) {
            ratings = new ArrayList<>();
        }

        Ratings rate = new Ratings();
        rate.setRating(rating);
        rate.setFreq(freq);

        ratings.add(rate);
    }
}