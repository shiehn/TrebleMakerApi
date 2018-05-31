package com.treblemaker.controllers.data;

import com.treblemaker.dal.interfaces.IAnalyticsHorizontalDal;
import com.treblemaker.dal.interfaces.InGramsDal;
import com.treblemaker.model.analytics.AnalyticsHorizontal;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.types.NGram;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class NgramsController {

    //TODO MOVE TO CONFIG FILE
    static class NGramTypes {

        public static String HARMONIC_LOOP = "hl";
        public static String HARMONIC_LOOP_ALT = "hlalt";
        public static String RHYTHMIC_LOOP = "rl";
        public static String RHYTHMIC_LOOP_ALT = "rlalt";
        public static String AMBIENCE_LOOP = "al";
        public static String AMBIENCE_LOOP_ALT = "alalt";
        public static String FILL = "fill";
    }

    @Autowired
    private IAnalyticsHorizontalDal analyticsHorizontalDal;

    @Autowired
    private InGramsDal nGramsDal;

    private static final int N_GRAM_SIZE = 2;

    @RequestMapping(value = "/api/ngrams/", method = RequestMethod.GET)
    public @ResponseBody
    String progressionGet(HttpServletResponse response, HttpServletRequest request) {

        Iterable<AnalyticsHorizontal> analyticsHorizontalList = analyticsHorizontalDal.findAll();

        for(AnalyticsHorizontal analyticsHorizontal : analyticsHorizontalList){

            List<CompositionTimeSlot> timeSlots = analyticsHorizontal.getComposition_time_slots();

            saveNgrams(analyticsHorizontal, timeSlots);
        }

        return "job complete";
    }

    public boolean saveNgrams(AnalyticsHorizontal analyticsHorizontal, List<CompositionTimeSlot> timeSlots) {

        for(int i=0; i<timeSlots.size(); i++){

            if(timeSlots.size() > N_GRAM_SIZE && i < timeSlots.size() - N_GRAM_SIZE){

                if(analyticsHorizontal.isCategory_harmonic()){
                    saveHarmonicNgrams(analyticsHorizontal, timeSlots, i);
                }

                if(analyticsHorizontal.isCategory_rhythmic()){
                    saveRythmicNgrams(analyticsHorizontal, timeSlots, i);
                }

                if(analyticsHorizontal.isCategory_ambience()){
                    saveAmbienceNgrams(analyticsHorizontal, timeSlots, i);
                }

                if(analyticsHorizontal.isCategory_fill()){
                    saveFillNgrams(analyticsHorizontal, timeSlots, i);
                }
            }
        }

        return true;
    }

    public boolean saveFillNgrams(AnalyticsHorizontal analyticsHorizontal, List<CompositionTimeSlot> timeSlots, int i) {
        NGram nGram = new NGram();

        nGram.setType(NGramTypes.FILL);
        nGram.setHorizontalTimeslotId(analyticsHorizontal.getId());

        nGram.setNgOne(timeSlots.get(i).getFillId().intValue());

        nGram.setNgTwo(timeSlots.get(i+1).getFillId().intValue());

        nGram.setNgTarget(timeSlots.get(i+N_GRAM_SIZE).getFillId().intValue());

        nGramsDal.save(nGram);
        return true;
    }

    public boolean saveAmbienceNgrams(AnalyticsHorizontal analyticsHorizontal, List<CompositionTimeSlot> timeSlots, int i) {
        NGram nGram = new NGram();
        NGram nGramAlt = new NGram();

        nGram.setType(NGramTypes.AMBIENCE_LOOP);
        nGramAlt.setType(NGramTypes.AMBIENCE_LOOP_ALT);

        nGram.setHorizontalTimeslotId(analyticsHorizontal.getId());
        nGramAlt.setHorizontalTimeslotId(analyticsHorizontal.getId());

        Integer al1 = timeSlots.get(i).getAmbientLoopId();
        nGram.setNgOne(al1 == null ? null : al1.intValue());

        Integer al1Alt = timeSlots.get(i).getAmbientLoopAltId();
        nGramAlt.setNgOne(al1Alt == null ? null : al1Alt.intValue());

        Integer al2 = timeSlots.get(i+1).getAmbientLoopId();
        nGram.setNgTwo(al2 == null ? null : al2.intValue());

        Integer al2Alt = timeSlots.get(i+1).getAmbientLoopAltId();
        nGramAlt.setNgTwo(al2Alt == null ? null : al2Alt.intValue());

        Integer al3 = timeSlots.get(i+N_GRAM_SIZE).getAmbientLoopId();
        nGram.setNgTarget(al3 == null ? null : al3.intValue());

        Integer al3Alt = timeSlots.get(i+N_GRAM_SIZE).getAmbientLoopAltId();
        nGramAlt.setNgTarget(al3Alt == null ? null : al3Alt.intValue());

        nGramsDal.save(nGram);
        nGramsDal.save(nGramAlt);
        return true;
    }

    public boolean saveRythmicNgrams(AnalyticsHorizontal analyticsHorizontal, List<CompositionTimeSlot> timeSlots, int i) {
        NGram nGram = new NGram();
        NGram nGramAlt = new NGram();

        nGram.setType(NGramTypes.RHYTHMIC_LOOP);
        nGramAlt.setType(NGramTypes.RHYTHMIC_LOOP_ALT);

        nGram.setHorizontalTimeslotId(analyticsHorizontal.getId());
        nGramAlt.setHorizontalTimeslotId(analyticsHorizontal.getId());

        Integer bl1 = timeSlots.get(i).getBeatLoopId();
        nGram.setNgOne(bl1 == null ? null : bl1.intValue());

        Integer bl1Alt = timeSlots.get(i).getBeatLoopAltId();
        nGramAlt.setNgOne(bl1Alt == null ? null : bl1Alt.intValue());

        Integer bl2 = timeSlots.get(i+1).getBeatLoopId();
        nGram.setNgTwo(bl2 == null ? null : bl2.intValue());

        Integer bl2Alt = timeSlots.get(i+1).getBeatLoopAltId();
        nGramAlt.setNgTwo(bl2Alt == null ? null : bl2Alt.intValue());

        Integer bl3 = timeSlots.get(i+N_GRAM_SIZE).getBeatLoopId();
        nGram.setNgTarget(bl3 == null ? null : bl3.intValue());

        Integer bl3Alt = timeSlots.get(i+N_GRAM_SIZE).getBeatLoopAltId();
        nGramAlt.setNgTarget(bl3Alt == null ? null : bl3Alt.intValue());

        nGramsDal.save(nGram);
        nGramsDal.save(nGram);
        return true;
    }

    public boolean saveHarmonicNgrams(AnalyticsHorizontal analyticsHorizontal, List<CompositionTimeSlot> timeSlots, int i) {
        NGram nGram = new NGram();
        NGram nGramAlt = new NGram();

        nGram.setType(NGramTypes.HARMONIC_LOOP);
        nGramAlt.setType(NGramTypes.HARMONIC_LOOP_ALT);

        nGram.setHorizontalTimeslotId(analyticsHorizontal.getId());
        nGramAlt.setHorizontalTimeslotId(analyticsHorizontal.getId());

        Integer hl1 = timeSlots.get(i).getHarmonicLoopId();
        nGram.setNgOne(hl1 == null ? null : hl1.intValue());

        Integer hlalt1 = timeSlots.get(i).getHarmonicLoopAltId();
        nGramAlt.setNgOne(hlalt1 == null ? null : hlalt1.intValue());

        Integer hl2 = timeSlots.get(i+1).getHarmonicLoopId();
        nGram.setNgTwo(hl2 == null ? null : hl2.intValue());

        Integer hlAlt2 = timeSlots.get(i+1).getHarmonicLoopAltId();
        nGramAlt.setNgTwo(hlAlt2 == null ? null : hlAlt2.intValue());

        Integer hl3 = timeSlots.get(i+N_GRAM_SIZE).getHarmonicLoopId();
        nGram.setNgTarget(hl3 == null ? null : hl3.intValue());

        Integer hlAlt3 = timeSlots.get(i+N_GRAM_SIZE).getHarmonicLoopId();
        nGramAlt.setNgTarget(hlAlt3 == null ? null : hlAlt3.intValue());

        nGramsDal.save(nGram);
        nGramsDal.save(nGramAlt);
        return true;
    }
}
