package com.treblemaker.tests;

import com.treblemaker.SpringConfiguration;
import com.treblemaker.controllers.interfaces.IParametricEqBeatLoopDal;
import com.treblemaker.controllers.interfaces.IParametricEqCompositionLayerDal;
import com.treblemaker.controllers.interfaces.IParametricEqHarmonicLoopDal;
import com.treblemaker.dal.interfaces.IAnalyticsVerticalDal;
import com.treblemaker.dal.interfaces.ICompositionDal;
import com.treblemaker.model.EqClassificationRequest;
import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.parametriceq.ParametricEqBeatLoop;
import com.treblemaker.model.parametriceq.ParametricEqCompositionLayer;
import com.treblemaker.model.parametriceq.ParametricEqHarmonicLoop;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDto;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDtoBand;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDtoRow;
import com.treblemaker.services.EqFormatter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.treblemaker.model.IParametricEq.EqBand;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

//package com.treblemaker.tests;
//
//import com.treblemaker.SpringConfiguration;
//import com.treblemaker.controllers.interfaces.IParametricEqBeatLoopDal;
//import com.treblemaker.controllers.interfaces.IParametricEqCompositionLayerDal;
//import com.treblemaker.controllers.interfaces.IParametricEqHarmonicLoopDal;
//import com.treblemaker.model.parametriceq.ParametricEqBeatLoop;
//import com.treblemaker.model.parametriceq.ParametricEqCompositionLayer;
//import com.treblemaker.model.parametriceq.ParametricEqHarmonicLoop;
//import com.treblemaker.model.parametriceq.ParametricEqWithType;
//import com.treblemaker.services.EqFormatter;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringConfiguration.class)
public class EqFormatterTests {

    private EqFormatter eqFormatter;

    @Before
    public void setUp() {

        IParametricEqBeatLoopDal mockEqBeatLoopDal = mock(IParametricEqBeatLoopDal.class);
        IParametricEqHarmonicLoopDal mockEqHarmonicLoopDal = mock(IParametricEqHarmonicLoopDal.class);
        IParametricEqCompositionLayerDal mockEqCompositionLayerDal = mock(IParametricEqCompositionLayerDal.class);
        ICompositionDal mockCompositionDal = mock(ICompositionDal.class);
        IAnalyticsVerticalDal mockIAnalyticsVerticalDal = mock(IAnalyticsVerticalDal.class);

        //BEAT LOOPS
        //BEAT LOOPS
        ParametricEqBeatLoop bla = new ParametricEqBeatLoop();
        bla.setBeatLoopId(11);
        bla.setFreq20(10.1);
        bla.setFreq125(11.1);

        ParametricEqBeatLoop blb = new ParametricEqBeatLoop();
        blb.setBeatLoopId(12);
        blb.setFreq63(55);

        ParametricEqBeatLoop blc = new ParametricEqBeatLoop();
        blc.setBeatLoopId(13);

        List<ParametricEqBeatLoop> mockBeatLoops = new ArrayList<>();
        mockBeatLoops.add(bla);
        mockBeatLoops.add(blb);
        mockBeatLoops.add(blc);
        when(mockEqBeatLoopDal.findAll()).thenReturn(mockBeatLoops);

        //HARMONIC LOOPS
        //HARMONIC LOOPS
        ParametricEqHarmonicLoop hla = new ParametricEqHarmonicLoop();
        hla.setHarmonicLoopId(21);
        hla.setFreq20(20.20);
        hla.setFreq125(22.2);

        ParametricEqHarmonicLoop hlb = new ParametricEqHarmonicLoop();
        hlb.setHarmonicLoopId(22);

        ParametricEqHarmonicLoop hlc = new ParametricEqHarmonicLoop();
        hlc.setHarmonicLoopId(23);
        hlc.setFreq63(66);

        List<ParametricEqHarmonicLoop> mockHarmonicLoops = new ArrayList<>();
        mockHarmonicLoops.add(hla);
        mockHarmonicLoops.add(hlb);
        mockHarmonicLoops.add(hlc);
        when(mockEqHarmonicLoopDal.findAll()).thenReturn(mockHarmonicLoops);


        //*******************************************************************
        //SynthHi
        ParametricEqCompositionLayer sha = new ParametricEqCompositionLayer();
        sha.setCompositionId(31);
        sha.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI);

