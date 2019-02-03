/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_ATTEMPT_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FILE_INFO_NODE;

/**
 * Created on 1/22/2019
 * Project : Driver
 */
public class SetNodeFileInfoAttempts implements
        OnCompleteListener<Void> {

    private static final String TAG = "SetNodeFileInfoAttempts";

    private Response response;

    public void updateNodeRequest(DatabaseReference uploadTaskNodeRef, FileToUploadInfo fileToUploadInfo, Response response){
        this.response = response;

        Timber.tag(TAG).d("   ...uploadTaskNodeRef = " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("   ...ownerGuid         = " + fileToUploadInfo.getOwnerGuid());
        Timber.tag(TAG).d("   ...attempts          = " + fileToUploadInfo.getAttempts());

        HashMap<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(RTD_FILE_INFO_ATTEMPT_NODE, fileToUploadInfo.getAttempts());

        uploadTaskNodeRef.child(RTD_FILE_INFO_NODE).child(fileToUploadInfo.getOwnerGuid()).updateChildren(childUpdates).addOnCompleteListener(this);
    }

    //// OnCompleteListener interface
    public void onComplete(@NonNull Task<Void> task) {
        Timber.tag(TAG).d("   onComplete...");

        if (task.isSuccessful()) {
            Timber.tag(TAG).d("      ...SUCCESS");
        } else {
            Timber.tag(TAG).w("      ...FAILURE");
            try {
                throw task.getException();
            }
            catch (Exception e) {
                Timber.tag(TAG).e(e);
            }
        }
        Timber.tag(TAG).d("COMPLETE");
        response.setNodeFileInfoAttemptsComplete();
    }

    public interface Response {
        void setNodeFileInfoAttemptsComplete();
    }
}
