/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsResponseHandlers;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsCanceledMonitor.FirebaseUploadsCanceledMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsFailedMonitor.FirebaseUploadsFailedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsNotStartedMonitor.FirebaseUploadsNotStartedMonitor;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadsPausedMonitor.FirebaseUploadsPausedMonitor;
import timber.log.Timber;

/**
 * Created on 1/20/2019
 * Project : Driver
 */
public class FirebaseUploadsFailureOrPausedResponseHandler implements
        FirebaseUploadsNotStartedMonitor.Update,
        FirebaseUploadsPausedMonitor.Update,
        FirebaseUploadsFailedMonitor.Update,
        FirebaseUploadsCanceledMonitor.Update,
        ResponseActionUploadStart.Response {

    private static final String TAG = "FirebaseUploadsFailureOrPausedResponseHandler";

    private DatabaseReference uploadTaskNodeRef;
    private FirebaseDatabase driverDb;

    public FirebaseUploadsFailureOrPausedResponseHandler(FirebaseDatabase driverDb, DatabaseReference uploadTaskNodeRef){
        Timber.tag(TAG).d("FirebaseUploadsFailureOrPausedResponseHandler");
        this.uploadTaskNodeRef = uploadTaskNodeRef;
        this.driverDb = driverDb;

        Timber.tag(TAG).d("   uploadTaskNodeRef    = " + uploadTaskNodeRef.toString());
    }

    ////
    ////    FirebaseUploadsNotStartedMonitor.Update interface
    ////
    public void uploadImageFile(String ownerGuid){
        Timber.tag(TAG).d("uploadImageFile");
        Timber.tag(TAG).d("   ownerGuid            = " + ownerGuid);

        new ResponseActionUploadStart().startUploadRequest(driverDb, uploadTaskNodeRef, ownerGuid, this);
    }

    ////
    ////  FirebaseUploadsPausedMonitor.Update interface
    ///
    public void resumeUploadImageFile(String ownerGuid){
        Timber.tag(TAG).d("resumeUploadImageFile");
        Timber.tag(TAG).d("   ownerGuid            = " + ownerGuid);

        new ResponseActionUploadStart().startUploadRequest(driverDb, uploadTaskNodeRef, ownerGuid, this);
    }

    /// FirebaseUploadsFailedMonitor.Update interface
    public void failedUploadImageFile(String ownerGuid){
        Timber.tag(TAG).d("failedUploadImageFile");
        Timber.tag(TAG).d("   ownerGuid            = " + ownerGuid);

        new ResponseActionUploadStart().startUploadRequest(driverDb, uploadTaskNodeRef, ownerGuid, this);
    }

    /// FirebaseUploadsCanceledMonitor.Update interface
    public void canceledUploadImageFile(String ownerGuid){
        Timber.tag(TAG).d("canceledUploadImageFile");
        Timber.tag(TAG).d("    ownerGuid = " + ownerGuid);

        new ResponseActionUploadStart().startUploadRequest(driverDb, uploadTaskNodeRef, ownerGuid, this);
    }

    public void startUploadComplete(String ownerGuid){
        Timber.tag(TAG).d("startUploadComplete (%s)", ownerGuid);
    }

}
