package com.treblemaker;

import com.treblemaker.controllers.data.VolumeController;
import com.treblemaker.dal.DALCache;
import com.treblemaker.dal.DALCacheFillService;
import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.composition.CompositionTimeSlot;
import com.treblemaker.model.composition.CompositionTimeSlotLevels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableAutoConfiguration
@Configuration
@ComponentScan({"com.treblemaker"})
public class SpringConfiguration {

    @Autowired
    private DALCacheFillService dalCacheFillService;

    @Bean(name = "volumeController")
    public VolumeController volumeController() {

        dalCacheFillService.fillCache();

        List<AnalyticsVertical> analyticsVerticals = DALCache.getInstance().getAnalyticsVertical();
        List<CompositionTimeSlot> compositionTimeSlots = DALCache.getInstance().getCompositionTimeSlots();
        List<CompositionTimeSlotLevels> timeSlotLevels = DALCache.getInstance().getCompositionTimeSlotLevels();

        return new VolumeController(analyticsVerticals, compositionTimeSlots, timeSlotLevels);
    }
}
