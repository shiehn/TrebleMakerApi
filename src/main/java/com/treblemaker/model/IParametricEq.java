package com.treblemaker.model;

public interface IParametricEq {

    enum EqBand {
        FREQ_20,
        FREQ_25,
        FREQ_31,
        FREQ_40,
        FREQ_50,
        FREQ_63,
        FREQ_80,
        FREQ_100,
        FREQ_125,
        FREQ_160,
        FREQ_200,
        FREQ_250,
        FREQ_315,
        FREQ_400,
        FREQ_500,
        FREQ_630,
        FREQ_800,
        FREQ_1000,
        FREQ_1250,
        FREQ_1600,
        FREQ_2000,
        FREQ_2500,
        FREQ_3150,
        FREQ_4000,
        FREQ_5000,
        FREQ_6300,
        FREQ_8000,
        FREQ_10000,
        FREQ_12500,
        FREQ_16000,
        FREQ_20000
    }

    void setFreq20(double freq20);

    void setFreq25(double freq25);

    void setFreq31(double freq31);

    void setFreq40(double freq40);

    void setFreq50(double freq50);

    void setFreq63(double freq63);

    void setFreq80(double freq80);

    void setFreq100(double freq100);

    void setFreq125(double freq125);

    void setFreq160(double freq160);

    void setFreq200(double freq200);

    void setFreq250(double freq250);

    void setFreq315(double freq315);

    void setFreq400(double freq400);

    void setFreq500(double freq500);

    void setFreq630(double freq630);

    void setFreq800(double freq800);

    void setFreq1000(double freq1000);

    void setFreq1250(double freq1250);

    void setFreq1600(double freq1600);

    void setFreq2000(double freq2000);

    void setFreq2500(double freq2500);

    void setFreq3150(double freq3150);

    void setFreq4000(double freq4000);

    void setFreq5000(double freq5000);

    void setFreq6300(double freq6300);

    void setFreq8000(double freq8000);

    void setFreq10000(double freq10000);

    void setFreq12500(double freq12500);

    void setFreq16000(double freq16000);

    void setFreq20000(double freq20000);

    double[] getAllBands();

    double getByFreq(EqBand eqBand);
}
