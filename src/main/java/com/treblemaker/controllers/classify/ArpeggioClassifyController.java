package com.treblemaker.controllers.classify;

import com.treblemaker.controllers.ControllerThreadSync;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.neuralnets.NNArpeggio;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class ArpeggioClassifyController extends NNBaseClass {

    @Autowired
    private ConfigurationProvider configurationProvider;

    @RequestMapping(value = "/classify/arpeggio", method = RequestMethod.GET)
    public
    @ResponseBody
    Integer progressionGet(@RequestParam("arpeggio") float[] predictionInputs) throws InterruptedException, IOException, URISyntaxException {

        NNSingelton.INSTANCE.deAllocatNeuralNets("ArpeggioClassifyController");

        int rating;
        long startTime = getAStartTime();

        synchronized (ControllerThreadSync.getInstance()) {

            if (NNSingelton.INSTANCE.nnArpeggio == null) {
                NNSingelton.INSTANCE.nnArpeggio = new NNArpeggio();
            }

            rating = NNSingelton.INSTANCE.nnArpeggio.trainArpeggioNetWork(predictionInputs, configurationProvider.getServerPort());

            logLapsedTime(startTime, this.getClass().getName());
        }

        return rating;
    }
}
