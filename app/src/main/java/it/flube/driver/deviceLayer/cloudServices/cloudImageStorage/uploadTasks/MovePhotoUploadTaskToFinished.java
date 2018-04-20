/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 2/22/2018
 * Project : Driver
 */

public class MovePhotoUploadTaskToFinished implements
        SetNodeNotStarted.Response,
        SetNodeInProgress.Response,
        SetNodeFinished.Response,
        SetNodeFailed.Response {

    private static final String TAG = "MovePhotoUploadTaskToFinished";

    CloudImageStorageInterface.MovePhotoUploadTaskFinishedResponse response;

    private DatabaseReference batchDataNodeRef;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private String photoRequestGuid;
    private String deviceGuid;
    private String deviceAbsoluteFileName;
    private String cloudStorageFileName;

    public MovePhotoUploadTaskToFinished(){
        Timber.tag(TAG).d("created...");
    }

    public void movePhotoUploadTaskToFinished(DatabaseReference batchDataNodeRef,
                                              String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                              String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                              CloudImageStorageInterface.MovePhotoUploadTaskFinishedResponse response) {


        this.batchDataNodeRef = batchDataNodeRef;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.orderStepGuid = orderStepGuid;
        this.photoRequestGuid = photoRequestGuid;
        this.deviceGuid = deviceGuid;
        this.deviceAbsoluteFileName = deviceAbsoluteFileName;
        this.cloudStorageFileName = cloudStorageFileName;
        this.response = response;

        //Step 1 -> set node finished for this photo request
        new SetNodeFinished().addNodeRequest(batchDataNodeRef, batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid,
                deviceAbsoluteFileName, cloudStorageFileName, this);
    }

    public void setNodeFinishedComplete(){
        //Step 2 -> set node not started to null
        new SetNodeNotStarted().removeNodeRequest(batchDataNodeRef, batchGuid, deviceGuid, photoRequestGuid, this);
    }

    public void setNodeNotStartedComplete(){
        //Step 3 -> set node in progress to null
        new SetNodeInProgress().removeNodeRequest(batchDataNodeRef, batchGuid, deviceGuid, photoRequestGuid, this);
    }

    public void setNodeInProgressComplete(){
        //Step 4 -> set node failed to null
        new SetNodeFailed().removeNodeRequest(batchDataNodeRef, batchGuid, deviceGuid, photoRequestGuid, this);
    }

    public void setNodeFailedComplete(Integer attemptCount){
        // we're done, return
        response.cloudImageMovePhotoUploadTaskFinishedComplete();
    }

}
