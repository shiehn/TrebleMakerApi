package com.treblemaker.controllers.classify;

import com.treblemaker.controllers.ControllerThreadSync;
import com.treblemaker.controllers.data.BeatAltController;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.neuralnets.NNBass;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
public class BassClassifyController extends NNBaseClass {

    public NNBass nnBass;

    @Autowired
    private ConfigurationProvider configurationProvider;

    @Autowired
    private BeatAltController beatAltController;

    @RequestMapping(value = "/classify/bass", method = RequestMethod.GET)
    public
    @ResponseBody
    Integer progressionGet(@RequestParam("bass") float[] predictionInputs) throws InterruptedException, IOException, URISyntaxException {

        NNSingelton.INSTANCE.deAllocatNeuralNets("BassClassifyController");

        int rating;
        long startTime = getAStartTime();

        synchronized (ControllerThreadSync.getInstance()) {

            if (NNSingelton.INSTANCE.nnBass == null) {
                NNSingelton.INSTANCE.nnBass = new NNBass();
            }

            rating = NNSingelton.INSTANCE.nnBass.trainNetWork(predictionInputs, configurationProvider.getServerPort());

            logLapsedTime(startTime, this.getClass().getName());
        }

        return rating;
    }
}
