package com.treblemaker.controllers.data;

import com.treblemaker.controllers.data.VolumeController;
import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.composition.CompositionTimeSlotLevels;
import com.treblemaker.model.composition.CompositionTimeSlotLevelsWithRating;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class VolumeControllerTest {

    private List<AnalyticsVertical> analyticsVerticals; // = DALCache.getInstance().getAnalyticsVertical();
    private List<CompositionTimeSlot> compositionTimeSlots; // = DALCache.getInstance().getCompositionTimeSlots();
    private List<CompositionTimeSlotLevels> timeSlotLevels;
    private List<CompositionTimeSlotLevelsWithRating> compositionTimeSlotLevelsWithRatings;

    private VolumeController volumeController;

    @Before
    public void setup() {

        AnalyticsVertical analyticsVerticalOne = new AnalyticsVertical();
        analyticsVerticalOne.setId(1);
        analyticsVerticalOne.setTimeSlotId(11);
        analyticsVerticalOne.setLevelsRating(0);

        AnalyticsVertical analyticsVerticalTwo = new AnalyticsVertical();
        analyticsVerticalTwo.setId(2);
        analyticsVerticalTwo.setTimeSlotId(22);
        analyticsVerticalTwo.setLevelsRating(1);

        analyticsVerticals = new ArrayList<>();
        analyticsVerticals.add(analyticsVerticalOne);
        analyticsVerticals.add(analyticsVerticalTwo);


        CompositionTimeSlot compositionTimeSlotOne = new CompositionTimeSlot();
        compositionTimeSlotOne.setId(11);
        compositionTimeSlotOne.setCompositionId(111);

        CompositionTimeSlot compositionTimeSlotTwo = new CompositionTimeSlot();
        compositionTimeSlotTwo.setId(22);
        compositionTimeSlotTwo.setCompositionId(111);

        compositionTimeSlots = new ArrayList<>();
        compositionTimeSlots.add(compositionTimeSlotOne);
        compositionTimeSlots.add(compositionTimeSlotTwo);


        CompositionTimeSlotLevels levelsOne = new CompositionTimeSlotLevels();
        levelsOne.setId(1111);
        levelsOne.setCompositionId(111);
        levelsOne.setLevelBeforeMixed(15.0);
        levelsOne.setCompHi(25.0);
        levelsOne.setCompHiAlt(-3.0);
        levelsOne.setCompMid(-33.56);
        levelsOne.setCompMidAlt(-5.3);
        levelsOne.setCompLow(6.6);
        levelsOne.setCompLowAlt(2.1);
        levelsOne.setAmbient(7.1);
        levelsOne.setBeat(22.2);
        levelsOne.setBeatAlt(21.1);
        levelsOne.setHarmonic(12.2);
        levelsOne.setHarmonicAlt(10.9);
        levelsOne.setHits(3.2);
        levelsOne.setFills(7.222);

        CompositionTimeSlotLevels levelsTwo = new CompositionTimeSlotLevels();
        levelsTwo.setId(2222);
        levelsTwo.setCompositionId(111);
        levelsTwo.setLevelBeforeMixed(15.0);
        levelsTwo.setCompHi(24.0);
        levelsTwo.setCompHiAlt(-13.0);
        levelsTwo.setCompMid(-4.56);
        levelsTwo.setCompMidAlt(-6.3);
        levelsTwo.setCompLow(1.6);
        levelsTwo.setCompLowAlt(8.1);
        levelsTwo.setAmbient(9.1);
        levelsTwo.setBeat(12.2);
        levelsTwo.setBeatAlt(21.1);
        levelsTwo.setHarmonic(11.2);
        levelsTwo.setHarmonicAlt(16.9);
        levelsTwo.setHits(4.2);
        levelsTwo.setFills(8.222);

        timeSlotLevels = new ArrayList<>();
        timeSlotLevels.add(levelsOne);
        timeSlotLevels.add(levelsTwo);


        CompositionTimeSlotLevelsWithRating levelsWithRatingOne = new CompositionTimeSlotLevelsWithRating(levelsOne);
        levelsWithRatingOne.setRating(0);

        CompositionTimeSlotLevelsWithRating levelsWithRatingTwo = new CompositionTimeSlotLevelsWithRating(levelsTwo);
        levelsWithRatingTwo.setRating(0);

        compositionTimeSlotLevelsWithRatings = new ArrayList<>();
        compositionTimeSlotLevelsWithRatings.add(levelsWithRatingOne);
        compositionTimeSlotLevelsWithRatings.add(levelsWithRatingTwo);

        volumeController = new VolumeController(analyticsVerticals, compositionTimeSlots, timeSlotLevels);
    }

    @Test
    public void shouldFindLowestValues() {

        double expectedValue = -33.56;
        double actualValue = volumeController.findLowestValue(compositionTimeSlotLevelsWithRatings);

        assertThat(actualValue).isEqualTo(actualValue);
    }

    @Test
    public void shouldFindHighestValues() {

        double expectedValue = 25.0;
        double actualValue = volumeController.findHigestValue(compositionTimeSlotLevelsWithRatings);

        assertThat(actualValue).isEqualTo(actualValue);
    }

    @Test
    public void normalizedDataIsWithinNegativeAndPositiveOne() {

        List<CompositionTimeSlotLevelsWithRating> normalizedLevels = volumeController.normalizeRows(this.compositionTimeSlotLevelsWithRatings);

        for (CompositionTimeSlotLevelsWithRating level : normalizedLevels) {

            assertThat(level.getCompHi()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getCompHiAlt()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getCompMid()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getCompMidAlt()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getCompLow()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getCompLowAlt()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getBeat()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getBeatAlt()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getHarmonic()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getHarmonicAlt()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getAmbient()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getFills()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
            assertThat(level.getHits()).isGreaterThanOrEqualTo(-1.0).isLessThanOrEqualTo(1.0);
        }
    }
}
