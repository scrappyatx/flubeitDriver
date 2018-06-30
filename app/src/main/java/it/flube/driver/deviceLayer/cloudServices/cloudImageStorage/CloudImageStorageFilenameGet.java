/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 2/22/2018
 * Project : Driver
 */

public class CloudImageStorageFilenameGet {
    private static final String TAG = "CloudImageStorageFilenameGet";

    private static final String CLOUD_FILE_NAME = "photo.jpg";

    public CloudImageStorageFilenameGet(){
        Timber.tag(TAG).d("created...");
    }

    public void getFileNameRequest(StorageReference storageRef,
                                   String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                                   GetFileNameResponse response){

        Timber.tag(TAG).d("getFileNameRequest START...");
        Timber.tag(TAG).d("   ...storageRef                       -> " + storageRef.toString());
        Timber.tag(TAG).d("   ...batchGuid                     -> " + batchGuid);
        Timber.tag(TAG).d("   ...serviceOrderGuid              -> " + serviceOrderGuid);
        Timber.tag(TAG).d("   ...orderStepGuid                 -> " + orderStepGuid);
        Timber.tag(TAG).d("   ...photoRequestGuid              -> " + photoRequestGuid);

        //determine cloud storage file name
        StorageReference fileRef = storageRef.child(batchGuid).child(serviceOrderGuid).child(orderStepGuid).child(photoRequestGuid).child(CLOUD_FILE_NAME);
        String cloudStorageFileName = fileRef.toString();

        Timber.tag(TAG).d("   ...cloudStorageFileName          -> " + cloudStorageFileName);
        response.fileNameGetComplete(cloudStorageFileName);

        Timber.tag(TAG).d("...getFileNameRequest COMPLETE");
    }

    public interface GetFileNameResponse {
        void fileNameGetComplete(String cloudStorageFileName);
    }
}
