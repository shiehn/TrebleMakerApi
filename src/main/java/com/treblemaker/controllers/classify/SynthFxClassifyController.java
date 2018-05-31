package com.treblemaker.controllers.classify;

import com.treblemaker.controllers.ControllerThreadSync;
import com.treblemaker.controllers.data.SynthFXController;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.neuralnets.NNSynthFx;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class SynthFxClassifyController extends NNBaseClass {

    @Autowired
    private SynthFXController synthFXController;

    @Autowired
    private ConfigurationProvider configurationProvider;

    @RequestMapping(value = "/classify/synthfx", method = RequestMethod.GET)
    public
    @ResponseBody
    Integer progressionGet(@RequestParam("synthid") Integer synthid,
                           @RequestParam("sixteenthfreq") Float sixteenthfreq,
                           @RequestParam("eigthfreq") Float eigthfreq,
                           @RequestParam("quarterfreq") Float quarterfreq,
                           @RequestParam("dottedquarterfreq") Float dottedquarterfreq,
                           @RequestParam("halffreq") Float halffreq,
                           @RequestParam("fxvol") Float fxvol,
                           @RequestParam("fxtype") Float fxtype) throws InterruptedException, IOException, URISyntaxException {

        NNSingelton.INSTANCE.deAllocatNeuralNets("SynthFxClassifyController");

        int rating;
        long startTime = getAStartTime();

        synchronized (ControllerThreadSync.getInstance()) {

            if (NNSingelton.INSTANCE.nnSynthFx == null) {
                NNSingelton.INSTANCE.nnSynthFx = new NNSynthFx();
            }

            float[] predictionInputs = new float[]{sixteenthfreq, eigthfreq, quarterfreq, dottedquarterfreq, halffreq, fxvol, fxtype};

            rating = NNSingelton.INSTANCE.nnSynthFx.trainNetWork(synthid, predictionInputs, configurationProvider.getServerPort());

            logLapsedTime(startTime, this.getClass().getName());
        }

        return rating;
    }
}
