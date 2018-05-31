package com.treblemaker.model.parametriceq;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.treblemaker.model.IParametricEq;

import javax.persistence.*;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "parametric_eq_harmonic_loops")
public class ParametricEqHarmonicLoop implements IParametricEq {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "harmonicloop_id")
    private int harmonicLoopId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHarmonicLoopId() {
        return harmonicLoopId;
    }

    public void setHarmonicLoopId(int harmonicLoopId) {
        this.harmonicLoopId = harmonicLoopId;
    }

    private double freq20;
    private double freq25;
    private double freq31;
    private double freq40;
    private double freq50;
    private double freq63;
    private double freq80;
    private double freq100;
    private double freq125;
    private double freq160;
    private double freq200;
    private double freq250;
    private double freq315;
    private double freq400;
    private double freq500;
    private double freq630;
    private double freq800;
    private double freq1000;
    private double freq1250;
    private double freq1600;
    private double freq2000;
    private double freq2500;
    private double freq3150;
    private double freq4000;
    private double freq5000;
    private double freq6300;
    private double freq8000;
    private double freq10000;
    private double freq12500;
    private double freq16000;
    private double freq20000;

    public void setFreq20(double freq20) { this.freq20 = freq20; }

    public void setFreq25(double freq25) { this.freq25 = freq25;}

    public void setFreq31(double freq31) {
        this.freq31 = freq31;
    }

    public void setFreq40(double freq40) {
        this.freq40 = freq40;
    }

    public void setFreq50(double freq50) {
        this.freq50 = freq50;
    }

    public void setFreq63(double freq63) {
        this.freq63 = freq63;
    }

    public void setFreq80(double freq80) {
        this.freq80 = freq80;
    }

    public void setFreq100(double freq100) {
        this.freq100 = freq100;
    }

    public void setFreq125(double freq125) {
        this.freq125 = freq125;
    }

    public void setFreq160(double freq160) {
        this.freq160 = freq160;
    }

    public void setFreq200(double freq200) {
        this.freq200 = freq200;
    }

    public void setFreq250(double freq250) {
        this.freq250 = freq250;
    }

    public void setFreq315(double freq315) {
        this.freq315 = freq315;
    }

    public void setFreq400(double freq400) {
        this.freq400 = freq400;
    }

    public void setFreq500(double freq500) {
        this.freq500 = freq500;
    }

    public void setFreq630(double freq630) {
        this.freq630 = freq630;
    }

    public void setFreq800(double freq800) {
        this.freq800 = freq800;
    }

    public void setFreq1000(double freq1000) {
        this.freq1000 = freq1000;
    }

    public void setFreq1250(double freq1250) {
        this.freq1250 = freq1250;
    }

    public void setFreq1600(double freq1600) {
        this.freq1600 = freq1600;
    }

    public void setFreq2000(double freq2000) {
        this.freq2000 = freq2000;
    }

    public void setFreq2500(double freq2500) {
        this.freq2500 = freq2500;
    }

    public void setFreq3150(double freq3150) {
        this.freq3150 = freq3150;
    }

    public void setFreq4000(double freq4000) {
        this.freq4000 = freq4000;
    }

    public void setFreq5000(double freq5000) {
        this.freq5000 = freq5000;
    }

    public void setFreq6300(double freq6300) {
        this.freq6300 = freq6300;
    }

    public void setFreq8000(double freq8000) {
        this.freq8000 = freq8000;
    }

    public void setFreq10000(double freq10000) {
        this.freq10000 = freq10000;
    }

    public void setFreq12500(double freq12500) {
        this.freq12500 = freq12500;
    }

    public void setFreq16000(double freq16000) {
        this.freq16000 = freq16000;
    }

    public void setFreq20000(double freq20000) {
        this.freq20000 = freq20000;
    }

    @Transient
    public double[] getAllBands() {
        double[] allBands = {
                freq20,
                freq25,
                freq31,
                freq40,
                freq50,
                freq63,
                freq80,
                freq100,
                freq125,
                freq160,
                freq200,
                freq250,
                freq315,
                freq400,
                freq500,
                freq630,
                freq800,
                freq1000,
                freq1250,
                freq1600,
                freq2000,
                freq2500,
                freq3150,
                freq4000,
                freq5000,
                freq6300,
                freq8000,
                freq10000,
                freq12500,
                freq16000,
                freq20000
        };

        return allBands;
    }

    @Override
    public double getByFreq(EqBand eqBand) {

        switch (eqBand) {
            case FREQ_20:
                return freq20;
            case FREQ_25:
                return freq25;
            case FREQ_31:
                return freq31;
            case FREQ_40:
                return freq40;
            case FREQ_50:
                return freq50;
            case FREQ_63:
                return freq63;
            case FREQ_80:
                return freq80;
            case FREQ_100:
                return freq100;
            case FREQ_125:
                return freq125;
            case FREQ_160:
                return freq160;
            case FREQ_200:
                return freq200;
            case FREQ_250:
                return freq250;
            case FREQ_315:
                return freq315;
            case FREQ_400:
                return freq400;
            case FREQ_500:
                return freq500;
            case FREQ_630:
                return freq630;
            case FREQ_800:
                return freq800;
            case FREQ_1000:
                return freq1000;
            case FREQ_1250:
                return freq1250;
            case FREQ_1600:
                return freq1600;
            case FREQ_2000:
                return freq2000;
            case FREQ_2500:
                return freq2500;
            case FREQ_3150:
                return freq3150;
            case FREQ_4000:
                return freq4000;
            case FREQ_5000:
                return freq5000;
            case FREQ_6300:
                return freq6300;
            case FREQ_8000:
                return freq8000;
            case FREQ_10000:
                return freq10000;
            case FREQ_12500:
                return freq12500;
            case FREQ_16000:
                return freq16000;
            case FREQ_20000:
                return freq20000;
        }

        throw new RuntimeException("UNSUPPORTED FREQUENCY");
    }
}
