package com.treblemaker.controllers.classify;

import com.treblemaker.configs.AppConfigs;
import com.treblemaker.controllers.ControllerThreadSync;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.neuralnets.NNVolume;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class VolumeClassifyController extends NNBaseClass {

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Autowired
    private AppConfigs appConfigs;

    @Value("${tm.api.user}")
    String apiUser;

    @Value("${tm.api.pass}")
    String apiPassword;

    @RequestMapping(value = "/classify/volume/{comphi}/{comphialt}/{compmid}/{compmidalt}/{complow}/{complowalt}/{beat}/{beatalt}/{harmonic}/{harmonicalt}/{ambient}/{fills}/{hits}", method = RequestMethod.GET)
    public
    @ResponseBody
    Integer classifyVolume(@PathVariable("comphi") Float compHi,
                           @PathVariable("comphialt") Float compHitAlt,
                           @PathVariable("compmid") Float compMid,
                           @PathVariable("compmidalt") Float compMidAlt,
                           @PathVariable("complow") Float compLow,
                           @PathVariable("complowalt") Float compLowAlt,
                           @PathVariable("beat") Float beat,
                           @PathVariable("beatalt") Float beatalt,
                           @PathVariable("harmonic") Float harmonic,
                           @PathVariable("harmonicalt") Float harmonicalt,
                           @PathVariable("ambient") Float ambient,
                           @PathVariable("fills") Float fills,
                           @PathVariable("hits") Float hits) throws InterruptedException, IOException, URISyntaxException {

        NNSingelton.INSTANCE.deAllocatNeuralNets("VolumeClassifyController");

        int rating;
        long startTime = getAStartTime();

        synchronized (ControllerThreadSync.getInstance()) {

            if (NNSingelton.INSTANCE.nnVolume == null) {
                NNSingelton.INSTANCE.nnVolume = new NNVolume(appConfigs, apiUser, apiPassword);
            }

            float[] predictionInputs = new float[]{
                    compHi,
                    compHitAlt,
                    compMid,
                    compMidAlt,
                    compLow,
                    compLowAlt,
                    beat,
                    beatalt,
                    harmonic,
                    harmonicalt,
                    ambient,
                    fills,
                    hits
            };

            rating = NNSingelton.INSTANCE.nnVolume.trainArpeggioNetWork(predictionInputs, configurationProvider.getServerPort());

            logLapsedTime(startTime, this.getClass().getName());
        }

        return rating;
    }
}