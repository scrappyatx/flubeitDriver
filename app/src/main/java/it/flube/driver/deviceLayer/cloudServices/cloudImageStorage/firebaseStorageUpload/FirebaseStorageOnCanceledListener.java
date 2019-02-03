/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.firebaseStorageUpload;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DatabaseReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToCanceled;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToFailed;
import timber.log.Timber;

/**
 * Created on 1/28/2019
 * Project : Driver
 */
public class FirebaseStorageOnCanceledListener implements
        OnCanceledListener,
        MoveImageUploadTaskToCanceled.Response {
    public static final String TAG = "FirebaseStorageOnCanceledListener";

    private DatabaseReference uploadTaskNodeRef;
    private FileToUploadInfo fileToUploadInfo;

    public FirebaseStorageOnCanceledListener(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo){
        this.fileToUploadInfo = fileToUploadInfo;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
    }

    public void onCanceled() {
        // Handle unsuccessful uploads
        Timber.tag(TAG).w("onCanceled ownerGuid (%s)", fileToUploadInfo.getOwnerGuid());

        // move owner guid to failed
        new MoveImageUploadTaskToCanceled().moveImageUploadTaskToCanceled(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
    }

    public void moveImageUploadTaskToCanceledComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToFailedComplete");
    }
}
