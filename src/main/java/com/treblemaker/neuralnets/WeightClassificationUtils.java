package com.treblemaker.neuralnets;

import com.treblemaker.model.eq.EqWeightResponse;
import com.treblemaker.model.eq.Ratings;
import com.treblemaker.weighters.enums.WeightClass;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static com.treblemaker.model.IParametricEq.EqBand;

@Component
public class WeightClassificationUtils {

    private static List<EqBand> lowEqRange = Arrays.asList(EqBand.FREQ_20, EqBand.FREQ_25, EqBand.FREQ_31, EqBand.FREQ_40, EqBand.FREQ_50, EqBand.FREQ_63, EqBand.FREQ_80, EqBand.FREQ_100, EqBand.FREQ_125, EqBand.FREQ_160, EqBand.FREQ_200);
    private static List<EqBand> midEqRange = Arrays.asList(EqBand.FREQ_250, EqBand.FREQ_315, EqBand.FREQ_400, EqBand.FREQ_500, EqBand.FREQ_630, EqBand.FREQ_800, EqBand.FREQ_1000, EqBand.FREQ_1250, EqBand.FREQ_1600, EqBand.FREQ_2000);
    private static List<EqBand> hiEqRange = Arrays.asList(EqBand.FREQ_2500, EqBand.FREQ_3150, EqBand.FREQ_4000, EqBand.FREQ_5000, EqBand.FREQ_6300, EqBand.FREQ_8000, EqBand.FREQ_10000, EqBand.FREQ_12500, EqBand.FREQ_16000, EqBand.FREQ_20000);

//    public static WeightClass verticalWeightToClass(String verticalClass) {
//
//        if (verticalClass.contains("2")) {
//            return WeightClass.GOOD;
//        } else if (verticalClass.contains("1")) {
//            return WeightClass.OK;
//        } else if (verticalClass.contains("0")) {
//            return WeightClass.BAD;
//        }
//
//        throw new RuntimeException("CANNOT CAST verticalClass string to ENUM");
//    }



    public static WeightClass eqResponseToWeightClass(EqWeightResponse eqWeightResponse) {

        int badCount = 0;
        int okCount = 0;
        int goodCount = 0;

        WeightClass hiClass = weightHiEqRegister(eqWeightResponse);
        WeightClass midClass = weightMidEqRegister(eqWeightResponse);
        WeightClass lowClass = weightLowEqRegister(eqWeightResponse);

        if (hiClass.equals(WeightClass.BAD)) {
            badCount++;
        }

        if (midClass.equals(WeightClass.BAD)) {
            badCount++;
        }

        if (lowClass.equals(WeightClass.BAD)) {
            badCount++;
        }

        // /////////////////

        if (hiClass.equals(WeightClass.OK)) {
            okCount++;
        }

        if (midClass.equals(WeightClass.OK)) {
            okCount++;
        }

        if (lowClass.equals(WeightClass.OK)) {
            okCount++;
        }

        // /////////////////

        if (hiClass.equals(WeightClass.GOOD)) {
            goodCount++;
        }

        if (midClass.equals(WeightClass.GOOD)) {
            goodCount++;
        }

        if (lowClass.equals(WeightClass.GOOD)) {
            goodCount++;
        }

        if (okCount >= goodCount && okCount >= badCount) {
            return WeightClass.OK;
        }

        if (badCount >= goodCount && badCount >= okCount) {
            return WeightClass.BAD;
        }

        return WeightClass.GOOD;
    }

    public static WeightClass weightLowEqRegister(EqWeightResponse eqWeightResponse) {

        int badCount = 0;
        int okCount = 0;
        int goodCount = 0;

        for (Ratings rating : eqWeightResponse.getRatings()) {
            if (isLowEqRegister(rating)) {
                if(rating.getRating().equals(0)){
                    badCount++;
                }

                if(rating.getRating().equals(1)){
                    okCount++;
                }

                if(rating.getRating().equals(2)){
                    goodCount++;
                }
            }
        }

        if (badCount >= okCount && badCount >= goodCount) {
            return WeightClass.BAD;
        }

        if (okCount >= goodCount && okCount >= badCount) {
            return WeightClass.OK;
        }

        return WeightClass.GOOD;
    }

    public static WeightClass weightMidEqRegister(EqWeightResponse eqWeightResponse) {

        int badCount = 0;
        int okCount = 0;
        int goodCount = 0;

        for (Ratings rating : eqWeightResponse.getRatings()) {
            if (isMidEqRegister(rating)) {
                if(rating.getRating().equals(0)){
                    badCount++;
                }

                if(rating.getRating().equals(1)){
                    okCount++;
                }

                if(rating.getRating().equals(2)){
                    goodCount++;
                }
            }
        }

        if (badCount >= okCount && badCount >= goodCount) {
            return WeightClass.BAD;
        }

        if (okCount >= goodCount && okCount >= badCount) {
            return WeightClass.OK;
        }

        return WeightClass.GOOD;
    }

    public static WeightClass weightHiEqRegister(EqWeightResponse eqWeightResponse) {

        int badCount = 0;
        int okCount = 0;
        int goodCount = 0;

        for (Ratings rating : eqWeightResponse.getRatings()) {
            if (isHiEqRegister(rating)) {
                if(rating.getRating().equals(0)){
                    badCount++;
                }

                if(rating.getRating().equals(1)){
                    okCount++;
                }

                if(rating.getRating().equals(2)){
                    goodCount++;
                }
            }
        }

        if (badCount >= okCount && badCount >= goodCount) {
            return WeightClass.BAD;
        }

        if (okCount >= goodCount && okCount >= badCount) {
            return WeightClass.OK;
        }

        return WeightClass.GOOD;
    }

    public static boolean isLowEqRegister(Ratings rating) {
        return lowEqRange.contains(rating.getFreq());
    }

    public static boolean isMidEqRegister(Ratings rating) {
        return midEqRange.contains(rating.getFreq());
    }

    public static boolean isHiEqRegister(Ratings rating) {
        return hiEqRange.contains(rating.getFreq());
    }


}