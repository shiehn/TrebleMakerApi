package com.treblemaker.services;

import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.arpeggio.Arpeggio;
import com.treblemaker.model.arpeggio.ArpeggioJson;
import com.treblemaker.model.arpeggio.ArpeggioTimeslotData;
import com.treblemaker.model.bassline.BassLineJson;
import com.treblemaker.model.bassline.Bassline;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.utils.arpeggio.BassAndArpeggioUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArpeggioAndBassUtilsService {

    public enum ArpeggioSlotType {
        ARPEGGIO,
        BASSLINE
    }

    public List<ArpeggioTimeslotData> extractArpeggioSlotData(ArpeggioSlotType arpeggioSlotType, List<CompositionTimeSlot> timeSlots, List<AnalyticsVertical> analyticsVerticals, List<Composition> compositions){

        List<ArpeggioTimeslotData> arpeggioTimeslots = new ArrayList<>();

        compositions.forEach(composition -> {

            boolean skip = false;

            for (int i=0; i<composition.getCompositionTimeSlots().size(); i++) {

                if(!skip){

                    if(findAnalyticVerticalById(composition.getCompositionTimeSlots().get(i).getId(), analyticsVerticals) != null && findAnalyticVerticalById(composition.getCompositionTimeSlots().get(i+1).getId(), analyticsVerticals) != null) {

                        if (composition.getCompositionTimeSlots().get(i).getBeatLoopId() != null && composition.getCompositionTimeSlots().get(i).getBeatLoopId() != 0
                                && composition.getCompositionTimeSlots().get(i).getHarmonicLoopId() != null && composition.getCompositionTimeSlots().get(i).getHarmonicLoopId() != 0
                                && composition.getCompositionTimeSlots().get(i).getBasslineId() != null && composition.getCompositionTimeSlots().get(i).getBasslineId() != 0
                                ) {

                            ArpeggioTimeslotData basslinetimeslot = new ArpeggioTimeslotData();

                            int bassRatingOne = findAnalyticVerticalById(composition.getCompositionTimeSlots().get(i).getId(), analyticsVerticals).getBasslineRating();
                            int bassRatingTwo = findAnalyticVerticalById(composition.getCompositionTimeSlots().get(i + 1).getId(), analyticsVerticals).getBasslineRating();

                            basslinetimeslot.setArpeggioId(composition.getCompositionTimeSlots().get(i).getArpeggioId());
                            basslinetimeslot.setBasslineId(composition.getCompositionTimeSlots().get(i).getBasslineId());

                            if(arpeggioSlotType == ArpeggioSlotType.ARPEGGIO) {
                                int arpeggioRatingOne = findAnalyticVerticalById(composition.getCompositionTimeSlots().get(i).getId(), analyticsVerticals).getArpeggioRating();
                                int arpeggioRatingTwo = findAnalyticVerticalById(composition.getCompositionTimeSlots().get(i + 1).getId(), analyticsVerticals).getArpeggioRating();

                                basslinetimeslot.setArpeggioRating(getAverageRating(arpeggioRatingOne, arpeggioRatingTwo));
                            }

                            basslinetimeslot.setTimeSlotId(composition.getCompositionTimeSlots().get(i).getId());
                            basslinetimeslot.setBasslineRating(getAverageRating(bassRatingOne, bassRatingTwo));
                            basslinetimeslot.setBeatLoopId(composition.getCompositionTimeSlots().get(i).getBeatLoopId());
                            basslinetimeslot.setHarmonicLoopId(composition.getCompositionTimeSlots().get(i).getHarmonicLoopId());

                            arpeggioTimeslots.add(basslinetimeslot);
                        }else{
                            System.out.println("ERROR BEATLOOP HARMONIC OR BASSLINE IS NULL!!");
                        }

                        System.out.println("SUCCESSFULLY FOUND VERTICAL ANALYTIC!!");
                    }else{
                        System.out.println("ERROR CANNOT FIND VERTICAL ANALYTIC!!");
                    }
                }

                skip = !skip;
            }
        });

        return arpeggioTimeslots;
    }

    public List<ArpeggioTimeslotData> addBasslineIntervals(List<ArpeggioTimeslotData> basslineTimeslotData, List<Bassline> basslines){

        for (ArpeggioTimeslotData timeSlot: basslineTimeslotData) {

            Bassline bassline = findBasslineById(timeSlot.getBeatLoopId(), basslines);

            BassLineJson basslineJson = bassline.getBassline();

            double[] intervals = new double[32];

            for(int i=0; i<32; i++){
                intervals[i] = BassAndArpeggioUtil.convertIntervalsToDouble(basslineJson.getIntervalByPosition(i));
            }

            timeSlot.setBasslineIntervals(intervals);
        }

        return basslineTimeslotData;
    }

    public List<ArpeggioTimeslotData> addArpeggioIntervals(List<ArpeggioTimeslotData> arpeggioTimeslotData, List<Arpeggio> arpeggios){

        for (ArpeggioTimeslotData timeSlot: arpeggioTimeslotData) {

            Arpeggio arpeggio = findArpeggioById(timeSlot.getArpeggioId(), arpeggios);

            ArpeggioJson arpeggioJson = arpeggio.getArpeggioJson();

            double[] intervals = new double[32];

            for(int i=0; i<32; i++){
                intervals[i] = BassAndArpeggioUtil.convertIntervalsToDouble(Integer.toString(arpeggioJson.getArpeggio()[i]));
            }

            timeSlot.setArpeggioIntervals(intervals);
        }

        return arpeggioTimeslotData;
    }

    public Bassline findBasslineById(int basslineId, List<Bassline> basslines){

        for (Bassline bassline:basslines) {
            if(bassline.getId() == basslineId){
                return bassline;
            }
        }

        return null;
    }

    public Arpeggio findArpeggioById(int arpeggioId, List<Arpeggio> arpeggios){

        for (Arpeggio arpeggio:arpeggios) {
            if(arpeggio.getId() == arpeggioId){
                return arpeggio;
            }
        }

        return null;
    }

    public AnalyticsVertical findAnalyticVerticalById(int id, List<AnalyticsVertical> analyticsVerticals){

        for (AnalyticsVertical vertical:analyticsVerticals) {

            if(vertical.getTimeSlotId() == id){
                return vertical;
            }
        }

        return null;
    }

    public double getAverageRating(int ratingOne, int ratingTwo){

        if(ratingOne == 0 || ratingTwo == 0){
            return 0.0;
        } else if(ratingOne == 1 || ratingTwo == 1){
            return 0.5;
        } else if(ratingOne == 2 && ratingTwo == 2){
            return 1.0;
        }

        throw new RuntimeException("ERROR UNEXPECTED AVERAGE RATING !!!");
    }
}
