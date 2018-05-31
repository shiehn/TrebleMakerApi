package com.treblemaker.model.parametriceq.eqprediction;

import java.util.HashMap;
import java.util.Map;

public class PredictEqBeatLoop {

    private Map<Integer, PredictEqBandBeatLoop> eqBandsBeatLoopMap = new HashMap<>();

    public Map<Integer, PredictEqBandBeatLoop> getEqBandsBeatLoopMap() {
        return eqBandsBeatLoopMap;
    }
}
