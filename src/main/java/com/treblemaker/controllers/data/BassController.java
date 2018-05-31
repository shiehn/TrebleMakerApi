package com.treblemaker.controllers.data;

import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.dal.DALCache;
import com.treblemaker.dal.DALCacheFillService;
import com.treblemaker.model.arpeggio.ArpeggioTimeslotData;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.services.ArpeggioAndBassUtilsService;
import com.treblemaker.services.BasslineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class BassController {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs


    private static Date resultSetExpiry;

    @Autowired
    private DALCacheFillService cacheFillService;

    @Autowired
    private BasslineService basslineService;

    @Autowired
    private ArpeggioAndBassUtilsService arpeggioAndBassUtilsService;

    @RequestMapping(value = "/data/bass", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");

        cacheFillService.fillCache();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();

        try
        {
            //1) get compId,TimeSlotId,Rating,BeatLoopId,HarmonicLoop,bassline
            List<ArpeggioTimeslotData> basslineSlotData = basslineService.extractBasslineSlotData(timeSlots, DALCache.getInstance().getAnalyticsVertical(), DALCache.getInstance().getCompositions());

            //2) normalize beatloop to 2 bars
            basslineSlotData = basslineService.addBeatRythmicalAccents(basslineSlotData, DALCache.getInstance().getBeatLoops());

            //3) normalize harmloops to 2 bars
            basslineSlotData = basslineService.addHarmonicRythmicalAccents(basslineSlotData, DALCache.getInstance().getHarmonicLoops());

            //4) get durartion & interval for basslines
            basslineSlotData = basslineService.addDurations(basslineSlotData, DALCache.getInstance().getBasslines());

            //5) add intervals

            basslineSlotData = arpeggioAndBassUtilsService.addBasslineIntervals(basslineSlotData, DALCache.getInstance().getBasslines());
            //finished !!!!!!!!


            // CREATE CSV
            // CREATE CSV
            // CREATE CSV
            // CREATE CSV
            // CREATE CSV
            // CREATE CSV


            try
            {
                OutputStream outputStream = response.getOutputStream();

                StringWriter sw = new StringWriter();

                if(NNSingelton.INSTANCE.bassCsvString != null){
                    outputStream.write(NNSingelton.INSTANCE.bassCsvString.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }else {

                    int inputs = 129;

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

                    for (int CURRENT_ITERATION = 0; CURRENT_ITERATION < basslineSlotData.size(); CURRENT_ITERATION++) {

                        //ADD RATING
                        //ADD RATING
                        //ADD RATING
                        if (basslineSlotData.get(CURRENT_ITERATION).getBasslineRating() == 0.0) {
                            sw.write("0");
                        } else if (basslineSlotData.get(CURRENT_ITERATION).getBasslineRating() == 0.5) {
                            sw.write("1");
                        } else if (basslineSlotData.get(CURRENT_ITERATION).getBasslineRating() == 1.0) {
                            sw.write("2");
                        } else{
                            throw new RuntimeException("MISSING RATING!!! FOR BASSLINE");
                        }
                        sw.write(COMMA_DELIMITER);
                        //ADD RATING
                        //ADD RATING
                        //ADD RATING

                        //ADD THE RECORDS ..
                        int arrayLength = 32;
                        for (int i = 0; i < arrayLength; i++) {

//                           // if (i == 0) {
                                //sw.write("" + Double.toString(basslineSlotData.get(CURRENT_ITERATION).getBasslineRating()));
                                //sw.write(COMMA_DELIMITER);
                            try {
                                System.out.println("A");
                                sw.write(Double.toString(basslineSlotData.get(CURRENT_ITERATION).getBasslineIntervals()[i]));
                                sw.write(COMMA_DELIMITER);

                                System.out.println("B");
                                sw.write(Double.toString(basslineSlotData.get(CURRENT_ITERATION).getBasslineDurations()[i]));
                                sw.write(COMMA_DELIMITER);

                                System.out.println("C");
                                sw.write(Double.toString((double) basslineSlotData.get(CURRENT_ITERATION).getBeatLoopRhythm()[i]));
                                sw.write(COMMA_DELIMITER);

                                System.out.println("D");
                                //TODO THIS IS A HACK!!! WHY ARE RHYTMIC LOOPS MISSING RHyTM
                                //TODO HACK THIS CONDITION SHOULD NOT BE HERE!!!!!!
                                if(basslineSlotData.get(CURRENT_ITERATION).getHarmonicLoopRhythm() == null){
                                    sw.write("0");
                                }else {
                                    sw.write(Double.toString((double) basslineSlotData.get(CURRENT_ITERATION).getHarmonicLoopRhythm()[i]));
                                }
                            }catch (Exception e){
                                String sdf = e.getMessage();
                            }
                            if(i != arrayLength-1) {
                                sw.write(COMMA_DELIMITER);
                            }
                        }

                        sw.write(NEW_LINE_SEPARATOR);
                    }

                    NNSingelton.INSTANCE.bassCsvString = sw.toString();

                    outputStream.write(NNSingelton.INSTANCE.bassCsvString.getBytes());
                    outputStream.flush();
                    outputStream.close();
                }
            }
            catch(Exception e)
            {
                System.out.println(e.toString());
            }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
