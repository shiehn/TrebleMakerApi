package com.treblemaker.controllers.data;

import com.treblemaker.model.EqClassificationRequest;
import com.treblemaker.model.parametriceq.eqprediction.ParametricDto;
import com.treblemaker.services.EqFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EqBandController {

    private EqFormatter eqFormatter;

    @Autowired
    public EqBandController(EqFormatter eqFormatter){
        this.eqFormatter = eqFormatter;
    }

    @RequestMapping("data/eq/{phase}/{harmonicid}/{beatid}")
    public @ResponseBody
    ParametricDto Get(@PathVariable("phase") String phase,
                      @PathVariable("harmonicid") Integer harmonicid,
                      @PathVariable("beatid") Integer beatid) {

        EqClassificationRequest eqClassificationRequest = new EqClassificationRequest(
                phase, harmonicid, beatid
        );

        ParametricDto parametricDto = null;

        switch (phase){
            case "one":
                parametricDto = eqFormatter.createPhaseOne(eqClassificationRequest);
                break;
            case "two":
                break;
            case "three":
                break;
            case "four":
                break;
            case "five":
                break;
        }

        System.out.println("phase = " + phase);
        System.out.println("harmonicid = " + harmonicid);
        System.out.println("beatid = " + beatid);

        return parametricDto;
    }
}