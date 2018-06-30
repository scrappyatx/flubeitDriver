/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataUpdate;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_STEPS_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PHOTO_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PHOTO_REQUEST_HAS_DEVICE_FILE_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.PHOTO_REQUEST_LIST_NODE;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.ATTEMPT_COUNT_KEY;
import static it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.PhotoStepConstants.STATUS_KEY;

/**
 * Created on 5/6/2018
 * Project : Driver
 */
public class FirebasePhotoRequestDeviceAbsoluteFilename implements OnCompleteListener<Void> {
    private static final String TAG = "FirebasePhotoRequestDeviceAbsoluteFilename";

    private CloudActiveBatchInterface.PhotoRequestDeviceAbsoluteFileNameResponse response;

    public void updatePhotoRequestDeviceAbsoluteFilenameRequest(DatabaseReference activeBatchRef, PhotoRequest photoRequest, String absoluteFileName, Boolean hasFile,
                                                                CloudActiveBatchInterface.PhotoRequestDeviceAbsoluteFileNameResponse response){

        Timber.tag(TAG).d("activeBatchRef = " + activeBatchRef.toString());
        this.response = response;

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put(PHOTO_REQUEST_HAS_DEVICE_FILE_NODE, hasFile);
        data.put(PHOTO_REQUEST_DEVICE_ABSOLUTE_FILENAME_NODE, absoluteFileName);
        data.put(ATTEMPT_COUNT_KEY, photoRequest.getAttemptCount()+1);

        if (hasFile) {
            data.put(STATUS_KEY, PhotoRequest.PhotoStatus.PHOTO_SUCCESS);
        } else {
            data.put(STATUS_KEY, PhotoRequest.PhotoStatus.FAILED_ATTEMPTS);
        }

        activeBatchRef.child(photoRequest.getBatchGuid()).child(BATCH_DATA_STEPS_NODE).child(photoRequest.getStepGuid())
                .child(PHOTO_REQUEST_LIST_NODE).child(photoRequest.getGuid())
                .updateChildren(data).addOnCompleteListener(this);


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
        response.cloudActiveBatchUpdatePhotoRequestDeviceAbsoluteFilenameComplete();
    }

}