        ParametricEqCompositionLayer shb = new ParametricEqCompositionLayer();
        shb.setCompositionId(32);
        shb.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI);

        ParametricEqCompositionLayer shc = new ParametricEqCompositionLayer();
        shc.setCompositionId(33);
        shc.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI);

        //SynthHiAlt
        ParametricEqCompositionLayer shaa = new ParametricEqCompositionLayer();
        shaa.setCompositionId(41);
        shaa.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI_ALT);

        ParametricEqCompositionLayer shab = new ParametricEqCompositionLayer();
        shab.setCompositionId(42);
        shab.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI_ALT);

        ParametricEqCompositionLayer shac = new ParametricEqCompositionLayer();
        shac.setCompositionId(43);
        shac.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI_ALT);

        //SynthMid
        ParametricEqCompositionLayer sma = new ParametricEqCompositionLayer();
        sma.setCompositionId(51);
        sma.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID);

        ParametricEqCompositionLayer smb = new ParametricEqCompositionLayer();
        smb.setCompositionId(52);
        smb.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID);

        ParametricEqCompositionLayer smc = new ParametricEqCompositionLayer();
        smc.setCompositionId(53);
        smc.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID);

        //SynthMid_ALT
        ParametricEqCompositionLayer smaa = new ParametricEqCompositionLayer();
        smaa.setCompositionId(61);
        smaa.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT);

        ParametricEqCompositionLayer smab = new ParametricEqCompositionLayer();
        smab.setCompositionId(62);
        smab.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT);

        ParametricEqCompositionLayer smac = new ParametricEqCompositionLayer();
        smac.setCompositionId(63);
        smac.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT);

        //Synth_LOW
        ParametricEqCompositionLayer sla = new ParametricEqCompositionLayer();
        sla.setCompositionId(71);
        sla.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW);

        ParametricEqCompositionLayer slb = new ParametricEqCompositionLayer();
        slb.setCompositionId(72);
        slb.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW);

        ParametricEqCompositionLayer slc = new ParametricEqCompositionLayer();
        slc.setCompositionId(73);
        slc.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW);

        //Synth_LOW_ALT
        ParametricEqCompositionLayer slaa = new ParametricEqCompositionLayer();
        slaa.setCompositionId(81);
        slaa.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW_ALT);

        ParametricEqCompositionLayer slab = new ParametricEqCompositionLayer();
        slab.setCompositionId(82);
        slab.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW_ALT);

        ParametricEqCompositionLayer slac = new ParametricEqCompositionLayer();
        slac.setCompositionId(83);
        slac.setLayerType(ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW_ALT);


        List<ParametricEqCompositionLayer> mockCompositionLayers = new ArrayList<>();
        mockCompositionLayers.add(sha);
        mockCompositionLayers.add(shb);
        mockCompositionLayers.add(shc);
        mockCompositionLayers.add(shaa);
        mockCompositionLayers.add(shab);
        mockCompositionLayers.add(shac);
        mockCompositionLayers.add(sma);
        mockCompositionLayers.add(smb);
        mockCompositionLayers.add(smc);
        mockCompositionLayers.add(smaa);
        mockCompositionLayers.add(smab);
        mockCompositionLayers.add(smac);
        mockCompositionLayers.add(sla);
        mockCompositionLayers.add(slb);
        mockCompositionLayers.add(slc);
        mockCompositionLayers.add(slaa);
        mockCompositionLayers.add(slab);
        mockCompositionLayers.add(slac);
        when(mockEqCompositionLayerDal.findAll()).thenReturn(mockCompositionLayers);

        // MOCK COMPOSITION DAL
        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        CompositionTimeSlot timeSlot = new CompositionTimeSlot();
        timeSlot.setId(99);
        timeSlot.setBeatLoopId(12);
        timeSlot.setHarmonicLoopId(23);
        timeSlot.setAmbientLoopId(34);
        timeSlots.add(0,timeSlot);

        Composition composition = new Composition();
        composition.setCompositionTimeSlots(timeSlots);
        List<Composition> compositions = new ArrayList<>();
        compositions.add(composition);

        when(mockCompositionDal.findAll()).thenReturn(compositions);

        // MOCK VERTICAL DAL
        List<AnalyticsVertical> analyticsVerticals = new ArrayList<>();
        AnalyticsVertical analyticsVertical = new AnalyticsVertical();
        analyticsVertical.setTimeSlotId(99);
        analyticsVertical.setRating(2);
        analyticsVerticals.add(analyticsVertical);
        when(mockIAnalyticsVerticalDal.findAll()).thenReturn(analyticsVerticals);

        eqFormatter = new EqFormatter(mockEqBeatLoopDal, mockEqHarmonicLoopDal, mockEqCompositionLayerDal, mockCompositionDal, mockIAnalyticsVerticalDal);
    }

    @Test
    public void should_handle_unknownLoops() {

        try {
            EqClassificationRequest request = new EqClassificationRequest("one", 9999, 9999);
            ParametricDto parametricDto = eqFormatter.createPhaseOne(request);
            Assert.assertTrue(true);
        }catch (Exception e){
            Assert.fail("Did Not Handle Exception!!!");
        }
    }

    @Ignore
    @Test
    public void createPhaseOne() {

        EqClassificationRequest request = new EqClassificationRequest("one",21,11);

        ParametricDto parametricDto = eqFormatter.createPhaseOne(request);

        int numOfFreq = EqFormatter.eqBands.length;
        Assert.assertEquals(numOfFreq, parametricDto.getEqBands().size());

        for(EqBand band : EqFormatter.eqBands){

            ParametricDtoBand parametricDtoBand = parametricDto.getEqBands().stream().filter(b -> b.getFreq() == band).findFirst().get();

            Assert.assertNotNull(parametricDto);

            if(band == EqBand.FREQ_63){
                Optional<ParametricDtoRow> row = parametricDtoBand.getRows().stream().filter(r ->
                        r.getBeat() == 55.0
                                && r.getHarm() == 66.0
                                && r.getRating()
                                .equalsIgnoreCase("good")
                ).findFirst();

                Assert.assertTrue(row.isPresent());
                Assert.assertNotNull(row.get());
            }else {

                Optional<ParametricDtoRow> row = parametricDtoBand.getRows().stream().filter(r ->
                        r.getBeat() == 0.0
                                && r.getHarm() == 0.0
                                && r.getRating()
                                .equalsIgnoreCase("good")
                ).findFirst();

                Assert.assertTrue(row.isPresent());
                Assert.assertNotNull(row.get());
            }
        }

        for (ParametricDtoBand band : parametricDto.getEqBands()) {

            if(band.getFreq().equals(EqBand.FREQ_20)){
                double expected = 10.1;
                double actual = band.getTarget().getBeat();
                Assert.assertEquals(expected, actual, 0.0);

                expected = 20.2;
                actual = band.getTarget().getHarm();
                Assert.assertEquals(expected, actual, 0.0);

                System.out.println("ENTERED TEST 1");
            }
        }

        for (ParametricDtoBand band : parametricDto.getEqBands()) {

            if(band.getFreq().equals(EqBand.FREQ_125)){
                double expected = 11.1;
                double actual = band.getTarget().getBeat();
                Assert.assertEquals(expected, actual, 0.0);

                expected = 22.2;
                actual = band.getTarget().getHarm();
                Assert.assertEquals(expected, actual, 0.0);

                System.out.println("ENTERED TEST 2");
            }
        }

    }

}
//
//    @Test
//    public void createPhaseOne(){
//
//        //BEAT LOOP
//        //harm + beat
//
//        List<ParametricEqWithType> eqWithTypeList = eqFormatter.createPhaseOne();
//
//        int beatCount = 0;
//        int harmCount = 0;
//
//        for(ParametricEqWithType eqWithType : eqWithTypeList){
//            if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.beatLoopKey)){
//                beatCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.harmonicLoopKey)){
//                harmCount++;
//            }
//        }
//
//        Assert.assertEquals(eqWithTypeList.size(), 6);
//        Assert.assertEquals(beatCount, 3);
//        Assert.assertEquals(harmCount, 3);
//    }
//
//    @Test
//    public void createPhaseTwo(){
//
//        //synth_hi LOOP
//        //harm + beat + synthHiKey
//
//        List<ParametricEqWithType> eqWithTypeList = eqFormatter.createPhaseTwo();
//
//        int beatCount = 0;
//        int harmCount = 0;
//        int synthHiCount = 0;
//
//        for(ParametricEqWithType eqWithType : eqWithTypeList){
//            if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.beatLoopKey)){
//                beatCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.harmonicLoopKey)){
//                harmCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.synthHiKey)){
//                synthHiCount++;
//            }
//        }
//
//        Assert.assertEquals(eqWithTypeList.size(), 12);
//        Assert.assertEquals(beatCount, 3);
//        Assert.assertEquals(harmCount, 3);
//        Assert.assertEquals(synthHiCount, 6);
//    }
//
//    @Test
//    public void createPhaseThree(){
//
//        //synth_hi_ALT LOOP
//        //harm + beat + synthLow
//
//        List<ParametricEqWithType> eqWithTypeList = eqFormatter.createPhaseThree();
//
//        int beatCount = 0;
//        int harmCount = 0;
//        int synthHiCount = 0;
//        int synthLowCount = 0;
//
//        for(ParametricEqWithType eqWithType : eqWithTypeList){
//            if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.beatLoopKey)){
//                beatCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.harmonicLoopKey)){
//                harmCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.synthHiKey)){
//                synthHiCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.synthLowKey)){
//                synthLowCount++;
//            }
//        }
//
//        Assert.assertEquals(eqWithTypeList.size(), 18);
//        Assert.assertEquals(beatCount, 3);
//        Assert.assertEquals(harmCount, 3);
//        Assert.assertEquals(synthHiCount, 6);
//        Assert.assertEquals(synthLowCount, 6);
//    }
//
//    @Test
//    public void createPhaseFour(){
//
//        //synth_mid LOOP
//        //harm + beat + synthMidKey
//
//        List<ParametricEqWithType> eqWithTypeList = eqFormatter.createPhaseFour();
//
//        int beatCount = 0;
//        int harmCount = 0;
//        int synthHiCount = 0;
//        int synthLowCount = 0;
//
//        for(ParametricEqWithType eqWithType : eqWithTypeList){
//            if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.beatLoopKey)){
//                beatCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.harmonicLoopKey)){
//                harmCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.synthHiKey)){
//                synthHiCount++;
//            }else if(eqWithType.getType().equalsIgnoreCase(ParametricEqWithType.synthLowKey)){
//                synthLowCount++;
//            }
//        }
//
//        Assert.assertEquals(eqWithTypeList.size(), 18);
//        Assert.assertEquals(beatCount, 3);
//        Assert.assertEquals(harmCount, 3);
//        Assert.assertEquals(synthHiCount, 6);
//        Assert.assertEquals(synthLowCount, 6);
//    }
//}
