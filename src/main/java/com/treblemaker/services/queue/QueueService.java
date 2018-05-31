package com.treblemaker.services.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treblemaker.dal.interfaces.IQueueItemsDal;
import com.treblemaker.model.progressions.ProgressionDTO;
import com.treblemaker.model.progressions.ProgressionUnit;
import com.treblemaker.model.queues.QueueItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class QueueService {

    @Autowired
    private IQueueItemsDal queueItemsDal;

    public long addQueueItem() throws IOException {
        String queueId = UUID.randomUUID().toString();

        QueueItem queueItem = generateQueueItem();
        queueItem.setQueueItemId(queueId);
        queueItem.setJobStatus(0);
        queueItem.setOutputPath("");
        queueItem.setBpm(80);
        queueItem.setUsers_id(1);

        try {
            queueItemsDal.save(queueItem);
        }catch (Exception e){
            System.out.println("Add Queue ERROR : " + e.getMessage());
        }

        System.out.println("QueueItem : " + queueId + " was successfully added");

        return queueItem.getId();
    }

    private QueueItem generateQueueItem() throws IOException {

        List<ProgressionUnit> structure = new ArrayList<>();
        structure.add(generateProgressionUnitVerse());
        structure.add(generateProgressionUnitVerse());
        structure.add(generateProgressionUnitChorus());
        structure.add(generateProgressionUnitVerse());
        structure.add(generateProgressionUnitVerse());
        structure.add(generateProgressionUnitChorus());
        structure.add(generateProgressionUnitBridge());
        structure.add(generateProgressionUnitChorus());

        ProgressionDTO progressionDTO = new ProgressionDTO();


        progressionDTO.setStructure(structure);

        QueueItem queueItem = new QueueItem();
        queueItem.setUsers_id(1); //hard code to only user ..

        ObjectMapper mapper = new ObjectMapper();
        String progressionDTOString = mapper.writeValueAsString(progressionDTO);

        queueItem.setQueueItem(progressionDTOString);

        return queueItem;
    }

    private ProgressionUnit generateProgressionUnitVerse(){

        ProgressionUnit progressionUnit = null;

        progressionUnit = new ProgressionUnit();

        progressionUnit.setType(ProgressionUnit.ProgressionType.VERSE);
        progressionUnit.setComplexity(70);
        progressionUnit.setMinorToMajor(5);
        progressionUnit.setBarCount(4);

        return progressionUnit;
    }

    private ProgressionUnit generateProgressionUnitBridge(){

        ProgressionUnit progressionUnit = new ProgressionUnit();

        progressionUnit.setType(ProgressionUnit.ProgressionType.BRIDGE);
        progressionUnit.setComplexity(100);
        progressionUnit.setMinorToMajor(5);
        progressionUnit.setBarCount(4);

        return progressionUnit;
    }

    private ProgressionUnit generateProgressionUnitChorus(){

        ProgressionUnit progressionUnit = new ProgressionUnit();

        progressionUnit.setType(ProgressionUnit.ProgressionType.CHORUS);
        progressionUnit.setComplexity(70);
        progressionUnit.setMinorToMajor(5);
        progressionUnit.setBarCount(4);

        return progressionUnit;
    }
}
