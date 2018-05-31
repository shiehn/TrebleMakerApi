package com.treblemaker.controllers.data;

import com.treblemaker.dal.interfaces.IAnalyticsVerticalCustomDal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.StringWriter;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;

@Controller
public class DataController {

    @Autowired
    private IAnalyticsVerticalCustomDal analyticsVerticalCustomDal;

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";

    static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

    private static String csvString;
    private static Date resultSetExpiry;

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"userDirectory.csv\"");
        try
        {
            OutputStream outputStream = response.getOutputStream();

            StringWriter sw = new StringWriter();

            //CREATE HEADER ..
            sw.write("rating");
            sw.write(COMMA_DELIMITER);
            sw.write("beat_loop_id");
            sw.write(COMMA_DELIMITER);
            sw.write("beat_loop_alt_id");
            sw.write(COMMA_DELIMITER);
            sw.write("fill_id");
            sw.write(COMMA_DELIMITER);
            sw.write("harmonic_loop_id");
            sw.write(COMMA_DELIMITER);
            sw.write("harmonic_loop_alt_id");
            sw.write(COMMA_DELIMITER);
            sw.write("ambient_loop_id");
            sw.write(COMMA_DELIMITER);
            sw.write("ambient_loop_alt_id");
            sw.write(COMMA_DELIMITER);
            sw.write("chord_id");
            sw.write(COMMA_DELIMITER);
            sw.write("synth_template_id");
            sw.write(COMMA_DELIMITER);
            sw.write("synth_template_hi_id");
            sw.write(COMMA_DELIMITER);
            sw.write("synth_template_mid_id");
            sw.write(COMMA_DELIMITER);
            sw.write("synth_template_low_id");
            sw.write(NEW_LINE_SEPARATOR);

            ResultSet resultSet = null;
            boolean updateString = false;

            if(csvString == null || csvString.equalsIgnoreCase("") || resultSetExpiry == null || (new Date()).compareTo(resultSetExpiry)>0){
                System.out.println("Date1 is after Date2");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");
                System.out.println("PULLING DATA FROM DATABASE!!!!!!");

                resultSet = analyticsVerticalCustomDal.getAnalyticsVertical();
                updateString = true;

                Calendar date = Calendar.getInstance();
                long t= date.getTimeInMillis();
                resultSetExpiry = new Date(t + (5 * ONE_MINUTE_IN_MILLIS));
            }

            if(resultSet != null){

                while (resultSet.next()) {

                    sw.write("r_" + Integer.toString(resultSet.getInt("rating")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("bl_"  + Integer.toString(resultSet.getInt("beat_loop_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("blalt_" + Integer.toString(resultSet.getInt("beat_loop_alt_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("fill_" + Integer.toString(resultSet.getInt("fill_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("hl_"  + Integer.toString(resultSet.getInt("harmonic_loop_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("hlalt_" + Integer.toString(resultSet.getInt("harmonic_loop_alt_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("al_" + Integer.toString(resultSet.getInt("ambient_loop_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("alalt_" + Integer.toString(resultSet.getInt("ambient_loop_alt_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("chord_" + Integer.toString(resultSet.getInt("chord_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("s_"  + Integer.toString(resultSet.getInt("synth_template_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("shi_" + Integer.toString(resultSet.getInt("synth_template_hi_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("smid_" + Integer.toString(resultSet.getInt("synth_template_mid_id")));
                    sw.write(COMMA_DELIMITER);
                    sw.write("slow_" + Integer.toString(resultSet.getInt("synth_template_low_id")));
                    sw.write(NEW_LINE_SEPARATOR);
                }
            }

//            sw

            if(updateString) {
                csvString = sw.toString();
            }

            outputStream.write(csvString.getBytes());
//            outputStream.write(outputResult.getBytes());
            outputStream.flush();
            outputStream.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
