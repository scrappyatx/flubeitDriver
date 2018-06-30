/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.uploadList;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.CloudImageStorageFilenameGet;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 5/26/2018
 * Project : Driver
 */
public class AddDeviceImageToUploadList implements
    CloudImageStorageFilenameGet.GetFileNameResponse,
    AddPhotoUploadTaskNotStarted.AddPhotoUploadTaskNotStartedResponse {

    public static final String TAG = "AddDeviceImageToUploadList";

    private DatabaseReference batchDataNode;
    private String deviceStorgageAbsoluteFileName;
    private String deviceGuid;
    private String batchGuid;
    private String serviceOrderGuid;
    private String orderStepGuid;
    private String photoRequestGuid;
    private CloudImageStorageInterface.AddDeviceImageResponse response;

    public void addToList(DatabaseReference batchDataNode, StorageReference storageRef,
                          String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid,
                          String deviceStorgageAbsoluteFileName, String deviceGuid,
                          CloudImageStorageInterface.AddDeviceImageResponse response) {

        Timber.tag(TAG).d("addToList START...");
        Timber.tag(TAG).d("   batchDataNode                 -> " + batchDataNode.toString());
        Timber.tag(TAG).d("   storageRef                    -> " + storageRef.toString());
        Timber.tag(TAG).d("   deviceStorageAbsoluteFileName -> " + deviceStorgageAbsoluteFileName);
        Timber.tag(TAG).d("   deviceGuid                    -> " + deviceGuid);
        Timber.tag(TAG).d("   batchGuid                     -> " + batchGuid);
        Timber.tag(TAG).d("   serviceOrderGuid              -> " + serviceOrderGuid);
        Timber.tag(TAG).d("   orderStepGuid                 -> " + orderStepGuid);
        Timber.tag(TAG).d("   photoRequestGuid              -> " + photoRequestGuid);

        this.batchDataNode = batchDataNode;
        this.deviceStorgageAbsoluteFileName = deviceStorgageAbsoluteFileName;
        this.deviceGuid = deviceGuid;
        this.batchGuid = batchGuid;
        this.serviceOrderGuid = serviceOrderGuid;
        this.orderStepGuid = orderStepGuid;
        this.photoRequestGuid = photoRequestGuid;
        this.response = response;

        Timber.tag(TAG).d("   getting cloud image storage filename...");
        new CloudImageStorageFilenameGet().getFileNameRequest(storageRef, batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, this);
    }

    public void fileNameGetComplete(String cloudStorageFileName){
        Timber.tag(TAG).d("   fileNameGetComplete, cloudStorageFileName -> " + cloudStorageFileName);
        Timber.tag(TAG).d("   calling AddPhotoUploadTaskNotStarted...");
        new AddPhotoUploadTaskNotStarted().addPhotoUploadTaskToNotStartedRequest(batchDataNode, batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, deviceGuid, deviceStorgageAbsoluteFileName, cloudStorageFileName, this );
    }

    public void addPhotoUploadTaskComplete(){
        Timber.tag(TAG).d("      addPhotoUploadTaskComplete");
        response.cloudImageStorageAddDeviceImageComplete();
    }

}
