/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudDatabase.uploadTasks;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.ATTEMPT_COUNT_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.BATCH_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.CLOUD_FILE_NAME_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.DEVICE_FILE_NAME_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.DEVICE_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.ORDER_STEP_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.PHOTO_REQUEST_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.PROGRESS_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.SERVICE_ORDER_GUID_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.SESSION_URI_STRING_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.TIMESTAMP_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.UPLOAD_TASKS_FAILED_NODE;
import static it.flube.driver.deviceLayer.cloudDatabase.uploadTasks.Constants.UPLOAD_TASKS_IN_PROGRESS_NODE;

/**
 * Created on 2/23/2018
 * Project : Driver
 */

public class SetNodeFailed implements
        OnCompleteListener<Void>,
        GetNodeFailedAttemptCount.Response {

    public static final String TAG = "SetNodeFailed";

    private Response response;

    private DatabaseReference batchDataNodeRef;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private String photoRequestGuid;
    private String deviceGuid;
    private String deviceAbsoluteFileName;
    private String cloudStorageFileName;

    private Integer attemptCount;

    public SetNodeFailed(){
        Timber.tag(TAG).d("created...");
        attemptCount = 0;
    }

    public void addNodeRequest(DatabaseReference batchDataNodeRef,
                               String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                               String deviceGuid, String deviceAbsoluteFileName, String cloudStorageFileName,
                               Response response){

        Timber.tag(TAG).d("addNodeRequest START...");

        this.response = response;
        this.batchDataNodeRef = batchDataNodeRef;
        Timber.tag(TAG).d("   ...batchDataNodeRef = " + batchDataNodeRef.toString());


        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.orderStepGuid = orderStepGuid;
        this.photoRequestGuid = photoRequestGuid;
        this.deviceGuid = deviceGuid;
        this.deviceAbsoluteFileName = deviceAbsoluteFileName;
        this.cloudStorageFileName = cloudStorageFileName;

        new GetNodeFailedAttemptCount().getAttemptCountRequest(batchDataNodeRef, batchGuid, photoRequestGuid, this);

        Timber.tag(TAG).d("   ...getting attempt count");
    }

    public void getAttemptCountResponse(Integer attemptCount){
        Timber.tag(TAG).d("   ...getAttemptCountResponse --> attemptCount = " + attemptCount);
        this.attemptCount = attemptCount + 1;

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(BATCH_GUID_NODE, batchGuid);
        data.put(SERVICE_ORDER_GUID_NODE, serviceOrderGuid);
        data.put(ORDER_STEP_GUID_NODE, orderStepGuid);
        data.put(PHOTO_REQUEST_GUID_NODE, photoRequestGuid);
        data.put(DEVICE_GUID_NODE, deviceGuid);
        data.put(DEVICE_FILE_NAME_NODE, deviceAbsoluteFileName);
        data.put(CLOUD_FILE_NAME_NODE, cloudStorageFileName);
        data.put(ATTEMPT_COUNT_NODE, this.attemptCount);
        data.put(TIMESTAMP_NODE, ServerValue.TIMESTAMP);

        Timber.tag(TAG).d("   ...setting node data");
        batchDataNodeRef.child(batchGuid).child(UPLOAD_TASKS_FAILED_NODE).child(deviceGuid).child(photoRequestGuid).setValue(data).addOnCompleteListener(this);
    }

    public void removeNodeRequest(DatabaseReference batchDataNodeRef, String batchGuid, String deviceGuid, String photoRequestGuid,
                                  Response response){

        Timber.tag(TAG).d("removeNodeRequest START...");
        this.response = response;
        Timber.tag(TAG).d("   ...removing node data");
        batchDataNodeRef.child(batchGuid).child(UPLOAD_TASKS_FAILED_NODE).child(deviceGuid).child(photoRequestGuid).setValue(null).addOnCompleteListener(this);
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
        response.setNodeFailedComplete(attemptCount);
    }

    interface Response {
        void setNodeFailedComplete(Integer attemptCount);
    }

}
