/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToFinished;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPermanentlyFailedMonitor.FirebasePermanentlyFailedMonitor;
import timber.log.Timber;

/**
 * Created on 1/23/2019
 * Project : Driver
 */
public class FirebaseUploadsPermanentlyFailedResponseHandler implements
    FirebasePermanentlyFailedMonitor.Update,
    MoveImageUploadTaskToFinished.Response {
    private static final String TAG = "FirebaseUploadsPermanentlyFailedResponseHandler";

    private DatabaseReference uploadTaskNodeRef;


    public FirebaseUploadsPermanentlyFailedResponseHandler(DatabaseReference uploadTaskNodeRef){
        Timber.tag(TAG).d("FirebaseUploadsPermanentlyFailedResponseHandler");
        this.uploadTaskNodeRef = uploadTaskNodeRef;
        Timber.tag(TAG).d("   uploadTaskNodeRef    = " + uploadTaskNodeRef.toString());
    }

    //// FirebasePermanentlyFailedMonitor.Update interface
    public void permanentlyFailedUploadImageFile(String ownerGuid) {
        Timber.tag(TAG).d("permanentlyFailedUploadImageFile, ownerGuid -> %s", ownerGuid);
        //// 1. We just want to move the task to finished
        //// 2. This state is here so in-case we want to do some additional processing before we move it to finished we can
        new MoveImageUploadTaskToFinished().moveImageUploadTaskToFinished(uploadTaskNodeRef, ownerGuid, this);
    }

    //// MoveImageUploadTaskToFinished.Response
    public void moveImageUploadTaskFinishedComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToFinishedComplete");
    }

}
