/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudTextDetection;

import android.graphics.Bitmap;

import it.flube.driver.deviceLayer.cloudServices.cloudImageDetection.FirebaseCloudImageLabelDetection;
import it.flube.driver.modelLayer.interfaces.CloudTextDetectionInterface;
import it.flube.driver.modelLayer.interfaces.DeviceImageStorageInterface;
import timber.log.Timber;

/**
 * Created on 3/4/2019
 * Project : Driver
 */
public class FirebaseCloudTextStart implements
        DeviceImageStorageInterface.GetResponse {
    private static final String TAG = "FirebaseCloudTextStart";

    private CloudTextDetectionInterface.CloudDetectImageTextResponse response;

    public void textDetectionStart(DeviceImageStorageInterface deviceImageStorage, String imageAbsoluteFilename, CloudTextDetectionInterface.CloudDetectImageTextResponse response){
        Timber.tag(TAG).d("textDetectionStart");
        Timber.tag(TAG).d("   imageAbsoluteFilename -> %s",imageAbsoluteFilename);
        this.response = response;
        //load the image file into a bitmap
        deviceImageStorage.getImageRequest(imageAbsoluteFilename, this);
    }


    //// response interface for deviceImageStorageGet
    public void deviceImageStorageGetSuccess(Bitmap bitmap){
        Timber.tag(TAG).d("deviceImageStorageGetSuccess");
        ///now we can do cloud image label detection on this bitmap
        new FirebaseCloudTextDetection().detectTextRequest(bitmap, response);
    }

    public void deviceImageStorageGetFailure(){
        Timber.tag(TAG).w("deviceImageStorageGetFailure");
        // we weren't able to read this file, something went wrong
        response.cloudDetectImageTextFailure();
        response = null;
    }


}