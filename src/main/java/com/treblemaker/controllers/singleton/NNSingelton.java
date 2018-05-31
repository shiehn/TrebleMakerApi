package com.treblemaker.controllers.singleton;

import com.treblemaker.neuralnets.*;

public enum NNSingelton {

    INSTANCE;

    public NNArpeggio nnArpeggio;
    public NNBass nnBass;
    public NNBeatAlt nnBeatAlt;
    public NNEq nnEq;
    public NNHarmonicAlt nnHarmonicAlt;
    public NNSynthFx nnSynthFx;
    public NNVolume nnVolume;

    //CSVs
    public String arpgCsvString;
    public String bassCsvString;
    public String beatAltCsvString;
    public String harmAltCsvString;
    public String synthFxCsvString;


    public void deAllocatNeuralNets(String className){
//        if(!className.equalsIgnoreCase("ArpeggioClassifyController")) {
//            NNSingelton.INSTANCE.nnArpeggio = null;
//            NNSingelton.INSTANCE.arpgCsvString = null;
//        }
//
//        if(!className.equalsIgnoreCase("BassClassifyController")) {
//            NNSingelton.INSTANCE.nnBass = null;
//            NNSingelton.INSTANCE.bassCsvString = null;
//        }
//
//        if(!className.equalsIgnoreCase("BeatAltClassifyController")) {
//            NNSingelton.INSTANCE.nnBeatAlt = null;
//            NNSingelton.INSTANCE.beatAltCsvString = null;
//        }
//
//        if(!className.equalsIgnoreCase("EqClassifyController")) {
//            NNSingelton.INSTANCE.nnEq = null;
//        }
//
//        if(!className.equalsIgnoreCase("HarmonicAltController")) {
//            NNSingelton.INSTANCE.nnHarmonicAlt = null;
//            NNSingelton.INSTANCE.harmAltCsvString = null;
//        }
//
//        if(!className.equalsIgnoreCase("SynthFxClassifyController")) {
//            NNSingelton.INSTANCE.nnSynthFx = null;
//            NNSingelton.INSTANCE.synthFxCsvString = null;
//        }
//
//        if(!className.equalsIgnoreCase("VolumeClassifyController")) {
//            NNSingelton.INSTANCE.nnVolume = null;
//        }
    }
}
