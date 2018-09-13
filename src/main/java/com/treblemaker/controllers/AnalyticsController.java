package com.treblemaker.controllers;

import com.treblemaker.controllers.dto.AnalyticsTimeslot;
import com.treblemaker.controllers.dto.TimeSlot;
import com.treblemaker.controllers.interfaces.IFillRatingDal;
import com.treblemaker.controllers.interfaces.IHitRatingDal;
import com.treblemaker.dal.interfaces.*;
import com.treblemaker.model.BeatLoop;
import com.treblemaker.model.HarmonicLoop;
import com.treblemaker.model.analytics.AnalyticsHorizontal;
import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.hitsandfills.FillRating;
import com.treblemaker.model.hitsandfills.HitRating;
import com.treblemaker.model.stations.StationTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class AnalyticsController {

    @Autowired
    public AnalyticsController(ICompositionDal compositionDal,
                               IFillRatingDal fillRatingDal,
                               IHitRatingDal hitRatingDal) {
        this.compositionDal = compositionDal;
        this.fillRatingDal = fillRatingDal;
        this.hitRatingDal = hitRatingDal;
    }

    private ICompositionDal compositionDal;
    private IFillRatingDal fillRatingDal;
    private IHitRatingDal hitRatingDal;

    @Autowired
    private ICompositionTimeSlotDal compositionTimeSlotDal;

    @Autowired
    private IAnalyticsVerticalDal analyticsVerticalDal;

    @Autowired
    private IAnalyticsHorizontalDal analyticsHorizontalDal;

    @Autowired
    private IBeatLoopsDal beatLoopsDal;

    @Autowired
    private IHarmonicLoopsDal harmonicLoopsDal;

    @Autowired
    private IStationTrackDal stationTrackDal;

    private List<BeatLoop> beatLoops;
    private List<HarmonicLoop> harmonicLoops;

//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public class AnalyticsFeedback
//    {
//        public AnalyticsFeedback(){}
//
//        @JsonProperty("TimeSlots")
//        public List<TimeSlot> TimeSlots = new ArrayList<>();
//
//        @JsonProperty("TimeSlots")
//        public List<TimeSlot> getTimeSlots() {
//            return TimeSlots;
//        }
//
//        @JsonProperty("TimeSlots")
//        public void setTimeSlots(List<TimeSlot> timeSlots) {
//            this.TimeSlots = timeSlots;
//        }
//    }

    static class TimeSlotList extends ArrayList<TimeSlot> {
    }

    @RequestMapping(value = "/api/Analytics/{compositionId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<TimeSlot> progressionGet(@PathVariable("compositionId") String compositionId, HttpServletResponse response, HttpServletRequest request) {

        List<TimeSlot> timeSlots = new ArrayList<>();

        Composition composition = compositionDal.findByCompositionUid(compositionId);

        if (composition != null) {

            composition.getCompositionTimeSlots().forEach(timeSlot -> {

                TimeSlot timeSlotDto = new TimeSlot();
                timeSlotDto.setId(timeSlot.getId());
                timeSlotDto.setRating(1);
                timeSlotDto.setEqRating(1);
                timeSlotDto.setLevelsRating(1);
                timeSlotDto.setPanningRating(1);
                timeSlotDto.setArpeggioRating(1);
                timeSlotDto.setCompRating(1);
                timeSlotDto.setBasslineRating(1);
                timeSlotDto.setKickRating(1);
                timeSlotDto.setHatRating(1);
                timeSlotDto.setSnareRating(1);
                timeSlotDto.setStartTime(Integer.toString(timeSlot.getMsecStart()));

                timeSlots.add(timeSlotDto);
            });
        }

        return timeSlots;
    }

    @RequestMapping(value = "/api/Analytics/timeslots/{compositionId}", method = RequestMethod.GET)
    public
    @ResponseBody
    List<AnalyticsTimeslot> getTimeSlots(@PathVariable("compositionId") String compositionId) {

        List<AnalyticsTimeslot> analytics = new ArrayList<>();

        if (beatLoops == null) {
            beatLoops = beatLoopsDal.findAll();
        }

        if (harmonicLoops == null) {
            harmonicLoops = harmonicLoopsDal.findAll();
        }

        Composition composition = compositionDal.findByCompositionUid(compositionId);
        if (composition != null) {

            for (CompositionTimeSlot timeSlot : composition.getCompositionTimeSlots()) {

                AnalyticsTimeslot ats = new AnalyticsTimeslot();
                ats.setBeatLoopId(timeSlot.getBeatLoopId());
                ats.setBlBars(getBeatBarCount(timeSlot.getBeatLoopId(), beatLoops));
                ats.setBeatLoopAltId(timeSlot.getBeatLoopAltId());
                ats.setBlAltBars(getBeatBarCount(timeSlot.getBeatLoopAltId(), beatLoops));

                ats.setHarmonicLoopId(timeSlot.getHarmonicLoopId());
                ats.setHlBars(getHarmonicBarCount(timeSlot.getHarmonicLoopId(), harmonicLoops));
                ats.setHarmonicLoopAltId(timeSlot.getHarmonicLoopAltId());
                ats.setHlAltBars(getHarmonicBarCount(timeSlot.getHarmonicLoopAltId(), harmonicLoops));

                ats.setAmbientLoopId(timeSlot.getAmbientLoopId());

                ats.setHiSynthId(timeSlot.getSynthTemplateHiId());
                ats.setMidSynthId(timeSlot.getSynthTemplateMidId());
                ats.setLowSynthId(timeSlot.getSynthTemplateLowId());

                analytics.add(ats);
            }
            return analytics;
        }

        return null;
    }

    private Integer getBeatBarCount(Integer beatLoopId, List<BeatLoop> beatLoops) {

        for (BeatLoop beatLoop : beatLoops) {
            if (beatLoop.getId().equals(beatLoopId)) {
                return beatLoop.getBarCount();
            }
        }

        return null;
    }

    private Integer getHarmonicBarCount(Integer harmonicLoopId, List<HarmonicLoop> harmonicLoops) {

        for (HarmonicLoop harmonic : harmonicLoops) {
            if (harmonic.getId().equals(harmonicLoopId)) {
                return harmonic.getBarCount();
            }
        }

        return null;
    }

    @RequestMapping(value = "/api/analytics", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity progressionPost(@RequestBody List<TimeSlot> timeSlotList) {
        List<List<CompositionTimeSlot>> horizontalRatings = new ArrayList<>();

        boolean addHorizontalRatings = false;

        List<CompositionTimeSlot> horizontalList = new ArrayList<>();

        for (int i = 0; i < timeSlotList.size(); i++) {

            CompositionTimeSlot compositionTimeSlot = compositionTimeSlotDal.findById(timeSlotList.get(i).getId()).get();

            if(i == 0){
                Composition composition = compositionDal.findById(compositionTimeSlot.getCompositionId()).get();

                List<StationTrack> stationTracks = stationTrackDal.findAll();
                for(StationTrack stationTrack:stationTracks){
                    if(stationTrack.getFile().equalsIgnoreCase(composition.getCompositionUid())){
                        stationTrack.setSelectedMelody(timeSlotList.get(0).getSelectedMelody());
                        stationTrackDal.save(stationTrack);
                    }
                }
            }

            AnalyticsVertical analyticsVertical = new AnalyticsVertical();
            analyticsVertical.setTimeSlotId(compositionTimeSlot.getId());
            analyticsVertical.setUserId(1);
            analyticsVertical.setRating(timeSlotList.get(i).getRating());
            analyticsVertical.setEqRating(timeSlotList.get(i).getEqRating());
            analyticsVertical.setLevelsRating(timeSlotList.get(i).getLevelsRating());
            analyticsVertical.setPanningRating(timeSlotList.get(i).getPanningRating());
            analyticsVertical.setArpeggioRating(timeSlotList.get(i).getArpeggioRating());
            analyticsVertical.setCompRating(timeSlotList.get(i).getCompRating());
            analyticsVertical.setBasslineRating(timeSlotList.get(i).getBasslineRating());
            analyticsVertical.setKickRating(timeSlotList.get(i).getKickRating());
            analyticsVertical.setHatRating(timeSlotList.get(i).getHatRating());
            analyticsVertical.setSnareRating(timeSlotList.get(i).getSnareRating());

            analyticsVertical.setRated(1);

            //collect horizontalRatings
            if (timeSlotList.get(i).HorizontalStart) {
                addHorizontalRatings = true;
                horizontalList = new ArrayList<>();
            }

            if (timeSlotList.get(i).HorizontalStop) {

                horizontalList.add(compositionTimeSlot);

                AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
                analyticsHorizontal.setUser_id(1);
                analyticsHorizontal.setRating(timeSlotList.get(i).getHorizontalRating());
                analyticsHorizontal.setCategory_harmonic(timeSlotList.get(i).HorizontalCategoryHarmonic);
                analyticsHorizontal.setCategory_rhythmic(timeSlotList.get(i).HorizontalCategoryRhythmic);
                analyticsHorizontal.setCategory_ambience(timeSlotList.get(i).HorizontalCategoryAmbience);
                analyticsHorizontal.setCategory_fill(timeSlotList.get(i).HorizontalCategoryFill);
                analyticsHorizontal.setComposition_time_slots(horizontalList);
                analyticsHorizontalDal.save(analyticsHorizontal);

                addHorizontalRatings = false;
            }

            if (addHorizontalRatings) {
                horizontalList.add(compositionTimeSlot);
            }

            //TODO SHOULD THIS BE A BATCH SAVE???
                analyticsVerticalDal.save(analyticsVertical);
                compositionTimeSlot.setRated(1);
                compositionTimeSlotDal.save(compositionTimeSlot);
        }

        //SAVE HORIZONTAL ANALYTICS
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/api/analytics/fillrating/{compositionId}/{fillrating}", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean recordFillsAnalytics(@PathVariable("compositionId") String compositionId,
                                 @PathVariable("fillrating") Integer rating) {

        Composition composition = compositionDal.findByCompositionUid(compositionId);

        if (composition != null) {

            boolean alreadyExists = false;
            for (FillRating fillRating : fillRatingDal.findAll()) {
                if (fillRating.getCompositionId().equals(composition.getId())) {
                    alreadyExists = true;
                }
            }

            if (!alreadyExists) {
                Integer fillId = null;
                for (CompositionTimeSlot timeSlot : composition.getCompositionTimeSlots()) {
                    if (timeSlot.getFillId() != null) {
                        fillId = timeSlot.getFillId();
                    }
                }

                if (fillId != null) {
                    FillRating fillRating = new FillRating();
                    fillRating.setRating(rating);
                    fillRating.setCompositionId(composition.getId());
                    fillRating.setFillId(fillId);

                    fillRatingDal.save(fillRating);
                    return true;
                }
            }
        }

        return false;
    }

    @RequestMapping(value = "/api/analytics/hitrating/{compositionId}/{hitrating}", method = RequestMethod.GET)
    public
    @ResponseBody
    boolean recordHitsAnalytics(@PathVariable("compositionId") String compositionId,
                                @PathVariable("hitrating") Integer rating) {

        Composition composition = compositionDal.findByCompositionUid(compositionId);

        if (composition != null) {

            boolean alreadyExists = false;
            for (HitRating hitRating : hitRatingDal.findAll()) {
                if (hitRating.getCompositionId().equals(composition.getId())) {
                    alreadyExists = true;
                }
            }

            if (!alreadyExists) {
                Integer hitId = null;
                for (CompositionTimeSlot timeSlot : composition.getCompositionTimeSlots()) {
                    if (timeSlot.getHitId() != null) {
                        hitId = timeSlot.getHitId();
                    }
                }

                if (hitId != null) {
                    HitRating hitRating = new HitRating();
                    hitRating.setRating(rating);
                    hitRating.setCompositionId(composition.getId());
                    hitRating.setHitId(hitId);

                    hitRatingDal.save(hitRating);
                    return true;
                }

            }
        }

        return false;
    }

    @RequestMapping(value = "/api/analytics/addtostation/{compositionId}", method = RequestMethod.GET)
    public
    @ResponseBody
    String addToStation(@PathVariable("compositionId") String compositionId) {

        final int ADD_TO_STATION = 1;

        List<StationTrack> stationTracks = stationTrackDal.findAll();

        for (StationTrack stationTrack : stationTracks) {
            if (stationTrack.getFile().toLowerCase().contains(compositionId.toLowerCase())) {
                stationTrack.setAddToStation(ADD_TO_STATION);
                stationTrackDal.save(stationTrack);
                return "success";
            }
        }

        return "error";
    }
}
