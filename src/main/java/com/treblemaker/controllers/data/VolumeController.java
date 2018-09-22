package com.treblemaker.controllers.data;

import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.composition.CompositionTimeSlotLevels;
import com.treblemaker.model.composition.CompositionTimeSlotLevelsWithRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class VolumeController {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private List<AnalyticsVertical> analyticsVerticals; // = DALCache.getInstance().getAnalyticsVertical();
    private List<CompositionTimeSlot> compositionTimeSlots; // = DALCache.getInstance().getCompositionTimeSlots();
    private List<CompositionTimeSlotLevels> timeSlotLevels; // = compositionTimeSlotLevelsDal.findAll();

    @Autowired
    public VolumeController(List<AnalyticsVertical> analyticsVerticals,
                            List<CompositionTimeSlot> compositionTimeSlots,
                            List<CompositionTimeSlotLevels> timeSlotLevels
    ) {
        this.analyticsVerticals = analyticsVerticals;
        this.compositionTimeSlots = compositionTimeSlots;
        this.timeSlotLevels = timeSlotLevels;
    }

    @RequestMapping(value = "/data/volume", method = RequestMethod.GET)
    public void getVolumeCsv(HttpServletResponse response) throws IOException {

        List<CompositionTimeSlotLevelsWithRating> nonNormalizedRows = getNonNormalizedData();

        List<CompositionTimeSlotLevelsWithRating> normalizedRows = normalizeRows(nonNormalizedRows);

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");

        OutputStream outputStream = response.getOutputStream();

        StringWriter sw = new StringWriter();

        for (CompositionTimeSlotLevelsWithRating row : normalizedRows) {

            sw.write(Integer.toString(row.getRating()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getCompHi()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getCompHiAlt()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getCompMid()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getCompMidAlt()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getCompLow()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getCompLowAlt()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getBeat()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getBeatAlt()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getHarmonic()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getHarmonicAlt()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getAmbient()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getFills()));
            sw.write(COMMA_DELIMITER);
            sw.write(Double.toString(row.getHits()));
            sw.write(NEW_LINE_SEPARATOR);
        }

        outputStream.write(sw.toString().getBytes());
//            outputStream.write(outputResult.getBytes());
        outputStream.flush();
        outputStream.close();
    }

    public List<CompositionTimeSlotLevelsWithRating> getNonNormalizedData() {
        List<CompositionTimeSlotLevelsWithRating> unNormalizedRows = new ArrayList<>();

        for (AnalyticsVertical analyticsVertical : analyticsVerticals) {
            if (analyticsVertical.getLevelsRating() != null) {
                for (CompositionTimeSlot timeSlot : compositionTimeSlots) {
                    if (timeSlot.getId().equals(analyticsVertical.getTimeSlotId())) {
                        for (CompositionTimeSlotLevels level : timeSlotLevels) {
                            if (timeSlot.getCompositionId().equals(level.getCompositionId())) {
                                CompositionTimeSlotLevelsWithRating row = new CompositionTimeSlotLevelsWithRating();
                                row.setRating(analyticsVertical.getLevelsRating());
                                row.setId(level.getId());
                                row.setCompositionId(level.getCompositionId());
                                row.setLevelBeforeMixed(level.getLevelBeforeMixed());

                                row.setCompHi(level.getCompHi());
                                row.setCompHiAlt(level.getCompHiAlt());
                                row.setCompMid(level.getCompMid());
                                row.setCompMidAlt(level.getCompMidAlt());
                                row.setCompLow(level.getCompLow());
                                row.setCompLowAlt(level.getCompLowAlt());
                                row.setAmbient(level.getAmbient());
                                row.setBeat(level.getBeat());
                                row.setBeatAlt(level.getBeatAlt());
                                row.setHarmonic(level.getHarmonic());
                                row.setHarmonicAlt(level.getCompHiAlt());
                                row.setHits(level.getHits());
                                row.setFills(level.getFills());

                                unNormalizedRows.add(row);
                            }
                        }
                    }
                }
            }
        }
        return unNormalizedRows;
    }

    public List<CompositionTimeSlotLevelsWithRating> normalizeRows(List<CompositionTimeSlotLevelsWithRating> levelsWithRating) {

        List<CompositionTimeSlotLevelsWithRating> deviations = calculateDeviations(levelsWithRating);

        double lowesetValue = findLowestValue(deviations);
        double highestValue = findHigestValue(deviations);

        List<CompositionTimeSlotLevelsWithRating> normalized = normalizeValues(lowesetValue, highestValue, deviations);

        return normalized;
    }

    public List<CompositionTimeSlotLevelsWithRating> normalizeValues(double lowest, double highest, List<CompositionTimeSlotLevelsWithRating> nonNormalized) {

        List<CompositionTimeSlotLevelsWithRating> normalizedList = new ArrayList<>();

        for (CompositionTimeSlotLevelsWithRating nn : nonNormalized) {
            CompositionTimeSlotLevelsWithRating normalized = new CompositionTimeSlotLevelsWithRating();

            normalized.setRating(nn.getRating());
            normalized.setId(nn.getId());
            normalized.setCompositionId(nn.getCompositionId());

            normalized.setCompHi((nn.getCompHi() < 0.0) ? nn.getCompHi() / lowest : nn.getCompHi() / highest);
            normalized.setCompHiAlt((nn.getCompHiAlt() < 0.0) ? nn.getCompHiAlt() / lowest : nn.getCompHiAlt() / highest);
            normalized.setCompMid((nn.getCompMid() < 0.0) ? nn.getCompMid() / lowest : nn.getCompMid() / highest);
            normalized.setCompMidAlt((nn.getCompMidAlt() < 0.0) ? nn.getCompMidAlt() / lowest : nn.getCompMidAlt() / highest);
            normalized.setCompLow((nn.getCompLow() < 0.0) ? nn.getCompLow() / lowest : nn.getCompLow() / highest);
            normalized.setCompLowAlt((nn.getCompLowAlt() < 0.0) ? nn.getCompLowAlt() / lowest : nn.getCompLowAlt() / highest);
            normalized.setBeat((nn.getBeat() < 0.0) ? nn.getBeat() / lowest : nn.getBeat() / highest);
            normalized.setBeatAlt((nn.getBeatAlt() < 0.0) ? nn.getBeatAlt() / lowest : nn.getBeatAlt() / highest);
            normalized.setHarmonic((nn.getHarmonic() < 0.0) ? nn.getHarmonic() / lowest : nn.getHarmonic() / highest);
            normalized.setHarmonicAlt((nn.getHarmonicAlt() < 0.0) ? nn.getHarmonicAlt() / lowest : nn.getHarmonicAlt() / highest);
            normalized.setAmbient((nn.getAmbient() < 0.0) ? nn.getAmbient() / lowest : nn.getAmbient() / highest);
            normalized.setHits((nn.getHits() < 0.0) ? nn.getHits() / lowest : nn.getHits() / highest);
            normalized.setFills((nn.getFills() < 0.0) ? nn.getFills() / lowest : nn.getFills() / highest);

            normalizedList.add(normalized);
        }

        return normalizedList;
    }

    public List<CompositionTimeSlotLevelsWithRating> calculateDeviations(List<CompositionTimeSlotLevelsWithRating> levelsWithRating) {

        List<CompositionTimeSlotLevelsWithRating> deviations = new ArrayList<>();

        for (CompositionTimeSlotLevelsWithRating level : levelsWithRating) {

            CompositionTimeSlotLevelsWithRating deviation = new CompositionTimeSlotLevelsWithRating();
            if (deviation.getLevelBeforeMixed() != null) {
                deviation.setId(level.getId());
                deviation.setCompositionId(level.getCompositionId());
                deviation.setRating(level.getRating());
                deviation.setLevelBeforeMixed(level.getLevelBeforeMixed());

                deviation.setCompHi(deviation.getLevelBeforeMixed() - level.getCompHi());
                deviation.setCompHiAlt(deviation.getLevelBeforeMixed() - level.getCompHiAlt());
                deviation.setCompMid(deviation.getLevelBeforeMixed() - level.getCompMid());
                deviation.setCompMidAlt(deviation.getLevelBeforeMixed() - level.getCompMidAlt());
                deviation.setCompHiAlt(deviation.getLevelBeforeMixed() - level.getCompMidAlt());
                deviation.setCompLow(deviation.getLevelBeforeMixed() - level.getCompLow());
                deviation.setCompLowAlt(deviation.getLevelBeforeMixed() - level.getCompLowAlt());
                deviation.setBeat(deviation.getLevelBeforeMixed() - level.getBeat());
                deviation.setBeatAlt(deviation.getLevelBeforeMixed() - level.getBeatAlt());
                deviation.setHarmonic(deviation.getLevelBeforeMixed() - level.getHarmonic());
                deviation.setHarmonicAlt(deviation.getLevelBeforeMixed() - level.getHarmonicAlt());
                deviation.setAmbient(deviation.getLevelBeforeMixed() - level.getAmbient());
                deviation.setFills(deviation.getLevelBeforeMixed() - level.getFills());
                deviation.setHits(deviation.getLevelBeforeMixed() - level.getHits());

                deviations.add(deviation);
            }
        }

        return deviations;
    }

    public double findLowestValue(List<CompositionTimeSlotLevelsWithRating> levelsWithRating) {

        double lowestValue = 0.0;

        for (CompositionTimeSlotLevelsWithRating ts : levelsWithRating) {

            if (ts.getCompHi() < lowestValue) {
                lowestValue = ts.getCompHi();
            }

            if (ts.getCompHiAlt() < lowestValue) {
                lowestValue = ts.getCompHiAlt();
            }

            if (ts.getCompMid() < lowestValue) {
                lowestValue = ts.getCompMid();
            }

            if (ts.getCompMidAlt() < lowestValue) {
                lowestValue = ts.getCompMidAlt();
            }

            if (ts.getCompLow() < lowestValue) {
                lowestValue = ts.getCompLow();
            }

            if (ts.getCompLowAlt() < lowestValue) {
                lowestValue = ts.getCompLowAlt();
            }

            if (ts.getBeat() < lowestValue) {
                lowestValue = ts.getBeat();
            }

            if (ts.getBeatAlt() < lowestValue) {
                lowestValue = ts.getBeatAlt();
            }

            if (ts.getHarmonic() < lowestValue) {
                lowestValue = ts.getHarmonic();
            }

            if (ts.getHarmonicAlt() < lowestValue) {
                lowestValue = ts.getHarmonicAlt();
            }

            if (ts.getAmbient() < lowestValue) {
                lowestValue = ts.getAmbient();
            }

            if (ts.getFills() < lowestValue) {
                lowestValue = ts.getFills();
            }
        }

        return lowestValue;
    }

    public double findHigestValue(List<CompositionTimeSlotLevelsWithRating> levelsWithRating) {

        double highestValue = 0.0;

        for (CompositionTimeSlotLevelsWithRating ts : levelsWithRating) {

            if (ts.getCompHi() > highestValue) {
                highestValue = ts.getCompHi();
            }

            if (ts.getCompHiAlt() > highestValue) {
                highestValue = ts.getCompHiAlt();
            }

            if (ts.getCompMid() > highestValue) {
                highestValue = ts.getCompMid();
            }

            if (ts.getCompMidAlt() > highestValue) {
                highestValue = ts.getCompMidAlt();
            }

            if (ts.getCompLow() > highestValue) {
                highestValue = ts.getCompLow();
            }

            if (ts.getCompLowAlt() > highestValue) {
                highestValue = ts.getCompLowAlt();
            }

            if (ts.getBeat() > highestValue) {
                highestValue = ts.getBeat();
            }

            if (ts.getBeatAlt() > highestValue) {
                highestValue = ts.getBeatAlt();
            }

            if (ts.getHarmonic() > highestValue) {
                highestValue = ts.getHarmonic();
            }

            if (ts.getHarmonicAlt() > highestValue) {
                highestValue = ts.getHarmonicAlt();
            }

            if (ts.getAmbient() > highestValue) {
                highestValue = ts.getAmbient();
            }

            if (ts.getFills() > highestValue) {
                highestValue = ts.getFills();
            }
        }

        return highestValue;
    }
}

