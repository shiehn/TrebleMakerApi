package com.treblemaker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treblemaker.configs.AppConfigs;
import com.treblemaker.controllers.dto.UploadHarmonicLoopsRequest;
import com.treblemaker.dal.interfaces.*;
import com.treblemaker.model.HarmonicLoop;
import com.treblemaker.model.HiveChord;
import com.treblemaker.model.RhythmicAccents;
import com.treblemaker.model.UploadListener;
import com.treblemaker.model.queues.QueueAudioTransfer;
import com.treblemaker.services.AudioTransferService;
import com.treblemaker.utils.LoopUtils;
import com.treblemaker.utils.interfaces.IAudioUtils;
import com.treblemaker.utils.rythmTemplateParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Controller
public class HarmonicLoopsController {

    @Autowired
    private IRhythmicAccentsDal rhythmicAccentsDal;

    @Autowired
    private IHiveChordDal hiveChordDal;

    @Autowired
    private IAudioUtils audioUtils;

    @Autowired
    private IHarmonicLoopsDal harmonicLoopsDal;

    @Autowired
    private AudioTransferService audioTransferService;

    @Autowired
    private IGenreDal genreDal;

    @Autowired
    private ICharacteristicsDal characteristicsDal;

    @Autowired
    private IInstrumentsDal instrumentsDal;

    @Autowired
    private AppConfigs appConfigs;

    @RequestMapping(value = "/api/UploadHarmonicLoops/PostStuff", method = RequestMethod.POST)
    public ResponseEntity<?> upload(HttpServletRequest request,
                                    HttpServletResponse response) throws InterruptedException {

        AppConfigs.generateSessionId();

        MultipartHttpServletRequest mRequest;

        UploadListener uploadListener = new UploadListener();

        try {
            mRequest = (MultipartHttpServletRequest) request;

            String modelFromRequest = mRequest.getParameterMap().get("model")[0];

            ObjectMapper mapper = new ObjectMapper();

            UploadHarmonicLoopsRequest uploadLoopRequest = mapper.readValue(modelFromRequest, UploadHarmonicLoopsRequest.class);

            List<RhythmicAccents> rhythmicAccents = rythmTemplateParser.ParseAccents(uploadLoopRequest.rhythmicAccents);

            for (RhythmicAccents rhythmicAccent : rhythmicAccents) {
                rhythmicAccentsDal.save(rhythmicAccent);
            }

            mRequest.getParameterMap();

            String fileName = "";

            //CHECK IF ALREADY IN DB
//            Iterable<HarmonicLoop> harmonicLoops = harmonicLoopsDal.findAll();
//            for (HarmonicLoop harmonicLoop : harmonicLoops) {
//                if(harmonicLoop.getFileName().equalsIgnoreCase(fileName)){
//                    throw new RuntimeException("LOOP ALREADY EXISITS IN DB");
//                }
//            }

            Iterator<String> itr = mRequest.getFileNames();
            while (itr.hasNext()) {
                MultipartFile mFile = mRequest.getFile(itr.next());
                fileName = mFile.getOriginalFilename();

                Files.deleteIfExists(new File(AppConfigs.getTempUploadFullPath()).toPath());

                mFile.transferTo(new File(AppConfigs.getTempUploadFullPath()));
            }

            HarmonicLoop harmonicLoop = addHarmonicLoopToDatabase(fileName, AppConfigs.getTempUploadFullPath(), uploadLoopRequest, rhythmicAccents);

            audioTransferService.uploadAudioFile(harmonicLoop, null, QueueAudioTransfer.LOOP_TYPE_HARMONIC, AppConfigs.getTempUploadFullPath(), uploadListener);

        } catch (Exception e) {
            e.printStackTrace();
        }

        while (true) {
            if (uploadListener.isUploadComplete()) {
                try {
                    Files.deleteIfExists(new File(AppConfigs.getTempUploadFullPath()).toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("** COMPLETE!! ...");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            System.out.println("** STILL WAITING ...");
            Thread.sleep(1000);
        }
    }

    private HarmonicLoop addHarmonicLoopToDatabase(String filename, String filePath, UploadHarmonicLoopsRequest request, List<RhythmicAccents> rhythmicAccents) throws Exception {

        HarmonicLoop harmonicLoop = new HarmonicLoop();
        harmonicLoop.setStationId(request.stationid);
        harmonicLoop.setUsersId(1);
        harmonicLoop.setAssociatedId("1");

        float audioLength = audioUtils.getAudioLength(filePath);
        harmonicLoop.setAudioLength(audioLength);

        int barCount = LoopUtils.getBarCount(request.bpm, audioLength);
        harmonicLoop.setBarCount(barCount);

        harmonicLoop.setBpm(request.bpm);

        harmonicLoop.setComplexity(request.complexity);
        harmonicLoop.setMoodDark(request.moodDark);
        harmonicLoop.setMoodLight(request.moodLight);
        harmonicLoop.setCurrentBar(0);
        harmonicLoop.setDate(new Date().toString());
        harmonicLoop.setFileName(filename);
        harmonicLoop.setFilePath(appConfigs.HARMONIC_LOOPS_RELATIVE_PATH);

        harmonicLoopsDal.save(harmonicLoop);

        harmonicLoop.setChords(getChords(request.chordRoots));

        harmonicLoopsDal.save(harmonicLoop);

        harmonicLoop.setRhythmicAccents(rhythmicAccents);

        harmonicLoopsDal.save(harmonicLoop);


                /*
    public List<Integer> genres;
    public List<Integer> characteristics;
    public List<Integer> instruments;
    public int complexity;
    public int moodLight;
    public int moodDark;
    public int audioLength;
    public List<Integer> chordRoots;
    public int bpm;
    public List<Boolean> rhythmicAccents;
         */


        return harmonicLoop;
    }

    private List<HiveChord> getChords(List<Integer> chordIds) {

        List<HiveChord> chords = new ArrayList<>();
        chordIds.forEach(chordId -> chords.add(hiveChordDal.findOne(chordId)));

        return chords;
    }
}



/*
$scope.save = function () {

        console.log("createInstrumentList()", createInstrumentList());

        $scope.model = {

            saveToDatabase: true,
            genres: createGenreList(),
            characteristics: createCharacteristicsList(),
            instruments: createInstrumentList(),
            complexity: $scope.selectedComplexityOption.id,
            moodDark: $scope.selectedMoodDarkOption.id,
            moodLight: $scope.selectedMoodLightOption.id,
            audioLength: $scope.selectedAudioLengthOption.id,

            chordRoots :  selectedChordsIds,
            bpm: $scope.selectedBpmOption.id,
            rhythmicAccents: $scope.accentArray
        };

        $http({
            method: 'POST',
            url: "/api/UploadHarmonicLoops/PostStuff",
            headers: { 'Content-Type': undefined },
            transformRequest: function (data) {

                var formData = new FormData();
                formData.append("model", angular.toJson(data.model));
                for (var i = 0; i < data.files.length; i++) {
                    formData.append("file" + i, data.files[i]);
                }

                return formData;
            },
            data: { model: $scope.model, files: $scope.files }
        }).
        success(function (data, status, headers, config) {

            if (data && (data.StatusCode != 500)) {

                var response = data.replace(/['"]+/g, '');
                response = response.split("*");

                trackId = response[0];
                $scope.recomendedBpm = response[1];
                $window.location.reload();
                //checkForTrackCompletion();
            } else {
                alert("Sorry, Encountered an error generating a harmonic progression");
            }
        }).
        error(function (data, status, headers, config) {
            alert("failed!");
        });
    };
 */