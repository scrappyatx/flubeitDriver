/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.uploadTasks;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import timber.log.Timber;

/**
 * Created on 2/22/2018
 * Project : Driver
 */

public class AddPhotoUploadTaskNotStarted implements
    SetNodeNotStarted.Response,
    SetNodeInProgress.Response,
    SetNodeFinished.Response,
    SetNodeFailed.Response {

    private static final String TAG = "AddPhotoUploadTaskNotStarted";

    CloudDatabaseInterface.AddPhotoUploadTaskResponse response;

    private DatabaseReference batchDataNodeRef;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private String photoRequestGuid;
    private String deviceGuid;
    private String deviceAbsoluteFileName;
    private String cloudStorageFileName;

    public AddPhotoUploadTaskNotStarted(){
        Timber.tag(TAG).d("created...");
    }

    public void addPhotoUploadTaskToNotStartedRequest(DatabaseReference batchDataNodeRef,
                                                      String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                                      String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                                                      CloudDatabaseInterface.AddPhotoUploadTaskResponse response){

        this.batchDataNodeRef = batchDataNodeRef;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.orderStepGuid = orderStepGuid;
        this.photoRequestGuid = photoRequestGuid;
        this.deviceGuid = deviceGuid;
        this.deviceAbsoluteFileName = deviceAbsoluteFileName;
        this.cloudStorageFileName = cloudStorageFileName;
        this.response = response;

        //Step 1 -> set node not started for this photo request
        new SetNodeNotStarted().addNodeRequest(batchDataNodeRef, batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid, deviceAbsoluteFileName, cloudStorageFileName, this);
    }

    public void setNodeNotStartedComplete(){
        //Step 2 -> set node in progress to null
        new SetNodeInProgress().removeNodeRequest(batchDataNodeRef, batchGuid, deviceGuid, photoRequestGuid, this);
    }

    public void setNodeInProgressComplete(){
        //Step 3 -> set node finished to null
        new SetNodeFinished().removeNodeRequest(batchDataNodeRef, batchGuid, deviceGuid, photoRequestGuid, this);
    }

    public void setNodeFinishedComplete(){
        //Step 4 -> set node failed to null
        new SetNodeFailed().removeNodeRequest(batchDataNodeRef, batchGuid, deviceGuid, photoRequestGuid, this);
    }

    public void setNodeFailedComplete(Integer attemptCount){
        // we're done, return
        response.cloudDatabaseAddPhotoUploadTaskComplete();
    }


}
