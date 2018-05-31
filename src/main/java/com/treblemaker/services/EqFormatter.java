package com.treblemaker.services;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.treblemaker.model.IParametricEq.EqBand;
import static com.treblemaker.model.parametriceq.ParametricEqWithType.EqPhase;

@Service
public class EqFormatter {

    private IParametricEqBeatLoopDal parametricEqBeatLoopDal;
    private IParametricEqHarmonicLoopDal parametricEqHarmonicLoopDal;
    private IParametricEqCompositionLayerDal parametricEqCompositionLayerDal;
    private ICompositionDal compositionDal;
    private IAnalyticsVerticalDal analyticsVerticalDal;

    List<ParametricEqBeatLoop> eqBeatLoops = null;
    List<ParametricEqHarmonicLoop> eqHarmonicLoops = null;
    List<ParametricEqCompositionLayer> eqCompositionLayers = null;
    List<Composition> compositions = null;
    List<AnalyticsVertical> analyticsVerticals = null;

    private void updateCache() {

        if (eqBeatLoops == null) {
            eqBeatLoops = StreamSupport.stream(parametricEqBeatLoopDal.findAll().spliterator(), false).collect(Collectors.toList());
        }

        if (eqHarmonicLoops == null) {
            eqHarmonicLoops = StreamSupport.stream(parametricEqHarmonicLoopDal.findAll().spliterator(), false).collect(Collectors.toList());
        }

        if (eqCompositionLayers == null) {
            eqCompositionLayers = StreamSupport.stream(parametricEqCompositionLayerDal.findAll().spliterator(), false).collect(Collectors.toList());
        }

        if (compositions == null) {
            compositions = StreamSupport.stream(compositionDal.findAll().spliterator(), false).collect(Collectors.toList());
        }

        if(analyticsVerticals == null){
            analyticsVerticals = StreamSupport.stream(analyticsVerticalDal.findAll().spliterator(), false).collect(Collectors.toList());
        }
    }

    public static final EqBand[] eqBands = {EqBand.FREQ_20, EqBand.FREQ_25, EqBand.FREQ_31, EqBand.FREQ_40, EqBand.FREQ_50, EqBand.FREQ_63, EqBand.FREQ_80, EqBand.FREQ_100, EqBand.FREQ_125, EqBand.FREQ_160, EqBand.FREQ_200, EqBand.FREQ_250, EqBand.FREQ_315, EqBand.FREQ_400, EqBand.FREQ_500, EqBand.FREQ_630, EqBand.FREQ_800, EqBand.FREQ_1000, EqBand.FREQ_1250, EqBand.FREQ_1600, EqBand.FREQ_2000, EqBand.FREQ_2500, EqBand.FREQ_3150, EqBand.FREQ_4000, EqBand.FREQ_5000, EqBand.FREQ_6300, EqBand.FREQ_8000, EqBand.FREQ_10000, EqBand.FREQ_12500, EqBand.FREQ_16000, EqBand.FREQ_20000
    };

    @Autowired
    public EqFormatter(IParametricEqBeatLoopDal parametricEqBeatLoopDal, IParametricEqHarmonicLoopDal parametricEqHarmonicLoopDal, IParametricEqCompositionLayerDal parametricEqCompositionLayerDal,ICompositionDal compositionDal, IAnalyticsVerticalDal analyticsVerticalDal) {

        this.parametricEqBeatLoopDal = parametricEqBeatLoopDal;
        this.parametricEqHarmonicLoopDal = parametricEqHarmonicLoopDal;
        this.parametricEqCompositionLayerDal = parametricEqCompositionLayerDal;
        this.compositionDal = compositionDal;
        this.analyticsVerticalDal = analyticsVerticalDal;
    }

    private Map<String, List<ParametricDtoRow>> cachedEqRows = new HashMap<>();

