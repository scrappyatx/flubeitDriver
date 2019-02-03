/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.firebaseStorageUpload;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.text.DecimalFormat;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.SetNodeFileInfoProgress;
import timber.log.Timber;

/**
 * Created on 1/21/2019
 * Project : Driver
 */
public class FirebaseStorageOnProgressListener implements
        OnProgressListener<UploadTask.TaskSnapshot>,
        SetNodeFileInfoProgress.Response {

    public static final String TAG = "FirebaseStorageOnProgressListener";

    private DatabaseReference uploadTaskNodeRef;
    private FileToUploadInfo fileToUploadInfo;

    public FirebaseStorageOnProgressListener(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo){
        this.fileToUploadInfo = fileToUploadInfo;
        this.uploadTaskNodeRef = uploadTaskNodeRef;
    }

    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
        Timber.tag(TAG).d("onProgress ownerGuid -> " + fileToUploadInfo.getOwnerGuid());

        try {
            // calculate percent progress
            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
            Timber.tag(TAG).d("   ...progress -> " + Double.toString(progress));

            // save session uri & progress to fileToUploadInfo
            if (taskSnapshot.getUploadSessionUri() != null) {
                fileToUploadInfo.setSessionUriString(taskSnapshot.getUploadSessionUri().toString());
                Timber.tag(TAG).d("   ...sessionUri -> " + taskSnapshot.getUploadSessionUri().toString());
            } else {
                fileToUploadInfo.setSessionUriString(null);
                Timber.tag(TAG).d("   ...sessionUri -> NULL");
            }
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
    }
}
