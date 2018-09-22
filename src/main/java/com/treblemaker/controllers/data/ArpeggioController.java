package com.treblemaker.controllers.data;

import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.dal.DALCache;
import com.treblemaker.dal.DALCacheFillService;
import com.treblemaker.model.arpeggio.ArpeggioTimeslotData;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.services.ArpeggioAndBassUtilsService;
import com.treblemaker.services.ArpeggioService;
import com.treblemaker.services.BasslineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ArpeggioController {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";


    @Autowired
    private BasslineService basslineService;

    @Autowired
    private ArpeggioAndBassUtilsService arpeggioAndBassUtilsService;

    @Autowired
    private ArpeggioService arpeggioService;

    @Autowired
    private DALCacheFillService dalCacheFillService;

    @RequestMapping(value = "/data/arpeggio", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        dalCacheFillService.fillCache();

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();

        try {
            //1) get compId,TimeSlotId,Rating,BeatLoopId,HarmonicLoop,bassline
            List<ArpeggioTimeslotData> arpeggioSlotData = arpeggioService.extractArpeggioSlotData(timeSlots, DALCache.getInstance().getAnalyticsVertical(), DALCache.getInstance().getCompositions());

            //2) normalize beatloop to 2 bars
            arpeggioSlotData = basslineService.addBeatRythmicalAccents(arpeggioSlotData, DALCache.getInstance().getBeatLoops());

            //3) normalize harmloops to 2 bars
            arpeggioSlotData = basslineService.addHarmonicRythmicalAccents(arpeggioSlotData, DALCache.getInstance().getHarmonicLoops());

            //4) get durartion & interval for basslines
            arpeggioSlotData = basslineService.addDurations(arpeggioSlotData, DALCache.getInstance().getBasslines());

            //5) add intervals
            arpeggioSlotData = arpeggioAndBassUtilsService.addBasslineIntervals(arpeggioSlotData, DALCache.getInstance().getBasslines());

            arpeggioSlotData = arpeggioAndBassUtilsService.addArpeggioIntervals(arpeggioSlotData, DALCache.getInstance().getArpeggios());

            try {
                OutputStream outputStream = response.getOutputStream();

                StringWriter sw = new StringWriter();

                if (NNSingelton.INSTANCE.arpgCsvString != null) {
                    outputStream.write(NNSingelton.INSTANCE.arpgCsvString.getBytes());
                    outputStream.flush();
                    outputStream.close();
                } else {

                    int inputs = 97;

                    //CREATE THE HEADER. ..
                    //CREATE THE HEADER. ..
                    //CREATE THE HEADER. ..
                    //CREATE THE HEADER. ..

//                    for (int i = 0; i < inputs; i++) {
//                        //CREATE HEADER ..
//                        //sw.write("rating");
//                        sw.write("" + Integer.toString(i));
//                        if (i != inputs - 1) {
//                            sw.write(COMMA_DELIMITER);
//                        }
//                    }
//                    sw.write(NEW_LINE_SEPARATOR);

                    //CREATE THE HEADER. ..
                    //CREATE THE HEADER. ..

                    for (int CURRENT_ITERATION = 0; CURRENT_ITERATION < arpeggioSlotData.size(); CURRENT_ITERATION++) {

                        try {
                            StringWriter subSw = new StringWriter();

                            //ADD RATING
                            //ADD RATING
                            //ADD RATING
                            if (arpeggioSlotData.get(CURRENT_ITERATION).getArpeggioRating() == 0.0) {
                                subSw.write("0");
                            } else if (arpeggioSlotData.get(CURRENT_ITERATION).getArpeggioRating() == 0.5) {
                                subSw.write("1");
                            } else if (arpeggioSlotData.get(CURRENT_ITERATION).getArpeggioRating() == 1.0) {
                                subSw.write("2");
                            } else{
                                throw new RuntimeException("MISSING RATING!!! FOR ARPEGGIO");
                            }
                            subSw.write(COMMA_DELIMITER);
                            //ADD RATING
                            //ADD RATING
                            //ADD RATING


                            //ADD THE RECORDS ..
                            int arrayLength = 32;
                            for (int i = 0; i < arrayLength; i++) {

                                subSw.write(Double.toString(arpeggioSlotData.get(CURRENT_ITERATION).getArpeggioIntervals()[i]));
                                subSw.write(COMMA_DELIMITER);

                                double totalWeight = getTotalWeight(arpeggioSlotData.get(CURRENT_ITERATION), i);
                                subSw.write(Double.toString(totalWeight));
                                subSw.write(COMMA_DELIMITER);

                                subSw.write(Double.toString(arpeggioSlotData.get(CURRENT_ITERATION).getBasslineIntervals()[i]));

                                if(i != arrayLength-1) {
                                    subSw.write(COMMA_DELIMITER);
                                }
                            }

                            subSw.write(NEW_LINE_SEPARATOR);

                            sw.write(subSw.toString());
                        }catch (Exception e){
                            e.printStackTrace();
                            System.out.println("ERROR when creating CSV : " + e.getMessage());
                        }
                    }

                    NNSingelton.INSTANCE.arpgCsvString = sw.toString();

                    outputStream.write(NNSingelton.INSTANCE.arpgCsvString.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public double getTotalWeight(ArpeggioTimeslotData arpeggioSlotData, int i){
        int tmpWeight = 0;

        if (arpeggioSlotData.getBasslineIntervals()[i] > 0.0) {
            tmpWeight++;
        }

        if (arpeggioSlotData.getHarmonicLoopRhythm()[i] > 0.0) {
            tmpWeight++;
        }

        if (arpeggioSlotData.getBeatLoopRhythm()[i] > 0.0) {
            tmpWeight++;
        }

        double totalWeight;

        switch (tmpWeight) {
            case 1:
                totalWeight = 0.3;
                break;
            case 2:
                totalWeight = 0.6;
                break;
            case 3:
                totalWeight = 1.0;
                break;
            default:
                totalWeight = 0.0;
        }

        return totalWeight;
    }
}