package com.treblemaker.controllers;

import com.treblemaker.controllers.dto.UploadTemplateRequest;
import com.treblemaker.dal.interfaces.ISynthTemplateDal;
import com.treblemaker.model.SynthTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class SynthTemplateController {

    @Autowired
    private ISynthTemplateDal synthTemplateDal;

    @RequestMapping(value = "/api/SynthTemplate/post", method = RequestMethod.POST)
    public ResponseEntity<?> upload(@RequestBody UploadTemplateRequest templateRequest) {

        System.out.println(templateRequest.Complexity);

        SynthTemplate synthTemplate = new SynthTemplate();

        synthTemplate.setMoodDarkLevel(templateRequest.MoodDarkLevel);
        synthTemplate.setMoodLightLevel(templateRequest.MoodLightLevel);
        synthTemplate.setComplexity(templateRequest.Complexity);

        synthTemplate.setHiSynthId(templateRequest.HiSynthId);
        synthTemplate.setHiSynthName(templateRequest.HiSynthName);

        synthTemplate.setHiSynthIdAlt(templateRequest.HiSynthIdAlt);
        synthTemplate.setHiSynthNameAlt(templateRequest.HiSynthNameAlt);

        synthTemplate.setMidSynthId(templateRequest.MidSynthId);
        synthTemplate.setMidSynthName(templateRequest.MidSynthName);

        synthTemplate.setMidSynthIdAlt(templateRequest.MidSynthIdAlt);
        synthTemplate.setMidSynthNameAlt(templateRequest.MidSynthNameAlt);

        synthTemplate.setLowSynthId(templateRequest.LowSynthId);
        synthTemplate.setLowSynthName(templateRequest.LowSynthName);

        synthTemplate.setLowSynthIdAlt(templateRequest.LowSynthIdAlt);
        synthTemplate.setLowSynthNameAlt(templateRequest.LowSynthNameAlt);

        synthTemplate.setUsersId(1);

        synthTemplateDal.save(synthTemplate);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
