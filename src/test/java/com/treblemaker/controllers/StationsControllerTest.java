package com.treblemaker.controllers;

import com.treblemaker.dal.interfaces.IStationDal;
import com.treblemaker.dal.interfaces.IStationTrackDal;
import com.treblemaker.model.stations.StationTrack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class StationsControllerTest {

    private StationsController stationsController;

    @Before
    public void setup(){
        int apiVersion = 7;

        IStationDal mockStationDal = mock(IStationDal.class);
        IStationTrackDal mockStationTrackDal = mock(IStationTrackDal.class);

        StationTrack tOne = new StationTrack();
        tOne.setId(1);
        tOne.setSelectedMelody(1);
        tOne.setFile("1");
        tOne.setUploaded(1);
        tOne.setStationId(2);
        tOne.setApiVersion(apiVersion);

        StationTrack tTwo = new StationTrack();
        tTwo.setId(2);
        tTwo.setSelectedMelody(2);
        tTwo.setFile("2");
        tTwo.setUploaded(1);
        tTwo.setStationId(2);
        tTwo.setApiVersion(apiVersion);

        StationTrack tThree = new StationTrack();
        tThree.setId(3);
        tThree.setSelectedMelody(3);
        tThree.setFile("3");
        tThree.setUploaded(1);
        tThree.setStationId(2);
        tThree.setApiVersion(apiVersion);

        StationTrack tFour = new StationTrack();
        tFour.setId(4);
        tFour.setSelectedMelody(4);
        tFour.setFile("4");
        tFour.setUploaded(1);
        tFour.setStationId(2);
        tFour.setApiVersion(apiVersion);

        List<StationTrack> tracks = Arrays.asList(tOne,tTwo,tThree,tFour);
        when(mockStationTrackDal.findAll()).thenReturn(tracks);

        stationsController = new StationsController(mockStationDal, mockStationTrackDal, apiVersion);
    }

    @Test
    public void shouldRandomizeResults(){
        int numOfIds = 0;
        int iterations = 1000;

        for(int i=0; i<iterations; i++) {
            if((stationsController.getTrack()).getName().equalsIgnoreCase("1")){
                numOfIds++;
            }
        }

        System.out.println("NUM OF IDS");
        System.out.println(numOfIds);
        assertNotEquals(numOfIds, iterations);
    }
}