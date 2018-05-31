package com.treblemaker.dal.interfaces;

import com.treblemaker.model.queues.QueueAudioTransfer;
import org.springframework.data.repository.CrudRepository;

public interface IQueueAudioTransferDal extends CrudRepository<QueueAudioTransfer, Integer> {
}
