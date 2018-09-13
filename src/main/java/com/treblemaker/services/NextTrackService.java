package com.treblemaker.services;

import com.treblemaker.configs.AppConfigs;
import com.treblemaker.dal.interfaces.ISentimentCompositionDal;
import com.treblemaker.dal.interfaces.IStationDal;
import com.treblemaker.model.sentiment.SentimentComposition;
import com.treblemaker.model.stations.Station;
import com.treblemaker.model.stations.StationTrack;
import com.treblemaker.services.model.NextTrackResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NextTrackService {

    @Autowired
    private AppConfigs appConfigs;

    @Value("${s3.bucket}")
    private String S3_BUCKET;

    private IStationDal stationDal;
    private ISentimentCompositionDal sentimentCompositionDal;

    List<String> emojis = new ArrayList<>();
    List<String> urls = new ArrayList<>();

    @Autowired
    public NextTrackService(IStationDal stationDal, ISentimentCompositionDal sentimentCompositionDal) {
        this.stationDal = stationDal;
        this.sentimentCompositionDal = sentimentCompositionDal;

        emojis.add("ambient");
        emojis.add("bass");
        emojis.add("bright");
        emojis.add("dark");
        emojis.add("drums");
        emojis.add("fast");
        emojis.add("slow");
    }

    public List<String> getNextTracks(int stationId, int currentTrackId) {

        Station station = stationDal.findById(stationId).get();

        List<StationTrack> stationTracks = new ArrayList<>();
        stationTracks.addAll(station.getStationTracks());

        List<StationTrack> uploadedTracks = stationTracks.stream().filter(stationTrack -> stationTrack.getUploaded() == 1).collect(Collectors.toList());

        //TODO THIS WILL RETURN A RANDOM TRACK FOR THE TIME BEING ..
        //TODO THIS WILL RETURN A RANDOM TRACK FOR THE TIME BEING ..
        //TODO THIS WILL RETURN A RANDOM TRACK FOR THE TIME BEING ..
        //TODO THIS WILL RETURN A RANDOM TRACK FOR THE TIME BEING ..

        Collections.shuffle(uploadedTracks);

        List<String> ouput = new ArrayList<>();

        for (StationTrack track : uploadedTracks) {
            String trackUrl = S3_BUCKET + track.getFile() + ".mp3";
            ouput.add(trackUrl);
        }
        return ouput;
    }


    public NextTrackResult getNextTrack(int stationId) {

        NextTrackResult nextTrackResult = new NextTrackResult();

        //MAP SENTIMENT_COMPOSITION TO NEXTTRACKRESULT
        SentimentComposition sentimentComposition = getSentimentComposition(stationId);




        return nextTrackResult;
    }

    public SentimentComposition getSentimentComposition(int stationId){
        Station station = stationDal.findById(stationId).get();
        List<StationTrack> uploadedTracks = station.getStationTracks().stream().filter(stationTrack -> stationTrack.getUploaded() == 1
                && stationTrack.getAddToStation() == 1
                && stationTrack.getNumOfVersions() > 1
                && stationTrack.getNumOfVersionVariations() > 1
        ).collect(Collectors.toList());

        List<SentimentComposition> sentimentCompositions = sentimentCompositionDal.findAll();

        List<SentimentComposition> tracksWithSentiment = new ArrayList<>();

        for (StationTrack stationTrack : uploadedTracks) {

            Optional<SentimentComposition> sentimentCompositionOptional = sentimentCompositions.stream().filter(sc ->
                    sc.getCompositionId().equalsIgnoreCase(stationTrack.getFile())).findFirst();

            if(sentimentCompositionOptional.isPresent()) {
                tracksWithSentiment.add(sentimentCompositionOptional.get());
            }
        }

        return tracksWithSentiment.get(new Random().nextInt(tracksWithSentiment.size()));
    }
}
