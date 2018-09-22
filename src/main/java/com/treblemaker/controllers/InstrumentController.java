package com.treblemaker.controllers;

import com.treblemaker.controllers.dto.InstrumentItem;
import com.treblemaker.dal.interfaces.IInstrumentsDal;
import com.treblemaker.model.Instruments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class InstrumentController {

    @Autowired
    private IInstrumentsDal instrumentsDal;

    @RequestMapping(value = "/api/instruments/get", method = RequestMethod.GET)
    public @ResponseBody
    List<InstrumentItem> get()
    {
            Iterable<Instruments> instrumentList = instrumentsDal.findAll();

            List<InstrumentItem> instrumentItems = new ArrayList<>();

            for(Instruments instrmnt : instrumentList)
            {
                InstrumentItem instrumentItem = new InstrumentItem();
                instrumentItem.setId(instrmnt.getId());
                instrumentItem.setInstrument(instrmnt.getName());

                instrumentItems.add(instrumentItem);
            }

            return instrumentItems;
    }
}
