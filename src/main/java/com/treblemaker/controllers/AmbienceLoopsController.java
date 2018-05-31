package com.treblemaker.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treblemaker.configs.AppConfigs;
import com.treblemaker.controllers.dto.UploadAmbienceLoopRequest;
import com.treblemaker.dal.interfaces.IAmbienceLoopsDal;
import com.treblemaker.dal.interfaces.IHiveChordDal;
import com.treblemaker.dal.interfaces.IRhythmicAccentsDal;
import com.treblemaker.model.AmbienceLoop;
import com.treblemaker.model.HiveChord;
import com.treblemaker.model.UploadListener;
import com.treblemaker.model.queues.QueueAudioTransfer;
import com.treblemaker.services.AudioTransferService;
import com.treblemaker.utils.interfaces.IAudioUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Controller
public class AmbienceLoopsController {

    @Autowired
    private IRhythmicAccentsDal rhythmicAccentsDal;

    @Autowired
    private IHiveChordDal hiveChordDal;

    @Autowired
    private IAudioUtils audioUtils;

    @Autowired
    private IAmbienceLoopsDal ambienceLoopsDal;

    @Autowired
    private AppConfigs appConfigs;

    @Autowired
    private AudioTransferService audioTransferService;

    @RequestMapping(value = "/api/UploadAmbience/PostStuff", method = RequestMethod.POST)
    public ResponseEntity<?> upload(HttpServletRequest request,
                                    HttpServletResponse response) throws InterruptedException {

        MultipartHttpServletRequest mRequest;

        UploadListener uploadListener = new UploadListener();

        try {
            mRequest = (MultipartHttpServletRequest) request;

            String modelFromRequest = mRequest.getParameterMap().get("model")[0];

            ObjectMapper mapper = new ObjectMapper();

            UploadAmbienceLoopRequest uploadLoopRequest = mapper.readValue(modelFromRequest, UploadAmbienceLoopRequest.class);

            mRequest.getParameterMap();

            String fileName = "";

            Iterator<String> itr = mRequest.getFileNames();
            while (itr.hasNext()) {
                MultipartFile mFile = mRequest.getFile(itr.next());
                fileName = mFile.getOriginalFilename();

//                Files.deleteIfExists(new File(AppConfigs.getTempUploadFullPath()).toPath());
//
//                mFile.transferTo(new File(AppConfigs.getTempUploadFullPath()));
            }

            AmbienceLoop ambiencecLoop = addAmbienceLoopToDatabase(fileName, AppConfigs.getTempUploadFullPath(), uploadLoopRequest);

            audioTransferService.uploadAudioFile(ambiencecLoop, null, QueueAudioTransfer.LOOP_TYPE_AMBIENT, AppConfigs.getTempUploadFullPath(), uploadListener);

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

    private AmbienceLoop addAmbienceLoopToDatabase(String filename, String filePath, UploadAmbienceLoopRequest request) throws Exception {

        AmbienceLoop ambienceLoop = new AmbienceLoop();
        ambienceLoop.setUsersId(1);
        ambienceLoop.setStationId(request.getStationid());

        float audioLength = audioUtils.getAudioLength(filePath);
        ambienceLoop.setAudioLength(audioLength);

        ambienceLoop.setComplexity(request.complexity);
        ambienceLoop.setDate(new Date());
        ambienceLoop.setFileName(filename);
        ambienceLoop.setFilePath(appConfigs.getHarmonicLoopsDir());

        ambienceLoopsDal.save(ambienceLoop);

        ambienceLoop.setChords(getChords(request.rootChords));

        ambienceLoopsDal.save(ambienceLoop);

        return ambienceLoop;
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