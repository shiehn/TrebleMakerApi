package com.treblemaker.tests;

import com.treblemaker.model.eq.EqWeightResponse;
import com.treblemaker.model.eq.Ratings;
import com.treblemaker.neuralnets.WeightClassificationUtils;
import com.treblemaker.weighters.enums.WeightClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.treblemaker.model.IParametricEq.EqBand;
import static org.assertj.core.api.Assertions.assertThat;

public class WeightClassificationUtilTests {

    @Test
    public void shouldFindInHiRegister() {

        Ratings rating = new Ratings();
        rating.setRating(1);
        rating.setFreq(EqBand.FREQ_16000);

        boolean inHiRegister = WeightClassificationUtils.isHiEqRegister(rating);

        assertThat(inHiRegister).isTrue();
    }

    @Test
    public void shouldFindInMidRegister() {

        Ratings rating = new Ratings();
        rating.setRating(1);
        rating.setFreq(EqBand.FREQ_800);

        boolean inMidRegister = WeightClassificationUtils.isMidEqRegister(rating);

        assertThat(inMidRegister).isTrue();
    }

    @Test
    public void shouldFindInLowRegister() {

        Ratings rating = new Ratings();
        rating.setRating(1);
        rating.setFreq(EqBand.FREQ_20);

        boolean inLowRegister = WeightClassificationUtils.isLowEqRegister(rating);

        assertThat(inLowRegister).isTrue();
    }

    private List<Ratings> generateBands() {

        List<Ratings> eqBands = new ArrayList<>();

        //LOW
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_20);
            setRating(Ratings.BAD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_25);
            setRating(Ratings.BAD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_31);
            setRating(Ratings.BAD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_40);
            setRating(Ratings.BAD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_50);
            setRating(Ratings.BAD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_63);
            setRating(Ratings.BAD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_80);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_100);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_125);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_160);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_200);
            setRating(Ratings.GOOD);
        }});

        //MID
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_250);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_315);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_400);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_500);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_630);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_800);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_1000);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_1250);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_1600);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_2000);
            setRating(Ratings.BAD);
        }});

        //HI
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_2500);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_3150);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_4000);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_5000);
            setRating(Ratings.GOOD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_6300);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_8000);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_10000);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_12500);
            setRating(Ratings.OK);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_16000);
            setRating(Ratings.BAD);
        }});
        eqBands.add(new Ratings() {{
            setFreq(EqBand.FREQ_20000);
            setRating(Ratings.BAD);
        }});

        return eqBands;
    }

    @Test
    public void shouldClassifyAsOk() {

        EqWeightResponse eqWeightResponse = new EqWeightResponse();
        eqWeightResponse.setRatings(generateBands());

        WeightClass weightClass = WeightClassificationUtils.weightHiEqRegister(eqWeightResponse);

        assertThat(weightClass).isEqualTo(WeightClass.OK);
    }

    @Test
    public void shouldClassifyAsGood() {

        EqWeightResponse eqWeightResponse = new EqWeightResponse();
        eqWeightResponse.setRatings(generateBands());

        WeightClass weightClass = WeightClassificationUtils.weightMidEqRegister(eqWeightResponse);

        assertThat(weightClass).isEqualTo(WeightClass.GOOD);
    }

    @Test
    public void shouldClassifyAsBad() {

        EqWeightResponse eqWeightResponse = new EqWeightResponse();
        eqWeightResponse.setRatings(generateBands());

        WeightClass weightClass = WeightClassificationUtils.weightLowEqRegister(eqWeightResponse);

        assertThat(weightClass).isEqualTo(WeightClass.BAD);
    }

    @Test
    public void shouldClassifyOveralOk() {

        EqWeightResponse eqWeightResponse = new EqWeightResponse();
        eqWeightResponse.setRatings(generateBands());

        WeightClass weightClass = WeightClassificationUtils.eqResponseToWeightClass(eqWeightResponse);

        assertThat(weightClass).isEqualTo(WeightClass.OK);
    }
}