package com.treblemaker.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressEventType;
import com.amazonaws.services.s3.transfer.PersistableTransfer;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.services.s3.transfer.internal.S3ProgressListener;
import com.treblemaker.configs.AppConfigs;
import com.treblemaker.dal.interfaces.IQueueAudioTransferDal;
import com.treblemaker.model.SoftSynths;
import com.treblemaker.model.UploadListener;
import com.treblemaker.model.interfaces.IWeightable;
import com.treblemaker.model.queues.QueueAudioTransfer;
import com.treblemaker.providers.ConfigurationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;

@Service
public class AudioTransferService {

    @Autowired
    private IQueueAudioTransferDal queueAudioTransferDal;

    @Autowired
    private ConfigurationProvider configurationProvider;

    private void addBeatLoopQueueItem(String filePath, IWeightable loop){

        QueueAudioTransfer queueAudioTransfer = new QueueAudioTransfer();
        queueAudioTransfer.setState(QueueAudioTransfer.PROGRESS_UNSTARTED);
        queueAudioTransfer.setFilepath(filePath);
        queueAudioTransfer.setFilename(loop.getFileName());
        queueAudioTransfer.setLoopId(loop.getId());
        queueAudioTransfer.setDate(Calendar.getInstance().getTime());
        queueAudioTransfer.setLoop_type(QueueAudioTransfer.LOOP_TYPE_BEAT);

        queueAudioTransferDal.save(queueAudioTransfer);
    }

    private void addHarmonicLoopQueueItem(String filePath, IWeightable loop){

        QueueAudioTransfer queueAudioTransfer = new QueueAudioTransfer();
        queueAudioTransfer.setState(QueueAudioTransfer.PROGRESS_UNSTARTED);
        queueAudioTransfer.setFilepath(filePath);
        queueAudioTransfer.setFilename(loop.getFileName());
        queueAudioTransfer.setLoopId(loop.getId());
        queueAudioTransfer.setDate(Calendar.getInstance().getTime());
        queueAudioTransfer.setLoop_type(QueueAudioTransfer.LOOP_TYPE_HARMONIC);

        queueAudioTransferDal.save(queueAudioTransfer);
    }

    private void addAmbientLoopQueueItem(String filePath, IWeightable loop ){

        QueueAudioTransfer queueAudioTransfer = new QueueAudioTransfer();
        queueAudioTransfer.setState(QueueAudioTransfer.PROGRESS_UNSTARTED);
        queueAudioTransfer.setFilepath(filePath);
        queueAudioTransfer.setFilename(loop.getFileName());
        queueAudioTransfer.setLoopId(loop.getId());
        queueAudioTransfer.setDate(Calendar.getInstance().getTime());
        queueAudioTransfer.setLoop_type(QueueAudioTransfer.LOOP_TYPE_AMBIENT);

        queueAudioTransferDal.save(queueAudioTransfer);
    }

    private void addSoundFont(String filePath, SoftSynths softSynth){

        QueueAudioTransfer queueAudioTransfer = new QueueAudioTransfer();
        queueAudioTransfer.setState(QueueAudioTransfer.PROGRESS_UNSTARTED);
        queueAudioTransfer.setFilepath(filePath);
        queueAudioTransfer.setFilename(softSynth.getSynthName());
        queueAudioTransfer.setLoopId(softSynth.getId());
        queueAudioTransfer.setDate(Calendar.getInstance().getTime());
        queueAudioTransfer.setLoop_type(QueueAudioTransfer.LOOP_TYPE_SOUND_FONT);

        queueAudioTransferDal.save(queueAudioTransfer);
    }

    public void uploadAudioFile(IWeightable loop, SoftSynths softSynth, int loopType, String filePath, UploadListener listener){

        String existingBucketName = "hivecomposeaudio";

        TransferManager tm = new TransferManager(new EnvironmentVariableCredentialsProvider());
        System.out.println("About to Attempt Audio Upload");

        Upload upload = null;

        if(loopType == QueueAudioTransfer.LOOP_TYPE_SOUND_FONT){
            upload =tm.upload(existingBucketName, softSynth.getSynthName(), new File(filePath));
        }else{
            upload =tm.upload(existingBucketName, loop.getFileName(), new File(filePath));
        }

        System.out.println("loop type: " + QueueAudioTransfer.LOOP_TYPE_SOUND_FONT);

        try {
            // Or you can block and wait for the upload to finish
            upload.addProgressListener(new S3ProgressListener() {
                @Override
                public void onPersistableTransfer(PersistableTransfer persistableTransfer) {
                }

                @Override
                public void progressChanged(ProgressEvent progressEvent) {

                    if(progressEvent.getEventType() == ProgressEventType.TRANSFER_COMPLETED_EVENT){

                        System.out.print("%%%%% COMPLETE EVENT FIRED");

                        String fileName = "";
                        String audioPath = "";
                        if(loopType == QueueAudioTransfer.LOOP_TYPE_SOUND_FONT){
                            fileName = softSynth.getSynthName();
                            audioPath = configurationProvider.getS3Bucket() + softSynth.getSynthName();
                        }else{
                            fileName = loop.getFileName();
                            audioPath = configurationProvider.getS3Bucket() + loop.getFileName();
                        }

                        boolean queueExisits = false;
                        for(QueueAudioTransfer queueAudioTransfer : queueAudioTransferDal.findAll()){
                            if(queueAudioTransfer.getFilename().equalsIgnoreCase(fileName)){
                                queueExisits = true;
                            }
                        }

                        if(!queueExisits){
                            switch (loopType){
                                case QueueAudioTransfer.LOOP_TYPE_BEAT:
                                    addBeatLoopQueueItem(audioPath, loop);
                                    break;
                                case QueueAudioTransfer.LOOP_TYPE_HARMONIC:
                                    addHarmonicLoopQueueItem(audioPath,loop);
                                    break;
                                case QueueAudioTransfer.LOOP_TYPE_AMBIENT:
                                    addAmbientLoopQueueItem(audioPath,loop);
                                    break;
                                case QueueAudioTransfer.LOOP_TYPE_SOUND_FONT:
                                    addSoundFont(configurationProvider.getS3Bucket() + softSynth.getSynthName(), softSynth);
                                    break;
                                default:
                                        throw new RuntimeException("No Matching Loop Type Found!");
                            }
                        }else{
                            System.out.print("%%%%% QUEUE ITEM ALREADY EXITS %%%%%%");
                        }

                        tm.shutdownNow();

                        //CLEAN UP THE TEMP FILE AFTER UPLOAD COMPLETE ..
                        try {
                            Files.deleteIfExists(new File(AppConfigs.getTempUploadFullPath()).toPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        listener.setUploadComplete();
                    }else if(
                            progressEvent.getEventType() == ProgressEventType.CLIENT_REQUEST_RETRY_EVENT ||
                                    progressEvent.getEventType() == ProgressEventType.TRANSFER_FAILED_EVENT ||
                                    progressEvent.getEventType() == ProgressEventType.TRANSFER_CANCELED_EVENT
                            ){

                        System.out.print("%%%%% CLIENT_REQUEST_RETRY_EVENT ||||  TRANSFER_FAILED_EVENT |||| TRANSFER_CANCELED_EVENT, %%%%%%");
                    }
                }
            });

            upload.waitForCompletion();

        } catch (AmazonClientException amazonClientException) {
            System.out.print(amazonClientException.getStackTrace().toString());
            amazonClientException.printStackTrace();
        } catch (InterruptedException e) {
            System.out.print(e.getStackTrace().toString());
            e.printStackTrace();
        }
    }
}
