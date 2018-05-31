package com.treblemaker.services;

import com.treblemaker.model.BeatLoop;
import com.treblemaker.model.HarmonicLoop;
import com.treblemaker.model.RhythmicAccents;
import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.arpeggio.ArpeggioTimeslotData;
import com.treblemaker.model.bassline.BassLineJson;
import com.treblemaker.model.bassline.Bassline;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.utils.arpeggio.BassAndArpeggioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BasslineService {

    @Autowired
    private ArpeggioAndBassUtilsService arpeggioUtils;

    public List<ArpeggioTimeslotData> extractBasslineSlotData(List<CompositionTimeSlot> timeSlots, List<AnalyticsVertical> analyticsVerticals, List<Composition> compositions){
        return arpeggioUtils.extractArpeggioSlotData(ArpeggioAndBassUtilsService.ArpeggioSlotType.BASSLINE, timeSlots, analyticsVerticals, compositions);
    }

    public List<ArpeggioTimeslotData> addBeatRythmicalAccents(List<ArpeggioTimeslotData> basslineTimeslotData, List<BeatLoop> beatLoops){

        for (ArpeggioTimeslotData timeSlot: basslineTimeslotData) {

            BeatLoop beatLoop = getBeatLoopById(timeSlot.getBeatLoopId(), beatLoops);

            List<RhythmicAccents> rhythmicAccents = beatLoop.getRhythmicAccents();

            List<RhythmicAccents> normalizedAccents = normalizeListRhythmicAccentsToTwoBars(rhythmicAccents);


            //TODO THIS HAS BEEN MADE INTO A UTIL IN THE CAPRICOUS APP (RhythmicAccentUtil)
            int[] accents = new int[32];

            if(normalizedAccents == null){
                System.out.println("ERROR ***NOT RYTHMIC ACCENTS MUST FIX !!!!!***");
            }else {

                int index = 0;

                for (RhythmicAccents rAccents : normalizedAccents) {
                    int[] accentsAsInts = rAccents.getAsIntegerArray();
                    for (int a : accentsAsInts) {
                        accents[index] = a;
                        index++;
                    }
                }
            }
            //TODO THIS HAS BEEN MADE INTO A UTIL IN THE CAPRICOUS APP (RhythmicAccentUtil)

            timeSlot.setBeatLoopRhythm(accents);
        }

        return basslineTimeslotData;
    }

    public BeatLoop getBeatLoopById(int id, List<BeatLoop> beatLoops){

        for(BeatLoop beatLoop : beatLoops){
            if(beatLoop.getId() == id){
                return beatLoop;
            }
        }

        throw new RuntimeException("Error: beatLoop NotFound By ID");
    }

    public List<RhythmicAccents> normalizeListRhythmicAccentsToTwoBars(List<RhythmicAccents> rhythmicAccents){

        List<RhythmicAccents> accents = new ArrayList<>();

        if(rhythmicAccents == null || rhythmicAccents.size() == 0){
            return null;
        }
        else if(rhythmicAccents.size() == 1){
            accents.add(rhythmicAccents.get(0));
            accents.add(rhythmicAccents.get(0));
        }else {
            accents.add(rhythmicAccents.get(0));
            accents.add(rhythmicAccents.get(1));
        }

        return accents;
    }

    public List<ArpeggioTimeslotData> addHarmonicRythmicalAccents(List<ArpeggioTimeslotData> basslineTimeslotData, List<HarmonicLoop> harmonicLoops){

        for (ArpeggioTimeslotData timeSlot: basslineTimeslotData) {

            HarmonicLoop harmonicLoop = getHarmonicLoopById(timeSlot.getHarmonicLoopId(), harmonicLoops);
            if(harmonicLoop == null){
                System.out.println("ERROR in Time_slot_id: " + timeSlot.getTimeSlotId());
                System.out.println("ERROR in Arpeggio_time_slot_id: " + timeSlot.getArpeggioId());
            }else{
                List<RhythmicAccents> rhythmicAccents = harmonicLoop.getRhythmicAccents();

                List<RhythmicAccents> normalizedAccents = normalizeListRhythmicAccentsToTwoBars(rhythmicAccents);

                int[] accents = new int[32];

                if(normalizedAccents == null){
                    System.out.println("ERROR ***NOT RYTHMIC ACCENTS MUST FIX !!!!!***");
                }else {
                    int index = 0;

                    for (RhythmicAccents rAccents : normalizedAccents) {
                        int[] accentsAsInts = rAccents.getAsIntegerArray();
                        for (int a : accentsAsInts) {
                            accents[index] = a;
                            index++;
                        }
                    }
                }

                timeSlot.setHarmonicLoopRhythm(accents);
            }
        }

        return basslineTimeslotData;
    }

    public HarmonicLoop getHarmonicLoopById(int id, List<HarmonicLoop> harmonicLoops){

        for(HarmonicLoop harmonicLoop : harmonicLoops){
            if(harmonicLoop.getId() == id){
                return harmonicLoop;
            }
        }

        System.out.println("Error: Harmonic NotFound. HL_id:" + id);
        return null;
    }

    public List<ArpeggioTimeslotData> addDurations(List<ArpeggioTimeslotData> basslineTimeslotData, List<Bassline> basslines){

        for (ArpeggioTimeslotData timeSlot: basslineTimeslotData) {

            Bassline bassline = arpeggioUtils.findBasslineById(timeSlot.getBeatLoopId(), basslines);

            BassLineJson basslineJson = bassline.getBassline();

            double[] durations = new double[32];

            for(int i=0; i<32; i++){
                durations[i] = BassAndArpeggioUtil.convertDurationToInt(basslineJson.getDurationByPosition(i));
            }

            timeSlot.setBasslineDurations(durations);
        }

        return basslineTimeslotData;
    }




}
