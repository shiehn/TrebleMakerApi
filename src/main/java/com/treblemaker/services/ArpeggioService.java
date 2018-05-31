package com.treblemaker.services;

import com.treblemaker.model.analytics.AnalyticsVertical;
import com.treblemaker.model.arpeggio.ArpeggioTimeslotData;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionTimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.treblemaker.services.ArpeggioAndBassUtilsService.*;

@Service
public class ArpeggioService {

    @Autowired
    private ArpeggioAndBassUtilsService arpeggioUtils;

    public List<ArpeggioTimeslotData> extractArpeggioSlotData(List<CompositionTimeSlot> timeSlots, List<AnalyticsVertical> analyticsVerticals, List<Composition> compositions){

        return arpeggioUtils.extractArpeggioSlotData(ArpeggioSlotType.ARPEGGIO, timeSlots, analyticsVerticals, compositions);
    }
}
