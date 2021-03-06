package com.treblemaker.controllers;

import com.treblemaker.dal.interfaces.ICompositionDal;
import com.treblemaker.dal.interfaces.ICompositionTimeSlotDal;
import com.treblemaker.model.JsonString;
import com.treblemaker.model.composition.Composition;
import com.treblemaker.model.composition.CompositionDto;
import com.treblemaker.model.composition.CompositionTimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class CompositionStatusController {

    @Autowired
    ICompositionTimeSlotDal compositionTimeSlotDal;

    @Autowired
    ICompositionDal compositionDal;

    @RequestMapping(value = "/api/CompositionStatus/findUnRated", method = RequestMethod.GET)
    public @ResponseBody
    CompositionDto findComposition(){
        List<CompositionTimeSlot> timeSlots = compositionTimeSlotDal.findByRated(0);

        if(timeSlots.size() > 0){
            Composition composition = compositionDal.findById(timeSlots.get(0).getCompositionId()).get();
            return new CompositionDto(composition);
        }else{
            return new CompositionDto();
        }
    }
}
