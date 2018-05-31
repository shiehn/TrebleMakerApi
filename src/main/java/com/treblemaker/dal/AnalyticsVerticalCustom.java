package com.treblemaker.dal;

import com.treblemaker.dal.interfaces.IAnalyticsVerticalCustomDal;
import com.treblemaker.dal.interfaces.ISqlManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class AnalyticsVerticalCustom implements IAnalyticsVerticalCustomDal {

    @Autowired
    private ISqlManager sqlManager;

    public ResultSet getAnalyticsVertical() throws SQLException, ClassNotFoundException {

        Statement statement = sqlManager.getConnection().createStatement();

        String sqlString = "SELECT analytics_vertical.rating, composition_time_slots.composition_id, composition_time_slots.beat_loop_id, composition_time_slots.beat_loop_alt_id, composition_time_slots.fill_id, composition_time_slots.harmonic_loop_id, composition_time_slots.harmonic_loop_alt_id, composition_time_slots.ambient_loop_id, composition_time_slots.ambient_loop_alt_id, composition_time_slots.chord_id, composition_time_slots.synth_template_id, composition_time_slots.synth_template_hi_id, composition_time_slots.synth_template_mid_id, composition_time_slots.synth_template_low_id FROM analytics_vertical INNER JOIN composition_time_slots ON composition_time_slots.id = analytics_vertical.time_slot_id";
        ResultSet resultSet = statement.executeQuery(sqlString);

        return resultSet;
    }
}
