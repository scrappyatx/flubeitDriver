/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers;

import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToFinished;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPermanentlyFailedMonitor.FirebasePermanentlyFailedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsSuccessMonitor.FirebaseUploadsSuccessMonitor;
import timber.log.Timber;

/**
 * Created on 1/25/2019
 * Project : Driver
 */
public class FirebaseUploadsSuccessResponseHandler implements
        FirebaseUploadsSuccessMonitor.Update,
        MoveImageUploadTaskToFinished.Response {
    private static final String TAG = "FirebaseUploadsPermanentlyFailedResponseHandler";

    private DatabaseReference uploadTaskNodeRef;


    public FirebaseUploadsSuccessResponseHandler(DatabaseReference uploadTaskNodeRef){
        Timber.tag(TAG).d("FirebaseUploadsPermanentlyFailedResponseHandler");
        this.uploadTaskNodeRef = uploadTaskNodeRef;
        Timber.tag(TAG).d("   uploadTaskNodeRef    = " + uploadTaskNodeRef.toString());
    }

    //// FirebaseUploadsSuccessMonitor.Update interface
    public void successUploadImageFile(String ownerGuid) {
        Timber.tag(TAG).d("successUploadImageFile, ownerGuid -> %s", ownerGuid);
        //// 1. We just want to move the task to finished
        //// 2. This state is here so in-case we want to do some additional processing before we move it to finished we can
        new MoveImageUploadTaskToFinished().moveImageUploadTaskToFinished(uploadTaskNodeRef, ownerGuid, this);
    }

    //// MoveImageUploadTaskToFinished.Response
    public void moveImageUploadTaskFinishedComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToFinishedComplete");
    }

}

