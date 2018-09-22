package com.treblemaker.controllers.data;

import com.treblemaker.SpringConfiguration;
import com.treblemaker.controllers.data.HarmonicAltController;
import com.treblemaker.dal.interfaces.IAnalyticsVerticalCustomDal;
import com.treblemaker.dal.interfaces.IBeatLoopsDal;
import com.treblemaker.dal.interfaces.IHarmonicLoopsDal;
import com.treblemaker.dal.interfaces.ISoftSynthsDal;
import com.treblemaker.model.BeatLoop;
import com.treblemaker.model.HarmonicLoop;
import com.treblemaker.model.SoftSynths;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.StringWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class HarmonicAltControllerTest {

    private HarmonicAltController harmonicAltController;
    private IAnalyticsVerticalCustomDal analyticsVerticalCustomDalMock;
    private IBeatLoopsDal beatLoopsDalMock;
    private IHarmonicLoopsDal harmonicLoopsDalMock;
    private ISoftSynthsDal softSynthsDalMock;

    @Before
    public void setup() {

        analyticsVerticalCustomDalMock = mock(IAnalyticsVerticalCustomDal.class);
        beatLoopsDalMock = mock(IBeatLoopsDal.class);
        harmonicLoopsDalMock = mock(IHarmonicLoopsDal.class);
        softSynthsDalMock = mock(ISoftSynthsDal.class);

        BeatLoop beatLoopOne = new BeatLoop();
        beatLoopOne.setId(1);
        BeatLoop beatLoopTwo = new BeatLoop();
        beatLoopTwo.setId(2);
        BeatLoop beatLoopThree = new BeatLoop();
        beatLoopThree.setId(3);

        HarmonicLoop harmonicLoopOne = new HarmonicLoop();
        harmonicLoopOne.setId(1);
        HarmonicLoop harmonicLoopTwo = new HarmonicLoop();
        harmonicLoopTwo.setId(2);
        HarmonicLoop harmonicLoopThree = new HarmonicLoop();
        harmonicLoopThree.setId(3);

        SoftSynths synthOne = new SoftSynths();
        synthOne.setId(1);
        SoftSynths synthTwo = new SoftSynths();
        synthTwo.setId(2);
        SoftSynths synthThree = new SoftSynths();
        synthThree.setId(3);

        when(beatLoopsDalMock.findAll()).thenReturn(Arrays.asList(beatLoopOne, beatLoopTwo, beatLoopThree));
        when(harmonicLoopsDalMock.findAll()).thenReturn(Arrays.asList(harmonicLoopOne, harmonicLoopTwo, harmonicLoopThree));
        when(softSynthsDalMock.findAll()).thenReturn(Arrays.asList(synthOne,synthTwo,synthThree));

        harmonicAltController = new HarmonicAltController(analyticsVerticalCustomDalMock, beatLoopsDalMock, harmonicLoopsDalMock, softSynthsDalMock);
    }

    @Test
    public void shouldCreateCorrectHeaders() {

        List<String> csvHeaders = harmonicAltController.createCSVHeaders();

        assert(csvHeaders.get(0)).equals("rating");
        assert(csvHeaders.get(1)).equals("bl_1");
        assert(csvHeaders.get(2)).equals("bl_2");
        assert(csvHeaders.get(3)).equals("bl_3");
        assert(csvHeaders.get(4)).equals("hl_1");
        assert(csvHeaders.get(5)).equals("hl_2");
        assert(csvHeaders.get(6)).equals("hl_3");
        assert(csvHeaders.get(7)).equals("s_1");
        assert(csvHeaders.get(8)).equals("s_2");
        assert(csvHeaders.get(9)).equals("s_3");
    }

    @Test
    public void shouldAddCorrectHeaders(){

        StringWriter sw = new StringWriter();

        List<String> csvHeaders = Arrays.asList("0","1","2","3");

        sw = harmonicAltController.addCsvHeader(sw, csvHeaders);

        String outPut = sw.toString();

        Assert.assertEquals("0,1,2,3\n", outPut);
    }

    @Test
    public void shouldCreateCorrectRow() throws SQLException {

        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getInt("rating")).thenReturn(3);

        when(resultSetMock.getInt("beat_loop_id")).thenReturn(2);

        when(resultSetMock.getInt("harmonic_loop_id")).thenReturn(2);
        when(resultSetMock.getInt("harmonic_loop_alt_id")).thenReturn(3);

        when(resultSetMock.getInt("synth_template_hi_id")).thenReturn(1);
        when(resultSetMock.getInt("synth_template_mid_id")).thenReturn(2);
        when(resultSetMock.getInt("synth_template_low_id")).thenReturn(3);


        StringWriter sw = new StringWriter();
        sw = harmonicAltController.appendCSVRow(sw, resultSetMock);

        String outPut = sw.toString();

        Assert.assertEquals("3,0,1,0,0,1,1,1,1,1\n", outPut);
    }

    @Test
    public void shouldCreateCorrectPredictRow() throws SQLException {

        ResultSet resultSetMock = mock(ResultSet.class);

        when(resultSetMock.getInt("rating")).thenReturn(3);

        when(resultSetMock.getInt("beat_loop_id")).thenReturn(2);

        when(resultSetMock.getInt("harmonic_loop_id")).thenReturn(2);
        when(resultSetMock.getInt("harmonic_loop_alt_id")).thenReturn(3);

        when(resultSetMock.getInt("synth_template_hi_id")).thenReturn(1);
        when(resultSetMock.getInt("synth_template_mid_id")).thenReturn(2);
        when(resultSetMock.getInt("synth_template_low_id")).thenReturn(3);

        float[] actualResult = harmonicAltController.createPrdectionRow(
                resultSetMock.getInt("beat_loop_id"),
                resultSetMock.getInt("harmonic_loop_id"),
                resultSetMock.getInt("harmonic_loop_alt_id"),
                resultSetMock.getInt("synth_template_hi_id"),
                resultSetMock.getInt("synth_template_mid_id"),
                resultSetMock.getInt("synth_template_low_id"));

        float[] expectedResult = new float[]{0,1,0,0,1,1,1,1,1};

        assertThat(actualResult).isEqualTo(expectedResult);
    }
}

