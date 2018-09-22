package com.treblemaker.controllers;

import com.treblemaker.dal.interfaces.IStationDal;
import com.treblemaker.dal.interfaces.IStationTrackDal;
import com.treblemaker.model.stations.Station;
import com.treblemaker.model.stations.StationDto;
import com.treblemaker.model.stations.StationTrack;
import com.treblemaker.model.stations.StationTrackDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class StationsController {

    @Autowired
    private IStationDal stationDal;

    @Autowired
    private IStationTrackDal stationTrackDal;

    @Value("${api.version}")
    int apiVersion;

    @RequestMapping(value = "/api/track", method = RequestMethod.GET)
    public @ResponseBody
    StationTrackDto getTrack(){
        List<StationTrack> tracks = stationTrackDal.findAll();
        if(tracks == null || tracks.isEmpty()){
            StationTrack emptyStationTrack = new StationTrack();
            emptyStationTrack.setStationId(0);
            emptyStationTrack.setName("error");
            return new StationTrackDto(emptyStationTrack);
        }

        List<StationTrack> filteredTracks = tracks.stream().filter(t -> t.getUploaded() == 1 && t.getStationId() == 2 && t.getApiVersion() == apiVersion).collect(Collectors.toList());
        if(filteredTracks == null || filteredTracks.isEmpty()){
            StationTrack emptyStationTrack = new StationTrack();
            emptyStationTrack.setStationId(0);
            emptyStationTrack.setName("error");
            return new StationTrackDto(emptyStationTrack);
        }

        StationTrack selection = filteredTracks.get((new Random()).nextInt(filteredTracks.size()));
        return new StationTrackDto(selection);
    }

    @RequestMapping(value = "/api/station", method = RequestMethod.GET)
    public @ResponseBody List<StationDto> get(){
        List<Station> stations = stationDal.findAll();
        return stations.stream().map(s -> new StationDto(s)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/api/stationids/get", method = RequestMethod.GET)
    public @ResponseBody List<Integer> getIds(){
        List<Station> stations = stationDal.findAll();
        return stations.stream().map(station -> station.getId()).collect(Collectors.toList());
    }
}
