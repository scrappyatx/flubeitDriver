/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudImageStorage;

import android.graphics.Bitmap;
import android.net.Uri;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import it.flube.driver.modelLayer.interfaces.CloudImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 9/16/2017
 * Project : Driver
 */

public class CloudImageStorageFirebaseWrapper implements
        CloudImageStorageInterface {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom
    private static class Loader {
        static volatile CloudImageStorageFirebaseWrapper instance = new CloudImageStorageFirebaseWrapper();
    }

    private CloudImageStorageFirebaseWrapper() {
        storage = FirebaseStorage.getInstance();
    }

    public static CloudImageStorageFirebaseWrapper getInstance() {
        return CloudImageStorageFirebaseWrapper.Loader.instance;
    }

    private static final String TAG = "CloudImageStorageFirebaseWrapper";
    private FirebaseStorage storage;

    public void getCloudStorageFileName(String batchGuid, String serviceOrderGuid, String orderStepGuid, String photoRequestGuid, GetFileNameResponse response){
        Timber.tag(TAG).d("getCloudStorageFileName...");
        new CloudImageStorageFilenameGet().getFileNameRequest(storage, batchGuid, serviceOrderGuid, orderStepGuid, photoRequestGuid, response);
    }

    public void saveImageStartRequest(String deviceStorageAbsoluteFileName, String cloudStorageFileName, SaveStartResponse response){

        Timber.tag(TAG).d("saveImageStartRequest...");
        new CloudImageStorageSaveImageStart().saveImageStartRequest(storage, deviceStorageAbsoluteFileName, cloudStorageFileName, response);
    }

    public void saveImageResumeRequest(String sessionUriString, String deviceStorageAbsoluteFileName, String cloudStorageFileName, SaveResumeResponse response){

        Timber.tag(TAG).d("saveImageResumeRequest...");
        new CloudImageStorageSaveImageResume().saveImageResumeRequest(storage, sessionUriString, deviceStorageAbsoluteFileName, cloudStorageFileName, response);
    }

}
