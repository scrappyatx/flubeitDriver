/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToDelete;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.fileInfoNode.FileToUploadInfo;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageConstants.RTD_DEVICE_FILES_TO_DELETE_PATH;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class AddDeviceFileToFilesToDelete implements
        OnCompleteListener<Void> {
    public static final String TAG = "AddDeviceFileToFilesToDelete";

    private Response response;

    public void addDeviceFileToFilesToDelete(FirebaseDatabase driverDb, FileToUploadInfo fileToUploadInfo, Response response){
        Timber.tag(TAG).d("addDeviceFileToFilesToDelete");
        this.response = response;
        String filesToDeleteNode = String.format(RTD_DEVICE_FILES_TO_DELETE_PATH,fileToUploadInfo.getClientId(), fileToUploadInfo.getDeviceGuid());
        Timber.tag(TAG).d("    filesToDeleteNode      -> " + filesToDeleteNode);
        Timber.tag(TAG).d("    deletionKey            -> " + fileToUploadInfo.getDeletionKey());
        Timber.tag(TAG).d("    deviceAbsoluteFileName -> " + fileToUploadInfo.getDeviceAbsoluteFileName());

        driverDb.getReference(filesToDeleteNode).child(fileToUploadInfo.getDeletionKey()).setValue(fileToUploadInfo.getDeviceAbsoluteFileName()).addOnCompleteListener(this);
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
        response.addDeviceFileToFilesToDeleteComplete();
    }

    public interface Response {
        void addDeviceFileToFilesToDeleteComplete();
    }

}
