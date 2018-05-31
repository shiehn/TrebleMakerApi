package com.treblemaker.tests;

import com.treblemaker.services.NextTrackService;

public class NextTrackServiceTests {

    NextTrackService nextTrackService;

    String TRACK_NAME = "6fe0b2ce-67ca-425a-b2e9-5af50e4d0366";

//    @Before
//    public void setup() {
//
//        StationTrack stationTrackOne = new StationTrack();
//        stationTrackOne.setUploaded(0);
//        StationTrack stationTrackTwo = new StationTrack();
//        stationTrackTwo.setUploaded(1);
//        stationTrackTwo.setFile(TRACK_NAME);
//        stationTrackTwo.setNumOfVersions(2);
//        stationTrackTwo.setNumOfVersionVariations(3);
//
//        Set<StationTrack> tracks = new HashSet<>();
//        tracks.add(stationTrackOne);
//        tracks.add(stationTrackTwo);
//
//        Station station = new Station();
//        station.setId(1);
//        station.setStationTracks(tracks);
//
//        IStationDal stationDal = mock(IStationDal.class);
//        when(stationDal.findOne(1)).thenReturn(station);
//
//        nextTrackService = new NextTrackService(stationDal);
//    }
//
//    @Test
//    public void should_createCorrectResult() throws JsonProcessingException {
//
//        TrackResult oneOne = new TrackResult();
//        oneOne.setUrl(Constants.S3_BUCKET + TRACK_NAME + "_0_1.mp3");
//        oneOne.setEmojis(Arrays.asList("aggressive", "calm"));
//
//        TrackResult oneTwo = new TrackResult();
//        oneTwo.setUrl(Constants.S3_BUCKET + TRACK_NAME + "_0_2.mp3");
//        oneTwo.setEmojis(Arrays.asList("aggressive", "calm"));
//
//        TrackResult oneThree = new TrackResult();
//        oneThree.setUrl(Constants.S3_BUCKET + TRACK_NAME + "_0_3.mp3");
//        oneThree.setEmojis(Arrays.asList("aggressive", "calm"));
//
//        TrackResult twoOne = new TrackResult();
//        twoOne.setUrl(Constants.S3_BUCKET + TRACK_NAME + "_1_1.mp3");
//        twoOne.setEmojis(Arrays.asList("aggressive", "calm"));
//
//        TrackResult twoTwo = new TrackResult();
//        twoTwo.setUrl(Constants.S3_BUCKET + TRACK_NAME + "_1_2.mp3");
//        twoTwo.setEmojis(Arrays.asList("aggressive", "calm"));
//
//        TrackResult twoThree = new TrackResult();
//        twoThree.setUrl(Constants.S3_BUCKET + TRACK_NAME + "_1_3.mp3");
//        twoThree.setEmojis(Arrays.asList("aggressive", "calm"));
//
//        NextTrackResult nextTrackResult = new NextTrackResult();
//        nextTrackResult.setTrackResults(Arrays.asList(oneOne, oneTwo, oneThree, twoOne, twoTwo, twoThree));
//
//        String expected = mapper.writeValueAsString(nextTrackResult);
//
//        NextTrackResult result = nextTrackService.getNextTrack(1);
//
//        String actual = mapper.writeValueAsString(result);
//
//        assertThat(expected).isEqualToIgnoringCase(actual);
//    }
}
