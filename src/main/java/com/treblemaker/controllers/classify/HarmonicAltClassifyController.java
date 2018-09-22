package com.treblemaker.controllers.classify;

import com.treblemaker.controllers.ControllerThreadSync;
import com.treblemaker.controllers.data.HarmonicAltController;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.neuralnets.NNHarmonicAlt;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class HarmonicAltClassifyController extends NNBaseClass {

    @Autowired
    private HarmonicAltController harmonicAltController;

    @Autowired
    private ConfigurationProvider configurationProvider;

    @RequestMapping(value = "/classify/harmalt/{beat_loop_id}/{harmonic_loop_id}/{harmonic_loop_alt_id}/{synth_template_hi_id}/{synth_template_mid_id}/{synth_template_low_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Integer progressionGet(@PathVariable("beat_loop_id") Integer beatLoopId,
                           @PathVariable("harmonic_loop_id") Integer harmonicLoopId,
                           @PathVariable("harmonic_loop_alt_id") Integer harmonicLoopAltId,
                           @PathVariable("synth_template_hi_id") Integer synthHiId,
                           @PathVariable("synth_template_mid_id") Integer synthMidId,
                           @PathVariable("synth_template_low_id") Integer synthTemplateLowId) throws InterruptedException, IOException, URISyntaxException {

        NNSingelton.INSTANCE.deAllocatNeuralNets("HarmonicAltController");

        int rating;
        long startTime = getAStartTime();

        synchronized (ControllerThreadSync.getInstance()) {

            if (NNSingelton.INSTANCE.nnHarmonicAlt == null) {
                NNSingelton.INSTANCE.nnHarmonicAlt = new NNHarmonicAlt();
            }

            float[] predictionInputs = harmonicAltController.createPrdectionRow(beatLoopId, harmonicLoopId, harmonicLoopAltId, synthHiId, synthMidId, synthTemplateLowId);

            rating = NNSingelton.INSTANCE.nnHarmonicAlt.trainHarmonicAltNetWork(predictionInputs, configurationProvider.getServerPort());

            logLapsedTime(startTime, this.getClass().getName());
        }

        return rating;
    }
}
