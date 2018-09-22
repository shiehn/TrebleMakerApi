package com.treblemaker.controllers;

import com.treblemaker.model.sentiment.SentimentAndUrls;
import com.treblemaker.model.sentiment.SentimentComposition;
import com.treblemaker.services.NextTrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class StationTrackController {

    @Autowired
    private NextTrackService nextTrackService;

//    @RequestMapping(value = "/api/stationtrack/next/{stationId}", method = RequestMethod.GET)
//    public @ResponseBody List<String> getNextTrack(@PathVariable("stationId") Integer stationId){
//
//        return nextTrackService.getNextTracks(stationId, 0);
//    }

    @RequestMapping(value = "/api/stationtrack/next/{stationId}", method = RequestMethod.GET)
    public @ResponseBody SentimentAndUrls getNextTrack(@PathVariable("stationId") Integer stationId) {

        SentimentComposition sentimentComposition = nextTrackService.getSentimentComposition(stationId);
        SentimentAndUrls sentimentAndUrls = new SentimentAndUrls(sentimentComposition.getSentimentSections());

        return sentimentAndUrls;
    }
}