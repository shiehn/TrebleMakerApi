package com.treblemaker.services;

import com.treblemaker.SpringConfiguration;
import com.treblemaker.model.parametriceq.ParametricEqWithType;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDtoBand;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class EqFormatHelperTest {

    @Test
    public void should_havePhaseOnePredictors(){

        //predict beat loop

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_ONE;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(2, band.getColumns().size());
    }

    @Test
    public void should_havePhaseTwoPredictors(){

        //predict synth hi

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_TWO;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(3, band.getColumns().size());
    }

    @Test
    public void should_havePhaseThreePredictors(){

        //predict synth low alt

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_THREE;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(4, band.getColumns().size());
    }

    @Test
    public void should_havePhaseFourPredictors(){

        //predict synth mid

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_FOUR;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(5, band.getColumns().size());
    }

    @Test
    public void should_havePhaseFivePredictors(){

        //predict synth mid alt

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_FIVE;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(5, band.getColumns().size());
    }

    @Test
    public void should_havePhaseSixPredictors(){

        //predict synth low

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_SIX;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(5, band.getColumns().size());
    }

    @Test
    public void should_havePhaseSevenPredictors(){

        //predict synth hight alt

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_SEVEN;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(5, band.getColumns().size());
    }

    @Test
    public void should_havePhaseEightPredictors(){

        //predict harmonic loop alt

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_EIGHT;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP_ALT));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(6, band.getColumns().size());
    }

    @Test
    public void should_havePhaseNinePredictors(){

        //predict beat loop alt

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_NINE;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP_ALT));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP_ALT));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(7, band.getColumns().size());
    }

    @Test
    public void should_havePhaseTenPredictors(){

        //predict ambience loop

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_TEN;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.AMBIENCE_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP_ALT));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP_ALT));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(8, band.getColumns().size());
    }

    @Test
    public void should_havePhaseElevenPredictors(){

        //predict fills

        ParametricEqWithType.EqPhase phase = ParametricEqWithType.EqPhase.PHASE_ELEVEN;
        ParametricDtoBand band = new ParametricDtoBand();

        band = EqFormatHelper.addPredictorColumns(band, phase);

        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.FILLS));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP_ALT));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP_ALT));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_HI));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_LOW));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.SYNTH_MID));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.BEAT_LOOP));
        Assert.assertTrue(band.getColumns().contains(EqPredictorColumns.HARMONIC_LOOP));

        Assert.assertEquals(8, band.getColumns().size());
    }
}
