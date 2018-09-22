package com.treblemaker.controllers;

import com.treblemaker.SpringConfiguration;
import com.treblemaker.controllers.interfaces.IFillRatingDal;
import com.treblemaker.controllers.interfaces.IHitRatingDal;
import com.treblemaker.dal.interfaces.*;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.hitsandfills.FillRating;
import com.treblemaker.model.hitsandfills.HitRating;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@ComponentScan({"com.treblemaker"})
@SpringBootTest(classes = SpringConfiguration.class)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class AnalyticsControllerTest {

    @Autowired
    private AnalyticsController analyticsController;

    @Autowired
    private IHitRatingDal hitRatingDal;

    @Autowired
    private IFillRatingDal fillRatingDal;

    private ICompositionDal compositionDal;

    private final int COMP_ID = 1;
    private final String COMP_UID = "aaaa";
    private final int HIT_ID = 9999;
    private final int FILL_ID = 8888;
    private final int RATING = 2;

    @Before
    public void setup() {

        compositionDal = mock(ICompositionDal.class);

        CompositionTimeSlot slotA = new CompositionTimeSlot();
        slotA.setFillId(FILL_ID);
        CompositionTimeSlot slotB = new CompositionTimeSlot();
        slotB.setHitId(HIT_ID);

        Composition composition = new Composition();
        composition.setId(COMP_ID);
        composition.setCompositionUid(COMP_UID);
        composition.setCompositionTimeSlots(Arrays.asList(slotA, slotB));
        when(compositionDal.findByCompositionUid(anyString())).thenReturn(composition);

        analyticsController = new AnalyticsController(compositionDal, fillRatingDal, hitRatingDal);
    }

    @Test
    public void shouldRecordHitsAnalytics() {

        analyticsController.recordHitsAnalytics(COMP_UID, RATING);

        Iterable<HitRating> hitRatings = hitRatingDal.findAll();

        List<HitRating> targets = new ArrayList<>();
        for (HitRating hit : hitRatings) {
            if (hit.getCompositionId().equals(COMP_ID)) {
                targets.add(hit);
            }
        }

        assertThat(targets).hasSize(1);
        assertThat(targets.get(0).getRating()).isEqualTo(RATING);

        hitRatingDal.delete(targets.get(0));
    }

    @Test
    public void shouldRecordFillsAnalytics() {

        analyticsController.recordFillsAnalytics(COMP_UID, RATING);

        Iterable<FillRating> fillRatings = fillRatingDal.findAll();

        List<FillRating> targets = new ArrayList<>();
        for (FillRating fill : fillRatings) {
            if (fill.getCompositionId().equals(COMP_ID)) {
                targets.add(fill);
            }
        }

        assertThat(targets).hasSize(1);
        assertThat(targets.get(0).getRating()).isEqualTo(RATING);

        fillRatingDal.delete(targets.get(0));
    }
}

