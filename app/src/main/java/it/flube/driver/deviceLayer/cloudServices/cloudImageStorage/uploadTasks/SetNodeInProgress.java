/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.BATCH_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.CLOUD_FILE_NAME_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.DEVICE_FILE_NAME_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.DEVICE_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.ORDER_STEP_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.PHOTO_REQUEST_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.PROGRESS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.SERVICE_ORDER_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.SESSION_URI_STRING_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.TIMESTAMP_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadTasks.Constants.UPLOAD_TASKS_IN_PROGRESS_NODE;

/**
 * Created on 2/23/2018
 * Project : Driver
 */

public class SetNodeInProgress implements
        OnCompleteListener<Void> {

    public static final String TAG = "SetNodeInProgress";

    private Response response;

    public SetNodeInProgress(){
        Timber.tag(TAG).d("created...");
    }

    public void addNodeRequest(DatabaseReference batchDataNodeRef,
                               String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                               String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                               String sessionUriString, Double progress,
                               Response response){

        Timber.tag(TAG).d("addNodeRequest START...");

        this.response = response;
        Timber.tag(TAG).d("   ...batchDataNodeRef = " + batchDataNodeRef.toString());

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(BATCH_GUID_NODE, batchGuid);
        data.put(SERVICE_ORDER_GUID_NODE, serviceOrderGuid);
        data.put(ORDER_STEP_GUID_NODE, orderStepGuid);
        data.put(PHOTO_REQUEST_GUID_NODE, photoRequestGuid);
        data.put(DEVICE_GUID_NODE, deviceGuid);
        data.put(DEVICE_FILE_NAME_NODE, deviceAbsoluteFileName);
        data.put(CLOUD_FILE_NAME_NODE, cloudStorageFileName);
        data.put(SESSION_URI_STRING_NODE, sessionUriString);
        data.put(PROGRESS_NODE, progress);
        data.put(TIMESTAMP_NODE, ServerValue.TIMESTAMP);

        Timber.tag(TAG).d("   ...setting node data");
        batchDataNodeRef.child(batchGuid).child(UPLOAD_TASKS_IN_PROGRESS_NODE).child(deviceGuid).child(photoRequestGuid).setValue(data).addOnCompleteListener(this);

    }

    public void removeNodeRequest(DatabaseReference batchDataNodeRef, String batchGuid, String deviceGuid, String photoRequestGuid,
                                  Response response){

        Timber.tag(TAG).d("removeNodeRequest START...");
        this.response = response;
        Timber.tag(TAG).d("   ...removing node data");
        batchDataNodeRef.child(batchGuid).child(UPLOAD_TASKS_IN_PROGRESS_NODE).child(deviceGuid).child(photoRequestGuid).setValue(null).addOnCompleteListener(this);
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
        response.setNodeInProgressComplete();
    }

    interface Response {
        void setNodeInProgressComplete();
    }

}