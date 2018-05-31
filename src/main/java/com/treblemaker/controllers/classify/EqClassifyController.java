package com.treblemaker.controllers.classify;

import com.treblemaker.controllers.ControllerThreadSync;
import com.treblemaker.controllers.data.EqBandController;
import com.treblemaker.controllers.singleton.NNSingelton;
import com.treblemaker.neuralnets.NNEq;
import com.treblemaker.neuralnets.WeightClassificationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class EqClassifyController extends NNBaseClass {

    @Autowired
    private EqBandController eqBandController;

    @Autowired
    private WeightClassificationUtils weightClassificationUtils;

    @RequestMapping(value = "/classify/eq/{harmonic_loop_id}/{beat_loop_id}", method = RequestMethod.GET)
    public
    @ResponseBody
    Integer progressionGet(@PathVariable("harmonic_loop_id") Integer harmonicLoopId,
                           @PathVariable("beat_loop_id") Integer beatLoopId) throws InterruptedException, IOException, URISyntaxException {

        NNSingelton.INSTANCE.deAllocatNeuralNets("EqClassifyController");

        int rating;
        long startTime = getAStartTime();

        synchronized (ControllerThreadSync.getInstance()) {

            if (NNSingelton.INSTANCE.nnEq == null) {
                NNSingelton.INSTANCE.nnEq = new NNEq(eqBandController, weightClassificationUtils);
            }

            rating = NNSingelton.INSTANCE.nnEq.trainNetWork(harmonicLoopId, beatLoopId);

            logLapsedTime(startTime, this.getClass().getName());
        }

        return rating;
    }
}
