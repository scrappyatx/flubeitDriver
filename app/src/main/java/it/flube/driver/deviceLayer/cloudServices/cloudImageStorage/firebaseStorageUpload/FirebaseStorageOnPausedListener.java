/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.firebaseStorageUpload;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.SetNodeFileInfoProgress;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks.MoveImageUploadTaskToPaused;
import timber.log.Timber;

/**
 * Created on 1/21/2019
 * Project : Driver
 */
public class FirebaseStorageOnPausedListener implements
        OnPausedListener<UploadTask.TaskSnapshot>,
        SetNodeFileInfoProgress.Response,
        MoveImageUploadTaskToPaused.Response {

    public static final String TAG = "FirebaseStorageOnPausedListener";


    private DatabaseReference uploadTaskNodeRef;
    private FileToUploadInfo fileToUploadInfo;

    public FirebaseStorageOnPausedListener(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo){
        this.fileToUploadInfo = fileToUploadInfo;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
    }

    public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
        Timber.tag(TAG).d("onPaused ownerGuid -> " + fileToUploadInfo.getOwnerGuid());

        try {
            // calculate percent progress
            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
            Timber.tag(TAG).d("   ...progress -> " + Double.toString(progress));

            // save session uri & progress to fileToUploadInfo
            fileToUploadInfo.setSessionUriString(taskSnapshot.getUploadSessionUri().toString());
            fileToUploadInfo.setProgress(new DecimalFormat("##.#%").format(progress));

            //update the fileInfoNode with the progress information
            Timber.tag(TAG).d("   ...updating progress in fileInfoNode");
            new SetNodeFileInfoProgress().updateNodeRequest(uploadTaskNodeRef, fileToUploadInfo, this);

        } catch (Exception e){
            Timber.tag(TAG).e(e);
            Timber.tag(TAG).w("something went wrong trying to save upload progress -> " + e.getLocalizedMessage());
        }
    }

    public void setNodeFileInfoProgressComplete(){
        Timber.tag(TAG).d("setNodeFileInfoProgressComplete");

        //move the node to paused
        new MoveImageUploadTaskToPaused().moveImageUploadTaskToPaused(uploadTaskNodeRef, fileToUploadInfo.getOwnerGuid(), this);
    }

    public void moveImageUploadTaskToPausedComplete(){
        Timber.tag(TAG).d("moveImageUploadTaskToPausedComplete");

    }
}
