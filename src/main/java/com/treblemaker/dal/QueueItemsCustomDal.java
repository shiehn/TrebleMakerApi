package com.treblemaker.dal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treblemaker.dal.interfaces.IQueueItemCustomDal;
import com.treblemaker.dal.interfaces.ISqlManager;
import com.treblemaker.model.queues.QueueItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class QueueItemsCustomDal implements IQueueItemCustomDal {

    @Autowired
    @Qualifier(value = "sqlManager")
    private ISqlManager sqlManager;


    public QueueItem getQueueItem() throws Exception {

        QueueItem queueItem = null;

        Statement statement = sqlManager.getConnection().createStatement();

        String sqlString = "SELECT * FROM queue_items WHERE status = 0 AND is_refactor = 0 LIMIT 1";
        ResultSet resultSet = statement.executeQuery(sqlString);

        while (resultSet.next()) {

            queueItem = new QueueItem();

//            ObjectMapper mapper = new ObjectMapper();
//            ProgressionDTO progression = mapper.readValue(resultSet.getString("queue_item"), ProgressionDTO.class);

            queueItem.setQueueItemId(resultSet.getString("queue_item_id"));
            queueItem.setQueueItem(resultSet.getString("queue_item"));
            queueItem.setJobStatus(resultSet.getInt("status"));
            queueItem.setIsRefactor(resultSet.getBoolean("is_refactor"));
        }

        return queueItem;
    }

    public ResultSet getAnalyticsVertical() throws SQLException, ClassNotFoundException {

        Statement statement = sqlManager.getConnection().createStatement();

        String sqlString = "SELECT analytics_vertical.rating, composition_time_slots.composition_id, composition_time_slots.beat_loop_id, composition_time_slots.beat_loop_alt_id, composition_time_slots.fill_id, composition_time_slots.harmonic_loop_id, composition_time_slots.harmonic_loop_alt_id, composition_time_slots.ambient_loop_id, composition_time_slots.ambient_loop_alt_id, composition_time_slots.chord_id, composition_time_slots.synth_template_id, composition_time_slots.synth_template_hi_id, composition_time_slots.synth_template_mid_id, composition_time_slots.synth_template_low_id FROM analytics_vertical INNER JOIN composition_time_slots ON composition_time_slots.id = analytics_vertical.time_slot_id";
        ResultSet resultSet = statement.executeQuery(sqlString);

        return resultSet;
    }

    public QueueItem getRefactoredQueueItem(String refactoredQueueId) throws SQLException, ClassNotFoundException {

        QueueItem queueItem = null;

            Statement statement = sqlManager.getConnection().createStatement();

            String sqlString = "SELECT TOP 1 * FROM dbo.QueueItems WHERE QueueItemId = '" + refactoredQueueId + "' AND IsRefactor = 1";
            ResultSet resultSet = statement.executeQuery(sqlString);

            while (resultSet.next()) {

                queueItem = new QueueItem();

//                ObjectMapper mapper = new ObjectMapper();
//                ProgressionDTO progression = new ProgressionDTO();
//
//                try {
//                    progression = mapper.readValue(resultSet.getString("QueueItem"), ProgressionDTO.class);
//                } catch (Exception e) {
//                    // TODO HANDLE EXCEPTION ..
//                    String error = e.getLocalizedMessage();
//                }

                queueItem.setQueueItemId(resultSet.getString("QueueItemId"));
                queueItem.setQueueItem(resultSet.getString("QueueItem"));
                queueItem.setJobStatus(resultSet.getInt("JobStatus"));
                queueItem.setIsRefactor(resultSet.getBoolean("IsRefactor"));
            }


        return queueItem;
    }

    public void setQueueItemProcessing(String queueItemId) throws ClassNotFoundException {

        try {

            String sqlString = "UPDATE queue_items SET status = 1 WHERE queue_item_id = '" + queueItemId + "'";

            // Use the connection to create the SQL statement.
            Statement statement = sqlManager.getConnection().createStatement();

            // Execute the statement.
            statement.executeUpdate(sqlString);

        } catch (SQLException e) {
            // TODO handle this ..
            // e.printStackTrace();
        }
    }

    public void setQueueItemComplete(String queueItemId, String outputPath, QueueItem queueItem) throws ClassNotFoundException, IOException, SQLException {

        String jsonQueueItem = new ObjectMapper().writeValueAsString(queueItem.getQueueItem());

        String sqlString = "UPDATE queue_items SET status = 2, is_refactor = 1, queue_item = '" + jsonQueueItem + "', output_path = '" + outputPath + "' WHERE queue_item_id = '" + queueItemId + "'";

        // Use the connection to create the SQL statement.
        Statement statement = sqlManager.getConnection().createStatement();

        // Execute the statement.
        statement.executeUpdate(sqlString);

    }
}
