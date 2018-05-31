package com.treblemaker.model.parametriceq.eqprediction;

public class PredictEqBandBeatLoop implements IFreqBand {

    private int freqBand;
    private double beatLoopEq;
    private double harmonicLoopEq;

    @Override
    public int getFreqBand() {
        return freqBand;
    }

    @Override
    public void setFreqBand(int freqBand) {
        this.freqBand = freqBand;
    }

    public double getBeatLoopEq() {
        return beatLoopEq;
    }

    public void setBeatLoopEq(double beatLoopEq) {
        this.beatLoopEq = beatLoopEq;
    }

    public double getHarmonicLoopEq() {
        return harmonicLoopEq;
    }

    public void setHarmonicLoopEq(double harmonicLoopEq) {
        this.harmonicLoopEq = harmonicLoopEq;
    }
}




/*
[{
  "freqBand":90,
  "beatLoopEq": 43.45345,
  "harmonicLoopEq": 35.234
},{
  "freqBand":90,
  "beatLoopEq": 2.45345,
  "harmonicLoopEq": 35.234
},{
  "freqBand":90,
  "beatLoopEq": 45.45345,
  "harmonicLoopEq": 35.234
},{
  "freqBand":90,
  "beatLoopEq": 77.45345,
  "harmonicLoopEq": 35.234
}]
*/