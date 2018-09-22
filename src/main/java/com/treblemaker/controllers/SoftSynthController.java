package com.treblemaker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treblemaker.configs.AppConfigs;
import com.treblemaker.controllers.dto.UploadSoftSynthRequest;
import com.treblemaker.dal.interfaces.ISoftSynthsDal;
import com.treblemaker.model.SoftSynths;
import com.treblemaker.model.UploadListener;
import com.treblemaker.model.queues.QueueAudioTransfer;
import com.treblemaker.services.AudioTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class SoftSynthController {

    @Autowired
    private ISoftSynthsDal softSynthsDal;

    @Autowired
    private AudioTransferService audioTransferService;

    @RequestMapping(value = "/api/softsynths/get", method = RequestMethod.GET)
    public ResponseEntity<?> getSynths() {

        HashMap<String, Integer> synthNameAndIds = new HashMap<>();

        Iterable<SoftSynths> synths = softSynthsDal.findAll();

        List<SoftSynths> synthsList = StreamSupport.stream(synths.spliterator(), false).filter(SoftSynths::isLoopTransferComplete).collect(Collectors.toList());

        //TODO REPLACE THIS WITH JAVA 8 .map
        for (SoftSynths synth : synthsList){
            synthNameAndIds.put(synth.getSynthName(), synth.getId());
        }

        return new ResponseEntity<>(synthNameAndIds,HttpStatus.OK);
    }

    public Predicate<SoftSynths> isTransferComplete(){
        return softSynths -> softSynths.isLoopTransferComplete() == true;
    }

    @RequestMapping(value = "/api/SoftSynths/PostStuff", method = RequestMethod.POST)
    public ResponseEntity<?> upload(HttpServletRequest request,
                                    HttpServletResponse response) throws InterruptedException {

        System.out.println("REQUEST MADE: '/api/SoftSynths/PostStuff'");

        MultipartHttpServletRequest mRequest;

        UploadListener uploadListener = new UploadListener();

        try {
            mRequest = (MultipartHttpServletRequest) request;

            String modelFromRequest = mRequest.getParameterMap().get("model")[0];

            System.out.println("Model from request: " + modelFromRequest);

            ObjectMapper mapper = new ObjectMapper();

            UploadSoftSynthRequest uploadLoopRequest = mapper.readValue(modelFromRequest, UploadSoftSynthRequest.class);

            mRequest.getParameterMap();

            String fileName = "";

            Iterator<String> itr = mRequest.getFileNames();
            while (itr.hasNext()) {
                MultipartFile mFile = mRequest.getFile(itr.next());
                fileName = sanitizeFileName(mFile.getOriginalFilename());

                System.out.println("fileName: " + fileName);

                Files.deleteIfExists(new File(AppConfigs.getTempUploadFullPath()).toPath());

                System.out.println("CHECK FOR EXISTING: " + AppConfigs.getTempUploadFullPath());

                mFile.transferTo(new File(AppConfigs.getTempUploadFullPath()));

                System.out.println("Transfering to: " + AppConfigs.getTempUploadFullPath());
            }

            SoftSynths softSynth = addSoftSynthToDatabase(fileName, AppConfigs.getTempUploadFullPath(), uploadLoopRequest);

            audioTransferService.uploadAudioFile(null, softSynth, QueueAudioTransfer.LOOP_TYPE_SOUND_FONT, AppConfigs.getTempUploadFullPath(), uploadListener);
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

    private String sanitizeFileName(String fileName){

        fileName = fileName.replaceAll("\\s+","_");

        fileName = fileName.toLowerCase();

        return fileName;
    }

    private SoftSynths addSoftSynthToDatabase(String fileName, String filePath, UploadSoftSynthRequest uploadLoopRequest){

        Iterable<SoftSynths> synths = softSynthsDal.findAll();

        for (SoftSynths s :synths) {
            if(s.getSynthName().equalsIgnoreCase(fileName)){
                System.out.println("ERROR: FILE EXISITS!!");
                throw new RuntimeException("FILE EXISITS!!");
            }
        }

        SoftSynths softSynths = new SoftSynths();
        softSynths.setMoodDark(999);
        softSynths.setMoodLight(999);
        softSynths.setDate(new Date());
        softSynths.setSixtyFourBit(1);
        softSynths.setSynthFilePath(filePath);
        softSynths.setSynthName(fileName);
        softSynths.setUsersId(1);

        softSynthsDal.save(softSynths);

        System.out.println("SOFT SYNTH ADDED TO DB");

        return softSynths;
    }
}
