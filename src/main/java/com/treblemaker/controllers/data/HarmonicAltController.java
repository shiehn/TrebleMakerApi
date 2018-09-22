package com.treblemaker.controllers.data;

import com.google.common.primitives.Floats;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.dal.interfaces.IAnalyticsVerticalCustomDal;
import com.treblemaker.dal.interfaces.IBeatLoopsDal;
import com.treblemaker.dal.interfaces.IHarmonicLoopsDal;
import com.treblemaker.dal.interfaces.ISoftSynthsDal;
import com.treblemaker.model.BeatLoop;
import com.treblemaker.model.HarmonicLoop;
import com.treblemaker.model.SoftSynths;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class HarmonicAltController {

    static final long ONE_MINUTE_IN_MILLIS = 60000;//millisecs
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    private static Date resultSetExpiry;
    private IAnalyticsVerticalCustomDal analyticsVerticalCustomDal;
    private IBeatLoopsDal beatLoopsDal;
    private IHarmonicLoopsDal harmonicLoopsDal;
    private ISoftSynthsDal softSynthsDal;

    //beat_loop_id, harmonic_loop_id, harmonic_loop_alt_id, synth_template_hi_id, synth_template_mid_id,synth_template_low_id
    private List<String> csvHeaders;
    private List<BeatLoop> beatLoops;
    private List<HarmonicLoop> harmonicLoops;
    private List<SoftSynths> softSynths;

    @Autowired
    public HarmonicAltController(IAnalyticsVerticalCustomDal analyticsVerticalCustomDal, IBeatLoopsDal beatLoopsDal, IHarmonicLoopsDal harmonicLoopsDal, ISoftSynthsDal softSynthsDal) {
        this.analyticsVerticalCustomDal = analyticsVerticalCustomDal;
        this.beatLoopsDal = beatLoopsDal;
        this.harmonicLoopsDal = harmonicLoopsDal;
        this.softSynthsDal = softSynthsDal;

        this.csvHeaders = new ArrayList<>();
        this.beatLoops = beatLoopsDal.findAll();
        this.harmonicLoops = harmonicLoopsDal.findAll();
        this.softSynths = softSynthsDal.findAll();
    }

    @RequestMapping(value = "/harmalt/inputcount", method = RequestMethod.GET)
    @ResponseBody
    public Integer getInputCount() throws InterruptedException, IOException, URISyntaxException {
        return beatLoops.size() + harmonicLoops.size() + softSynths.size();
    }

    @RequestMapping(value = "/harmalt/data", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws InterruptedException, IOException, URISyntaxException {

        System.out.println("BEAT_LOOP_COUNT=" + beatLoops.size());
        System.out.println("HARM_LOOP_COUNT=" + harmonicLoops.size());
        System.out.println("SYNTH_LOOP_COUNT=" + softSynths.size());
        System.out.println("TOTAL=" + (beatLoops.size() + harmonicLoops.size() + softSynths.size() - 1));//minus one for rating
        System.out.println("TOTAL_NO_SYNTH=" + (beatLoops.size() + harmonicLoops.size() - 1));

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");

        try {
            OutputStream outputStream = response.getOutputStream();

            StringWriter sw = new StringWriter();

//            List<String> csvHeaders = createCSVHeaders();
//            sw = addCsvHeader(sw, csvHeaders);


            //CREATE HEADER ..
//            sw.write("rating");
//            sw.write(COMMA_DELIMITER);
//            sw.write("beat_loop_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("harmonic_loop_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("harmonic_loop_alt_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("synth_template_hi_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("synth_template_mid_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("synth_template_low_id");
//            sw.write(NEW_LINE_SEPARATOR);

            ResultSet resultSet = null;
            boolean updateString = false;

            if (NNSingelton.INSTANCE.harmAltCsvString == null || NNSingelton.INSTANCE.harmAltCsvString.equalsIgnoreCase("") || resultSetExpiry == null || (new Date()).compareTo(resultSetExpiry) > 0) {
                System.out.println("Date1 is after Date2");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");

                resultSet = analyticsVerticalCustomDal.getAnalyticsVertical();
                updateString = true;

                Calendar date = Calendar.getInstance();
                long t = date.getTimeInMillis();
                resultSetExpiry = new Date(t + (5 * ONE_MINUTE_IN_MILLIS));
            }

            if (resultSet != null) {
                while (resultSet.next()) {
                    sw = appendCSVRow(sw, resultSet);
                }
            }

//            sw

            if (updateString) {
                NNSingelton.INSTANCE.harmAltCsvString = sw.toString();
            }

            outputStream.write(NNSingelton.INSTANCE.harmAltCsvString.getBytes());
//            outputStream.write(outputResult.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public List<String> createCSVHeaders() {

        csvHeaders.add("rating");

        for (BeatLoop beatLoop : beatLoops) {
            csvHeaders.add("bl_" + beatLoop.getId());
        }

        for (HarmonicLoop harmonicLoop : harmonicLoops) {
            csvHeaders.add("hl_" + harmonicLoop.getId());
        }

        for (SoftSynths synth : softSynths) {
            csvHeaders.add("s_" + synth.getId());
        }

        return csvHeaders;
    }

    public StringWriter addCsvHeader(StringWriter sw, List<String> csvHeaders) {

        for (Integer i = 0; i < csvHeaders.size(); i++) {

            sw.write(csvHeaders.get(i));

            if (i.equals(csvHeaders.size() - 1)) {
                //last one
                sw.write(NEW_LINE_SEPARATOR);
            } else {
                sw.write(COMMA_DELIMITER);
            }
        }

        return sw;
    }

    public float[] createPrdectionRow(int beat_loop_id,
                                      int harmonic_loop_id,
                                      int harmonic_loop_alt_id,
                                      int synth_template_hi_id,
                                      int synth_template_mid_id,
                                      int synth_template_low_id
    ) {

        List<Float> output = new ArrayList();

        for (BeatLoop beatLoop : beatLoops) {
            if (beatLoop.getId().equals(beat_loop_id)) {
                output.add(1f);
            } else {
                output.add(0f);
            }
        }

        for (HarmonicLoop harmonicLoop : harmonicLoops) {
            if (harmonicLoop.getId().equals(harmonic_loop_id)
                    || harmonicLoop.getId().equals(harmonic_loop_alt_id)) {
                output.add(1f);
            } else {
                output.add(0f);
            }
        }

        for (Integer i = 0; i < softSynths.size(); i++) {
            if (softSynths.get(i).getId() == synth_template_hi_id
                    || softSynths.get(i).getId() == synth_template_mid_id
                    || softSynths.get(i).getId() == synth_template_low_id) {
                output.add(1f);
            } else {
                output.add(0f);
            }
        }

        return Floats.toArray(output);
    }

    public StringWriter appendCSVRow(StringWriter sw, ResultSet resultSet) throws SQLException {

        sw.write(Integer.toString(resultSet.getInt("rating")));
        sw.write(COMMA_DELIMITER);

        //CREATE HEADER ..
//            sw.write("rating");
//            sw.write(COMMA_DELIMITER);
//            sw.write("beat_loop_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("harmonic_loop_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("harmonic_loop_alt_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("synth_template_hi_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("synth_template_mid_id");
//            sw.write(COMMA_DELIMITER);
//            sw.write("synth_template_low_id");
//            sw.write(NEW_LINE_SEPARATOR);

        for (BeatLoop beatLoop : beatLoops) {
            if (beatLoop.getId().equals(resultSet.getInt("beat_loop_id"))) {
                sw.write("1");
            } else {
                sw.write("0");
            }

            sw.write(COMMA_DELIMITER);
        }

        for (HarmonicLoop harmonicLoop : harmonicLoops) {
            if (harmonicLoop.getId().equals(resultSet.getInt("harmonic_loop_id"))
                    || harmonicLoop.getId().equals(resultSet.getInt("harmonic_loop_alt_id"))) {
                sw.write("1");
            } else {
                sw.write("0");
            }

            sw.write(COMMA_DELIMITER);
        }

        for (Integer i = 0; i < softSynths.size(); i++) {
            if (softSynths.get(i).getId() == resultSet.getInt("synth_template_hi_id")
                    || softSynths.get(i).getId() == resultSet.getInt("synth_template_mid_id")
                    || softSynths.get(i).getId() == resultSet.getInt("synth_template_low_id")) {
                sw.write("1");
            } else {
                sw.write("0");
            }

            if (i.equals(softSynths.size() - 1)) {
                sw.write(NEW_LINE_SEPARATOR);
            } else {
                sw.write(COMMA_DELIMITER);
            }
        }

        return sw;
    }


}
