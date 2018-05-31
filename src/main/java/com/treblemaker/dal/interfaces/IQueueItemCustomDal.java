package com.treblemaker.dal.interfaces;

import com.treblemaker.model.queues.QueueItem;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface IQueueItemCustomDal {

	QueueItem getQueueItem() throws Exception;

	QueueItem getRefactoredQueueItem(String refactoredQueueId) throws SQLException, ClassNotFoundException;

	void setQueueItemProcessing(String queueItemId) throws ClassNotFoundException;

	void setQueueItemComplete(String queueItemId, String outputPath, QueueItem queueItem) throws ClassNotFoundException, IOException, SQLException;

	ResultSet getAnalyticsVertical() throws SQLException, ClassNotFoundException;
}
