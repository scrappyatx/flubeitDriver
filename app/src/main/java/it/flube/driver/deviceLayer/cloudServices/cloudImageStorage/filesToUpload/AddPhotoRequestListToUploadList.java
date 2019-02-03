/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudImageStorage.filesToUpload;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import it.flube.driver.modelLayer.entities.DeviceInfo;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import it.flube.libbatchdata.entities.PhotoRequest;
import timber.log.Timber;

/**
 * Created on 1/24/2019
 * Project : Driver
 */
public class AddPhotoRequestListToUploadList implements
    CloudImageStorageInterface.AddImageToUploadListResponse {
    private static final String TAG = "AddPhotoRequestListToUploadList";

    private Integer responsesReceived;
    private Integer responsesExpected;
    private CloudImageStorageInterface.AddPhotoRequestListToUploadListResponse response;

    public void addPhotoRequestListToUploadList(FirebaseDatabase driverDb, StorageReference storageRef,
                                                Driver driver, DeviceInfo deviceInfo, ArrayList<PhotoRequest> photoList, CloudImageStorageInterface.AddPhotoRequestListToUploadListResponse response){
        Timber.tag(TAG).d("addPhotoRequestListToUploadList");

        this.response = response;
        responsesReceived = 0;
        responsesExpected = photoList.size();
        Timber.tag(TAG).d("   %s photoRequests to add to upload list", responsesExpected);

        for (PhotoRequest photoRequest : photoList) {
            new AddDeviceImageToUploadList().addPhotoRequestToList(driverDb, storageRef,
                    driver, deviceInfo, photoRequest, this);
        }
    }

    public void cloudImageStorageAddImageToUploadListComplete(String ownerGuid){
        responsesReceived = responsesReceived + 1;

        Timber.tag(TAG).d("cloudImageStorageAddImageToUploadListComplete, response %s, ownerGuid -> %s", responsesReceived, ownerGuid);

        if (responsesReceived.equals(responsesExpected)){
            Timber.tag(TAG).d("   finished adding images to upload list");
            response.cloudImageStorageAddPhotoRequestListToUploadListComplete();
        }
    }
}