    public ParametricDto createPhaseOne(EqClassificationRequest request) {

        //BEAT -> harmic
        updateCache();

        ParametricEqBeatLoop targetBeatEq = new ParametricEqBeatLoop();
        ParametricEqHarmonicLoop targetHarmonicEq = new ParametricEqHarmonicLoop();
        try {
              targetBeatEq = eqBeatLoops.stream().filter(l -> l.getBeatLoopId() == request.getBeatid()).findFirst().get();
              targetHarmonicEq = eqHarmonicLoops.stream().filter(l -> l.getHarmonicLoopId() == request.getHarmonicid()).findFirst().get();
        }catch (Exception e){
            //TODO SEND ERROR ALET OR SOMETHIGN ..
            System.out.println("NOT FINDING EQ LOOPS IN DATABASE");
        }

        ParametricDto parametricDto = new ParametricDto();

        for (EqBand eqband : eqBands) {

            ParametricDtoBand eqBandDto = new ParametricDtoBand(eqband);
            eqBandDto = EqFormatHelper.addPredictorColumns(eqBandDto, EqPhase.PHASE_ONE);

            ParametricDtoRow target = new ParametricDtoRow();
            target.setRating("ok");
            target.setBeat(targetBeatEq.getByFreq(eqband));
            target.setHarm(targetHarmonicEq.getByFreq(eqband));
            eqBandDto.setTarget(target);

            String cacheKey = "p_" + request.getPhase() + "_f_" + eqband.toString();
            List<ParametricDtoRow> cachedRows = cachedEqRows.get(cacheKey);
            if(cachedRows != null){
                eqBandDto.setRows(cachedRows);
            }else {

                for (Composition composition : compositions) {

                    for (CompositionTimeSlot ts : composition.getCompositionTimeSlots()) {

                        Optional<AnalyticsVertical> rating = analyticsVerticals.stream().filter(v -> v.getTimeSlotId() == ts.getId()).findFirst();

                        if (rating.isPresent() && rating.get() != null && ts.getBeatLoopId() != null && ts.getHarmonicLoopId() != null) {

                            ParametricDtoRow row = new ParametricDtoRow();

                            row.setRating(convertRating(rating.get().getRating()));

                            //ROWS
                            Optional<ParametricEqBeatLoop> beatLoop = eqBeatLoops.stream().filter(bl -> bl.getBeatLoopId() == ts.getBeatLoopId()).findFirst();
                            Optional<ParametricEqHarmonicLoop> harmonicLoop = eqHarmonicLoops.stream().filter(hl -> hl.getHarmonicLoopId() == ts.getHarmonicLoopId()).findFirst();

                            if (beatLoop.isPresent() && harmonicLoop.isPresent()) {
                                row.setBeat(normalizeFrom0To1(beatLoop.get().getByFreq(eqband)));
                                row.setHarm(normalizeFrom0To1(harmonicLoop.get().getByFreq(eqband)));

                                eqBandDto.addRow(row);
                            }
                        }
                    }
                }

                if(eqBandDto.getRows() != null && eqBandDto.getRows().size() > 0) {
                    cachedEqRows.put(cacheKey, eqBandDto.getRows());
                }
            }

            parametricDto.addEqBand(eqBandDto);
        }

        return parametricDto;
    }

    private double normalizeFrom0To1(double value){
        return value * 0.01;
    }

