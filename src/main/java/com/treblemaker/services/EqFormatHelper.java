package com.treblemaker.services;

import com.treblemaker.model.parametriceq.eqprediction.ParametricDtoBand;

import static com.treblemaker.model.parametriceq.ParametricEqWithType.*;

public class EqFormatHelper {

    public static ParametricDtoBand addPredictorColumns(ParametricDtoBand eqBandDto, EqPhase eqPhase){

        switch(eqPhase){
            case PHASE_ONE:
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_TWO:
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_THREE:
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_FOUR:
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_FIVE:
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_SIX:
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_SEVEN:
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_EIGHT:
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP_ALT);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_NINE:
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP_ALT);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP_ALT);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_TEN:
                eqBandDto.addColumn(EqPredictorColumns.AMBIENCE_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP_ALT);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP_ALT);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
            case PHASE_ELEVEN:
                eqBandDto.addColumn(EqPredictorColumns.FILLS);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP_ALT);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP_ALT);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_HI);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_LOW);
                eqBandDto.addColumn(EqPredictorColumns.SYNTH_MID);
                eqBandDto.addColumn(EqPredictorColumns.BEAT_LOOP);
                eqBandDto.addColumn(EqPredictorColumns.HARMONIC_LOOP);
                break;
        }

        return eqBandDto;
    }
}
