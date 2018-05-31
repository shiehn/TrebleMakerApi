package com.treblemaker.tests;

import com.treblemaker.controllers.classify.utils.ClassificationUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.cpu.nativecpu.NDArray;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
public class ClassificationUtilsTest {

    @Test
    public void shouldRateBad(){
        INDArray weights = new NDArray(new float[]{0.34f, 0f, 0.1f});
        assertThat(ClassificationUtils.extractRating(weights)).isEqualTo(0);
    }

    @Test
    public void shouldRateOk(){
        INDArray weights = new NDArray(new float[]{0.5f, 1.0f, 0.001f});
        assertThat(ClassificationUtils.extractRating(weights)).isEqualTo(1);
    }

    @Test
    public void shouldRateGood(){
        INDArray weights = new NDArray(new float[]{0.3f, 0.9f, 0.91f});
        assertThat(ClassificationUtils.extractRating(weights)).isEqualTo(2);
    }
}