    private String convertRating(int rating){
        if(rating == 0){
            return "bad";
        }else if(rating == 1){
            return "ok";
        }else if(rating == 2){
            return "good";
        }

        throw new RuntimeException("rating is out of bounds");
    }



}



    /*
    public List<ParametricEqWithType> createPhaseOne(){

        //BEAT LOOP -> harm

        updateCache();

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        EqPhase phase = EqPhase.PHASE_ONE;

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        return eqWithTypeList;
    }

    public List<ParametricEqWithType> createPhaseTwo(){

        updateCache();

        //synth_hi ->  harm + beat

        EqPhase phase = EqPhase.PHASE_TWO;

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI_ALT){
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthHiKey);
                eqType = (ParametricEqWithType)transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        return eqWithTypeList;
    }

    public List<ParametricEqWithType> createPhaseThree(){

        //synth_low_alt -> harm + beat

        updateCache();

        EqPhase phase = EqPhase.PHASE_THREE;

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthLowKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        return eqWithTypeList;
    }

    public List<ParametricEqWithType> createPhaseFour(){

        //synth_mid -> harm + beat

        updateCache();

        EqPhase phase = EqPhase.PHASE_FOUR;

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthMidKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        return eqWithTypeList;
    }


    public List<ParametricEqWithType> createPhaseFive(){

        //synth_mid_ALT -> harm + beat

        updateCache();

        EqPhase phase = EqPhase.PHASE_FIVE;

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthMidKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        return eqWithTypeList;
    }

    public List<ParametricEqWithType> createPhaseSix(){

        //synth_low -> synth_mids + harm + beat

        updateCache();

        EqPhase phase = EqPhase.PHASE_SIX;

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthMidKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthLowKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        return eqWithTypeList;
    }

    public List<ParametricEqWithType> createPhaseSeven(){

        //synth_hi -> synth_low + synth_mids + harm + beat

        updateCache();

        EqPhase phase = EqPhase.PHASE_SEVEN;

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthMidKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthLowKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthHiKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        return eqWithTypeList;
    }


    public List<ParametricEqWithType> createPhaseEight(){

        //harm_alt -> synth_hi + synth_low + synth_mids + harm + beat

        updateCache();

        EqPhase phase = EqPhase.PHASE_SEVEN;

        List<ParametricEqWithType> eqWithTypeList = new ArrayList<>();

        for (ParametricEqHarmonicLoop loops : eqHarmonicLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.harmonicLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loops);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqBeatLoop loop : eqBeatLoops) {

            ParametricEqWithType eqType = new ParametricEqWithType();
            eqType.setPhase(phase);
            eqType.setType(ParametricEqWithType.beatLoopKey);
            eqType = (ParametricEqWithType)transferBands(eqType, loop);

            eqWithTypeList.add(eqType);
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_MID_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthMidKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_LOW_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthLowKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        for (ParametricEqCompositionLayer loops : eqCompositionLayers) {

            if(loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI
                    || loops.getLayerType() == ParametricEqCompositionLayer.LAYER_TYPE_SYNTH_HI_ALT) {
                ParametricEqWithType eqType = new ParametricEqWithType();
                eqType.setPhase(phase);
                eqType.setType(ParametricEqWithType.synthHiKey);
                eqType = (ParametricEqWithType) transferBands(eqType, loops);

                eqWithTypeList.add(eqType);
            }
        }

        return eqWithTypeList;
    }
    */
/*
    public IParametricEq transferBands(IParametricEq targetEq, IParametricEq sourceEq){

        targetEq.setFreq20(   sourceEq.getFreq20()   );
        targetEq.setFreq25(   sourceEq.getFreq25()   );
        targetEq.setFreq31(   sourceEq.getFreq31()   );
        targetEq.setFreq40(   sourceEq.getFreq40()   );
        targetEq.setFreq50(   sourceEq.getFreq50()   );
        targetEq.setFreq63(   sourceEq.getFreq63()   );
        targetEq.setFreq80(   sourceEq.getFreq80()   );
        targetEq.setFreq100(  sourceEq.getFreq100()  );
        targetEq.setFreq125(  sourceEq.getFreq125()  );
        targetEq.setFreq160(  sourceEq.getFreq160()  );
        targetEq.setFreq200(  sourceEq.getFreq200()  );
        targetEq.setFreq250(  sourceEq.getFreq250()  );
        targetEq.setFreq315(  sourceEq.getFreq315()  );
        targetEq.setFreq400(  sourceEq.getFreq400()  );
        targetEq.setFreq500(  sourceEq.getFreq500()  );
        targetEq.setFreq630(  sourceEq.getFreq630()  );
        targetEq.setFreq800(  sourceEq.getFreq800()  );
        targetEq.setFreq1000( sourceEq.getFreq1000() );
        targetEq.setFreq1250( sourceEq.getFreq1250() );
        targetEq.setFreq1600( sourceEq.getFreq1600() );
        targetEq.setFreq2000( sourceEq.getFreq2000() );
        targetEq.setFreq2500( sourceEq.getFreq2500() );
        targetEq.setFreq3150( sourceEq.getFreq3150() );
        targetEq.setFreq4000( sourceEq.getFreq4000() );
        targetEq.setFreq5000( sourceEq.getFreq5000() );
        targetEq.setFreq6300( sourceEq.getFreq6300() );
        targetEq.setFreq8000( sourceEq.getFreq8000() );
        targetEq.setFreq10000(sourceEq.getFreq10000());
        targetEq.setFreq12500(sourceEq.getFreq12500());
        targetEq.setFreq16000(sourceEq.getFreq16000());
        targetEq.setFreq20000(sourceEq.getFreq20000());

        return targetEq;
    }







    public ParametricRowEq formateSingleBand(String phase, String band) {

        Iterable<ParametricEqBeatLoop> eqBeatLoops = parametricEqBeatLoopDal.findAll();
        Iterable<ParametricEqHarmonicLoop> eqHarmonicLoops = parametricEqHarmonicLoopDal.findAll();
        Iterable<ParametricEqCompositionLayer> eqCompositionLayers = parametricEqCompositionLayerDal.findAll();

        List<ParametricEqWithType> rowEqs = new ArrayList<>();

        for (ParametricEqBeatLoop parametricEqBeatLoop : eqBeatLoops) {

            ParametricEqWithType row = new ParametricEqWithType();


        }

        return null;
    }

        /*
        public static String harmonicLoopKey = "harm";
        public static String harmonicLoopAltKey = "harm_alt";
        public static String beatLoopKey = "beat";
        public static String beatLoopAltKey = "beat_alt";
        public static String synthHiKey = "synth_h";
        public static String synthMidKey = "synth_m";
        public static String synthLowKey = "synth_l";
        public static String ambienceLoopKey = "ambi";
        public static String ambienceLoopAltKey = "ambi_alt";
        public static String fillsKey = "fill";
        */

        //rating


