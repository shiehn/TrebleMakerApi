package com.treblemaker.model.parametriceq.eqprediction;

import com.treblemaker.model.IParametricEq;

import java.util.ArrayList;
import java.util.List;

public class ParametricDto {

    List<ParametricDtoBand> eqBands = new ArrayList<>();

    public List<ParametricDtoBand> getEqBands() {
        return eqBands;
    }

    public void setEqBands(List<ParametricDtoBand> eqBands) {
        this.eqBands = eqBands;
    }

    public void addEqBand(ParametricDtoBand eqBand) {
        this.eqBands.add(eqBand);
    }

    public ParametricDtoBand getBandByEq(IParametricEq.EqBand eqBand) {

        for (ParametricDtoBand parametricDtoBand : eqBands) {
            if (parametricDtoBand.getFreq().equals(eqBand)) {
                return parametricDtoBand;
            }
        }

        throw new RuntimeException("could not find eqBand");
    }
}
