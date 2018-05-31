package com.treblemaker.controllers.data;

import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.dal.DALCache;
import com.treblemaker.dal.DALCacheFillService;
import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.arpeggio.Arpeggio;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.fx.FXArpeggioDelay;
import com.treblemaker.model.fx.FXArpeggioDelayRating;
import com.treblemaker.model.fx.SynthFXCoefficients;
import com.treblemaker.model.fx.util.DurationAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class SynthFXController {

    @Autowired
    private DALCacheFillService cacheFillService;

    @Autowired
    private DurationAnalysis durationAnalysis;

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs


    private static Date resultSetExpiry;

    // /data/synthfx?synthid=31&sixteenthfreq=0.0&eigthfreq=0.1&quarterfreq=0.2&dottedquarterfreq=0.3&halffreq=0.4&fxvol=0.5&fxtype=0.6&fxmastervol=0.7
    /////////prediction

    @RequestMapping(value = "/data/synthfx", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response,
                      @RequestParam(value = "synthid", required = true) Integer synthid) {

        cacheFillService.fillCache();

        //1) get all time-slots where
        // synth_template_hi_id = synthid AND fx_id not null AND rated!!!

        List<CompositionTimeSlot> compositionTimeSlots = DALCache.getInstance().getCompositionTimeSlots().stream().filter(compositionTimeSlot ->
                compositionTimeSlot.getSynthTemplateHiId() == synthid
                        && compositionTimeSlot.getRated() == 1
                        && compositionTimeSlot.getFxArpeggioDelayId() != null
        ).collect(Collectors.toList());

        List<Arpeggio> arpeggios = DALCache.getInstance().getArpeggios();
        List<FXArpeggioDelay> fxArpeggioDelays = DALCache.getInstance().getFxArpeggioDelays();
        List<FXArpeggioDelayRating> fxArpeggioDelayRatings = DALCache.getInstance().getFxArpeggioDelayRatings();
        List<AnalyticsVertical> analyticsVerticals = DALCache.getInstance().getAnalyticsVertical();

        List<SynthFXCoefficients> synthFXCoefficientList = new ArrayList<>();

        for (CompositionTimeSlot timeSlot : compositionTimeSlots) {

            Optional<Arpeggio> arpeggio = arpeggios.stream().filter(arpg -> arpg.getId() == timeSlot.getArpeggioId()).findFirst();
            Optional<FXArpeggioDelay> fxArpeggioDelay = fxArpeggioDelays.stream().filter(fxDelay -> fxDelay.getId() == timeSlot.getFxArpeggioDelayId()).findFirst();
            Optional<AnalyticsVertical> analyticsVertical = analyticsVerticals.stream().filter(analytics -> analytics.getTimeSlotId() == timeSlot.getId()).findFirst();

            if (arpeggio.isPresent() && fxArpeggioDelay.isPresent() && analyticsVertical.isPresent()) {

                double sixteenthFreq = durationAnalysis.extractSixteenthFrequency(arpeggio.get().getArpeggioJson().getArpeggio());
                double eigthFreq = durationAnalysis.extractEightFrequency(arpeggio.get().getArpeggioJson().getArpeggio());
                double quarterFreq = durationAnalysis.extractQuarterFrequency(arpeggio.get().getArpeggioJson().getArpeggio());
                double dottedQuarterFreq = durationAnalysis.extractDottedQuarterFrequency(arpeggio.get().getArpeggioJson().getArpeggio());
                double halfFreq = durationAnalysis.extractHalfFrequency(arpeggio.get().getArpeggioJson().getArpeggio());

                SynthFXCoefficients synthFXCoefficients = new SynthFXCoefficients();
                synthFXCoefficients.setRating(analyticsVertical.get().getRating());
                synthFXCoefficients.setSixteenthfreq(sixteenthFreq);
                synthFXCoefficients.setEigthfreq(eigthFreq);
                synthFXCoefficients.setQuarterfreq(quarterFreq);
                synthFXCoefficients.setDottedquarterfreq(dottedQuarterFreq);
                synthFXCoefficients.setHalffreq(halfFreq);

                synthFXCoefficients.setFxmastervol(fxArpeggioDelay.get().getMasterVolume());
                synthFXCoefficients.setFxtype(fxArpeggioDelay.get().getDelayType());
                synthFXCoefficients.setFxvol(fxArpeggioDelay.get().getDelayVolume());

                synthFXCoefficientList.add(synthFXCoefficients);
            }
        }



 /*
 rating
 sixteenthfreq,
 eigthfreq,
 quarterfreq,
 dottedquarterfreq,
 halffreq,
 fxvol,
 fxtype,
 fxmastervol
         */

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");
        try {
            OutputStream outputStream = response.getOutputStream();

            StringWriter sw = new StringWriter();

            //CREATE HEADER ..
//            sw.write("rating");
//            sw.write(COMMA_DELIMITER);
//            sw.write("sixteenthfreq");
//            sw.write(COMMA_DELIMITER);
//            sw.write("eigthfreq");
//            sw.write(COMMA_DELIMITER);
//            sw.write("quarterfreq");
//            sw.write(COMMA_DELIMITER);
//            sw.write("dottedquarterfreq");
//            sw.write(COMMA_DELIMITER);
//            sw.write("halffreq");
//            sw.write(COMMA_DELIMITER);
//            sw.write("fxvol");
//            sw.write(COMMA_DELIMITER);
//            sw.write("fxtype");
//            sw.write(NEW_LINE_SEPARATOR);

            for (SynthFXCoefficients coefficient : synthFXCoefficientList) {

                sw.write(Integer.toString(coefficient.getRating()));
                sw.write(COMMA_DELIMITER);
                sw.write(Double.toString(coefficient.getSixteenthfreq()));
                sw.write(COMMA_DELIMITER);
                sw.write(Double.toString(coefficient.getEigthfreq()));
                sw.write(COMMA_DELIMITER);
                sw.write(Double.toString(coefficient.getQuarterfreq()));
                sw.write(COMMA_DELIMITER);
                sw.write(Double.toString(coefficient.getDottedquarterfreq()));
                sw.write(COMMA_DELIMITER);
                sw.write(Double.toString(coefficient.getHalffreq()));
                sw.write(COMMA_DELIMITER);
                sw.write(Double.toString(coefficient.getFxvol()));
                sw.write(COMMA_DELIMITER);
                sw.write(Double.toString(coefficient.getFxtype()));
                sw.write(NEW_LINE_SEPARATOR);
            }

            NNSingelton.INSTANCE.synthFxCsvString = sw.toString();

            outputStream.write(NNSingelton.INSTANCE.synthFxCsvString.getBytes());
//            outputStream.write(outputResult.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }





}
