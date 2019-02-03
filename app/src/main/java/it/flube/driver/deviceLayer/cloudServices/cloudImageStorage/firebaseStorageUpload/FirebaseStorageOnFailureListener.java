/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.firebaseStorageUpload;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToFailed;
import timber.log.Timber;

/**
 * Created on 1/21/2019
 * Project : Driver
 */
public class FirebaseStorageOnFailureListener implements
        OnFailureListener,
        MoveImageUploadTaskToFailed.Response {
    public static final String TAG = "FirebaseStorageOnFailureListener";

    private DatabaseReference uploadTaskNodeRef;
    private FileToUploadInfo fileToUploadInfo;

    public FirebaseStorageOnFailureListener(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo){
        this.fileToUploadInfo = fileToUploadInfo;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
    }

    public void onFailure(@NonNull Exception exception) {
        // Handle unsuccessful uploads
        Timber.tag(TAG).w("onFailure ownerGuid (%s) exception -> %s", fileToUploadInfo.getOwnerGuid(), exception.getLocalizedMessage());

        // move owner guid to failed
        new MoveImageUploadTaskToFailed().moveImageUploadTaskToFailed(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
    }

    public void moveImageUploadTaskToFailedComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToFailedComplete");
    }
}
