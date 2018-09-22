package com.treblemaker.controllers.data;

import com.treblemaker.SpringConfiguration;
import com.treblemaker.controllers.data.NgramsController;
import com.treblemaker.dal.interfaces.*;
import com.treblemaker.model.analytics.AnalyticsHorizontal;
import com.treblemaker.model.composition.CompositionTimeSlot;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@ComponentScan({"com.treblemaker"})
@SpringBootTest(classes = SpringConfiguration.class)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class NgramsControllerTest {

//    @Autowired
//    public IChordSequencesDal chordSequencesDal;


//    @Autowired
//    public IChordSequenceDal chordSequenceDal;
//
//    @Autowired
//    public IGenreDal genreDal;

//    @Test
//    public void insert(){
//
//        ChordSequence chordSequence = new ChordSequence();
////        chordSequence.setKey("F");
//        chordSequence.setTimeSignature("4_4");
//        chordSequence.setSongName("FUCK");
//
//        Genre genre = new Genre();
//        genre.setId(3);
//
//        List<Genre> genres = genreDal.findAll();
//
//        chordSequence.setGenre(genres.get(0));
//
//        chordSequenceDal.save(chordSequence);
//
//        System.out.println("ID : " + chordSequence.getId());
//
////        HiveChord chordA = new HiveChord();
////        chordA.setId(419);
////
////        HiveChord chordB = new HiveChord();
////        chordB.setId(464);
////
////        HiveChord chordC = new HiveChord();
////        chordC.setId(494);
////
////        ChordSequences chordSequences = new ChordSequences();
////        chordSequences.setSongName("test");
////        chordSequences.setTimeSignature("4/4");
////        chordSequences.setHarmonicKey("F");
////
////        Genre genre = new Genre();
////        genre.setId(1);
////
////        chordSequences.setGenre(genre);
////
////        chordSequences.setChords(Arrays.asList(chordA, chordB, chordC));
////
////        chordSequencesDal.save(chordSequences);
//    }



    @Mock
    private NgramsController ngramsController;

    @Autowired
    private IAnalyticsHorizontalDal analyticsHorizontalDal;

    @Autowired
    private InGramsDal nGramsDal;

    boolean saveHarmonicWasCalled = false;
    boolean saveRhythmicWasCalled = false;
    boolean saveFillWasCalled = false;
    boolean saveAmbienceWasCalled = false;

    public void initMocks(){
        MockitoAnnotations.initMocks(this);

        OngoingStubbing<Boolean> mock = Mockito.when(ngramsController.saveNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class))).thenCallRealMethod();

        saveHarmonicWasCalled = false;
        saveRhythmicWasCalled = false;
        saveFillWasCalled = false;
        saveAmbienceWasCalled = false;
    }

    @Test
    public void shouldSaveHarmonicLoops(){

        initMocks();

        Mockito.when(ngramsController.saveHarmonicNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveHarmonicWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_harmonic(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();
        CompositionTimeSlot ctsThree = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);
        timeSlots.add(ctsThree);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertTrue(saveHarmonicWasCalled);
        Assert.assertFalse(saveRhythmicWasCalled);
        Assert.assertFalse(saveFillWasCalled);
        Assert.assertFalse(saveAmbienceWasCalled);
    }

    @Test
    public void shouldNotSaveHarmonicLoops(){

        initMocks();

        Mockito.when(ngramsController.saveHarmonicNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveHarmonicWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_harmonic(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertFalse(saveHarmonicWasCalled);
        Assert.assertFalse(saveRhythmicWasCalled);
        Assert.assertFalse(saveFillWasCalled);
        Assert.assertFalse(saveAmbienceWasCalled);
    }

    @Test
    public void shouldSaveRythmicLoops(){

        initMocks();

        Mockito.when(ngramsController.saveRythmicNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveRhythmicWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_rhythmic(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();
        CompositionTimeSlot ctsThree = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);
        timeSlots.add(ctsThree);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertFalse(saveHarmonicWasCalled);
        Assert.assertTrue(saveRhythmicWasCalled);
        Assert.assertFalse(saveFillWasCalled);
        Assert.assertFalse(saveAmbienceWasCalled);
    }

    @Test
    public void shouldNotSaveRythmicLoops(){

        initMocks();

        Mockito.when(ngramsController.saveRythmicNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveRhythmicWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_rhythmic(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertFalse(saveHarmonicWasCalled);
        Assert.assertFalse(saveRhythmicWasCalled);
        Assert.assertFalse(saveFillWasCalled);
        Assert.assertFalse(saveAmbienceWasCalled);
    }


    @Test
    public void shouldSaveAmbienceLoops(){

        initMocks();

        Mockito.when(ngramsController.saveAmbienceNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveAmbienceWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_ambience(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();
        CompositionTimeSlot ctsThree = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);
        timeSlots.add(ctsThree);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertFalse(saveHarmonicWasCalled);
        Assert.assertFalse(saveRhythmicWasCalled);
        Assert.assertFalse(saveFillWasCalled);
        Assert.assertTrue(saveAmbienceWasCalled);
    }

    @Test
    public void shouldNotSaveAmbienceLoops(){

        initMocks();

        Mockito.when(ngramsController.saveAmbienceNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveAmbienceWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_ambience(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertFalse(saveHarmonicWasCalled);
        Assert.assertFalse(saveRhythmicWasCalled);
        Assert.assertFalse(saveFillWasCalled);
        Assert.assertFalse(saveAmbienceWasCalled);
    }

    @Test
    public void shouldSaveFillLoops(){

        initMocks();

        Mockito.when(ngramsController.saveFillNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveFillWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_fill(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();
        CompositionTimeSlot ctsThree = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);
        timeSlots.add(ctsThree);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertFalse(saveHarmonicWasCalled);
        Assert.assertFalse(saveRhythmicWasCalled);
        Assert.assertTrue(saveFillWasCalled);
        Assert.assertFalse(saveAmbienceWasCalled);
    }

    @Test
    public void shouldNotSaveFillLoops(){

        initMocks();

        Mockito.when(ngramsController.saveFillNgrams(Matchers.any(AnalyticsHorizontal.class), Matchers.anyListOf(CompositionTimeSlot.class), Matchers.anyInt())).thenAnswer(invocation -> {
            saveFillWasCalled = true;
            return null;
        });

        AnalyticsHorizontal analyticsHorizontal = new AnalyticsHorizontal();
        analyticsHorizontal.setCategory_ambience(true);

        CompositionTimeSlot ctsOne = new CompositionTimeSlot();
        CompositionTimeSlot ctsTwo = new CompositionTimeSlot();

        List<CompositionTimeSlot> timeSlots = new ArrayList<>();
        timeSlots.add(ctsOne);
        timeSlots.add(ctsTwo);

        ngramsController.saveNgrams(analyticsHorizontal, timeSlots);

        Assert.assertFalse(saveHarmonicWasCalled);
        Assert.assertFalse(saveRhythmicWasCalled);
        Assert.assertFalse(saveFillWasCalled);
        Assert.assertFalse(saveAmbienceWasCalled);
    }
}
