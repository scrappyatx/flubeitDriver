/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadManagement.uploadTasks;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_CANCELED_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FAILED_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_FINISHED_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_IN_PROGRESS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_NOT_STARTED_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_PAUSED_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_PERMANENTLY_FAILED_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_SUCCESS_NODE;

/**
 * Created on 1/22/2019
 * Project : Driver
 */
public class MoveImageUploadTaskToPaused implements
        OnCompleteListener<Void> {

    private static final String TAG = "MoveImageUploadTaskToPaused";

    private Response response;

    public MoveImageUploadTaskToPaused(){
        Timber.tag(TAG).d("created...");
    }

    public void moveImageUploadTaskToPaused(DatabaseReference uploadTaskNodeRef, String ownerGuid, Response response) {
        Timber.tag(TAG).d("moveImageUploadTaskToPaused...");

        this.response = response;

        Timber.tag(TAG).d("    uploadTaskNodeRef -> " + uploadTaskNodeRef.toString());
        Timber.tag(TAG).d("    ownerGuid         -> " + ownerGuid);

        this.response = response;


        //setup updates on children nodes
        HashMap<String, Object> childUpdates = new HashMap<String, Object>();
        childUpdates.put(String.format(RTD_NOT_STARTED_NODE, ownerGuid), null);          // notStarted
        childUpdates.put(String.format(RTD_IN_PROGRESS_NODE, ownerGuid), null);          // inProgress
        childUpdates.put(String.format(RTD_PAUSED_NODE, ownerGuid), true);               // paused
        childUpdates.put(String.format(RTD_FAILED_NODE, ownerGuid), null);               // failed
        childUpdates.put(String.format(RTD_CANCELED_NODE, ownerGuid), null);             // canceled
        childUpdates.put(String.format(RTD_SUCCESS_NODE, ownerGuid), null);              // success
        childUpdates.put(String.format(RTD_PERMANENTLY_FAILED_NODE, ownerGuid), null);   // failedPermanently
        childUpdates.put(String.format(RTD_FINISHED_NODE, ownerGuid), null);             // finished

        //update children
        uploadTaskNodeRef.updateChildren(childUpdates).addOnCompleteListener(this);
    }

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
        response.moveImageUploadTaskToPausedComplete();
    }

    public interface Response {
        void moveImageUploadTaskToPausedComplete();
    }

}