//        switch (PHASE) {
//
//            case 1:
//
//                //BEAT LOOP
//                //harm + beat
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 2:
//
//                //synth_hi LOOP
//                //harm + beat + synthHiKey
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 3:
//
//                //synth_hi_ALT LOOP
//                //harm + beat + synthLow
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 4:
//
//                //synth_mid LOOP
//                //harm + beat + synthMidKey
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 5:
//
//                //synth_mid_ALT LOOP
//                //harm + beat + synthMidKey
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 6:
//
//                //synth_Low LOOP
//                //harm + beat + synthMidKey
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 7:
//
//                //synth_Low_ALT LOOP
//                //harm + beat + synthMidKey
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 8:
//
//                //HARM ALT
//                //harm + beat + syntHi + synthMid + synthLow + harm_alt
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 9:
//
//                //BEAT ALT
//                //harm + beat + syntHi + synthMid + synthLow + harmAlt + beat_alt
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 10:
//
//                //Ambi
//                //harm + beat + syntHi + synthMid + synthLow + harmAlt + ambience
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 11:
//
//                //Ambi
//                //harm + beat + syntHi + synthMid + synthLow + harmAlt + ambience_alt
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//            case 12:
//
//                //Ambi
//                //harm + beat + syntHi + synthMid + synthLow + harmAlt + ambience + fills
//
//                switch (getEqBand) {
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                    case getEqBand:
//                        return 444;
//                }
//                break;
//        }



//    public void getAllRecords(){
//
//        int phaseType = 0;
//
//        Iterable<ParametricEqBeatLoop> parametricEqBeatLoop = parametricEqBeatLoopDal.findAll();
//        Iterable<ParametricEqHarmonicLoop> parametricEqHarmonicLoops = parametricEqHarmonicLoopDal.findAll();
//        Iterable<ParametricEqCompositionLayer> parametricEqCompositionLayers = parametricEqCompositionLayerDal.findAll();
//
//
//    }

    /*
        public static int LAYER_TYPE_SYNTH_HI = 1;
    public static int LAYER_TYPE_SYNTH_HI_ALT = 11;
    public static int LAYER_TYPE_SYNTH_MID = 2;
    public static int LAYER_TYPE_SYNTH_MID_ALT = 12;
    public static int LAYER_TYPE_SYNTH_LOW = 3;
    public static int LAYER_TYPE_SYNTH_LOW_ALT = 13;
    public static int LAYER_TYPE_HARMONIC_LOOP = 4;
    public static int LAYER_TYPE_HARMONIC_LOOP_ALT = 5;
    public static int LAYER_TYPE_AMBIENCE = 6;
    public static int LAYER_TYPE_AMBIENCE_ALT = 7;
    public static int LAYER_TYPE_BEAT_LOOP = 8;
    public static int LAYER_TYPE_BEAT_LOOP_ALT = 9;
    public static int LAYER_TYPE_FILL = 10;
     */



/*
IParametricEqBeatLoopDal
IParametricEqCompositionLayerDal
IParametricEqHarmonicLoopDal
 */