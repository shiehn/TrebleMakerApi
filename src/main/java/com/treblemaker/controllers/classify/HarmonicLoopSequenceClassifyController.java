//package com.treblemaker.controllers.classify;
//
//import com.treblemaker.controllers.ControllerThreadSync;
//import com.treblemaker.neuralnets.NNHarmonicAlt;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.io.IOException;
//import java.net.URISyntaxException;
//
//public class HarmonicLoopSequenceClassifyController extends NNBaseClass {
//
//    private NNHarmonicSequence nnHarmonicSequence;
//
//    @RequestMapping(value = "/classify/harmonicsequence", method = RequestMethod.GET)
//    public
//    @ResponseBody
//    Integer progressionGet(@RequestParam("loopone") String loopOne) throws InterruptedException, IOException, URISyntaxException {
//
//        int rating;
//        long startTime = getAStartTime();
//
//        synchronized (ControllerThreadSync.getInstance()) {
//
//            if (nnHarmonicSequence == null) {
//                nnHarmonicSequence = new NNHarmonicAlt();
//            }

//            float[] predictionInputs = harmonicAltController.createPrdectionRow(beatLoopId, harmonicLoopId, harmonicLoopAltId, synthHiId, synthMidId, synthTemplateLowId);
//
//            rating = nnHarmonicSequence.trainHarmonicAltNetWork(predictionInputs);
//
//            logLapsedTime(startTime, this.getClass().getName());
//        }
//
//        return rating;
//        return 5;
//    }
//}

