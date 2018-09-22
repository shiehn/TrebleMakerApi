package com.treblemaker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treblemaker.configs.AppConfigs;
import com.treblemaker.controllers.dto.UploadBeatsRequest;
import com.treblemaker.dal.interfaces.IBeatLoopsDal;
import com.treblemaker.dal.interfaces.IRhythmicAccentsDal;
import com.treblemaker.model.BeatLoop;
import com.treblemaker.model.RhythmicAccents;
import com.treblemaker.model.UploadListener;
import com.treblemaker.model.queues.QueueAudioTransfer;
import com.treblemaker.services.AudioTransferService;
import com.treblemaker.utils.rythmTemplateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.Iterator;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class BeatLoopsController {

    @Autowired
    private IBeatLoopsDal beatLoopsDal;

    @Autowired
    private IRhythmicAccentsDal rhythmicAccentsDal;

    @Autowired
    private AudioTransferService audioTransferService;

    @Autowired
    private AppConfigs appConfigs;

    @RequestMapping(value = "/api/UploadBeats/PostStuff", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> upload(HttpServletRequest request,
            HttpServletResponse response) throws InterruptedException {
        MultipartHttpServletRequest mRequest;

        UploadListener uploadListener = new UploadListener();

        try {
            mRequest = (MultipartHttpServletRequest) request;

            String modelFromRequest = mRequest.getParameterMap().get("model")[0];

            ObjectMapper mapper = new ObjectMapper();

            UploadBeatsRequest uploadBeatsRequest = mapper.readValue(modelFromRequest, UploadBeatsRequest.class);

            List<RhythmicAccents> rhythmicAccents = rythmTemplateParser.ParseAccents(uploadBeatsRequest.rhythmicAccents);

            for(RhythmicAccents rhythmicAccent : rhythmicAccents){
                rhythmicAccentsDal.save(rhythmicAccent);
            }

             mRequest.getParameterMap();

            String fileName = "";

            Iterator<String> itr = mRequest.getFileNames();
            while (itr.hasNext()) {
                MultipartFile mFile = mRequest.getFile(itr.next());
                fileName = mFile.getOriginalFilename();

                Files.deleteIfExists(new File(AppConfigs.getTempUploadFullPath()).toPath());

                mFile.transferTo(new File(AppConfigs.getTempUploadFullPath()));
            }

            BeatLoop beatLoop = addBeatLoopToDatabase(fileName, uploadBeatsRequest, rhythmicAccents);

            audioTransferService.uploadAudioFile(beatLoop, null, QueueAudioTransfer.LOOP_TYPE_BEAT, AppConfigs.getTempUploadFullPath(), uploadListener);

        } catch (Exception e) {
            e.printStackTrace();
        }

        while(true) {
            if(uploadListener.isUploadComplete()) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
            Thread.sleep(500);
        }
    }

    private BeatLoop addBeatLoopToDatabase(String filename, UploadBeatsRequest uploadBeatsRequest, List<RhythmicAccents> rhythmicAccents) {

        BeatLoop beatLoop = new BeatLoop();
        beatLoop.setStationId(uploadBeatsRequest.stationid);
        beatLoop.setComplexity(uploadBeatsRequest.complexity);
        beatLoop.setMoodDark(uploadBeatsRequest.moodDark);
        beatLoop.setMoodLight(uploadBeatsRequest.moodLight);
        beatLoop.setAudioLength(999.999f);
        beatLoop.setBarCount(uploadBeatsRequest.barcount);
        beatLoop.setBpm(uploadBeatsRequest.tempo);
        beatLoop.setFileName(filename);
        beatLoop.setFilePath(appConfigs.BEAT_LOOPS_RELATIVE_PATH);
        beatLoop.setUsersId(1);
        beatLoop.setNormalizedLength(0);
        beatLoop.setLoopTransferComplete(false);

        beatLoopsDal.save(beatLoop);

        beatLoop.setRhythmicAccents(rhythmicAccents);
        beatLoopsDal.save(beatLoop);

        return beatLoop;
    }
}