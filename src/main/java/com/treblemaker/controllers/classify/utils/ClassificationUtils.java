package com.treblemaker.controllers.classify.utils;

import org.nd4j.linalg.api.ndarray.INDArray;

public class ClassificationUtils {

    public static int extractRating(INDArray ratingWeights){

        int rating = 0;

        if(ratingWeights.getFloat(0) > ratingWeights.getFloat(1) && ratingWeights.getFloat(0) > ratingWeights.getFloat(2)){
            rating = 0;
        }

        if(ratingWeights.getFloat(1) > ratingWeights.getFloat(0) && ratingWeights.getFloat(1) > ratingWeights.getFloat(2)){
            rating = 1;
        }

        if(ratingWeights.getFloat(2) > ratingWeights.getFloat(0) && ratingWeights.getFloat(2) > ratingWeights.getFloat(1)){
            rating = 2;
        }

        return rating;
    }
}
