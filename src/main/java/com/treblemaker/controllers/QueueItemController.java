package com.treblemaker.controllers;

import com.treblemaker.dal.interfaces.IQueueItemsDal;
import com.treblemaker.model.JsonString;
import com.treblemaker.model.queues.QueueItem;
import com.treblemaker.services.AudioTransferService;
import com.treblemaker.services.queue.QueueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class QueueItemController {

    @Autowired
    private QueueService queueService;

    @Autowired
    private IQueueItemsDal queueItemsDal;

    @Autowired
    private AudioTransferService audioTransferService;

    private static final Logger logger = LoggerFactory.getLogger(QueueItemController.class);

    @RequestMapping(value = "/api/Progression/{testid}", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    JsonString progressionGet(@PathVariable("testid") int testid) {

        System.out.println("/api/Progression GET");

        QueueItem queueItem = queueItemsDal.findOne((long)testid);

        if(queueItem != null && queueItem.getJobStatus() == 2){

            System.out.println("getOutputPath = " + queueItem.getOutputPath());

            return new JsonString(queueItem.getOutputPath());
        }

        return new JsonString("error");
    }

    @RequestMapping(value = "/api/Progression", method = RequestMethod.POST)
    public @ResponseBody Long progressionPost(@RequestParam("testid") int testid,HttpServletResponse response, HttpServletRequest request) throws IOException {

        return queueService.addQueueItem();
    }



}
