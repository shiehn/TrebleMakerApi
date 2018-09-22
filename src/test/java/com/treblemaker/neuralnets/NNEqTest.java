package com.treblemaker.neuralnets;

import com.treblemaker.SpringConfiguration;
import com.treblemaker.model.IParametricEq;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ComponentScan({"com.treblemaker"})
@SpringBootTest(classes = SpringConfiguration.class)
@TestPropertySource(
        locations = "classpath:application-test.properties")
public class NNEqTest {

    @Autowired
    NNEq nnEq;

    @Ignore
    @Test
    public void shouldCreateFreq20Csv() throws InterruptedException, IOException, URISyntaxException {

        nnEq.trainNetWork(25, 25);

        File freq20 = new File(IParametricEq.EqBand.FREQ_20.toString() + ".csv");
        File freq250 = new File(IParametricEq.EqBand.FREQ_250.toString() + ".csv");
        File freq800 = new File(IParametricEq.EqBand.FREQ_800.toString() + ".csv");
        File freq16000 = new File(IParametricEq.EqBand.FREQ_16000.toString() + ".csv");

        assertThat(freq20).exists();
        assertThat(freq250).exists();
        assertThat(freq800).exists();
        assertThat(freq16000).exists();
    }
}
