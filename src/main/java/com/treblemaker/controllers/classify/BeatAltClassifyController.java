package com.treblemaker.controllers.classify;

import com.treblemaker.configs.AppConfigs;
import com.treblemaker.controllers.ControllerThreadSync;
import com.treblemaker.controllers.data.BeatAltController;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.neuralnets.NNBeatAlt;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class BeatAltClassifyController extends NNBaseClass {

    @Autowired
    private BeatAltController beatAltController;

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Autowired
    private AppConfigs appConfigs;

    @Value("${tm.api.user}")
    String apiUser;

    @Value("${tm.api.pass}")
    String apiPassword;

    @RequestMapping(value = "/classify/beatalt/{beat_loop_id}/{beat_loop_alt_id}/{harmonic_loop_id}/{harmonic_loop_alt_id}/{synth_template_hi_id}/{synth_template_mid_id}/{synth_template_low_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Integer progressionGet(@PathVariable("beat_loop_id") Integer beatLoopId,
                           @PathVariable("beat_loop_alt_id") Integer beatLoopAltId,
                           @PathVariable("harmonic_loop_id") Integer harmonicLoopId,
                           @PathVariable("harmonic_loop_alt_id") Integer harmonicLoopAltId,
                           @PathVariable("synth_template_hi_id") Integer synthHiId,
                           @PathVariable("synth_template_mid_id") Integer synthMidId,
                           @PathVariable("synth_template_low_id") Integer synthTemplateLowId) throws InterruptedException, IOException, URISyntaxException {

        NNSingelton.INSTANCE.deAllocatNeuralNets("BeatAltClassifyController");

        int rating;
        long startTime = getAStartTime();

        synchronized(ControllerThreadSync.getInstance()) {

            if (NNSingelton.INSTANCE.nnBeatAlt == null) {
                NNSingelton.INSTANCE.nnBeatAlt = new NNBeatAlt(appConfigs, apiUser, apiPassword);
            }

            float[] predictionInputs = beatAltController.createPrdectionRow(beatLoopId, beatLoopAltId, harmonicLoopId, harmonicLoopAltId, synthHiId, synthMidId, synthTemplateLowId);

            rating = NNSingelton.INSTANCE.nnBeatAlt.trainBeatAltNetWork(predictionInputs, configurationProvider.getServerPort());

            logLapsedTime(startTime, this.getClass().getName());
        }

        return rating;
    }
}
